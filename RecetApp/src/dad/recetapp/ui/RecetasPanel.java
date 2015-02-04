package dad.recetapp.ui;

import java.io.IOException;
import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentKeyListener;
import org.apache.pivot.wtk.FillPane;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.ListButtonSelectionListener;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.Spinner;
import org.apache.pivot.wtk.SpinnerSelectionListener;
import org.apache.pivot.wtk.SplitPane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TextInput;

import dad.recetapp.RecetappApplication;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.RecetaListItem;

public class RecetasPanel extends FillPane implements Bindable {

	@BXML
	private SplitPane splitPane;
	@BXML
	public static TableView tableView;
	public static org.apache.pivot.collections.List<RecetaListItem> variables;
	private org.apache.pivot.collections.List<RecetaListItem> lista;
	@BXML
	private static ListButton categoriasListButton;
	private static org.apache.pivot.collections.List<CategoriaItem> categoriasBD;
	@BXML
	private Button anadirRecetaButton;
	@BXML
	private Button eliminarRecetaButton;
	@BXML
	private Button editarRecetaButton;
	@BXML
	private TextInput filtrarNombre;
	@BXML
	private Spinner filtrarMinutos, filtrarSegundos;

	@SuppressWarnings({ "unchecked" })
	@Override
	public void initialize(Map<String, Object> namespace, URL location,Resources resources) {

		variables = new org.apache.pivot.collections.ArrayList<RecetaListItem>();
		
		filtrarDatosTabla();

		try {
			lista = convertirList(ServiceLocator.getRecetasService().listarRecetas());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		for (RecetaListItem l : lista) {
			variables.add(l);
		}
		tableView.setTableData(variables);
		try {
			recargarCategoriaListButton();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		anadirRecetaButton.getButtonPressListeners().add(new ButtonPressListener() {
					@Override
					public void buttonPressed(Button button) {
						onAnadirRecetaButtonActionPerformed();
					}
				});

		eliminarRecetaButton.getButtonPressListeners().add(new ButtonPressListener() {
					@Override
					public void buttonPressed(Button button) {
						onEliminarRecetaButtonActionPerformed();
					}
				});

		editarRecetaButton.getButtonPressListeners().add(new ButtonPressListener() {
					@Override
					public void buttonPressed(Button button) {
						onEditarRecetaButtonActionPerformed();
					}
				});
	}

	protected void onAnadirRecetaButtonActionPerformed() {
		NuevaRecetaWindow nuevaRecetaWindow = null;

		try {
			nuevaRecetaWindow = (NuevaRecetaWindow) RecetappApplication.loadWindow("dad/recetapp/ui/NuevaRecetaWindow.bxml");
			nuevaRecetaWindow.setVariables(variables);
			nuevaRecetaWindow.open(getDisplay());
			RecetappFrame.setNumReceta();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SerializationException e) {
			e.printStackTrace();
		}
		recargarTabla();
	}

	protected void onEditarRecetaButtonActionPerformed() {
		Sequence<?> seleccionados = tableView.getSelectedRows();
		if (seleccionados.getLength() == 0 || seleccionados.getLength() > 1) {
			Prompt error = new Prompt("Debe seleccionar una receta.");
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
				}
			});
		} else {
			EditarRecetaWindow editarRecetaWindow = null;
			try {
				editarRecetaWindow = (EditarRecetaWindow) RecetappApplication.loadWindow("dad/recetapp/ui/EditarRecetaWindow.bxml");
				editarRecetaWindow.setReceta((RecetaListItem)tableView.getSelectedRow());
				editarRecetaWindow.open(getDisplay());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SerializationException e) {
				e.printStackTrace();
			}
		}
		recargarTabla();
	}

	protected void onEliminarRecetaButtonActionPerformed() {
		Sequence<?> seleccionados = tableView.getSelectedRows();
		if (seleccionados.getLength() == 0) {
			Prompt error = new Prompt("Debe seleccionar una receta.");
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
				}
			});
		} else {
			StringBuffer mensaje = new StringBuffer();
			mensaje.append("¿Desea eliminar las siguientes recetas?\n\n");

			for (int i = 0; i < seleccionados.getLength(); i++) {
				RecetaListItem recetaSeleccionada = (RecetaListItem) seleccionados.get(i);
				mensaje.append(" - " + recetaSeleccionada.getNombre() + "\n");
			}

			Prompt confirmar = new Prompt(MessageType.WARNING, mensaje.toString(), new ArrayList<String>("Sí", "No"));
			confirmar.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
					if (confirmar.getResult() && confirmar.getSelectedOption().equals("Sí")) {
						for (int i = 0; i < seleccionados.getLength(); i++) {
							try {
								RecetaListItem recetaSeleccionada = (RecetaListItem) seleccionados.get(i);
								variables.remove(recetaSeleccionada);
								ServiceLocator.getRecetasService().eliminarReceta(recetaSeleccionada.getId());
								RecetappFrame.setNumReceta();
							} catch (ServiceException e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
		}
		recargarTabla();
	}

	@SuppressWarnings("unchecked")
	public static void recargarCategoriaListButton() throws ServiceException {
		CategoriaItem categoriaTitle = new CategoriaItem();
		categoriaTitle.setId(null);
		categoriaTitle.setDescripcion("<Todas>");
		categoriasBD = convertirList(ServiceLocator.getCategoriasService()
				.listarCategorias());
		categoriasBD.insert(categoriaTitle, 0);
		categoriasListButton.setListData(categoriasBD);
		categoriasListButton.setSelectedItem(categoriaTitle);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static org.apache.pivot.collections.List convertirList(
			java.util.List<?> listaUtil) {
		org.apache.pivot.collections.List listaApache = new org.apache.pivot.collections.ArrayList();
		for (int i = 0; i < listaUtil.size(); i++) {
			listaApache.add(listaUtil.get(i));
		}
		return listaApache;
	}

	// FILTRO
	protected void filtrarDatosTabla() {
		filtrarSegundos.getSpinnerSelectionListeners().add(
				new SpinnerSelectionListener() {

					@Override
					public void selectedItemChanged(Spinner arg0, Object arg1) {
						filtroTabla();
					}

					@Override
					public void selectedIndexChanged(Spinner arg0, int arg1) {
						filtroTabla();
					}
				});

		filtrarMinutos.getSpinnerSelectionListeners().add(
				new SpinnerSelectionListener() {
					@Override
					public void selectedItemChanged(Spinner arg0, Object arg1) {
						filtroTabla();
					}

					@Override
					public void selectedIndexChanged(Spinner arg0, int arg1) {
						filtroTabla();
					}
				});

		filtrarNombre.getComponentKeyListeners().add(
				new ComponentKeyListener.Adapter() {
					@Override
					public boolean keyTyped(Component arg0, char arg1) {
						try {
							filtroTabla();
						} catch (NullPointerException e) {
						}
						return false;
					}
				});

		categoriasListButton.getListButtonSelectionListeners().add(
				new ListButtonSelectionListener.Adapter() {
					@Override
					public void selectedItemChanged(ListButton listButton,
							Object previousSelectedItem) {
						filtroTabla();
					}
				});
	}

	@SuppressWarnings("unchecked")
	protected void filtroTabla() {

		CategoriaItem selectedItem = (CategoriaItem) categoriasListButton
				.getSelectedItem();
		Integer tiempoFinal = (filtrarMinutos.getSelectedIndex() * 60)
				+ filtrarSegundos.getSelectedIndex();
		if (tiempoFinal == 0) {
			tiempoFinal = null;
		}

		if (selectedItem != null) {
			try {
				lista = convertirList(ServiceLocator.getRecetasService()
						.buscarRecetas(filtrarNombre.getText(), tiempoFinal,
								selectedItem.getId()));
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		tableView.setTableData(lista);
	}
	
	protected void recargarTabla(){
		tableView.setTableData(variables);
	}

}
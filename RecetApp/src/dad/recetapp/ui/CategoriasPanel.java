package dad.recetapp.ui;

import java.net.URL;
import java.util.List;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TableViewRowListener;
import org.apache.pivot.wtk.TextInput;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;

public class CategoriasPanel extends TablePane implements Bindable {

	@BXML
	private TableView tableView;
	private org.apache.pivot.collections.List<CategoriaItem> variables;
	private List<CategoriaItem> lista;
	@BXML
	private Button anadirCategoriaButton;
	@BXML
	private Button eliminarCategoriaButton;
	@BXML
	private TextInput descripcionText;


	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		variables = new org.apache.pivot.collections.ArrayList<CategoriaItem>();

		try {
			lista = ServiceLocator.getCategoriasService().listarCategorias();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		for (CategoriaItem l : lista) {
			variables.add(l);
		}
		tableView.setTableData(variables);

		anadirCategoriaButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAnadirCategoriaButtonActionPerformed();
			}
		});

		eliminarCategoriaButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEliminarCategoriaButtonActionPerformed();
			}
		});

		tableView.getTableViewRowListeners().add(new TableViewRowListener.Adapter(){
			@Override
			public void rowUpdated(TableView tableView, int row) {
				onRowUpdated();
				super.rowUpdated(tableView, row);
			}	
		});
	}


	protected void onAnadirCategoriaButtonActionPerformed() {
		if(descripcionText.getText().equals("")){
			Prompt error = new Prompt(MessageType.ERROR, "La categoría no puede estar vacía", new ArrayList<String>("OK"));
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {}
			});
		} else {
			CategoriaItem nuevaCategoria = new CategoriaItem();
			nuevaCategoria.setId(1l);
			nuevaCategoria.setDescripcion(descripcionText.getText());
			variables.add(nuevaCategoria);
			try {
				ServiceLocator.getCategoriasService().crearCategoria(nuevaCategoria);
				RecetasPanel.recargarCategoriaListButton();
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			descripcionText.setText("");
		}
	}


	protected void onEliminarCategoriaButtonActionPerformed() {
		Sequence<?> seleccionados = tableView.getSelectedRows();
		if(seleccionados.getLength() == 0){
			Prompt error = new Prompt(MessageType.ERROR, "Debe selecionar una categoría", new ArrayList<String>("OK"));
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {}
			});
		} else {
			StringBuffer mensaje = new StringBuffer();
			mensaje.append("¿Desea eliminar las siguientes categorías?\n\n");

			for (int i = 0; i < seleccionados.getLength(); i++) {
				CategoriaItem categoriaSeleccionada = (CategoriaItem) seleccionados.get(i);
				mensaje.append(" - " + categoriaSeleccionada.getDescripcion() + "\n");
			}

			Prompt confirmar = new Prompt(MessageType.WARNING, mensaje.toString(), new ArrayList<String>("Sí", "No"));
			confirmar.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {

					if (confirmar.getResult() && confirmar.getSelectedOption().equals("Sí")) {
						Sequence<?> seleccionados = tableView.getSelectedRows();
						for (int i = 0; i < seleccionados.getLength(); i++) {
							CategoriaItem categoriaSeleccionada = (CategoriaItem) seleccionados.get(i);
							variables.remove(categoriaSeleccionada);
							try {
								ServiceLocator.getCategoriasService().eliminarCategoria(categoriaSeleccionada.getId());
								RecetasPanel.recargarCategoriaListButton();
							} catch (ServiceException e) {
								e.printStackTrace();
							}
						}
					}			
				}
			});
		}
	}

	protected void onRowUpdated() {
		CategoriaItem seleccionado = (CategoriaItem) tableView.getSelectedRow();
		try {
			ServiceLocator.getCategoriasService().modificarCategoria(seleccionado);
			RecetasPanel.recargarCategoriaListButton();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
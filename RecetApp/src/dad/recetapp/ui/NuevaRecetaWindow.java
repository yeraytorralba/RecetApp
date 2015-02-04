package dad.recetapp.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentMouseButtonListener;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.Spinner;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;

public class NuevaRecetaWindow extends Window implements Bindable {

	@BXML
	private NuevaRecetaWindow anadirRecetaDialog;
	@BXML
	private Button crearRecetaWindowButton, cancelarRecetaWindowButton;
	@BXML
	public static TabPane tabPaneNuevaReceta;

	private static org.apache.pivot.collections.List<CategoriaItem> categoriasBD;

	@BXML
	private TextInput nombreText, paraText;
	@BXML
	private Spinner spinnerThermoS, spinnerThermoM, spinnerTotalS,
	spinnerTotalM;
	@BXML
	private static ListButton categoriasListButton, paraCombo;
	public org.apache.pivot.collections.List<RecetaListItem> variables;

	private static List<ComponenteReceta> componentes;

	@Override
	public void initialize(Map<String, Object> namespace, URL location,Resources resources) {
		componentes = new ArrayList<ComponenteReceta>();
		crearNuevoPanelComponente();

		try {
			recargarCategoriaListButton();

		} catch (ServiceException e) {
			e.printStackTrace();
		}

		tabPaneNuevaReceta.getComponentMouseButtonListeners().add(new ComponentMouseButtonListener.Adapter() {
					@Override
					public boolean mouseClick(Component arg0,org.apache.pivot.wtk.Mouse.Button arg1, int arg2,int arg3, int arg4) {
						if (tabPaneNuevaReceta.getSelectedIndex() == tabPaneNuevaReceta.getTabs().getLength()-1) {
							crearNuevoPanelComponente();
						}
						return false;
					}
				});

		cancelarRecetaWindowButton.getButtonPressListeners().add(new ButtonPressListener() {
					@Override
					public void buttonPressed(Button arg0) {
						close();
					}
				});

		crearRecetaWindowButton.getButtonPressListeners().add(new ButtonPressListener() {
					@Override
					public void buttonPressed(Button arg0) {
						crearReceta();
					}
				});
	}

	private void crearReceta() {
		if (nombreText.getText().equals("") || paraText.getText().equals("") || categoriasListButton.getSelectedItem().toString().equals("<Seleccione una categoría>")) {
			Prompt error = new Prompt("Los campos: nombre, cantidad y categoría son obligatorios.");
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
				}
			});
		} else {
			RecetaItem receta = new RecetaItem();
			receta.setNombre(nombreText.getText());
			receta.setCantidad(Integer.parseInt(paraText.getText()));
			receta.setCategoria((CategoriaItem) categoriasListButton.getSelectedItem());
			receta.setPara(paraCombo.getSelectedItem().toString());
			receta.setFechaCreacion(new Date());
			receta.setTiempoThermomix(spinnerThermoM.getSelectedIndex() * 60 + spinnerThermoS.getSelectedIndex());
			receta.setTiempoTotal(spinnerTotalM.getSelectedIndex() * 60 + spinnerTotalS.getSelectedIndex());

			for (ComponenteReceta componente : componentes) {
				if(!componente.getSeccion().getNombre().equals("")){
					receta.getSecciones().add(componente.getSeccion());
				}
			}
			try {
				ServiceLocator.getRecetasService().crearReceta(receta);
			} catch (ServiceException e) {
				Prompt error = new Prompt("Error al crear la receta.");
				error.open(this.getWindow(), new SheetCloseListener() {
					public void sheetClosed(Sheet sheet) {
					}
				});
			}

			RecetaListItem recetaListItem = new RecetaListItem();
			recetaListItem.setId(receta.getId());
			recetaListItem.setNombre(nombreText.getText());
			recetaListItem.setCantidad(Integer.parseInt(paraText.getText()));
			recetaListItem.setCategoria(categoriasListButton.getSelectedItem().toString());
			recetaListItem.setPara(paraCombo.getSelectedItem().toString());
			recetaListItem.setFechaCreacion(new Date());
			recetaListItem.setTiempoThermomix(spinnerThermoM.getSelectedIndex()* 60 + spinnerThermoS.getSelectedIndex());
			recetaListItem.setTiempoTotal(spinnerTotalM.getSelectedIndex() * 60 + spinnerTotalS.getSelectedIndex());

			variables.add(recetaListItem);
			close();
		}
	}

	protected void crearNuevoPanelComponente() {
		try {
			URL bxmlUrl = getClass().getResource("ComponenteReceta.bxml");
			BXMLSerializer serializer = new BXMLSerializer();
			ComponenteReceta componenteReceta = (ComponenteReceta) serializer.readObject(bxmlUrl);
			componenteReceta.setParent(this);
			tabPaneNuevaReceta.getTabs().insert(componenteReceta,tabPaneNuevaReceta.getLength() - 2);
			tabPaneNuevaReceta.setSelectedTab(componenteReceta);
			componentes.add(componenteReceta);

		} catch (IOException | SerializationException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("static-access")
	public void cambiarTituloPestana(String titulo) {
		tabPaneNuevaReceta.setTabData(tabPaneNuevaReceta.getSelectedTab(),titulo);
	}

	public static void eliminarPestanaActual() {
		if (tabPaneNuevaReceta.getTabs().getLength() > 0) {
			int posicion = tabPaneNuevaReceta.getSelectedIndex();
			if (posicion == 0) {
				tabPaneNuevaReceta.setSelectedIndex(posicion+1);
			} else {
				tabPaneNuevaReceta.setSelectedIndex(posicion-1);
			}
			componentes.remove(posicion);
			tabPaneNuevaReceta.getTabs().remove(posicion, 1);
		} else if (tabPaneNuevaReceta.getTabs().getLength()==2) {

			tabPaneNuevaReceta.setSelectedIndex(tabPaneNuevaReceta.getTabs().getLength()-1);
			tabPaneNuevaReceta.getTabs().remove(tabPaneNuevaReceta.getSelectedIndex()-1, 1);
			componentes.remove(tabPaneNuevaReceta.getSelectedIndex()-1);
		}
	}

	@SuppressWarnings("unchecked")
	public static void recargarCategoriaListButton() throws ServiceException {
		CategoriaItem categoriaTitle = new CategoriaItem();
		categoriaTitle.setId(null);
		categoriaTitle.setDescripcion("<Seleccione una categoría>");
		categoriasBD = convertirList(ServiceLocator.getCategoriasService().listarCategorias());
		categoriasBD.insert(categoriaTitle, 0);
		categoriasListButton.setListData(categoriasBD);
		categoriasListButton.setSelectedItem(categoriaTitle);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static org.apache.pivot.collections.List convertirList(java.util.List<?> listaUtil) {
		org.apache.pivot.collections.List listaApache = new org.apache.pivot.collections.ArrayList();
		for (int i = 0; i < listaUtil.size(); i++) {
			listaApache.add(listaUtil.get(i));
		}
		return listaApache;
	}
	
	public void setVariables(org.apache.pivot.collections.List<RecetaListItem> variables){
		this.variables = variables;
	}
	
}
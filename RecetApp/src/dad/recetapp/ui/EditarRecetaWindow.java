package dad.recetapp.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.InstruccionItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;
import dad.recetapp.services.items.SeccionItem;

public class EditarRecetaWindow extends Window implements Bindable {

	@BXML
	private EditarRecetaWindow anadirRecetaDialog;
	@BXML
	private Button editarRecetaWindowButton, cancelarRecetaWindowButton;
	@BXML
	public static TabPane tabPaneEditarReceta;

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

	private RecetaItem receta;
	private RecetaListItem recetaList;
	private int posicionRecetaListSeleccionada;
	private Long id;

	@Override
	public void initialize(Map<String, Object> namespace, URL location,Resources resources) {

		componentes = new ArrayList<ComponenteReceta>();

		try {
			recargarCategoriaListButton();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		categoriasListButton.setSelectedIndex(0);

		tabPaneEditarReceta.getComponentMouseButtonListeners().add(new ComponentMouseButtonListener.Adapter() {
			@Override
			public boolean mouseClick(Component arg0,org.apache.pivot.wtk.Mouse.Button arg1, int arg2,int arg3, int arg4) {
				if (tabPaneEditarReceta.getSelectedIndex() == tabPaneEditarReceta.getTabs().getLength() - 1) {
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

		editarRecetaWindowButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button arg0) {
				editarReceta();
			}
		});
	}

	protected void editarReceta() {
		if (nombreText.getText().equals("") || paraText.getText().equals("")) {
			Prompt error = new Prompt("Los campos \"Nombre\", \"Para\" y \"Categoría\" son obligatorios.");
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
				}
			});
		} else {
			int minutosTotal = spinnerTotalM.getSelectedIndex();
			int segundosTotal = spinnerTotalS.getSelectedIndex();
			int minutosThermo = spinnerThermoM.getSelectedIndex();
			int segundosThermo = spinnerThermoS.getSelectedIndex();

			recetaList.setNombre(nombreText.getText());
			recetaList.setCantidad(Integer.parseInt(paraText.getText()));
			recetaList.setCategoria(categoriasListButton.getSelectedItem().toString());
			recetaList.setPara(paraCombo.getSelectedItem().toString());
			recetaList.setTiempoTotal(minutosTotal*60 + segundosTotal);
			recetaList.setTiempoThermomix(minutosThermo*60 + segundosThermo);

			receta.setNombre(nombreText.getText());
			receta.setCantidad(Integer.parseInt(paraText.getText()));
			receta.setCategoria((CategoriaItem)categoriasListButton.getSelectedItem());
			receta.setPara(paraCombo.getSelectedItem().toString());
			receta.setTiempoTotal(minutosTotal*60 + segundosTotal);
			receta.setTiempoThermomix(minutosThermo*60 + segundosThermo);

			receta.getSecciones().removeAll(receta.getSecciones());

			for (ComponenteReceta componente : componentes) {
				if (!componente.getSeccion().getNombre().equals("")) {
					receta.getSecciones().add(componente.getSeccion());
				}
			}

			try {
				ServiceLocator.getRecetasService().modificarReceta(receta);
				RecetasPanel.variables.update(posicionRecetaListSeleccionada, recetaList);
				RecetasPanel.tableView.setTableData(RecetasPanel.variables);
			} catch (ServiceException e) {
			}

			close();
		}
	}

	protected void crearNuevoPanelComponente() {
		try {
			URL bxmlUrl = getClass().getResource("ComponenteReceta.bxml");
			BXMLSerializer serializer = new BXMLSerializer();
			ComponenteReceta componenteReceta = (ComponenteReceta) serializer.readObject(bxmlUrl);
			componenteReceta.setParent(this);

			tabPaneEditarReceta.getTabs().insert(componenteReceta,tabPaneEditarReceta.getLength() - 2);
			tabPaneEditarReceta.setSelectedTab(componenteReceta);
			componentes.add(componenteReceta);

		} catch (IOException | SerializationException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "static-access", "unchecked" })
	protected void crearPanelesExistentes(String texto, List<IngredienteItem> ingredientes, List<InstruccionItem> instrucciones) {

		try {
			URL bxmlUrl = getClass().getResource("ComponenteReceta.bxml");
			BXMLSerializer serializer = new BXMLSerializer();
			ComponenteReceta componenteReceta = (ComponenteReceta) serializer.readObject(bxmlUrl);

			componenteReceta.setParent(this);

			tabPaneEditarReceta.getTabs().insert(componenteReceta,tabPaneEditarReceta.getLength() - 2);
			tabPaneEditarReceta.setSelectedTab(componenteReceta);
			tabPaneEditarReceta.setTabData(tabPaneEditarReceta.getSelectedTab(), texto);
			componenteReceta.setSeccionText(texto);
			componenteReceta.setVariablesIngredientes(convertirList(ingredientes));
			componenteReceta.setVariablesInstrucciones(convertirList(instrucciones));

			componentes.add(componenteReceta);

		} catch (IOException | SerializationException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public void cambiarTituloPestana(String titulo) {
		tabPaneEditarReceta.setTabData(tabPaneEditarReceta.getSelectedTab(),titulo);
	}

	public static void eliminarPestanaActual() {

		if (tabPaneEditarReceta.getTabs().getLength() > 0) {
			int posicion = tabPaneEditarReceta.getSelectedIndex();
			if (posicion == 0) {
				tabPaneEditarReceta.setSelectedIndex(posicion + 1);
			} else {
				tabPaneEditarReceta.setSelectedIndex(posicion - 1);
			}
			componentes.remove(posicion);
			tabPaneEditarReceta.getTabs().remove(posicion, 1);
		} else if (tabPaneEditarReceta.getTabs().getLength() == 2) {

			tabPaneEditarReceta.setSelectedIndex(tabPaneEditarReceta.getTabs().getLength() - 1);
			tabPaneEditarReceta.getTabs().remove(tabPaneEditarReceta.getSelectedIndex() - 1, 1);
			componentes.remove(tabPaneEditarReceta.getSelectedIndex() - 1);
		}
	}

	@SuppressWarnings("unchecked")
	public static void recargarCategoriaListButton() throws ServiceException {
		categoriasBD = convertirList(ServiceLocator.getCategoriasService().listarCategorias());
		categoriasListButton.setListData(categoriasBD);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static org.apache.pivot.collections.List convertirList(java.util.List<?> listaUtil) {
		org.apache.pivot.collections.List listaApache = new org.apache.pivot.collections.ArrayList();
		for (int i = 0; i < listaUtil.size(); i++) {
			listaApache.add(listaUtil.get(i));
		}
		return listaApache;
	}

	public void setReceta(RecetaListItem recetaList){
		this.recetaList = recetaList;
		id = recetaList.getId();

		try {
			receta = ServiceLocator.getRecetasService().obtenerReceta(id);
		} catch (ServiceException e1) {
		}

		int minutosTotal = receta.getTiempoTotal() / 60;
		int segundosTotal = receta.getTiempoTotal() % 60;
		int minutosThermo = receta.getTiempoThermomix() / 60;
		int segundosThermo = receta.getTiempoThermomix() % 60;

		nombreText.setText(receta.getNombre());
		paraText.setText(receta.getCantidad().toString());
		spinnerThermoM.setSelectedIndex(minutosThermo);
		spinnerThermoS.setSelectedIndex(segundosThermo);
		spinnerTotalM.setSelectedIndex(minutosTotal);
		spinnerTotalS.setSelectedIndex(segundosTotal);

		if(receta.getSecciones().size() == 0){
			crearNuevoPanelComponente();
		}
		else{
			for (SeccionItem seccion : receta.getSecciones()) {
				crearPanelesExistentes(seccion.getNombre(), seccion.getIngredientes(), seccion.getInstrucciones());
			}
		}
	}

	public void setVariables(org.apache.pivot.collections.List<RecetaListItem> variables){
		this.variables = variables;
	}

	public void setPosicionRecetaListSeleccionada(int posicionRecetaListSeleccionada) {
		this.posicionRecetaListSeleccionada = posicionRecetaListSeleccionada;
	}


}
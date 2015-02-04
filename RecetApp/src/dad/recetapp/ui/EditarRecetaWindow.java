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
import org.apache.pivot.wtk.Spinner;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;

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

	private static List<ComponenteReceta> componentes;

	private RecetaItem receta;

	@Override
	public void initialize(Map<String, Object> namespace, URL location,Resources resources) {
		componentes = new ArrayList<ComponenteReceta>();
		RecetaListItem recetaSeleccionada = (RecetaListItem) RecetasPanel.tableView.getSelectedRow();
		Long id = recetaSeleccionada.getId();
		try {
			receta = ServiceLocator.getRecetasService().obtenerReceta(id);
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		Sequence<?> seleccionados = RecetasPanel.tableView.getSelectedRows();
//		for (int i = 0; i < seleccionados.getLength(); i++) {
//			try {
//				RecetaListItem recetaSeleccionada = (RecetaListItem) seleccionados.get(i);
//				Long id = recetaSeleccionada.getId();
//				receta = ServiceLocator.getRecetasService().obtenerReceta(id);
//			} catch (ServiceException e) {
//				e.printStackTrace();
//			}
//		}

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
	}

	protected void crearNuevoPanelComponente() {
		try {
			URL bxmlUrl = getClass().getResource("ComponenteReceta.bxml");
			BXMLSerializer serializer = new BXMLSerializer();
			ComponenteReceta componenteReceta = (ComponenteReceta) serializer.readObject(bxmlUrl);

			tabPaneEditarReceta.getTabs().insert(componenteReceta,tabPaneEditarReceta.getLength() - 2);
			tabPaneEditarReceta.setSelectedTab(componenteReceta);

			componenteReceta.setName("componente"+ tabPaneEditarReceta.getSelectedIndex());

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
}
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
import dad.recetapp.services.items.TipoAnotacionItem;
import dad.recetapp.services.items.TipoIngredienteItem;

public class IngredientesPanel extends TablePane implements Bindable {

	@BXML
	private TableView tableView;
	private org.apache.pivot.collections.List<TipoIngredienteItem> variables;
	private List<TipoIngredienteItem> lista;
	@BXML
	private Button anadirIngredienteButton;
	@BXML
	private Button eliminarIngredienteButton;
	@BXML
	private TextInput nombreText;


	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		variables = new org.apache.pivot.collections.ArrayList<TipoIngredienteItem>();

		try {
			lista = ServiceLocator.getTiposIngredientesService().listarTiposIngredientes();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		for (TipoIngredienteItem l : lista) {
			variables.add(l);
		}
		tableView.setTableData(variables);

		anadirIngredienteButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAnadirIngredienteButtonActionPerformed();
			}
		});

		eliminarIngredienteButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEliminarIngredienteButtonActionPerformed();
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


	protected void onAnadirIngredienteButtonActionPerformed() {
		if(nombreText.getText().equals("")){
			Prompt error = new Prompt(MessageType.ERROR, "El nombre del ingrediente no puede estar vacío", new ArrayList<String>("OK"));
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {}
			});
		} else {
			TipoIngredienteItem nuevoTipoIngrediente = new TipoIngredienteItem();
			nuevoTipoIngrediente.setId(1l);
			nuevoTipoIngrediente.setNombre(nombreText.getText());
			variables.add(nuevoTipoIngrediente);
			try {
				ServiceLocator.getTiposIngredientesService().crearTipoIngrediente(nuevoTipoIngrediente);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			nombreText.setText("");
		}
	}


	protected void onEliminarIngredienteButtonActionPerformed() {
		Sequence<?> seleccionados = tableView.getSelectedRows();
		if(seleccionados.getLength() == 0){
			Prompt error = new Prompt(MessageType.ERROR, "Debe selecionar un tipo de ingrediente", new ArrayList<String>("OK"));
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {}
			});
		} else {
			StringBuffer mensaje = new StringBuffer();
			mensaje.append("¿Desea eliminar los siguientes tipos de ingrediente?\n\n");

			for (int i = 0; i < seleccionados.getLength(); i++) {
				TipoAnotacionItem tipoIngredienteSeleccionado = (TipoAnotacionItem) seleccionados.get(i);
				mensaje.append(" - " + tipoIngredienteSeleccionado.getDescripcion() + "\n");
			}

			Prompt confirmar = new Prompt(MessageType.WARNING, mensaje.toString(), new ArrayList<String>("Sí", "No"));
			confirmar.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {

					if (confirmar.getResult() && confirmar.getSelectedOption().equals("Sí")) {
						Sequence<?> seleccionados = tableView.getSelectedRows();
						for (int i = 0; i < seleccionados.getLength(); i++) {
							TipoIngredienteItem tipoIngredienteSeleccionado = (TipoIngredienteItem) seleccionados.get(i);
							variables.remove(tipoIngredienteSeleccionado);
							try {
								ServiceLocator.getTiposIngredientesService().eliminarTipoIngrediente(tipoIngredienteSeleccionado.getId());
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
		TipoIngredienteItem seleccionado = (TipoIngredienteItem) tableView.getSelectedRow();
		try {
			ServiceLocator.getTiposIngredientesService().modificarTipoIngrediente(seleccionado);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
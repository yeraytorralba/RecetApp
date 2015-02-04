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
import dad.recetapp.services.items.MedidaItem;

public class MedidasPanel extends TablePane implements Bindable {

	@BXML
	private TableView tableView;
	private org.apache.pivot.collections.List<MedidaItem> variables;
	private List<MedidaItem> lista;
	@BXML
	private Button anadirMedidaButton;
	@BXML
	private Button eliminarMedidaButton;
	@BXML
	private TextInput nombreText;
	@BXML
	private TextInput abreviaturaText;

	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		variables = new org.apache.pivot.collections.ArrayList<MedidaItem>();

		try {
			lista = ServiceLocator.getMedidasService().listarMedidas();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		for (MedidaItem l : lista) {
			variables.add(l);
		}
		tableView.setTableData(variables);

		anadirMedidaButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAnadirMedidaButtonActionPerformed();
			}
		});
		eliminarMedidaButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEliminarMedidaButtonActionPerformed();
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

	protected void onAnadirMedidaButtonActionPerformed() {
		if(nombreText.getText().equals("")){
			Prompt error = new Prompt(MessageType.ERROR, "El nombre de la medida no puede estar vacío", new ArrayList<String>("OK"));
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {}
			});
		} else {
			MedidaItem nuevaMedida = new MedidaItem();
			nuevaMedida.setId(1l);
			nuevaMedida.setNombre(nombreText.getText());
			nuevaMedida.setAbreviatura(abreviaturaText.getText());
			variables.add(nuevaMedida);
			try {
				ServiceLocator.getMedidasService().crearMedida(nuevaMedida);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			nombreText.setText("");
			abreviaturaText.setText("");
		}
	}

	protected void onEliminarMedidaButtonActionPerformed() {
		Sequence<?> seleccionados = tableView.getSelectedRows();
		if(seleccionados.getLength() == 0){
			Prompt error = new Prompt(MessageType.ERROR, "Debe selecionar una medida", new ArrayList<String>("OK"));
			error.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {}
			});
		} else {

			StringBuffer mensaje = new StringBuffer();
			mensaje.append("¿Desea eliminar las siguientes medidas?\n\n");

			for (int i = 0; i < seleccionados.getLength(); i++) {
				MedidaItem medidaSeleccionada = (MedidaItem) seleccionados.get(i);
				mensaje.append(" - " + medidaSeleccionada.getNombre() + "\n");
			}

			Prompt confirmar = new Prompt(MessageType.WARNING, mensaje.toString(), new ArrayList<String>("Sí", "No"));
			confirmar.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {

					if (confirmar.getResult() && confirmar.getSelectedOption().equals("Sí")) {
						Sequence<?> seleccionados = tableView.getSelectedRows();
						for (int i = 0; i < seleccionados.getLength(); i++) {
							MedidaItem medidaSeleccionada = (MedidaItem) seleccionados.get(i);
							variables.remove(medidaSeleccionada);
							try {
								ServiceLocator.getMedidasService().eliminarMedida(medidaSeleccionada.getId());
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
		MedidaItem seleccionado = (MedidaItem) tableView.getSelectedRow();
		try {
			ServiceLocator.getMedidasService().modificarMedida(seleccionado);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
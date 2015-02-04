package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.TextArea;
import org.apache.pivot.wtk.TextInput;

import dad.recetapp.services.items.InstruccionItem;

public class EditarInstruccionDialog extends Dialog implements Bindable{
	@BXML
	private EditarInstruccionDialog editarInstruccionDialog;
	@BXML
	private Button editarInstruccionButton, cancelarInstruccionButton;
	@BXML
	private TextInput ordenText;
	@BXML
	private TextArea descripcionText;
	
	public static InstruccionItem instruccion;
	
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    	
    	instruccion = (InstruccionItem) ComponenteReceta.instruccionesTable.getSelectedRow();
    	
    	ordenText.setText(instruccion.getOrden().toString());
    	descripcionText.setText(instruccion.getDescripcion());
    	
    	cancelarInstruccionButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button arg0) {
				editarInstruccionDialog.close();
			}
		});
    	
    	editarInstruccionButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button arg0) {
				if(ordenText.getText().equals("") || descripcionText.getText().equals("")){
					System.out.println("Debe rellenar todos los campos");
				}
				else{
					InstruccionItem instruccionModificado = new InstruccionItem();
					instruccionModificado.setOrden(Integer.valueOf(ordenText.getText()));
					instruccionModificado.setDescripcion(descripcionText.getText());
					
					ComponenteReceta.variablesInstrucciones.update(ComponenteReceta.instruccionesTable.getLastSelectedIndex(), instruccionModificado);
					ComponenteReceta.instruccionesTable.setTableData(ComponenteReceta.variablesInstrucciones);
					close();
				}
			}
		});
    }
}

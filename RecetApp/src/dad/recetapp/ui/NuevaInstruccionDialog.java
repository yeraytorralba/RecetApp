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

public class NuevaInstruccionDialog extends Dialog implements Bindable{
	@BXML
	private NuevaInstruccionDialog nuevaInstruccionDialog;
	@BXML
	private Button anadirInstruccionButton, cancelarInstruccionButton;
	@BXML
	private TextInput ordenText;
	@BXML
	private TextArea descripcionText;
	
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    			
		cancelarInstruccionButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button arg0) {
				nuevaInstruccionDialog.close();
			}
		});
    	
		anadirInstruccionButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button arg0) {
				if(ordenText.getText().equals("") || descripcionText.getText().equals("")){
					System.out.println("Debe rellenar todos los campos");
				}
				else{
					InstruccionItem instruccion = new InstruccionItem();
					instruccion.setOrden(Integer.valueOf(ordenText.getText()));
					instruccion.setDescripcion(descripcionText.getText());
					
					ComponenteReceta.variablesInstrucciones.add(instruccion);
					
					ComponenteReceta.instruccionesTable.setTableData(ComponenteReceta.variablesInstrucciones);
					
					
					nuevaInstruccionDialog.close();
				}
			}
		});
    }
}

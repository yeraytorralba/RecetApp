package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TextArea;
import org.apache.pivot.wtk.TextInput;

import dad.recetapp.services.items.InstruccionItem;

public class NuevaInstruccionDialog extends Dialog implements Bindable {
	
	private Boolean aceptar = false;
	
	@BXML
	private NuevaInstruccionDialog nuevaInstruccionDialog;
	@BXML
	private Button anadirInstruccionButton, cancelarInstruccionButton;
	@BXML
	private TextInput ordenText;
	@BXML
	private TextArea descripcionText;
	@BXML
	private Label errorLabel;

	InstruccionItem instruccion;
	
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		
		instruccion = new InstruccionItem();

		cancelarInstruccionButton.getButtonPressListeners().add(
				new ButtonPressListener() {
					@Override
					public void buttonPressed(Button arg0) {
						nuevaInstruccionDialog.close();
					}
				});

		anadirInstruccionButton.getButtonPressListeners().add(
				new ButtonPressListener() {
					@Override
					public void buttonPressed(Button arg0) {
						if (ordenText.getText().equals("")
								|| descripcionText.getText().equals("")) {
							errorLabel
									.setText("Debe rellenar todos los campos");
						} 
						else {
							instruccion.setOrden(Integer.valueOf(ordenText.getText()));
							instruccion.setDescripcion(descripcionText.getText());

							aceptar = true;
							NuevaInstruccionDialog.this.close();
						}
					}
				});
	}
	
	public InstruccionItem getInstruccion(){
		return this.instruccion;
	}

	public Boolean getAceptar() {
		return aceptar;
	}
	
}

package dad.recetapp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.Timer;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentMouseButtonListener;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.Mouse.Button;

import dad.recetapp.RecetappApplication;

public class PantallaInicio extends Window implements Bindable{

	@BXML
	private PantallaInicio pantallaInicio;
	@BXML
	private ImageView imagenInicio;
	private Timer temporizador;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {

		imagenInicio.getComponentMouseButtonListeners().add(new ComponentMouseButtonListener() {
			
			@Override
			public boolean mouseUp(Component arg0, Button arg1, int arg2, int arg3) {return false;}
			@Override
			public boolean mouseDown(Component arg0, Button arg1, int arg2, int arg3) {return false;}
			
			@Override
			public boolean mouseClick(Component arg0, Button arg1, int arg2, int arg3,int arg4) {
				initFrame();
				return false;
			}
		});
		
		temporizador = new Timer(4000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initFrame();				
			}
		});
		
		temporizador.start();
	}

	private void initFrame(){
		temporizador.stop();
		RecetappFrame mainFrame = null;
		try {
			mainFrame = (RecetappFrame) RecetappApplication.loadWindow("dad/recetapp/ui/RecetappFrame.bxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SerializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainFrame.open(this);
	}

}
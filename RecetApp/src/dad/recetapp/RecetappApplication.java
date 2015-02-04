package dad.recetapp;

import java.io.IOException;
import java.net.URL;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import dad.recetapp.ui.PantallaInicio;

public class RecetappApplication implements Application{
	
	private PantallaInicio pantallaInicio = null;
	
	public static Window loadWindow(String bxmlFile) throws IOException, SerializationException {
		URL bxmlUrl = RecetappApplication.class.getClassLoader().getResource(bxmlFile);
		BXMLSerializer serializer = new BXMLSerializer();
		return (Window) serializer.readObject(bxmlUrl);
	}
	
	@Override
	public void startup(Display display, Map<String, String> properties) throws Exception {
		
		pantallaInicio = (PantallaInicio) loadWindow("dad/recetapp/ui/PantallaInicio.bxml");
		pantallaInicio.open(display);
		pantallaInicio.setTitle("RecetApp");
		pantallaInicio.setIcon("/dad/recetapp/images/logo.png");
	}
	
	@Override
	public boolean shutdown(boolean optional) throws Exception {
		
		if (pantallaInicio != null) 
			pantallaInicio.close();
		
		return false;
	}
	
	@Override
	public void resume() throws Exception {}


	@Override
	public void suspend() throws Exception {}

}

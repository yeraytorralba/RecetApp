package dad.recetapp.prueba;

import java.io.IOException;
import java.net.URL;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;

public class HolaMundoApplication implements Application {
	
	private MainFrame mainFrame = null;
	
	
	@Override
	public void resume() throws Exception {

	}

	@Override
	public boolean shutdown(boolean optional) throws Exception {
		System.out.println("finalizando la aplicación");
		if (mainFrame != null) {
			mainFrame.close();
		}
		return false;
	}

	@Override
	public void startup(Display display, Map<String, String> properties) throws Exception {
		System.out.println("Iniciando la aplicación");
		mainFrame = (MainFrame) loadWindow("dad/recetapp/prueba/MainFrame.bxml");
		mainFrame.open(display);
	}

	@Override
	public void suspend() throws Exception {

	}
	
	public static Window loadWindow(String bxmlFile) throws IOException, SerializationException {
		URL bxmlUrl = HolaMundoApplication.class.getClassLoader().getResource(bxmlFile);
		BXMLSerializer serializer = new BXMLSerializer();
		return (Window) serializer.readObject(bxmlUrl);
	}
	
}

package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.Window;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
 
public class RecetappFrame extends Window implements Bindable {
	
	@BXML
	private Window recetappFrame;
	@BXML
    private static Label numRecetasPane;

	
    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    	recetappFrame.setIcon("/dad/recetapp/ui/images/logo.png");
    	setNumReceta();
    	
    }
    
    public static void setNumReceta(){
    	try {
			numRecetasPane.setText(String.valueOf(ServiceLocator.getRecetasService().listarRecetas().size()));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    }
 
}
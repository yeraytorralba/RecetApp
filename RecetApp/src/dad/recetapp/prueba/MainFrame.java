package dad.recetapp.prueba;

import java.net.URL;

import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.Window;
 
public class MainFrame extends Window implements Bindable {
	
    private TabPane tabPane = null;
    private Label numRecetasPane = new Label("jaja");
 
    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
        tabPane = (TabPane)namespace.get("tabPane");
        numRecetasPane = (Label)namespace.get("numRecetasPane");
    }
 
}
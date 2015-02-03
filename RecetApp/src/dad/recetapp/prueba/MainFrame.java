package dad.recetapp.prueba;

import java.net.URL;

import javax.swing.JTable;

import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Collection;
import org.apache.pivot.collections.LinkedList;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.Window;

import java.util.List;

import dad.recetapp.items.AnotacionItem;
import dad.recetapp.items.TipoAnotacionItem;
import dad.recetapp.services.ServicioException;
import dad.recetapp.services.impl.TipoAnotacionesServiceDB;
import dad.recetapp.ui.AnotacionesTableModel;

 
public class MainFrame extends Window implements Bindable {
	
    private TabPane tabPane = null;
    private Label numRecetasPane = new Label("jaja");
    private AnotacionesTableModel anotaciones=null;
    private TableView tabla=null; 
    private Collection<TipoAnotacionItem> colection;
    private LinkedList<String> listaTabla;
    private Collection<String> elemento;
 
    
    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    	try {
			anotaciones=new AnotacionesTableModel();
			List<TipoAnotacionItem> va1= anotaciones.getAnotaciones();
			tabla=new TableView();
			listaTabla=new LinkedList<String>();
			listaTabla.add("paco");
			
		} catch (ServicioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tabPane = (TabPane)namespace.get("tabPane");
        numRecetasPane = (Label)namespace.get("numRecetasPane");
        elemento= (Collection<String>) namespace.get("paco");
        tabla = (TableView)namespace.get("tableView");
        tabla.setTableData(listaTabla);
       
        
    }
 
}
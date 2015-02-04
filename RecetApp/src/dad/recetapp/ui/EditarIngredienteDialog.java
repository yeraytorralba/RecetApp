package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.TextInput;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.TipoIngredienteItem;
 
public class EditarIngredienteDialog extends Dialog implements Bindable {

	@BXML
	private EditarIngredienteDialog editarIngredienteDialog;
	@BXML
	private Button editarIngredienteDialogButton, cancelarIngredienteDialogButton;
	@BXML
	private static ListButton medidasListButton, tipoIngredienteListButton;
	@BXML
	private TextInput cantidadText;
	
	private static org.apache.pivot.collections.List<MedidaItem> medidasBD;
	private static org.apache.pivot.collections.List<TipoIngredienteItem> tiposIngredientesBD;
	
	public static IngredienteItem ingrediente;
	
    @Override
    public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
    	    	
    	ingrediente = (IngredienteItem) ComponenteReceta.ingredientesTable.getSelectedRow();
    	
    	try {
			cargarCombos();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    	
    	cantidadText.setText(String.valueOf(ingrediente.getCantidad()));
    	medidasListButton.setSelectedIndex(0);
    	tipoIngredienteListButton.setSelectedIndex(0);
    	
    	cancelarIngredienteDialogButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button arg0) {
				editarIngredienteDialog.close();
			}
		});
    	
    	editarIngredienteDialogButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button arg0) {
				if (cantidadText.getText().equals("") || medidasListButton.getSelectedItem().toString().equals("<Seleccione la medida>") || tipoIngredienteListButton.getSelectedItem().toString().equals("<Seleccione el tipo de instruccion>")) {
					System.out.println("Debe rellenar todos los campos");
				}
				else{
					IngredienteItem ingredienteModificado = new IngredienteItem();
					ingredienteModificado.setCantidad(Integer.valueOf(cantidadText.getText()));
					ingredienteModificado.setMedida((MedidaItem)medidasListButton.getSelectedItem());
					ingredienteModificado.setTipo((TipoIngredienteItem) tipoIngredienteListButton.getSelectedItem());
					
					ComponenteReceta.variablesIngredientes.update(ComponenteReceta.ingredientesTable.getLastSelectedIndex(), ingredienteModificado);
					ComponenteReceta.ingredientesTable.setTableData(ComponenteReceta.variablesIngredientes);
					close();
				}
			}
		});
    }
    
    @SuppressWarnings("unchecked")
   	public static void cargarCombos() throws ServiceException {
   		medidasBD = convertirList(ServiceLocator.getMedidasService().listarMedidas());
   		medidasListButton.setListData(medidasBD);
   	//	----------------------------------------------------
   		tiposIngredientesBD = convertirList(ServiceLocator.getTiposIngredientesService().listarTiposIngredientes());
   		tipoIngredienteListButton.setListData(tiposIngredientesBD);
   	}
       
       @SuppressWarnings({ "rawtypes", "unchecked" })
   	protected static org.apache.pivot.collections.List convertirList(java.util.List<?> listaUtil){
   		org.apache.pivot.collections.List listaApache = new org.apache.pivot.collections.ArrayList();
   		for(int i = 0; i<listaUtil.size(); i++){
   			listaApache.add(listaUtil.get(i));
   		}
   		return listaApache;
   	}

}
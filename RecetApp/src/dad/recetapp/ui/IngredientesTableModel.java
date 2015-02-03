package dad.recetapp.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dad.recetapp.items.IngredienteItem;
import dad.recetapp.items.TipoAnotacionItem;
import dad.recetapp.items.TipoIngredienteItem;
import dad.recetapp.services.ServicesLocator;
import dad.recetapp.services.ServicioException;

@SuppressWarnings("serial")
public class IngredientesTableModel extends AbstractTableModel{

	TipoIngredienteItem[] ingredientesArray = ServicesLocator.getTiposIngredientesService().listarTiposIngredientes();
	private List<TipoIngredienteItem> ingredientes = new ArrayList<TipoIngredienteItem>();
	private static final String[] COLUMN_NAMES = { "Nombre"};
	private static final Class<?>[] COLUMN_CLASSES = { String.class};

	public IngredientesTableModel() throws ServicioException {
		for (int i = 0; i < ingredientesArray.length; i++) {
			ingredientes.add(ingredientesArray[i]);
		}
	}
	
	public List<TipoIngredienteItem> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<TipoIngredienteItem> ingredientes) {
		this.ingredientes = ingredientes;
		fireTableDataChanged();
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_CLASSES[columnIndex];
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	@Override
	public int getRowCount() {
		return ingredientes.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return ingredientes.get(rowIndex).getNombre();
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		TipoIngredienteItem ingrediente = ingredientes.get(rowIndex);
		ingrediente.setNombre((String) value);
		fireTableRowsUpdated(rowIndex, rowIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	@Override
	 public void fireTableRowsUpdated(int firstRow, int lastRow) {
	 super.fireTableRowsUpdated(firstRow, lastRow);
	 
	 TipoIngredienteItem item = new TipoIngredienteItem();
	 item.setId(ingredientes.get(firstRow).getId());
	 item.setNombre(ingredientes.get(firstRow).getNombre());
	 
	 try {
	 ServicesLocator.getTiposIngredientesService().modificarTipoIngrediente(item);;
	 } catch (ServicioException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }
}

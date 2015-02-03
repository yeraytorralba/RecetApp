package dad.recetapp.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dad.recetapp.items.CategoriaItem;
import dad.recetapp.items.TipoAnotacionItem;
import dad.recetapp.services.ServicesLocator;
import dad.recetapp.services.ServicioException;

@SuppressWarnings("serial")
public class CategoriasTableModel extends AbstractTableModel{

	CategoriaItem[] categoriasArray = ServicesLocator.getCategoriasService().ListarCategorias();
	private List<CategoriaItem> categorias = new ArrayList<CategoriaItem>();
	private static final String[] COLUMN_NAMES = { "Descripción"};
	private static final Class<?>[] COLUMN_CLASSES = { String.class};

	public CategoriasTableModel() throws ServicioException {
		for (int i = 0; i < categoriasArray.length; i++) {
			categorias.add(categoriasArray[i]);
		}
	}

	public void setCategorias(List<CategoriaItem> categorias) {
		this.categorias = categorias;
		fireTableDataChanged();
	}
	
	public List<CategoriaItem> getCategorias() {
		return categorias;
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
		return categorias.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return categorias.get(rowIndex).getDescripcion();
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		CategoriaItem categoria = categorias.get(rowIndex);
		categoria.setDescripcion((String) value);
		fireTableRowsUpdated(rowIndex, rowIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	@Override
	 public void fireTableRowsUpdated(int firstRow, int lastRow) {
	 super.fireTableRowsUpdated(firstRow, lastRow);
	 
	 CategoriaItem item = new CategoriaItem();
	 item.setId(categorias.get(firstRow).getId());
	 item.setDescripcion(categorias.get(firstRow).getDescripcion());

	 try {
	 ServicesLocator.getCategoriasService().modificarCategoria(item);
	 } catch (ServicioException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }
}

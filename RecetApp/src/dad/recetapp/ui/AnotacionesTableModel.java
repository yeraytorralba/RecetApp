package dad.recetapp.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import dad.recetapp.items.TipoAnotacionItem;
import dad.recetapp.services.ServicesLocator;
import dad.recetapp.services.ServicioException;

@SuppressWarnings("serial")
public class AnotacionesTableModel extends AbstractTableModel{

	TipoAnotacionItem[] anotacionesArray = ServicesLocator.getTiposAnotacionesService().listarTiposAnotaciones();
	private List<TipoAnotacionItem> anotaciones = new ArrayList<TipoAnotacionItem>();
	private static final String[] COLUMN_NAMES = { "Descripción"};
	private static final Class<?>[] COLUMN_CLASSES = { String.class};

	public AnotacionesTableModel() throws ServicioException {
		for (int i = 0; i < anotacionesArray.length; i++) {
			anotaciones.add(anotacionesArray[i]);
		}
	}

	public void setAnotaciones(List<TipoAnotacionItem> anotaciones) {
		this.anotaciones = anotaciones;
		fireTableDataChanged();
	}

	public List<TipoAnotacionItem> getAnotaciones() {
		return anotaciones;
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
		return anotaciones.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return anotaciones.get(rowIndex).getDescripcion();
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		TipoAnotacionItem anotacion = anotaciones.get(rowIndex);
		anotacion.setDescripcion((String) value);
		fireTableRowsUpdated(rowIndex, rowIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}

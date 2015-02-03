package dad.recetapp.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import dad.recetapp.items.RecetaItem;
import dad.recetapp.items.RecetaListItem;
import dad.recetapp.services.ServicesLocator;
import dad.recetapp.services.ServicioException;

@SuppressWarnings("serial")
public class RecetasTableModel extends AbstractTableModel{

	private RecetaListItem recetasListItem = ServicesLocator.getRecetasService().listarRecetas();
	private List<RecetaItem> recetasList = new ArrayList<RecetaItem>();
	private static final String[] COLUMN_NAMES = { "Nombre", "Para", "Tiempo Total", "Fecha de creación", "Categoría" };
	private static final Class<?>[] COLUMN_CLASSES = { String.class, String.class, Integer.class, Date.class, Long.class};

	public RecetasTableModel() throws ServicioException {
		this.recetasList = recetasListItem.getRecetas();
	}

	public void setRecetas(List<RecetaItem> recetas) {
		this.recetasList = recetas;
		fireTableDataChanged();
	}

	public List<RecetaItem> getRecetas() {
		return recetasList;
	}

	public void setRecetasList(List<RecetaItem> recetasList) {
		this.recetasList = recetasList;
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
		return recetasList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		RecetaItem receta = recetasList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			value = receta.getNombre();
			break;
		case 1:
			value = receta.getPara();
			break;
		case 2:
			value = receta.getTiempoTotal();
			break;
		case 3:
			value = receta.getFechaCreacion();
			break;
		case 4:
			value = receta.getCategoria();
			break;
		}
		return value;
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		RecetaItem receta = recetasList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			receta.setNombre((String) value);
			break;
		case 1:
			receta.setPara((String) value);
			break;
		case 2:
			receta.setTiempoTotal((Integer) value);
			break;
		case 3:
			receta.setFechaCreacion((Date) value);
			break;
		case 4:
			receta.setCategoria((Long) value);
			break;
		}
		fireTableRowsUpdated(rowIndex, rowIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
}

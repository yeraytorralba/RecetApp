package dad.recetapp.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dad.recetapp.items.MedidaItem;
import dad.recetapp.items.TipoAnotacionItem;
import dad.recetapp.services.ServicesLocator;
import dad.recetapp.services.ServicioException;

@SuppressWarnings("serial")
public class MedidasTableModel extends AbstractTableModel{

	MedidaItem[] medidasArray = ServicesLocator.getMedidasService().ListarMedidas();
	private List<MedidaItem> medidas = new ArrayList<MedidaItem>();
	private static final String[] COLUMN_NAMES = {"Nombre", "Abreviatura"};
	private static final Class<?>[] COLUMN_CLASSES = { String.class, String.class};

	public MedidasTableModel() throws ServicioException {
		for (int i = 0; i < medidasArray.length; i++) {
			medidas.add(medidasArray[i]);
		}
	}

	public void setMedidas(List<MedidaItem> medidas) {
		this.medidas = medidas;
		fireTableDataChanged();
	}
	
	public List<MedidaItem> getMedidas() {
		return medidas;
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
		return medidas.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		MedidaItem medida = medidas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			value = medida.getNombre();
			break;
		case 1:
			value = medida.getAbreviatura();
			break;
		}
		return value;
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		MedidaItem medida = medidas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			medida.setNombre((String) value);
			break;
		case 1:
			medida.setAbreviatura((String) value);
			break;
		}
		fireTableRowsUpdated(rowIndex, rowIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	@Override
	 public void fireTableRowsUpdated(int firstRow, int lastRow) {
	 super.fireTableRowsUpdated(firstRow, lastRow);
	 
	 MedidaItem item = new MedidaItem();
	 item.setId(medidas.get(firstRow).getId());
	 item.setNombre(medidas.get(firstRow).getNombre());
	 item.setAbreviatura(medidas.get(firstRow).getAbreviatura());
	 
	 try {
	 ServicesLocator.getMedidasService().modificarMedida(item);
	 } catch (ServicioException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }
}

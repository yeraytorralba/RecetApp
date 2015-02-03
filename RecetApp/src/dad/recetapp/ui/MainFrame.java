package dad.recetapp.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import dad.recetapp.items.CategoriaItem;
import dad.recetapp.items.MedidaItem;
import dad.recetapp.items.TipoAnotacionItem;
import dad.recetapp.items.TipoIngredienteItem;
import dad.recetapp.services.ServicesLocator;
import dad.recetapp.services.ServicioException;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	//PANEL PRINCIPAL
	private JLabel numRecetasLabel;
	
	//PANEL RECETAS
	private JTable recetasTable;
	private RecetasTableModel recetasTableModel = new RecetasTableModel();
	private JButton recetasAnadirButton, recetasEliminarButton, recetasEditarButton;
	private JTextField recetasNombreText;
	private JComboBox<String> recetasCategoriaCombo;
	private JSpinner recetasMSpinner, recetasSSpinner;
	
	//PANEL CATEGORIAS
	private JTable categoriasTable;
	private CategoriasTableModel categoriasTableModel = new CategoriasTableModel();
	private JButton categoriasAnadirButton, categoriasEliminarButton;
	private JTextField categoriasDescripcionText;
	
	//PANEL INGREDIENTES
	private JTable ingredientesTable;
	private IngredientesTableModel ingredientesTableModel = new IngredientesTableModel();
	private JButton ingredientesAnadirButton, ingredientesEliminarButton;
	private JTextField ingredientesNombreText;
	
	//PANEL MEDIDAS
	private JTable medidasTable;
	private MedidasTableModel medidasTableModel = new MedidasTableModel();
	private JButton medidasAnadirButton, medidasEliminarButton;
	private JTextField medidasNombreText, medidasAbreviaturaText;
	
	//PANEL ANOTACIONES
	private JTable anotacionesTable;
	private AnotacionesTableModel anotacionesTableModel = new AnotacionesTableModel();
	private JButton anotacionesAnadirButton, anotacionesEliminarButton;
	private JTextField anotacionesDescripcionText;
	
	public MainFrame() throws SQLException, ServicioException{
		initFrame();
		initComponents();
	}

	private void initFrame() {
		setTitle("RecetApp");
		setSize(800, 600);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				onWindowClosing(e);
			}
		});
	}

	private void initComponents() throws ServicioException {
        Border bordePrincipalPanel = new EmptyBorder(5,5,5,5);
		JPanel principalPanel = new JPanel(new BorderLayout());
        principalPanel.setBorder(bordePrincipalPanel);
        
		JTabbedPane tabbedPanel = new JTabbedPane();
		tabbedPanel.add("Recetas", crearRecetasPanel());
		tabbedPanel.add("Categorías", crearCategoriasPanel());
		tabbedPanel.add("Ingredientes", crearIngredientesPanel());
		tabbedPanel.add("Medidas", crearMedidasPanel());
		tabbedPanel.add("Anotaciones", crearAnotacionesPanel());
		
		principalPanel.add(tabbedPanel, BorderLayout.CENTER);
		
		JPanel inferiorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		numRecetasLabel = new JLabel("Recetas: " + recetasTable.getRowCount());
		inferiorPanel.add(numRecetasLabel);
		
		getContentPane().add(principalPanel, BorderLayout.CENTER);
		getContentPane().add(inferiorPanel, BorderLayout.SOUTH);
	}

	private JPanel crearRecetasPanel() throws ServicioException {
		
		JPanel recetasPanel = new JPanel(new BorderLayout());

		JPanel recetasIzqPanel = new JPanel(new BorderLayout());
		
		JPanel recetasCentroPanel = new JPanel(new BorderLayout());
		Border bordeRecetasCentroPanel  = new TitledBorder("Recetas:");
		recetasCentroPanel.setBorder(bordeRecetasCentroPanel);
		recetasTableModel = new RecetasTableModel();
		recetasTable = new JTable(recetasTableModel);
		recetasTable.setFillsViewportHeight(true);
		recetasTable.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(recetasTable);
		recetasCentroPanel.add(scrollPane, BorderLayout.CENTER);
		
		recetasNombreText = new JTextField();
		recetasNombreText.setColumns(15);
		recetasMSpinner = new JSpinner();
		recetasSSpinner = new JSpinner();
		recetasCategoriaCombo = new JComboBox<String>();
		cargarValoresComboCategorias();
		
		JPanel recetasTopPanel = new JPanel(new FlowLayout());
		Border bordeRecetasTopPanel  = new TitledBorder("Filtrar:");
		recetasTopPanel.setBorder(bordeRecetasTopPanel);
		recetasTopPanel.add(new JLabel("Nombre:"));
		recetasTopPanel.add(recetasNombreText);
		recetasTopPanel.add(new JLabel("Hasta:"));
		recetasTopPanel.add(recetasMSpinner);
		recetasTopPanel.add(new JLabel("M"));
		recetasTopPanel.add(recetasSSpinner);
		recetasTopPanel.add(new JLabel("S"));
		recetasTopPanel.add(new JLabel("Categoría:"));
		recetasTopPanel.add(recetasCategoriaCombo);
		
		recetasAnadirButton = new JButton("Añadir");
		recetasAnadirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onRecetasAnadirButtonActionPerformed(e);
			}
		});
		recetasEliminarButton = new JButton("Eliminar");
		recetasEliminarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onRecetasEliminarButtonActionPerformed(e);
			}
		});
		recetasEditarButton = new JButton("Editar");
		recetasEditarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onRecetasEditarButtonActionPerformed(e);
			}
		});
		
		recetasIzqPanel.add(recetasTopPanel, BorderLayout.NORTH);
		recetasIzqPanel.add(recetasCentroPanel, BorderLayout.CENTER);
		
		JPanel recetasBotonesPanel = new JPanel(new GridLayout(0,1,5,5));
		recetasBotonesPanel.add(recetasAnadirButton);
		recetasBotonesPanel.add(recetasEliminarButton);
		recetasBotonesPanel.add(recetasEditarButton);
			
		JPanel recetasDerPanel = new JPanel(new FlowLayout());
			recetasDerPanel.add(recetasBotonesPanel);
		
		recetasPanel.add(recetasIzqPanel, BorderLayout.CENTER);
		recetasPanel.add(recetasDerPanel, BorderLayout.EAST);

		return recetasPanel;
	}

	private JPanel crearCategoriasPanel() throws ServicioException {
		
		JPanel categoriasPanel = new JPanel(new BorderLayout());
		
		JPanel categoriasCentroPanel = new JPanel(new BorderLayout());
		categoriasTableModel = new CategoriasTableModel();
		categoriasTable = new JTable(categoriasTableModel);
		categoriasTable.setFillsViewportHeight(true);
		categoriasTable.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(categoriasTable);

		categoriasCentroPanel.add(scrollPane, BorderLayout.CENTER);
		
		JLabel categoriasDescripcionLabel = new JLabel("Descripción:");
		
		categoriasDescripcionText = new JTextField();
		categoriasDescripcionText.setColumns(15);
		
		categoriasAnadirButton = new JButton("Añadir");
		categoriasAnadirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					onCategoriasAnadirButtonActionPerformed(e);
				} catch (ServicioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		categoriasEliminarButton = new JButton("Eliminar");
		categoriasEliminarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					onCategoriasEliminarButtonActionPerformed(e);
				} catch (ServicioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JPanel categoriasBotonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		categoriasBotonesPanel.add(categoriasDescripcionLabel);
		categoriasBotonesPanel.add(categoriasDescripcionText);
		categoriasBotonesPanel.add(categoriasAnadirButton);
		categoriasBotonesPanel.add(categoriasEliminarButton);
			
		categoriasPanel.add(categoriasBotonesPanel, BorderLayout.NORTH);
		categoriasPanel.add(categoriasCentroPanel, BorderLayout.CENTER);

		return categoriasPanel;
	}

	private JPanel crearIngredientesPanel() throws ServicioException {
		
		JPanel ingredientesPanel = new JPanel(new BorderLayout());
		
		JPanel ingredientesCentroPanel = new JPanel(new BorderLayout());
		ingredientesTableModel = new IngredientesTableModel();
		ingredientesTable = new JTable(ingredientesTableModel);
		ingredientesTable.setFillsViewportHeight(true);
		ingredientesTable.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(ingredientesTable);

		ingredientesCentroPanel.add(scrollPane, BorderLayout.CENTER);
		
		JLabel ingredientesDescripcionLabel = new JLabel("Nombre:");
		
		ingredientesNombreText = new JTextField();
		ingredientesNombreText.setColumns(15);
		
		ingredientesAnadirButton = new JButton("Añadir");
		ingredientesAnadirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					onIngredientesAnadirButtonActionPerformed(e);
				} catch (ServicioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		ingredientesEliminarButton = new JButton("Eliminar");
		ingredientesEliminarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					onIngredientesEliminarButtonActionPerformed(e);
				} catch (ServicioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JPanel ingredientesBotonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ingredientesBotonesPanel.add(ingredientesDescripcionLabel);
		ingredientesBotonesPanel.add(ingredientesNombreText);
		ingredientesBotonesPanel.add(ingredientesAnadirButton);
		ingredientesBotonesPanel.add(ingredientesEliminarButton);
			
		ingredientesPanel.add(ingredientesBotonesPanel, BorderLayout.NORTH);
		ingredientesPanel.add(ingredientesCentroPanel, BorderLayout.CENTER);

		return ingredientesPanel;
	}
	
	private JPanel crearMedidasPanel() throws ServicioException {

		JPanel medidasPanel = new JPanel(new BorderLayout());
		
		JPanel medidasCentroPanel = new JPanel(new BorderLayout());
		medidasTableModel = new MedidasTableModel();
		medidasTable = new JTable(medidasTableModel);
		medidasTable.setFillsViewportHeight(true);
		medidasTable.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(medidasTable);

		medidasCentroPanel.add(scrollPane, BorderLayout.CENTER);
		
		JLabel medidasNombreLabel = new JLabel("Nombre:");
		JLabel medidasAbreviaturaLabel = new JLabel("Abreviatura:");
		
		medidasNombreText = new JTextField();
		medidasNombreText.setColumns(15);
		medidasAbreviaturaText = new JTextField();
		medidasAbreviaturaText.setColumns(15);
		
		medidasAnadirButton = new JButton("Añadir");
		medidasAnadirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					onMedidasAnadirButtonActionPerformed(e);
				} catch (ServicioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		medidasEliminarButton = new JButton("Eliminar");
		medidasEliminarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					onMedidasEliminarButtonActionPerformed(e);
				} catch (ServicioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JPanel medidasBotonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		medidasBotonesPanel.add(medidasNombreLabel);
		medidasBotonesPanel.add(medidasNombreText);
		medidasBotonesPanel.add(medidasAbreviaturaLabel);
		medidasBotonesPanel.add(medidasAbreviaturaText);
		medidasBotonesPanel.add(medidasAnadirButton);
		medidasBotonesPanel.add(medidasEliminarButton);
			
		medidasPanel.add(medidasBotonesPanel, BorderLayout.NORTH);
		medidasPanel.add(medidasCentroPanel, BorderLayout.CENTER);

		return medidasPanel;
	}

	private JPanel crearAnotacionesPanel() throws ServicioException {

		JPanel anotacionesPanel = new JPanel(new BorderLayout());
		
		JPanel anotacionesCentroPanel = new JPanel(new BorderLayout());
		anotacionesTableModel = new AnotacionesTableModel();
		anotacionesTable = new JTable(anotacionesTableModel);
		anotacionesTable.setFillsViewportHeight(true);
		anotacionesTable.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(anotacionesTable);

		anotacionesCentroPanel.add(scrollPane, BorderLayout.CENTER);
		
		JLabel anotacionesDescripcionLabel = new JLabel("Descripción:");
		
		anotacionesDescripcionText = new JTextField();
		anotacionesDescripcionText.setColumns(15);
		
		anotacionesAnadirButton = new JButton("Añadir");
		anotacionesAnadirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					onAnotacionesAnadirButtonActionPerformed(e);
				} catch (ServicioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		anotacionesEliminarButton = new JButton("Eliminar");
		anotacionesEliminarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					onAnotacionesEliminarButtonActionPerformed(e);
				} catch (ServicioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JPanel anotacionesBotonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		anotacionesBotonesPanel.add(anotacionesDescripcionLabel);
		anotacionesBotonesPanel.add(anotacionesDescripcionText);
		anotacionesBotonesPanel.add(anotacionesAnadirButton);
		anotacionesBotonesPanel.add(anotacionesEliminarButton);
			
		anotacionesPanel.add(anotacionesBotonesPanel, BorderLayout.NORTH);
		anotacionesPanel.add(anotacionesCentroPanel, BorderLayout.CENTER);

		return anotacionesPanel;
	}
	
	protected void onMedidasEliminarButtonActionPerformed(ActionEvent e) throws ServicioException, SQLException {
		int[] seleccionados = medidasTable.getSelectedRows();
		if (seleccionados.length > 0) {
			List<Object> eliminar = new ArrayList<Object>();
			for (int i : seleccionados) {
				i = medidasTable.convertRowIndexToModel(i);
				eliminar.add(medidasTableModel.getMedidas().get(i));
				ServicesLocator.getMedidasService().eliminarMedida(medidasTableModel.getMedidas().get(i).getId());
			}
			medidasTableModel.getMedidas().removeAll(eliminar);
			medidasTableModel.fireTableDataChanged();
			medidasTable.getSelectionModel().clearSelection();
		} else {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un libro","Editar Libro", JOptionPane.OK_OPTION);
		}
	}

	protected void onMedidasAnadirButtonActionPerformed(ActionEvent e) throws ServicioException, SQLException {
		String nombre = medidasNombreText.getText();
		String abreviatura = medidasAbreviaturaText.getText();
		if (nombre.equals("") || abreviatura.equals("")) {
			JOptionPane.showMessageDialog(this,"Debe especificar un nombre para la editorial","Añadir editorial", JOptionPane.OK_OPTION);
		} 
		else {
			MedidaItem medida = new MedidaItem();
			medida.setNombre(nombre);
			medida.setAbreviatura(abreviatura);
			List<MedidaItem> medidas = medidasTableModel.getMedidas();
			medidas.add(medida);
			medidasTableModel.setMedidas(medidas);
			medidasTableModel.fireTableDataChanged();
			ServicesLocator.getMedidasService().crearMedida(medida);
		}
	}
	
	protected void onAnotacionesEliminarButtonActionPerformed(ActionEvent e) throws ServicioException, SQLException {
		int[] seleccionados = anotacionesTable.getSelectedRows();
		if (seleccionados.length > 0) {
			List<Object> eliminar = new ArrayList<Object>();
			for (int i : seleccionados) {
				i = anotacionesTable.convertRowIndexToModel(i);
				eliminar.add(anotacionesTableModel.getAnotaciones().get(i));
				ServicesLocator.getTiposAnotacionesService().eliminarTipoAnotacion((anotacionesTableModel.getAnotaciones().get(i).getId()));
			}
			anotacionesTableModel.getAnotaciones().removeAll(eliminar);
			anotacionesTableModel.fireTableDataChanged();
			anotacionesTable.getSelectionModel().clearSelection();
		} else {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un libro","Editar Libro", JOptionPane.OK_OPTION);
		}
	}

	protected void onAnotacionesAnadirButtonActionPerformed(ActionEvent e) throws ServicioException, SQLException {
		String descripcion = anotacionesDescripcionText.getText();
		if (descripcion.equals("")) {
			JOptionPane.showMessageDialog(this,"Debe especificar un nombre para la editorial","Añadir editorial", JOptionPane.OK_OPTION);
		} 
		else {
			TipoAnotacionItem anotacion = new TipoAnotacionItem();
			anotacion.setDescripcion(descripcion);
			List<TipoAnotacionItem> anotaciones = anotacionesTableModel.getAnotaciones();
			anotaciones.add(anotacion);
			anotacionesTableModel.setAnotaciones(anotaciones);
			anotacionesTableModel.fireTableDataChanged();
			ServicesLocator.getTiposAnotacionesService().crearTipoAnotacion(anotacion);
		}
	}

	protected void onIngredientesEliminarButtonActionPerformed(ActionEvent e) throws ServicioException, SQLException {
		int[] seleccionados = ingredientesTable.getSelectedRows();
		if (seleccionados.length > 0) {
			List<Object> eliminar = new ArrayList<Object>();
			for (int i : seleccionados) {
				i = ingredientesTable.convertRowIndexToModel(i);
				eliminar.add(ingredientesTableModel.getIngredientes().get(i));
				ServicesLocator.getTiposIngredientesService().eliminarTipoIngrediente(ingredientesTableModel.getIngredientes().get(i).getId());
			}
			ingredientesTableModel.getIngredientes().removeAll(eliminar);
			ingredientesTableModel.fireTableDataChanged();
			ingredientesTable.getSelectionModel().clearSelection();
		} else {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un libro","Editar Libro", JOptionPane.OK_OPTION);
		}
	}

	protected void onIngredientesAnadirButtonActionPerformed(ActionEvent e) throws ServicioException, SQLException {
		String nombre = ingredientesNombreText.getText();
		if (nombre.equals("")) {
			JOptionPane.showMessageDialog(this,"Debe especificar un nombre para la editorial","Añadir editorial", JOptionPane.OK_OPTION);
		} 
		else {
			TipoIngredienteItem ingrediente = new TipoIngredienteItem();
			ingrediente.setNombre(nombre);
			List<TipoIngredienteItem> ingredientes = ingredientesTableModel.getIngredientes();
			ingredientes.add(ingrediente);
			ingredientesTableModel.setIngredientes(ingredientes);
			ingredientesTableModel.fireTableDataChanged();
			ServicesLocator.getTiposIngredientesService().crearTipoIngrediente(ingrediente);
		}
	}

	protected void onRecetasEditarButtonActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	protected void onRecetasEliminarButtonActionPerformed(ActionEvent e) {
		int[] seleccionados = recetasTable.getSelectedRows();
		if (seleccionados.length > 0) {
			List<Object> eliminar = new ArrayList<Object>();
			for (int i : seleccionados) {
				i = recetasTable.convertRowIndexToModel(i);
				eliminar.add(recetasTableModel.getRecetas().get(i));
			}
			recetasTableModel.getRecetas().removeAll(eliminar);
			recetasTableModel.fireTableDataChanged();
			recetasTable.getSelectionModel().clearSelection();
		} else {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un libro","Editar Libro", JOptionPane.OK_OPTION);
		}
	}

	protected void onRecetasAnadirButtonActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onCategoriasEliminarButtonActionPerformed(ActionEvent e) throws ServicioException, SQLException {
		int[] seleccionados = categoriasTable.getSelectedRows();
		if (seleccionados.length > 0) {
			List<Object> eliminar = new ArrayList<Object>();
			for (int i : seleccionados) {
				i = categoriasTable.convertRowIndexToModel(i);
				eliminar.add(categoriasTableModel.getCategorias().get(i));
				ServicesLocator.getCategoriasService().eliminarCategoria(categoriasTableModel.getCategorias().get(i).getId());
			}
			categoriasTableModel.getCategorias().removeAll(eliminar);
			categoriasTableModel.fireTableDataChanged();
			categoriasTable.getSelectionModel().clearSelection();
		} else {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un libro","Editar Libro", JOptionPane.OK_OPTION);
		}
	}

	protected void onCategoriasAnadirButtonActionPerformed(ActionEvent e) throws ServicioException, SQLException {
		String descripcion = categoriasDescripcionText.getText();
		if (descripcion.equals("")) {
			JOptionPane.showMessageDialog(this,"Debe especificar un nombre para la editorial","Añadir editorial", JOptionPane.OK_OPTION);
		} 
		else {
			CategoriaItem categoria = new CategoriaItem();
			categoria.setId(99L);
			categoria.setDescripcion(descripcion);
			
			List<CategoriaItem> categorias = categoriasTableModel.getCategorias();

			categorias.add(categoria);
			
			categoriasTableModel.setCategorias(categorias);
			categoriasTableModel.fireTableDataChanged();
			ServicesLocator.getCategoriasService().crearCategoria(categoria);
		}
	}
	
	private void cargarValoresComboCategorias() throws ServicioException {
		CategoriaItem[] categorias = ServicesLocator.getCategoriasService().ListarCategorias();
		for (int i = 0; i < categorias.length; i++) {
			recetasCategoriaCombo.addItem(categorias[i].toString());
		}
	}
	
	protected void onWindowClosing(WindowEvent e) {
		dispose();
	}
	
}
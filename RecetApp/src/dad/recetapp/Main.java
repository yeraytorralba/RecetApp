package dad.recetapp;

import org.apache.pivot.wtk.DesktopApplicationContext;

public class Main{

	
	public static void main(String[] args) {
		DesktopApplicationContext.main(RecetappApplication.class, args);
	}
}

//		System.out.println("Prueba de conexion: "+BaseDatos.test());
//		
//		CategoriaItem categoria = new CategoriaItem();
//		categoria.setDescripcion("Pescados");
//		
//		try {
//			ServiceLocator.getCategoriasService().crearCategoria(categoria);
//		} catch (ServiceException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//		}
//		
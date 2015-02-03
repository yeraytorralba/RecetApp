package dad.recetapp.services;

import java.sql.SQLException;

import dad.recetapp.items.CategoriaItem;

public interface CategoriasService {

	public void crearCategoria(CategoriaItem categoria) throws ServicioException, SQLException;
	public void modificarCategoria(CategoriaItem categoria) throws ServicioException;
	public void eliminarCategoria(Long id) throws ServicioException, SQLException;
	public CategoriaItem[] ListarCategorias() throws ServicioException;
	public CategoriaItem ObtenerCategoria(Long id) throws ServicioException;
	
}

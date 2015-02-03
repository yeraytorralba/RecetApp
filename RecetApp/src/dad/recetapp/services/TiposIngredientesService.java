package dad.recetapp.services;

import java.sql.SQLException;

import dad.recetapp.items.TipoIngredienteItem;

public interface TiposIngredientesService {

	public void crearTipoIngrediente(TipoIngredienteItem tipo) throws ServicioException, SQLException;
	public void modificarTipoIngrediente(TipoIngredienteItem tipo) throws ServicioException;
	public void eliminarTipoIngrediente(Long id) throws ServicioException, SQLException;
	public TipoIngredienteItem[] listarTiposIngredientes() throws ServicioException;
	public TipoIngredienteItem obtenerTipoIngrediente(Long id) throws ServicioException;
	
}
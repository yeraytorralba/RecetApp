package dad.recetapp.services;

import java.sql.SQLException;

import dad.recetapp.items.TipoAnotacionItem;

public interface TiposAnotacionesService {

	public void crearTipoAnotacion(TipoAnotacionItem tipo) throws ServicioException, SQLException;
	public void modificarTipoAnotacion(TipoAnotacionItem tipo) throws ServicioException;
	public void eliminarTipoAnotacion(Long id) throws ServicioException, SQLException;
	public TipoAnotacionItem[] listarTiposAnotaciones() throws ServicioException;
	public TipoAnotacionItem obtenerTipoAnotacion(Long id) throws ServicioException;
	
}
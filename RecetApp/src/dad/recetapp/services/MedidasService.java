package dad.recetapp.services;

import java.sql.SQLException;

import dad.recetapp.items.MedidaItem;

public interface MedidasService {

	public void crearMedida(MedidaItem medida) throws ServicioException;
	public void modificarMedida(MedidaItem medida) throws ServicioException;
	public void eliminarMedida(Long id) throws ServicioException;
	public MedidaItem[] ListarMedidas() throws ServicioException;
	public MedidaItem ObtenerMedida(Long id) throws ServicioException;
	
}

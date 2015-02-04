package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.BaseDatos;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ITiposAnotacionesService;
import dad.recetapp.services.items.TipoAnotacionItem;

public class TiposAnotacionesService implements ITiposAnotacionesService {

	@Override
	public void crearTipoAnotacion(TipoAnotacionItem tipo) throws ServiceException {
		try{
			if(tipo == null || tipo.getDescripcion() == null)
				throw new ServiceException("Error al crear el tipo de anotación: El tipo no puede ser nulo.");
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("INSERT INTO tipos_anotaciones (descripcion) VALUES (?)",PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1,tipo.getDescripcion());
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			Long id = null;
			if (rs.next()) {
				id = rs.getLong(1);
				tipo.setId(id);
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al crear el tipo de anotación '"+tipo.getDescripcion()+"': "+ e.getMessage());
		} catch (NullPointerException e) {
			throw new ServiceException("Error al crear el tipo de anotación: El tipo no puede ser nulo.");
		}
	}

	@Override
	public void modificarTipoAnotacion(TipoAnotacionItem tipo) throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("UPDATE tipos_anotaciones SET descripcion = ? WHERE id = ?");
			statement.setString(1, tipo.getDescripcion());
			statement.setLong(2, tipo.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al modificar el tipo de anotación '"+tipo.getDescripcion()+"': "+ e.getMessage());
		} catch (NullPointerException e) {
			throw new ServiceException("Error al modificar el tipo de anotación: El tipo no puede ser nulo.");
		}
	}

	@Override
	public void eliminarTipoAnotacion(Long id) throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("DELETE FROM tipos_anotaciones WHERE id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al eliminar el tipo de anotación '"+id+"': "+ e.getMessage());
		} catch (NullPointerException e){
			throw new ServiceException("Error al eliminar el tipo de anotación: El tipo no puede ser nulo.");
		}
	}

	@Override
	public List<TipoAnotacionItem> listarTiposAnotaciones() throws ServiceException {
		List<TipoAnotacionItem> tipos = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM tipos_anotaciones ORDER BY descripcion");
			ResultSet rs = statement.executeQuery();
			tipos = new ArrayList<TipoAnotacionItem>();
			while(rs.next()) {
				TipoAnotacionItem tipo = new TipoAnotacionItem();
				tipo.setId(rs.getLong("id"));
				tipo.setDescripcion(rs.getString("descripcion"));
				tipos.add(tipo);
			}
			statement.close();
		} catch(SQLException e) {
			throw new ServiceException("Error al recuperar los tipos de anotación", e);
		}
		return tipos;
	}

	@Override
	public TipoAnotacionItem obtenerTipoAnotacion(Long id) throws ServiceException {
		if(id == null)
			throw new ServiceException("Error al recuperar el tipo de anotación: Debe especificar un id válido.");
		TipoAnotacionItem tipo = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM tipos_anotaciones WHERE id = ?");
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				tipo = new TipoAnotacionItem();
				tipo.setId(id);
				tipo.setDescripcion(rs.getString("descripcion"));
			}
			statement.close();
		} catch(SQLException e) {
			throw new ServiceException("Error al recuperar el tipo de anotación con ID '"+id+"'", e);
		}
		return tipo;
	}

}

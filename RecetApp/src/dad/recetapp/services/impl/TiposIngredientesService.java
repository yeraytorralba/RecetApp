package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.BaseDatos;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ITiposIngredientesService;
import dad.recetapp.services.items.TipoIngredienteItem;

public class TiposIngredientesService implements ITiposIngredientesService {

	@Override
	public void crearTipoIngrediente(TipoIngredienteItem tipo)
			throws ServiceException {
		try {
			if (tipo == null || tipo.getNombre() == null)
				throw new ServiceException(
						"Error al crear el tipo de instruccion: El tipo no puede ser nulo.");
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO tipos_ingredientes (nombre) VALUES (?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, tipo.getNombre());
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
			throw new ServiceException(
					"Error al crear el tipo de instruccion '"
							+ tipo.getNombre() + "': " + e.getMessage());
		} catch (NullPointerException e) {
			throw new ServiceException(
					"Error al crear el tipo de instruccion: El tipo no puede ser nulo.");
		}
	}

	@Override
	public void modificarTipoIngrediente(TipoIngredienteItem tipo)
			throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE tipos_ingredientes SET nombre = ? WHERE id = ?");
			statement.setString(1, tipo.getNombre());
			statement.setLong(2, tipo.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException(
					"Error al modificar el tipo de instruccion '"
							+ tipo.getNombre() + "': " + e.getMessage());
		} catch (NullPointerException e) {
			throw new ServiceException(
					"Error al modificar el tipo de instruccion: El tipo no puede ser nulo.");
		}
	}

	@Override
	public void eliminarTipoIngrediente(Long id) throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("DELETE FROM tipos_ingredientes WHERE id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException(
					"Error al eliminar el tipo de instruccion '" + id + "': "
							+ e.getMessage());
		} catch (NullPointerException e) {
			throw new ServiceException(
					"Error al eliminar el tipo de instruccion: El tipo no puede ser nulo.");
		}
	}

	@Override
	public List<TipoIngredienteItem> listarTiposIngredientes()
			throws ServiceException {
		List<TipoIngredienteItem> tipos = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT * FROM tipos_ingredientes ORDER BY nombre");
			ResultSet rs = statement.executeQuery();
			tipos = new ArrayList<TipoIngredienteItem>();
			while (rs.next()) {
				TipoIngredienteItem tipo = new TipoIngredienteItem();
				tipo.setId(rs.getLong("id"));
				tipo.setNombre(rs.getString("nombre"));
				tipos.add(tipo);
			}
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException(
					"Error al recuperar los tipos de instruccion", e);
		}
		return tipos;
	}

	@Override
	public TipoIngredienteItem obtenerTiposIngredientes(Long id)
			throws ServiceException {
		if (id == null)
			throw new ServiceException(
					"Error al recuperar el tipo de instruccion: Debe especificar un id válido.");
		TipoIngredienteItem tipo = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT * FROM tipos_ingredientes WHERE id = ?");
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				tipo = new TipoIngredienteItem();
				tipo.setId(id);
				tipo.setNombre(rs.getString("nombre"));
			}
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException(
					"Error al recuperar el tipo de instruccion con ID '" + id
							+ "'", e);
		}
		return tipo;
	}

}

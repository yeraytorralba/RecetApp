package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.BaseDatos;
import dad.recetapp.services.IMedidasService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.MedidaItem;

public class MedidasService implements IMedidasService {

	@Override
	public void crearMedida(MedidaItem medida) throws ServiceException {
		try{
			if(medida == null || medida.getNombre()==null){
				throw new ServiceException("Error al crear la medida: La medida no puede ser nula.");
			}
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("INSERT INTO medidas (nombre, abreviatura) VALUES (?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, medida.getNombre());
			statement.setString(2, medida.getAbreviatura());
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			Long id = null;
			if (rs.next()) {
				id = rs.getLong(1);
				medida.setId(id);
			}
			rs.close();
			statement.close();
		} catch (SQLException e){
			throw new ServiceException("Error al crear la medida '"+medida.getNombre()+"': "+ e.getMessage());
		} catch (NullPointerException e){
			throw new ServiceException("Error al crear la medida: La medida no puede ser nula.");
		}
	}

	@Override
	public void modificarMedida(MedidaItem medida) throws ServiceException {
		try{
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("UPDATE medidas SET nombre = ?, abreviatura = ? WHERE id = ?");
			statement.setString(1, medida.getNombre());
			statement.setString(2, medida.getAbreviatura());
			statement.setLong(3, medida.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e){
			throw new ServiceException("Error al modificar la medida '"+medida.getNombre()+"': "+ e.getMessage());
		} catch (NullPointerException e){
			throw new ServiceException("Error al modificar la medida: La medida no puede ser nula.");
		}
	}

	@Override
	public void eliminarMedida(Long id) throws ServiceException {
		try{
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("DELETE FROM medidas WHERE id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e){
			throw new ServiceException("Error al eliminar la medida '"+id+"': "+ e.getMessage());
		} catch (NullPointerException e){
			throw new ServiceException("Error al eliminar la medida: La medida no puede ser nula.");
		}
	}

	@Override
	public List<MedidaItem> listarMedidas() throws ServiceException{
		List<MedidaItem> medidas = null;
		try{
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM medidas ORDER BY nombre");
			ResultSet rs = statement.executeQuery();
			medidas = new ArrayList<MedidaItem>();
			while(rs.next()){
				MedidaItem medida = new MedidaItem();
				medida.setId(rs.getLong("id"));
				medida.setNombre(rs.getString("nombre"));
				medida.setAbreviatura(rs.getString("abreviatura"));
				medidas.add(medida);
			}
			statement.close();
		} catch(SQLException e){
			throw new ServiceException("Error al recuperar las medidas", e);
		}
		return medidas;
	}

	@Override
	public MedidaItem obtenerMedida(Long id) throws ServiceException {
		if(id == null){
			throw new ServiceException("Error al recuperar la medida: Debe especificar un id válido.");
		}
		MedidaItem medida = null;
		try{
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM medidas WHERE id = ?");
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				medida = new MedidaItem();
				medida.setId(id);
				medida.setNombre(rs.getString("nombre"));
				medida.setAbreviatura(rs.getString("abreviatura"));
			}
			statement.close();
		} catch(SQLException e){
			throw new ServiceException("Error al recuperar la medida con ID '"+id+"'", e);
		}
		return medida;
	}

}

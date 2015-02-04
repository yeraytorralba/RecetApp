package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.BaseDatos;
import dad.recetapp.services.ICategoriasService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.CategoriaItem;

public class CategoriasService implements ICategoriasService {

	public CategoriasService() {}

	@Override
	public void crearCategoria(CategoriaItem categoria)	throws ServiceException {
		try{
			if(categoria == null || categoria.getDescripcion() == null){
				throw new ServiceException("Error al crear la categoría: La categoría no puede ser nula.");
			}
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("INSERT INTO categorias (descripcion) VALUES (?)",PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, categoria.getDescripcion());
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			Long id = null;
			if (rs.next()) {
				id = rs.getLong(1);
				categoria.setId(id);
			}
			rs.close();
			statement.close();
		} catch (SQLException e){
			throw new ServiceException("Error al crear la categoría '"+categoria.getDescripcion()+"': "+ e.getMessage());
		} catch (NullPointerException e){
			throw new ServiceException("Error al crear la categoría: La categoría no puede ser nula.");
		}
	}

	@Override
	public void modificarCategoria(CategoriaItem categoria)throws ServiceException {
		try{
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("UPDATE categorias SET descripcion = ? WHERE id = ?");
			statement.setString(1, categoria.getDescripcion());
			statement.setLong(2, categoria.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e){
			throw new ServiceException("Error al modificar la categoría '"+categoria.getDescripcion()+"': "+ e.getMessage());
		} catch (NullPointerException e){
			throw new ServiceException("Error al modificar la categoría: La categoría no puede ser nula.");
		}
	}

	@Override
	public void eliminarCategoria(Long id) throws ServiceException {
		try{
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("DELETE FROM categorias WHERE id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e){
			throw new ServiceException("Error al eliminar la categoría '"+id+"': "+ e.getMessage());
		} catch (NullPointerException e){
			throw new ServiceException("Error al eliminar la categoría: La categoría no puede ser nula.");
		}
	}

	@Override
	public List<CategoriaItem> listarCategorias() throws ServiceException {
		List<CategoriaItem> categorias = null;
		try{
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM categorias ORDER BY descripcion");
			ResultSet rs = statement.executeQuery();
			categorias = new ArrayList<CategoriaItem>();
			while(rs.next()){
				CategoriaItem categoria = new CategoriaItem();
				categoria.setId(rs.getLong("id"));
				categoria.setDescripcion(rs.getString("descripcion"));
				categorias.add(categoria);
			}
			statement.close();
		} catch(SQLException e){
			throw new ServiceException("Error al recuperar las categorias", e);
		}
		return categorias;
	}

	@Override
	public CategoriaItem obtenerCategoria(Long id) throws ServiceException {
		if(id == null){
			throw new ServiceException("Error al recuperar la categoría: Debe especificar un id válido.");
		}
		CategoriaItem categoria = null;
		try{
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM categorias WHERE id = ?");
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				categoria = new CategoriaItem();
				categoria.setId(id);
				categoria.setDescripcion(rs.getString("descripcion"));
			}
			statement.close();
		} catch(SQLException e){
			throw new ServiceException("Error al recuperar la categoria con ID '"+id+"'", e);
		}
		return categoria;
	}

}

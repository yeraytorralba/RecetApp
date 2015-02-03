package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.DataBase;
import dad.recetapp.items.CategoriaItem;
import dad.recetapp.services.CategoriasService;
import dad.recetapp.services.ServicioException;

public class CategoriasServiceDB implements CategoriasService{

	@Override
	public void crearCategoria(CategoriaItem categoria) throws ServicioException {
		try {
			
		Connection conn = DataBase.getConnection();
		Statement st =  conn.createStatement(); 
			
		Integer valor = null;
        ResultSet ultimoID = st.executeQuery("SELECT LAST_INSERT_ID() from categorias");
        while(ultimoID.next()){
        	valor = ultimoID.getInt(1);
        }
		
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO categorias VALUES(?,?)");
		
		if (valor == null) {
			stmt.setInt(1, 1);
		}
		else{
			stmt.setInt(1, valor);
		}
		stmt.setString(2, categoria.getDescripcion());
		stmt.executeUpdate();
		stmt.close();
		conn.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void modificarCategoria(CategoriaItem categoria) throws ServicioException {
		try {
			
		Connection conn = DataBase.getConnection();
		 
		  PreparedStatement stmt = conn.prepareStatement("UPDATE categorias SET descripcion=? WHERE id=?");
		 
		  stmt.setString(1, categoria.getDescripcion());
		  stmt.setLong(2,categoria.getId());

		  stmt.executeUpdate();
		  stmt.close();
		  conn.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void eliminarCategoria(Long id) throws ServicioException{
		try {
			
		
		Connection conn = DataBase.getConnection();
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM categorias WHERE id = ?");
		
		stmt.setLong(1, id);
		stmt.executeUpdate();
		stmt.close();
		conn.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public CategoriaItem[] ListarCategorias() throws ServicioException {
		List<CategoriaItem> categorias = new ArrayList<CategoriaItem>();
		CategoriaItem[] arrayCategorias = {};
		
		try {
			
			Connection conn = DataBase.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select id,descripcion from categorias");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				CategoriaItem categoria = new CategoriaItem();
				categoria.setId(rs.getLong(1));
				categoria.setDescripcion(rs.getString(2));
				categorias.add(categoria);
			}
			rs.close();
			stmt.close();
			conn.close();
			arrayCategorias = categorias.toArray(new CategoriaItem[categorias.size()]);
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return arrayCategorias;
	}

	@Override
	public CategoriaItem ObtenerCategoria(Long id) throws ServicioException {

		CategoriaItem categoria = new CategoriaItem();
		try {
			
		 
		 Connection conn = DataBase.getConnection();
		 PreparedStatement stmt = conn.prepareStatement("select id,descripcion from categorias where = ?");
		 stmt.setLong(1, id);
		 ResultSet rs = stmt.executeQuery();
		 
		 while (rs.next()) {
		 categoria.setId(rs.getLong(1));
		 categoria.setDescripcion(rs.getString(2));
		 }
		 rs.close();
		 stmt.close();
		 conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return categoria;
	}

	

}

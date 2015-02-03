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
	public void crearCategoria(CategoriaItem categoria) throws ServicioException, SQLException {
		
		Connection conn = DataBase.getConnection();
		Statement st =  conn.createStatement(); 
			
		Integer valor = null;
        ResultSet ultimoID = st.executeQuery("SELECT LAST_INSERT_ID() from categorias");
        while(ultimoID.next()){
        	valor = ultimoID.getInt(1);
        }
		
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO categorias VALUES(?,?)");
		//TODO ARREGLAR LA INSERCION
		stmt.setInt(1, valor);
		stmt.setString(2, categoria.getDescripcion());
		stmt.executeUpdate();
		stmt.close();
		
	}

	@Override
	public void modificarCategoria(CategoriaItem categoria) throws ServicioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarCategoria(Long id) throws ServicioException, SQLException {
		
		Connection conn = DataBase.getConnection();
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM categorias WHERE id = ?");
		//TODO ARREGLAR LA INSERCION
		stmt.setLong(1, id);
		stmt.executeUpdate();
		stmt.close();
		
	}

	@Override
	public CategoriaItem[] ListarCategorias() throws ServicioException {
		List<CategoriaItem> categorias = new ArrayList<CategoriaItem>();
		CategoriaItem[] arrayCategorias = {};
		
		try {
			
			Connection conn = DataBase.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select descripcion from categorias");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				CategoriaItem categoria = new CategoriaItem();
				categoria.setDescripcion(rs.getString(1));
				categorias.add(categoria);
			}
			rs.close();
			stmt.close();
			
			arrayCategorias = categorias.toArray(new CategoriaItem[categorias.size()]);
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return arrayCategorias;
	}

	@Override
	public CategoriaItem ObtenerCategoria(Long id) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	

}

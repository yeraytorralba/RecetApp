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
import dad.recetapp.items.TipoIngredienteItem;
import dad.recetapp.services.ServicioException;
import dad.recetapp.services.TiposIngredientesService;

public class TiposIngredientesServiceDB implements  TiposIngredientesService{

	@Override
	public void crearTipoIngrediente(TipoIngredienteItem ingrediente) throws ServicioException {
		try {
			
		
		Connection conn = DataBase.getConnection();
		Statement st =  conn.createStatement(); 
			
		Integer valor = null;
        ResultSet ultimoID = st.executeQuery("SELECT LAST_INSERT_ID() from tipos_ingredientes");
        while(ultimoID.next()){
        	valor = ultimoID.getInt(1);
        }
		
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO tipos_ingredientes VALUES(?,?)");
		if (valor == null) {
			stmt.setInt(1, 1);
		}
		else{
			stmt.setInt(1, valor);
		}
		stmt.setString(2, ingrediente.getNombre());
		stmt.executeUpdate();
		stmt.close();
		conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void modificarTipoIngrediente(TipoIngredienteItem tipo) throws ServicioException {
		try {
			
	
		Connection conn = DataBase.getConnection();
		 
		  PreparedStatement stmt = conn.prepareStatement("UPDATE tipos_ingredientes SET nombre=? WHERE id=?");
		 
		  stmt.setString(1, tipo.getNombre());
		  stmt.setLong(2,tipo.getId());

		  stmt.executeUpdate();
		  stmt.close();
		  conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void eliminarTipoIngrediente(Long id) throws ServicioException {
		try {
			
		
		Connection conn = DataBase.getConnection();
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM tipos_ingredientes WHERE id = ?");
	
		stmt.setLong(1, id);
		stmt.executeUpdate();
		stmt.close();
		conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public TipoIngredienteItem[] listarTiposIngredientes() throws ServicioException {
		List<TipoIngredienteItem> ingredientes = new ArrayList<TipoIngredienteItem>();
		TipoIngredienteItem[] arrayIngredientes = {};
		
		try {
			
			Connection conn = DataBase.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select id,nombre from tipos_ingredientes");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				TipoIngredienteItem ingrediente = new TipoIngredienteItem();
				ingrediente.setId(rs.getLong(1));
				ingrediente.setNombre(rs.getString(2));
				ingredientes.add(ingrediente);
			}
			rs.close();
			stmt.close();
			conn.close();
			
			arrayIngredientes = ingredientes.toArray(new TipoIngredienteItem[ingredientes.size()]);
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return arrayIngredientes;
	}

	@Override
	public TipoIngredienteItem obtenerTipoIngrediente(Long id) throws ServicioException {
		TipoIngredienteItem ingrediente = new TipoIngredienteItem();
		 try {
	
		 Connection conn = DataBase.getConnection();
		 PreparedStatement stmt = conn.prepareStatement("select id,nombre from tipos_ingredientes where = ?");
		 stmt.setLong(1, id);
		 ResultSet rs = stmt.executeQuery();
		 
		 while (rs.next()) {
		 ingrediente.setId(rs.getLong(1));
		 ingrediente.setNombre(rs.getString(2));
		 }
		 rs.close();
		 stmt.close();
		 conn.close();
			
			} catch (Exception e) {
				// TODO: handle exception
			}
		 return ingrediente;
	}


}

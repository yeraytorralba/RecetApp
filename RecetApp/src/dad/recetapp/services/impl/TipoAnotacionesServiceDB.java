package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.DataBase;
import dad.recetapp.items.AnotacionItem;
import dad.recetapp.items.TipoAnotacionItem;
import dad.recetapp.items.TipoIngredienteItem;
import dad.recetapp.services.ServicioException;
import dad.recetapp.services.TiposAnotacionesService;

public class TipoAnotacionesServiceDB implements TiposAnotacionesService{

	@Override
	public void crearTipoAnotacion(TipoAnotacionItem tipo) throws ServicioException {
		
		try {
			
		
		Connection conn = DataBase.getConnection();
		Statement st =  conn.createStatement(); 
			
		Integer valor = null;
        ResultSet ultimoID = st.executeQuery("SELECT LAST_INSERT_ID() from tipos_anotaciones");
        while(ultimoID.next()){
        	valor = ultimoID.getInt(1);
        }
		
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO tipos_anotaciones VALUES(?,?)");
		if (valor == null) {
			stmt.setInt(1, 1);
		}
		else{
			stmt.setInt(1, valor);
		}
		stmt.setString(2, tipo.getDescripcion());
		stmt.executeUpdate();
		stmt.close();
		conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void modificarTipoAnotacion(TipoAnotacionItem tipo) throws ServicioException  {
		try {
			
		
		Connection conn = DataBase.getConnection();
		 
		  PreparedStatement stmt = conn.prepareStatement("UPDATE tipos_anotaciones SET descripcion=? WHERE id=?");
		 
		  stmt.setString(1, tipo.getDescripcion());
		  stmt.setLong(2,tipo.getId());

		  stmt.executeUpdate();
		  stmt.close();
		  conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void eliminarTipoAnotacion(Long id) throws ServicioException {
		try {
			
		
		Connection conn = DataBase.getConnection();
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM tipos_anotaciones WHERE id = ?");


		stmt.setLong(1, id);
		stmt.executeUpdate();
		stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public TipoAnotacionItem[] listarTiposAnotaciones() throws ServicioException {
		
		List<TipoAnotacionItem> anotaciones = new ArrayList<TipoAnotacionItem>();
		TipoAnotacionItem[] arrayAnotaciones = {};
		
		try {
			
			Connection conn = DataBase.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select id,descripcion from tipos_anotaciones");
			
			while (rs.next()) {
				TipoAnotacionItem anotacion = new TipoAnotacionItem();
				anotacion.setId(rs.getLong(1));
				anotacion.setDescripcion(rs.getString(2));
				anotaciones.add(anotacion);
			}
			rs.close();
			stmt.close();
			
			arrayAnotaciones = anotaciones.toArray(new TipoAnotacionItem[anotaciones.size()]);
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return arrayAnotaciones;
	}

	@Override
	public TipoAnotacionItem obtenerTipoAnotacion(Long id) throws ServicioException {
		TipoAnotacionItem anotocion = new TipoAnotacionItem();
		try { 
		 Connection conn;
		
			conn = DataBase.getConnection();
		
		 PreparedStatement stmt = conn.prepareStatement("select id,descripcion from tipos_anotaciones where = ?");
		 stmt.setLong(1, id);
		 ResultSet rs = stmt.executeQuery();
		 
		 while (rs.next()) {
		 anotocion.setId(rs.getLong(1));
		 anotocion.setDescripcion(rs.getString(2));
		 }
		 rs.close();
		 stmt.close();
		 conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return anotocion;
	}

}

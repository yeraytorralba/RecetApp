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
import dad.recetapp.items.MedidaItem;
import dad.recetapp.services.MedidasService;
import dad.recetapp.services.ServicioException;

public class MedidasServiceDB implements MedidasService{

	@Override
	public void crearMedida(MedidaItem medida) throws ServicioException {
		try {
		
		Connection conn = DataBase.getConnection();
		Statement st =  conn.createStatement(); 
			
		Integer valor = null;
        ResultSet ultimoID = st.executeQuery("SELECT LAST_INSERT_ID() from medidas");
        while(ultimoID.next()){
        	valor = ultimoID.getInt(1);
        }
		ultimoID.close();

		PreparedStatement stmt = conn.prepareStatement("INSERT INTO medidas VALUES(?,?,?)");
		
		if (valor == null) {
			stmt.setInt(1, 1);
		}
		else{
			stmt.setInt(1, valor);
		}
		
		
		stmt.setString(2, medida.getNombre());
		stmt.setString(3, medida.getAbreviatura());
		stmt.executeUpdate();
		stmt.close();
		conn.close();
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void modificarMedida(MedidaItem medida) throws ServicioException {
		try {
			
		
		Connection conn = DataBase.getConnection(); 
		 PreparedStatement stmt = conn.prepareStatement("UPDATE medidas SET nombre=?, abreviatura=? WHERE id=?");

		 stmt.setString(1, medida.getNombre());
		 stmt.setString(2, medida.getAbreviatura());
		 stmt.setLong(3,medida.getId());

		 stmt.executeUpdate();
		 stmt.close();
		 conn.close();
		 } catch (Exception e) {
				// TODO: handle exception
			}
		
	}

	@Override
	public void eliminarMedida(Long id) throws ServicioException {
		try {
			
	
		Connection conn = DataBase.getConnection();
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM medidas WHERE id = ?");
		
		stmt.setLong(1, id);
		stmt.executeUpdate();
		stmt.close();
		conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public MedidaItem[] ListarMedidas() throws ServicioException {
		
		List<MedidaItem> medidas = new ArrayList<MedidaItem>();
		MedidaItem[] arrayMedidas = {};
		
		try {
			
			Connection conn = DataBase.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select id,nombre,abreviatura from medidas");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				MedidaItem medida = new MedidaItem();
				medida.setId(rs.getLong(1));
				medida.setNombre(rs.getString(2));
				medida.setAbreviatura(rs.getString(3));
				medidas.add(medida);
			}
			rs.close();
			stmt.close();
			conn.close();
			
			arrayMedidas = medidas.toArray(new MedidaItem[medidas.size()]);
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return arrayMedidas;
	}

	@Override
	public MedidaItem ObtenerMedida(Long id) throws ServicioException {
		MedidaItem medida = new MedidaItem();
		 try {
			
		
		 Connection conn = DataBase.getConnection();
		 PreparedStatement stmt = conn.prepareStatement("select id,nombre,abreviatura from medidas where = ?");
		 stmt.setLong(1, id);
		 ResultSet rs = stmt.executeQuery();
		 
		 while (rs.next()) {
		 medida.setId(rs.getLong(1));
		 medida.setNombre(rs.getString(2));
		 medida.setAbreviatura(rs.getString(3));
		 }
		 rs.close();
		 stmt.close();
		 conn.close();
		 } catch (Exception e) {
				// TODO: handle exception
			}
		 return medida;
	}

}

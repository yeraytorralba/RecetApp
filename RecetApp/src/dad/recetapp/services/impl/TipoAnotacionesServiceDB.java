package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import dad.recetapp.db.DataBase;
import dad.recetapp.items.TipoAnotacionItem;
import dad.recetapp.services.ServicioException;
import dad.recetapp.services.TiposAnotacionesService;

public class TipoAnotacionesServiceDB implements TiposAnotacionesService{

	@Override
	public void crearTipoAnotacion(TipoAnotacionItem tipo) throws ServicioException, SQLException {
		
		Connection conn = DataBase.getConnection();
		Statement st =  conn.createStatement(); 
			
		Integer valor = null;
        ResultSet ultimoID = st.executeQuery("SELECT LAST_INSERT_ID() from tipos_anotaciones");
        while(ultimoID.next()){
        	valor = ultimoID.getInt(1);
        }
		
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO tipos_anotaciones VALUES(?,?)");
		//TODO ARREGLAR LA INSERCION
		stmt.setInt(1, valor);
		stmt.setString(2, tipo.getDescripcion());
		stmt.executeUpdate();
		stmt.close();
		
	}

	@Override
	public void modificarTipoAnotacion(TipoAnotacionItem tipo) throws ServicioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarTipoAnotacion(Long id) throws ServicioException, SQLException {
		
		Connection conn = DataBase.getConnection();
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM tipos_anotaciones WHERE id = ?");
		//TODO ARREGLAR LA INSERCION
		stmt.setLong(1, id);
		stmt.executeUpdate();
		stmt.close();
		
	}

	@Override
	public TipoAnotacionItem[] listarTiposAnotaciones() throws ServicioException {
		
		List<TipoAnotacionItem> anotaciones = new ArrayList<TipoAnotacionItem>();
		TipoAnotacionItem[] arrayAnotaciones = null;
		
		try {
			
			Connection conn = DataBase.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select descripcion from tipos_anotaciones");
			
			while (rs.next()) {
				TipoAnotacionItem anotacion = new TipoAnotacionItem();
				anotacion.setDescripcion(rs.getString(1));
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
		// TODO Auto-generated method stub
		return null;
	}

}

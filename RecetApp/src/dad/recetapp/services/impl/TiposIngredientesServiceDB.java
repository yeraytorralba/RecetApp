package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.DataBase;
import dad.recetapp.items.TipoIngredienteItem;
import dad.recetapp.services.ServicioException;
import dad.recetapp.services.TiposIngredientesService;

public class TiposIngredientesServiceDB implements  TiposIngredientesService{

	@Override
	public void crearTipoIngrediente(TipoIngredienteItem ingrediente) throws ServicioException, SQLException {
		
		Connection conn = DataBase.getConnection();
		Statement st =  conn.createStatement(); 
			
		Integer valor = null;
        ResultSet ultimoID = st.executeQuery("SELECT LAST_INSERT_ID() from tipos_ingredientes");
        while(ultimoID.next()){
        	valor = ultimoID.getInt(1);
        }
		
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO tipos_ingredientes VALUES(?,?)");
		//TODO ARREGLAR LA INSERCION
		stmt.setInt(1, valor);
		stmt.setString(2, ingrediente.getNombre());
		stmt.executeUpdate();
		stmt.close();
		
	}

	@Override
	public void modificarTipoIngrediente(TipoIngredienteItem tipo) throws ServicioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarTipoIngrediente(Long id) throws ServicioException, SQLException {
		
		Connection conn = DataBase.getConnection();
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM tipos_ingredientes WHERE id = ?");
		//TODO ARREGLAR LA INSERCION
		stmt.setLong(1, id);
		stmt.executeUpdate();
		stmt.close();
		
	}

	@Override
	public TipoIngredienteItem[] listarTiposIngredientes() throws ServicioException {
		List<TipoIngredienteItem> ingredientes = new ArrayList<TipoIngredienteItem>();
		TipoIngredienteItem[] arrayIngredientes = {};
		
		try {
			
			Connection conn = DataBase.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select nombre from tipos_ingredientes");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				TipoIngredienteItem ingrediente = new TipoIngredienteItem();
				ingrediente.setNombre(rs.getString(1));
				ingredientes.add(ingrediente);
			}
			rs.close();
			stmt.close();
			
			arrayIngredientes = ingredientes.toArray(new TipoIngredienteItem[ingredientes.size()]);
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return arrayIngredientes;
	}

	@Override
	public TipoIngredienteItem obtenerTipoIngrediente(Long id) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}


}

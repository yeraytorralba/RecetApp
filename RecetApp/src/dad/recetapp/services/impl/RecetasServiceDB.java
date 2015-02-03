package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dad.recetapp.db.DataBase;
import dad.recetapp.items.RecetaItem;
import dad.recetapp.items.RecetaListItem;
import dad.recetapp.services.RecetasService;
import dad.recetapp.services.ServicioException;

public class RecetasServiceDB implements RecetasService{

	@Override
	public void crearReceta(RecetaItem receta) throws ServicioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarReceta(RecetaItem receta) throws ServicioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarReceta(Long id) throws ServicioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RecetaListItem listarRecetas() throws ServicioException {
		RecetaListItem recetasListItem = new RecetaListItem();
		List<RecetaItem> recetasList = new ArrayList<RecetaItem>();
		try {
			
			Connection conn = DataBase.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select nombre, para, tiempo_total, fecha_creacion, id_categoria from recetas");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				RecetaItem receta = new RecetaItem();
				receta.setNombre(rs.getString(1));
				receta.setPara(rs.getString(2));
				receta.setTiempoTotal(rs.getInt(3));
				receta.setFechaCreacion(rs.getDate(4));
				receta.setCategoria(rs.getLong(5));
				recetasList.add(receta);
			}
			rs.close();
			stmt.close();
			
			recetasListItem.setRecetas(recetasList);
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return recetasListItem;
	}

	@Override
	public RecetaItem obtenerReceta(Long id) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecetaListItem buscarRecetas(String nombre, Integer tiempoTotal,
			Long idCategoria) throws ServicioException {
		// TODO Auto-generated method stub
		return null;
	}



}

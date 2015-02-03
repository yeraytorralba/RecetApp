package dad.recetapp.services;

import dad.recetapp.items.RecetaItem;
import dad.recetapp.items.RecetaListItem;

public interface RecetasService {

	public void crearReceta(RecetaItem receta) throws ServicioException;
	public void modificarReceta(RecetaItem receta) throws ServicioException;
	public void eliminarReceta(Long id) throws ServicioException;
	public RecetaListItem listarRecetas() throws ServicioException;
	public RecetaItem obtenerReceta(Long id) throws ServicioException;
	public RecetaListItem buscarRecetas(String nombre, Integer tiempoTotal, Long idCategoria) throws ServicioException;
	
}
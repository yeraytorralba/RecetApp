package dad.recetapp.services.items;

import java.util.List;

public class SeccionItem {

	private Long id;
	private String nombre;
	private List<InstruccionItem> instrucciones;
	private List<IngredienteItem> ingredientes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<InstruccionItem> getInstrucciones() {
		return instrucciones;
	}
	public void setInstrucciones(List<InstruccionItem> instrucciones) {
		this.instrucciones = instrucciones;
	}
	public List<IngredienteItem> getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(List<IngredienteItem> ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	public String toString(){
		return nombre;
	}
}

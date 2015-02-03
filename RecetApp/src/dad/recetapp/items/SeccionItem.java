package dad.recetapp.items;

import java.util.List;

public class SeccionItem {

	Long id;
	String nombre;
	List<IngredienteItem> ingredientes;
	List<InstruccionItem> instrucciones;
	
	public SeccionItem() {}

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

	public List<IngredienteItem> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<IngredienteItem> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public List<InstruccionItem> getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(List<InstruccionItem> instrucciones) {
		this.instrucciones = instrucciones;
	}
	
}

package dad.recetapp.services.items;

import java.util.ArrayList;
import java.util.List;

public class SeccionItem {

	private Long id;
	private String nombre;
	private List<InstruccionItem> instrucciones = new ArrayList<InstruccionItem>();
	private List<IngredienteItem> ingredientes = new ArrayList<IngredienteItem>();

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

	public String toString() {
		return nombre;
	}

	public boolean equals(Object obj){
		if(obj instanceof SeccionItem){
			SeccionItem tipo = (SeccionItem) obj;
			return tipo.getId() == this.id;
		}
		return false;
	}
}

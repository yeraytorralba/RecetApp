package dad.recetapp.items;

public class AnotacionItem {

	Long id;
	String anotaciones;
	TipoAnotacionItem tipoAnotacion;
	
	public AnotacionItem() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public TipoAnotacionItem getTipoAnotacion() {
		return tipoAnotacion;
	}

	public void setTipoAnotacion(TipoAnotacionItem tipoAnotacion) {
		this.tipoAnotacion = tipoAnotacion;
	}
	
}

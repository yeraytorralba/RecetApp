package dad.recetapp.items;

public class IngredienteItem {

	Long id;
	Integer cantidad;
	MedidaItem medida;
	TipoIngredienteItem tipoIngrediente;
	
	public IngredienteItem() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public MedidaItem getMedida() {
		return medida;
	}

	public void setMedida(MedidaItem medida) {
		this.medida = medida;
	}

	public TipoIngredienteItem getTipoIngrediente() {
		return tipoIngrediente;
	}

	public void setTipoIngrediente(TipoIngredienteItem tipoIngrediente) {
		this.tipoIngrediente = tipoIngrediente;
	}
	
}

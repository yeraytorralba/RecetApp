package dad.recetapp.items;

import java.util.Date;
import java.util.List;

public class RecetaItem {

	Long id;
	String nombre;
	Date fechaCreacion;
	Integer cantidad;
	String para;
	Integer tiempoTotal;
	Integer tiempoThermomix;
	Long categoria;
	List<AnotacionItem> anotaciones;
	List<SeccionItem> secciones;
	
	public RecetaItem() {}

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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public Integer getTiempoTotal() {
		return tiempoTotal;
	}

	public void setTiempoTotal(Integer tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}

	public Integer getTiempoThermomix() {
		return tiempoThermomix;
	}

	public void setTiempoThermomix(Integer tiempoThermomix) {
		this.tiempoThermomix = tiempoThermomix;
	}

	public Long getCategoria() {
		return categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

	public List<AnotacionItem> getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(List<AnotacionItem> anotaciones) {
		this.anotaciones = anotaciones;
	}

	public List<SeccionItem> getSecciones() {
		return secciones;
	}

	public void setSecciones(List<SeccionItem> secciones) {
		this.secciones = secciones;
	}
	
}

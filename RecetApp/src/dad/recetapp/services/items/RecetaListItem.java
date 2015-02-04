package dad.recetapp.services.items;

import java.util.Calendar;
import java.util.Date;

public class RecetaListItem {
	private Long id;
	private String nombre;
	private Date fechaCreacion;
	private Integer cantidad;
	private String para;
	private Integer tiempoTotal;
	private Integer tiempoThermomix;
	private String categoria;

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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RecetaListItem) {
			RecetaListItem tipo = (RecetaListItem) obj;
			return tipo.getId() == this.id;
		}
		return false;
	}

	@Override
	public String toString() {
		return nombre;
	}

	public String getCantidadPara() {
		return cantidad + " " + para;
	}

	public String getTiempoFormateado() {
		int segundos, minutos;

		minutos = tiempoTotal/60;
		segundos = tiempoTotal%60; 

		if(minutos == 0 && segundos == 0){
			return "Desconocido";
		}
		else if(minutos != 0 && segundos == 0){
			return minutos + "M";
		}
		else if(minutos == 0 && segundos != 0){
			return segundos + "S";
		}

		return minutos + "M " + segundos + "S ";
	}

	public String getFechaFormateada(){
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaCreacion);

		String fechaFormateada = "" + calendario.get(Calendar.DAY_OF_MONTH) + "/" + (calendario.get(Calendar.MONTH)+1) + "/" + calendario.get(Calendar.YEAR);

		return fechaFormateada;
	}
}

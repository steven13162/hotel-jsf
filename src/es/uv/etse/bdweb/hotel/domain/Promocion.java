package es.uv.etse.bdweb.hotel.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Promocion
 *
 */
@Entity
@Table(name="promociones")

public class Promocion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name="prom_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="prom_fecha_inicio")
	private LocalDate fechaInicio;
	
	@Column(name="prom_fecha_final")
	private LocalDate fechaFinal;
	
	@Column(name="prom_descripcion")
	private String descripcion;
	
	@Column(name="prom_precio")
	private BigDecimal precio;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipos_habitaciones_tip_hab_id", nullable = false)
	private TipoHabitacion tipoHabitacion;
	
	@OneToMany(mappedBy="promocion")
	private List<Reserva> listaReservas;
	
	public Promocion() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(LocalDate fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public List<Reserva> getListaReservas() {
		return listaReservas;
	}

	public void setListaReservas(List<Reserva> listaReservas) {
		this.listaReservas = listaReservas;
	}
   
//	@Override
//	public String toString(){
//		return "Promocionn: id:"+ id + ", fechaInicio:" + fechaInicio
//				+ ", fechaFinal:" + fechaFinal + ", descripcion:" + descripcion
//				+ ", precio:" + precio + ", tipoHabitacion:" + tipoHabitacion;
//	}
	
}

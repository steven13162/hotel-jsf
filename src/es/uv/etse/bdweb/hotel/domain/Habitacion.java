package es.uv.etse.bdweb.hotel.domain;

import java.io.Serializable;
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
 * Entity implementation class for Entity: Habitacion
 *
 */
@Entity
@Table(name="habitaciones")

public class Habitacion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name="hab_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="hab_numero")
	private Integer numeroHabitacion;
	
	@Column(name="hab_ocupada")
	private Boolean habitacionOcupada;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipos_habitaciones_tip_hab_id", nullable = false)
	private TipoHabitacion tipoHabitacion;
	
	@OneToMany(mappedBy="habitacion")
	private List<Reserva> listaReservas;

	public Habitacion() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumeroHabitacion() {
		return numeroHabitacion;
	}

	public void setNumeroHabitacion(Integer numeroHabitacion) {
		this.numeroHabitacion = numeroHabitacion;
	}

	public Boolean getHabitacionOcupada() {
		return habitacionOcupada;
	}

	public void setHabitacionOcupada(Boolean habitacionOcupada) {
		this.habitacionOcupada = habitacionOcupada;
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
   
	
}

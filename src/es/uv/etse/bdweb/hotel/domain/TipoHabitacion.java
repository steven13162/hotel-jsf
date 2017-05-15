package es.uv.etse.bdweb.hotel.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: TipoHabitacion
 *
 */
@Entity
@Table(name="tipos_habitaciones")
public abstract class TipoHabitacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public enum TYPEHabitacion {
		INDIVIDUAL,
		DOBLE,
		MATRIMONIAL;
	}

	@Id @Column(name="tip_hab_id")
	private Long id;
	
	@Column(name="tip_hab_tipo")
	private TYPEHabitacion tipo;
	
	@Column(name="tip_hab_precio")
	private BigDecimal precio;
	
	public TipoHabitacion() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TYPEHabitacion getTipo() {
		return tipo;
	}

	public void setTipo(TYPEHabitacion tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
   
	
}

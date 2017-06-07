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
public class TipoHabitacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @Column(name="tip_hab_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tip_hab_tipo")
	private RoomType tipo;
	
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

	public RoomType getTipo() {
		return tipo;
	}

	public void setTipo(RoomType tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
   
	
}

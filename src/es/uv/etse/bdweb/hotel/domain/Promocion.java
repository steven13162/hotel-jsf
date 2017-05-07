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
   
}

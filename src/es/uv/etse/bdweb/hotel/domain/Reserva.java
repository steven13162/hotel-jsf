package es.uv.etse.bdweb.hotel.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.uv.etse.bdweb.hotel.services.LocalDateAttributeConverter;

/**
 * Entity implementation class for Entity: Reserva
 *
 */
@Entity
@Table(name="reservas")

public class Reserva implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name="res_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name = "res_fecha_inicio")
	private LocalDate fechaInicio;
	
	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name = "res_fecha_final")
	private LocalDate fechaFinal;
	
	@Column(name="res_estado")
	private EstadoReservaEnum estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="habitaciones_hab_id", nullable = false)
	private Habitacion habitacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="clientes_cli_id", nullable = false)
	private Cliente cliente;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="promociones_prom_id", nullable = true)
	private Promocion promocion;
	
	public Reserva() {
		super();
	}
   
}

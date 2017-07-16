package es.uv.etse.bdweb.hotel.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.uv.etse.bdweb.hotel.common.ReserveState;

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
	
//	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name = "res_fecha_inicio")
	private LocalDate fechaInicio;
	
//	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name = "res_fecha_final")
	private LocalDate fechaFinal;
	
	@Column(name="res_importe")
	private BigDecimal importe;
	
	@Column(name="res_cobrada")
	private Boolean cobrada = false;
	
	@Enumerated(EnumType.STRING)
	@Column(name="res_estado")
	private ReserveState estado;
	

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="habitaciones_hab_id", nullable = false)
	private Habitacion habitacion;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="clientes_cli_id", nullable = false)
	private Cliente cliente;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="promociones_prom_id", nullable = true)
	private Promocion promocion;
	
	public Reserva() {
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

	public ReserveState getEstado() {
		return estado;
	}

	public void setEstado(ReserveState estado) {
		this.estado = estado;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		setImporte();
		System.out.println("\n\n\n\n\n\nSET IMPORTE\n" + this.importe);
	}
	
	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte() {
		if (cliente != null && (cliente instanceof ClienteHabitual) && promocion == null) {
			ClienteHabitual clientehabitual = (ClienteHabitual) cliente;
			
			int dias = (int) Duration.between(fechaInicio.atTime(0, 0), fechaFinal.atTime(0, 0)).toDays();
			BigDecimal importeSinDesc = new BigDecimal(dias).multiply(habitacion.getTipoHabitacion().getPrecio());

			double descuento = (double) (100-clientehabitual.getDescuento().intValue())/100;
			BigDecimal importeConDesc = importeSinDesc.multiply(BigDecimal.valueOf(descuento));
			this.importe = importeConDesc.setScale(2);			
		}
		else if (promocion != null) {
			this.importe = promocion.getPrecio();
		}
		else
		{
			this.importe = habitacion.getTipoHabitacion().getPrecio().multiply(new BigDecimal(Duration.between(fechaInicio.atTime(0, 0), fechaFinal.atTime(0, 0)).toDays()));
		}
	}
	
	public void setImporte(BigDecimal importe) {
			this.importe = importe;
	}
	
	public Boolean getCobrada() {
		return cobrada;
	}

	public void setCobrada(Boolean cobrada) {
		this.cobrada = cobrada;
	}

	public Promocion getPromocion() {
		return promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
		this.importe = promocion.getPrecio();
	}
   
//	@Override
//	public String toString(){
//		return "Reserva: fechaInicio:"+ fechaInicio + ", fechaFinal:" + fechaFinal
//				+ ", importe:" + importe + ", cliente:" + cliente + ",  habitacion:" + habitacion;
//	}
}

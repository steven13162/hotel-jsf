package es.uv.etse.bdweb.hotel.web.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import es.uv.etse.bdweb.hotel.common.RoomType;

public class HabitacionBusquedaForma {
    private Integer ano;
    private Integer mes;
    private Integer dia;
    private Integer estanciaDias = 1;
    private RoomType tipoHabitacion;
    private BigDecimal precioHabitacion;
	private LinkedHashMap<RoomType, BigDecimal> listTiposHabitaciones;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;

	public Integer getEstanciaDias() {
		return estanciaDias;
	}

	public void setEstanciaDias(Integer estanciaDias) {
		this.estanciaDias = estanciaDias;
	}

	public RoomType getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(RoomType tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}
	
	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public LocalDate getFechaEntrada() {
		return LocalDate.of(ano, mes, dia); 
	}

	public void setFechaEntrada(LocalDate fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public LocalDate getFechaSalida() {
		return getFechaEntrada().plusDays(estanciaDias);
	}

	public void setFechaSalida(LocalDate fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public BigDecimal getPrecioHabitacion() {
		BigDecimal precio = listTiposHabitaciones.get(tipoHabitacion);
		System.out.println(precio);
		System.out.println(tipoHabitacion);
		if (precio != null) return precio;
		else return null;
	}

	public void setPrecioHabitacion(BigDecimal precioHabitacion) {
		this.precioHabitacion = precioHabitacion;
	}

	public Map<RoomType, BigDecimal> getListTiposHabitaciones() {
		return listTiposHabitaciones;
	}

	public void setListTiposHabitaciones(LinkedHashMap<RoomType, BigDecimal> listTiposHabitaciones) {
		this.listTiposHabitaciones = listTiposHabitaciones;
	}

	//el método-convertor para la lista h:selectOneMenu en index.xhtml
	public RoomType[] getTiposHabitaciones() {
	    return RoomType.values();
	}
}

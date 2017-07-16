package es.uv.etse.bdweb.hotel.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import es.uv.etse.bdweb.hotel.domain.Habitacion;
import es.uv.etse.bdweb.hotel.ejb.HabitacionBusquedaBean;
import es.uv.etse.bdweb.hotel.web.util.HabitacionBusquedaForma;

@ManagedBean(name="habbuscmanager")
@SessionScoped
public class HabitacionBusquedaManager implements Serializable {
 
	private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HabitacionBusquedaManager.class.getName());
    
	private HabitacionBusquedaForma habitacionBusquedaForma;
	private List<Habitacion> habitaciones;
	
    @EJB
    private HabitacionBusquedaBean habitacionBusquedaBean;
	
    @PostConstruct
    private void init() {
        logger.info("BusquedaHabitacionesManager===INIT===BusquedaHabitacionesManager===INIT===");
        this.habitacionBusquedaForma = new HabitacionBusquedaForma();
        //iniciamos tipos de habitaciones y precios
        this.habitacionBusquedaForma.setListTiposHabitaciones(habitacionBusquedaBean.getTiposHabitaciones());
        this.habitacionBusquedaForma.setTipoHabitacion(this.habitacionBusquedaForma.getListTiposHabitaciones().entrySet().iterator().next().getKey());
        this.habitacionBusquedaForma.setAno(dateTodayYear());
        this.habitacionBusquedaForma.setMes(dateTodayMonth());
        this.habitacionBusquedaForma.setDia(dateTodayDay());
    }
    
	public HabitacionBusquedaForma getHabitacionBusquedaForma() {
		logger.info("BusquedaHabitacionesManager===getFormaBusquedaHabitaciones");
		return habitacionBusquedaForma;
	}
	
	public void setHabitacionBusquedaForma(HabitacionBusquedaForma habitacionBusquedaForma) {
		logger.info("BusquedaHabitacionesManager===setFormaBusquedaHabitaciones");
		this.habitacionBusquedaForma = habitacionBusquedaForma;
	}
	
	public List<Habitacion> getHabitaciones() {
		logger.info("BusquedaHabitacionesManager===getHabitaciones");
		return habitaciones;
	}
	
	public void setHabitaciones(List<Habitacion> habitaciones) {
		logger.info("BusquedaHabitacionesManager===setHabitaciones");
		this.habitaciones = habitaciones;
	}
	
	/*
	 * aquí definimos la fecha de hoy para ponerlo en lá página
	 */
	public String dateToday() {
		LocalDate fechaToday = LocalDate.now();
		// formateamos la fecha de hoy "LocalDate" a un String
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
		String formattedString = fechaToday.format(formatter);
		return formattedString;
	}
	
	/*
	 * aquí definimos la fecha de hoy para ponerlo en lá busqueda por defecto
	 */
	public int dateTodayYear() {
		int todayYear = LocalDate.now().getYear();
		return todayYear;
	}
	public int dateTodayMonth() {
		int todayMonth = LocalDate.now().getMonthValue();
		return todayMonth;
	}
	public int dateTodayDay() {
		int todayDay = LocalDate.now().getDayOfMonth();
		return todayDay;
	}
	
	/*
	 * mostramos las habitaciones que cumplen los requisitos del formulario de búsqueda del usuario
	 */
	public String buscarHabitacionesPorFormulario(){
		logger.info("\nBusquedaHabitacionesManager===\nmostrarHabitaciones()\n");
		
		String navigation = "roomSearchList";
		
		LocalDate fechaEntrada = this.habitacionBusquedaForma.getFechaEntrada(); 
		LocalDate fechaSalida = this.habitacionBusquedaForma.getFechaSalida();
		String tipoHabitacion = this.habitacionBusquedaForma.getTipoHabitacion().getTipo();
		
		logger.info("formaBusquedaHabitaciones.getAno() " + this.habitacionBusquedaForma.getAno());
		logger.info("formaBusquedaHabitaciones.getMes() " + this.habitacionBusquedaForma.getMes());
		logger.info("formaBusquedaHabitaciones.getDia() " + this.habitacionBusquedaForma.getDia());
		logger.info("formaBusquedaHabitaciones.getestanciaDias() " + this.habitacionBusquedaForma.getEstanciaDias());
		logger.info("formaBusquedaHabitaciones..getTipoHabitacion() " + this.habitacionBusquedaForma.getTipoHabitacion());
		
		this.habitaciones = habitacionBusquedaBean.buscarHabitacionesPorFormaBD(fechaEntrada, fechaSalida, tipoHabitacion);
		
		return navigation;
	}
	
}
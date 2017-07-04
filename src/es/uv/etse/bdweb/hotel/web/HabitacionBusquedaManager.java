package es.uv.etse.bdweb.hotel.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import es.uv.etse.bdweb.hotel.domain.Habitacion;
import es.uv.etse.bdweb.hotel.ejb.HabitacionBusquedaBean;
import es.uv.etse.bdweb.hotel.web.util.HabitacionBusquedaForma;

@ManagedBean(name="habbuscmanager")
@SessionScoped
//request scope para no tener en la memoria siempre la forma por defecto,
//con que no se borra el precio de tipo de habitacion cuando eligimos null("Elija...")
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
        this.habitacionBusquedaForma.setListTiposHabitaciones(habitacionBusquedaBean.getTiposHabitaciones());
        this.habitacionBusquedaForma.setTipoHabitacion(this.habitacionBusquedaForma.getListTiposHabitaciones().entrySet().iterator().next().getKey());
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
	
	public String mostrarHabitaciones(){
		logger.info("BusquedaHabitacionesManager===mostrarHabitaciones");
		
		String navigation = "roomsearchlist";
		this.habitaciones = habitacionBusquedaBean.buscarHabitacionesPorFormaBD(habitacionBusquedaForma);
		
		return navigation;
	}
	
	public String mostrarHabitacionesPorEstado(){
		logger.info("BusquedaHabitacionesManager===mostrarHabitaciones");
		String navigation;
		List<Habitacion> listaHabitaciones = habitacionBusquedaBean.buscarHabitacionesPorEstadoBD(habitacionBusquedaForma);
		
		if (!listaHabitaciones.isEmpty()) {
			navigation = "roomsearchlist";
			this.habitaciones = listaHabitaciones;
		}
		else {
			navigation = "errorMessage";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Error search room!"));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		}
		
		return navigation;
	}
	
}
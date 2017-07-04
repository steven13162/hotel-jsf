package es.uv.etse.bdweb.hotel.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import es.uv.etse.bdweb.hotel.domain.Promocion;
import es.uv.etse.bdweb.hotel.domain.Reserva;
import es.uv.etse.bdweb.hotel.ejb.ReservasBusquedaBean;
import es.uv.etse.bdweb.hotel.web.util.SessionUtility;

@ManagedBean(name = "resbusc")
@RequestScoped
public class ReservasBusquedaManager implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReservasBusquedaManager.class.getName());
	
	private List<Reserva> listaReservas;
	
    @EJB
    private ReservasBusquedaBean reservasBusquedaBean;

	public List<Reserva> getListaReservas() {
		return listaReservas;
	}

	public void setListaReservas(List<Reserva> listaReservas) {
		this.listaReservas = listaReservas;
	}
	
	public String cobradaConverter(Boolean bool){
		if (bool) return "Sí";
		else return "No";
	}
	
	public String promocionConverter(Promocion promocion){
		if (promocion != null) return "promoción";
		else return "-";
	}
	
	public String buscarReservasRecientes(){
		String navigation = "/clihab/listaReservasRecientes";
		Long idCliHab;
		
		//aquí definimos intervalo de la fecha busqueda reservas
		LocalDate fechaHasta = LocalDate.now();
		LocalDate fechaDesde = fechaHasta.minusYears(2);
		
		idCliHab = SessionUtility.getClienteHabitualId();
		
		
		if (idCliHab != null)
		{
			listaReservas = reservasBusquedaBean.CliHabReservasRecientesBD(fechaDesde, fechaHasta, idCliHab);
		}
		return navigation;
	}
	
	public String buscarReservasCancelables(){
		String navigation = "/clihab/listaReservasCancelables";
		Long idCliHab;
		
		//aquí definimos intervalo de la fecha busqueda reservas
		LocalDate fechaHasta = LocalDate.now();
		LocalDate fechaDesde = fechaHasta.minusDays(2);
		
		idCliHab = SessionUtility.getClienteHabitualId();
		
		
		if (idCliHab != null)
		{
			listaReservas = reservasBusquedaBean.CliHabReservasCancelablesBD(fechaDesde, fechaHasta, idCliHab);
		}
		return navigation;
	}
	
}

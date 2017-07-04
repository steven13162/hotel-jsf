package es.uv.etse.bdweb.hotel.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import es.uv.etse.bdweb.hotel.common.ReserveState;
import es.uv.etse.bdweb.hotel.domain.Promocion;
import es.uv.etse.bdweb.hotel.domain.Reserva;
import es.uv.etse.bdweb.hotel.ejb.ReservaBusquedaBean;
import es.uv.etse.bdweb.hotel.web.util.SessionUtility;

@ManagedBean(name = "resrecien")
@RequestScoped
public class ReservasRecientes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ClienteHabitualManager.class.getName());
	
	private List<Reserva> listaReservas;
	
    @EJB
    private ReservaBusquedaBean reservaBusquedaBean;
	
	public ReservasRecientes() {
		// TODO Auto-generated constructor stub
	}

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
	
	public String customColor(Object obj){
		switch(obj.toString()){
		case "Sí":
		case "ACTIVA":
			return "green";
		case "No":
		case "CERRADA":
			return "red";
		}
		return null;
	}

	public String buscarReservasRecientes(){
		String navigation = "/clihab/listareservas";
		Long idCliHab;
		
		//aquí definimos intervalo de la fecha busqueda reservas
		LocalDate fechaHasta = LocalDate.now();
		LocalDate fechaDesde = fechaHasta.minusYears(2);
		
		idCliHab = SessionUtility.getClienteHabitualId();
		
		
		if (idCliHab != null)
		{
			listaReservas = reservaBusquedaBean.CliHabReservasRecientesBD(fechaDesde, fechaHasta, idCliHab);
		}
		return navigation;
	}
	
}

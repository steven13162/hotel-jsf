package es.uv.etse.bdweb.hotel.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import es.uv.etse.bdweb.hotel.domain.Promocion;
import es.uv.etse.bdweb.hotel.domain.Reserva;
import es.uv.etse.bdweb.hotel.ejb.ReservasBusquedaBean;
import es.uv.etse.bdweb.hotel.web.util.SessionUtility;

@ManagedBean(name = "resbusc")
@SessionScoped
public class ReservasBusquedaManager implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReservasBusquedaManager.class.getName());
	
	private List<Reserva> listaReservas;
	
    @EJB
    private ReservasBusquedaBean reservasBusquedaBean;

	@PostConstruct
	private void init() {
		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n"
				+ "******************ReservasBusquedaManager===INIT*******************************\n"
				+ "*****************************************\n*****************************************"
				+ "\n*****************************************");
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
	
	public String promoConverter(Promocion promocion){
		if (promocion != null) return "promoción";
		else return "-";
	}
	
	public String buscarReservasRecientes(){
		String navigation = "/clihab/listReservesRecents?faces-redirect=true";
		Long idCliHab;
		
		//aquí definimos intervalo de la fecha busqueda reservas
		LocalDate fechaHasta = LocalDate.now();
		LocalDate fechaDesde = fechaHasta.minusYears(2);
		
		idCliHab = SessionUtility.getClienteHabitualId();
		
		
		if (idCliHab != null)
		{
			this.listaReservas = reservasBusquedaBean.CliHabReservasRecientesBD(fechaDesde, fechaHasta, idCliHab);
		}
		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n"
				+ "buscarReservasRecientes \n"
				+ "\n*****************************************");
		return navigation;
	}
	
	public String buscarReservasCancelables(){
		String navigation = "/clihab/listReservesCancelables?faces-redirect=true";
		Long idCliHab;
		
		//aquí definimos intervalo de la fecha busqueda reservas
		LocalDate fechaDesde = LocalDate.now();
		LocalDate fechaHasta = fechaDesde.plusDays(2);
		
		idCliHab = SessionUtility.getClienteHabitualId();
		
		if (idCliHab != null)
		{
			this.listaReservas = reservasBusquedaBean.CliHabReservasCancelablesBD(fechaHasta, idCliHab);
		}
		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n"
				+ "buscarReservasCancelables \n"
				+ "\n*****************************************"
				+ "\nfechaDesde:" + fechaDesde
				+ "\nfechaHasta:" + fechaHasta
				+ "\nidCliHab:" + idCliHab
				);
		return navigation;
	}
	
	public String cancelarReserva(Reserva reserva){
		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n"
				+ "cancelarReserva \n"
				+ "\n*****************************************");
//		logger.info("\n*****************************************\n*****************************************\n"
//				+ "*****************************************\n"
//				+ "Reserva \n"
//				+ reserva.toString()
//				+ "\n*****************************************");
		if (reservasBusquedaBean.cancelarReservaBD(reserva.getId()))
		{
			this.listaReservas.remove(reserva);
		}

		return null;
	}
	
}

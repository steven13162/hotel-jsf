package es.uv.etse.bdweb.hotel.ejb;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

import es.uv.etse.bdweb.hotel.DAO.AbstractDAOFactory;
import es.uv.etse.bdweb.hotel.DAO.ReservaDAO;
import es.uv.etse.bdweb.hotel.domain.Reserva;

/**
 * Session Bean implementation class ReservaBusquedaBean
 */

@Stateless
public class ReservasBusquedaBean {

	private static final Logger logger = Logger.getLogger(HabitacionBusquedaBean.class.getName());
	
	@PostConstruct
	private void init() {
	}

	@PreDestroy
	private void clean() {
	}

	public List<Reserva> CliHabReservasRecientesBD(LocalDate fechaDesde, LocalDate fechaHasta, Long idCliHab){
		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ReservaDAO rDAO = myFactory.getReservaDAO();
		try {
			 return rDAO.getReservasRecientes(fechaDesde, fechaHasta, idCliHab);
		} catch (Exception e) {
			logger.info("Error search reserve");
		}
		return null;
	}
	
	public List<Reserva> CliHabReservasCancelablesBD(LocalDate fechaHasta, Long idCliHab){
		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ReservaDAO rDAO = myFactory.getReservaDAO();
		try {
			 return rDAO.getReservasCancelables(fechaHasta, idCliHab);
		} catch (Exception e) {
			logger.info("Error search reserve");
		}
		return null;
	}
	
	public Boolean cancelarReservaBD(Long id){
		logger.info("ReservasBusquedaBean==============cancelarReservaBD()");
		logger.info("\n==============================================================");
		logger.info("\n===========myFactory==================================================");
		logger.info("\n=============================rDAO================================");
		logger.info("\n==============================================================");

		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ReservaDAO rDAO = myFactory.getReservaDAO();
		try {
//			 rDAO.deleteReserva(reserva);
			 rDAO.deleteReservaById(id);
			 return true;
		} catch (Exception e) {
			logger.info("Error cancel reserve");
		}
		return false;
	}
	
}

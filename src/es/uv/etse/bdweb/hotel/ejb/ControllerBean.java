package es.uv.etse.bdweb.hotel.ejb;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

import es.uv.etse.bdweb.hotel.DAO.AbstractDAOFactory;
import es.uv.etse.bdweb.hotel.DAO.ClienteDAO;
import es.uv.etse.bdweb.hotel.DAO.ClienteHabitualDAO;
import es.uv.etse.bdweb.hotel.DAO.HabitacionDAO;
import es.uv.etse.bdweb.hotel.DAO.ReservaDAO;
import es.uv.etse.bdweb.hotel.domain.ClienteHabitual;
import es.uv.etse.bdweb.hotel.domain.Habitacion;
import es.uv.etse.bdweb.hotel.domain.Reserva;
import es.uv.etse.bdweb.hotel.domain.TipoHabitacion;
import es.uv.etse.bdweb.hotel.web.util.HabitacionBusquedaForma;

/**
 * Session Bean implementation class ControllerBean
 */
@Stateless
public class ControllerBean {

	private static final Logger logger = Logger.getLogger(ControllerBean.class.getName());

	@PostConstruct
	private void init() {
	}

	@PreDestroy
	private void clean() {
	}

	/*
	 * Buscamos en la BD si el cliente que entra su DNI is el cliente habitual
	 * ya
	 */
	public ClienteHabitual buscarClienteHabitualBD(String clienteDNI) {
		logger.info("ControllerBean==============buscarClienteHabitualBD()");
		logger.info("==============================================================");
		logger.info("===========myFactory==================================================");
		logger.info("=============================chDAO================================");
		logger.info("==============================================================");

		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ClienteHabitualDAO chDAO = myFactory.getClienteHabitualDAO();
		ClienteHabitual clienteHabitual = chDAO.getClienteHabitualByDNI(clienteDNI);

		return clienteHabitual;
	}

	/*
	 * Escribimos a la BD la reserva que está eligida por el usuario
	 */
	public Boolean reservarHabitacionBD(Reserva reserva, Boolean esClienteHabitual) {
		logger.info("ControllerBean==============reservarHabitacionBD()");
		logger.info("==============================================================");
		logger.info("===========myFactory==================================================");
		logger.info("=============================cDAO============chDAO====================");
		logger.info("======================================rDAO========================");

		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ReservaDAO rDAO = myFactory.getReservaDAO();

		if (!esClienteHabitual) {
			ClienteDAO cDAO = myFactory.getClienteDAO();
			try {
				cDAO.createCliente(reserva.getCliente());
			} catch (Exception e) {
				logger.info("Error creating client");
				return false;
			}
		} else {
			ClienteHabitualDAO chDAO = myFactory.getClienteHabitualDAO();
			try {
				chDAO.updateClienteHabitual((ClienteHabitual) reserva.getCliente());
			} catch (Exception e) {
				logger.info("Error editing client habitual");
				return false;
			}
		}

		try {
			rDAO.createReserva(reserva);
			return true;
		} catch (Exception e) {
			logger.info("Error creating reserve");
		}
		return false;
	}

	/*
	 * Cobramos la reserva si eligido el checkbox
	 * "Cóbrame el el formulario de la reserva"
	 */
	public Boolean cobrarReservaBD(Long id) {
		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ReservaDAO rDAO = myFactory.getReservaDAO();

		Reserva reserva = null;
		try {
			reserva = rDAO.getReservaById(id);
			reserva.setCobrada(true);
			rDAO.updateReserva(reserva);
			return true;
		} catch (Exception e) {
			logger.info("Error pay reserve");
		}
		return false;
	}

	/*
	 * Antes de hacer la reservación debemos asegurarnos que en el tiempo de
	 * introducir los datos por el usuario, esta habitación aún no está reservada
	 * por otro usuario
	 */
	public Boolean esDesponibleReservaBD(Long id, LocalDate fechaEntrada, LocalDate fechaSalida) {
		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ReservaDAO rDAO = myFactory.getReservaDAO();

		try {
			return rDAO.getReservaDisponibleByIdAndFechas(id, fechaEntrada, fechaSalida);
		} catch (Exception e) {
			logger.info("Error check reserve");
		}
		return false;
	}

	/*
	 * Registramos en la BD nuevo cliente habitual
	 */
	public Boolean doRegistrarClienteHabitualBD(ClienteHabitual clienteHabitual) {
		logger.info("ControllerBean==============doRegistratClienteHabitualBD()");
		logger.info("==============================================================");
		logger.info("===========myFactory==================================================");
		logger.info("=============================chDAO================================");
		logger.info("==============================================================");

		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ClienteHabitualDAO chDAO = myFactory.getClienteHabitualDAO();
		try {
			chDAO.createClienteHabitual(clienteHabitual);
			return true;
		} catch (Exception e) {
			logger.info("Error regester client habitual!");
		}
		return false;
	}

	/*
	 * Buscamos una habitación disponible para crear la reserva a través de la
	 * promoción
	 */
	public Habitacion buscarHabitacionDePromocionBD(LocalDate fechaEntrada, LocalDate fechaSalida, TipoHabitacion tipoHabitacion) {
		logger.info("ControllerBean==============buscarHabitacionDePromocionBD()");
		logger.info("==============================================================");
		logger.info("===========myFactory==================================================");
		logger.info("=============================hDAO================================");
		logger.info("==============================================================");

		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		HabitacionDAO hDAO = myFactory.getHabitacionDAO();

		List<Habitacion> lista = hDAO.getHabitacionByFormaBusqueda(fechaEntrada, fechaSalida, tipoHabitacion.getTipo().getTipo());

		return lista.get(0);
	}
}

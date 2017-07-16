package es.uv.etse.bdweb.hotel.web;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;

import es.uv.etse.bdweb.hotel.common.Constants;
import es.uv.etse.bdweb.hotel.common.ReserveState;
import es.uv.etse.bdweb.hotel.common.UtilityService;
import es.uv.etse.bdweb.hotel.domain.Cliente;
import es.uv.etse.bdweb.hotel.domain.ClienteHabitual;
import es.uv.etse.bdweb.hotel.domain.Reserva;
import es.uv.etse.bdweb.hotel.ejb.ControllerBean;
import es.uv.etse.bdweb.hotel.web.util.SessionUtility;

@ManagedBean
@RequestScoped
public class ControllerManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ControllerManager.class.getName());

	@EJB
	private ControllerBean controllerBean;

	@ManagedProperty(value = "#{climanager}")
	private ClienteManager clienteManager;

	@ManagedProperty(value = "#{clihabmanager}")
	private ClienteHabitualManager clienteHabitualManager;

	@ManagedProperty(value = "#{resmanager}")
	private ReservaManager reservaManager;

	@PostConstruct
	private void init() {
		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n"
				+ "****************** ControllerManager===INIT*******************************\n"
				+ "*****************************************\n*****************************************"
				+ "\n*****************************************");
	}

	// must povide the setter method for inject climanager Managed Bean
	public void setClienteManager(ClienteManager clienteManager) {
		this.clienteManager = clienteManager;
	}

	// must povide the setter method for inject clihabmanager Managed Bean
	public void setClienteHabitualManager(ClienteHabitualManager clienteHabitualManager) {
		this.clienteHabitualManager = clienteHabitualManager;
	}

	// must povide the setter method for inject resmanager Managed Bean
	public void setReservaManager(ReservaManager reservaManager) {
		this.reservaManager = reservaManager;
	}

	/*
	 * comprobar si el número que pasa el usuario en la página de la lista de las habitaciones
	 * para reservar es de usuario habitual
	 */
	public String comprobarDNI() {
		String navigation = "";
		ClienteHabitual clienteHabitual;

		logger.info("********** ControllerManager***********************************************");
		logger.info("**********comprobarDNI()****************************");
		logger.info("**clienteDNI****************" + clienteManager.getCliente().getDni()
				+ "**************************************");

		clienteHabitual = controllerBean.buscarClienteHabitualBD(clienteManager.getCliente().getDni());

		if (clienteHabitual != null) {
			logger.info("\n*****************************************\n*****************************************\n"
					+ "*****************************************\n"
					+ "******** ControllerManager**********Cliente habitual:******************************\n"
					+ "*****************************************\n*****************************************"
					+ "\n*****************************************");
			logger.info(clienteHabitual.toString());

			clienteHabitualManager.setClienteHabitual(clienteHabitual);
			//aquí asignamos el cliente a la reserva y también recontamos el importe en setCliente()
			reservaManager.getReserva().setCliente(clienteHabitual);

			logger.info("\n*****************************************\n*****************************************\n"
					+ "*****************************************\n"
					+ "******************ClienteHabitualManager:******************************\n"
					+ "****(reservaManager.getReserva()*************************************\n"
					+ "*****************************************" + "\n*****************************************");
			logger.info(reservaManager.getReserva().toString());

			clienteHabitualManager.setDniInput(clienteHabitual.getDni()); 
//			navigation = "index?faces-redirect=true";
			
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
			        "You are registered already, enter your password to login form for sign in and finish your reservation!", null));

			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			navigation = "roomSearchList?faces-redirect=true";

		} else {
			logger.info("\n*****************************************\n*****************************************\n"
					+ "*****************************************\n"
					+ "****************** NO EXISTE EL CLIENTE HABITUAL*******************************\n"
					+ reservaManager.getReserva().toString() + "\n"
					+ "*****************************************\n*****************************************"
					+ "\n*****************************************" + "\n*****************************************");
			logger.info("\n*****************************************\n*****************************************\n"
					+ "*****************************************\n"
					+ "****************** CLIENTE ACTUAL*******************************\n"
					+ clienteManager.getCliente().toString() + "\n"
					+ "*****************************************\n*****************************************"
					+ "\n*****************************************" + "\n*****************************************");

			navigation = "clientEdit?faces-redirect=true";
		}
		return navigation;
	}

	/*
	 * el principal método de crear la Reserva después de introducir todos los datos por el usuario 
	 */
	public String reservar() {
		String navigation = "errorMessage";
		String passwordRandom = "";
		Boolean esClienteHabitual = false;

		if (reservaManager.getReserva().getHabitacion() == null || reservaManager.getReserva().getFechaFinal() == null
				|| reservaManager.getReserva().getFechaInicio() == null
				|| reservaManager.getReserva().getImporte().signum() <= 0) {
			logger.info("***** ControllerManager*******reservar()*****************************"
					+ "\n******************reserva tiene campos == null***********************\n"
					+ "*****************************************\n"
					+ "****************** ERROR *******************************\n"
					+ "ERROR*****************************************" + "\n*****************************************"
					+ "\n*****************ERROR************************");

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Error to reserve the room! First of all you have to choose the room."));
			return navigation;
		}
		// String clienteHabitualDNI;
		HttpSession session = SessionUtility.getSession();

		if (session != null && session.getAttribute("clienteHabitualSession") != null) {
			//aquí definimos un cliente a la reserva y también recontamos el importe en setCliente()
			reservaManager.getReserva().setCliente(clienteHabitualManager.getClienteHabitual());
			esClienteHabitual = true;
			logger.info("**********************************************"
					+ "\n*****************hay session*************************************");
		} else {
			passwordRandom = UtilityService.getPasswordNumeric(8);
			clienteManager.getCliente()
					.setPassword(DigestUtils.sha512Hex(passwordRandom + Constants.passwordSalt).toLowerCase());
			//aquí definimos un cliente a la reserva y también recontamos el importe en setCliente()
			reservaManager.getReserva().setCliente(clienteManager.getCliente());
			logger.info("**********************************************"
					+ "\n*****************NO hay session*************************************");
		}

		reservaManager.getReserva().setEstado(ReserveState.ACTIVA);

		if (!controllerBean.esDesponibleReservaBD(reservaManager.getReserva().getHabitacion().getId(),
				reservaManager.getReserva().getFechaInicio(), reservaManager.getReserva().getFechaFinal())) {
			logger.info("***** ControllerManager*******reservar()*****************************"
					+ "\n******************comprobarReserva ***********************\n"
					+ "*****en caso de reservar por otro cliente mientras reservamos esa habitación************************************\n"
					+ "****************** ERROR *******************************\n"
					+ "ERROR*****************************************" + "\n*****************************************"
					+ "\n*****************ERROR************************");

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error to reserve the room!"));
			return navigation;
		}

		if (controllerBean.reservarHabitacionBD(reservaManager.getReserva(), esClienteHabitual)) {
			logger.info(
					"\n************reservar()*****************************\n*****************************************\n"
							+ "*****************************************\n"
							+ "******************  ControllerManager: reserva realizada *******************************\n"
							+ "*****************************************\n*****************************************"
							+ "\n*****************************************");
			navigation = "/reserveInfo?faces-redirect=true";

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reservation created successfully!"));
			if (!(reservaManager.getReserva().getCliente() instanceof ClienteHabitual)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Your password for access: " + passwordRandom));
			}

			// si checkbox se ha eligido, cobramos la reserva ahora
			if (reservaManager.getCobrarReserva()) {

				// FIXME
				// llamar al servicio de cargar a la tarjeta el importe total de
				// la estancia
				// en general hay que hacerlo como única transacción con la
				// consulta a la BD siguiente

				if (controllerBean.cobrarReservaBD(reservaManager.getReserva().getId())) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reservation paid successfully!"));
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reservation paid error!"));
				}

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Way to pay the reservation: in hotel"));
			}

			clienteManager.setCliente(new Cliente());
			reservaManager.setReserva(new Reserva());

			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

			return navigation;
		} else {
			logger.info(
					"\n************reservar()*****************************\n******************false BD***********************\n"
							+ "*****************************************\n"
							+ "****************** ERROR *******************************\n"
							+ "ERROR*****************************************\n*****************************************"
							+ "\n*****************ERROR************************");

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error to reserve the room!"));
			return navigation;
		}

	}

	/*
	 * Registramos el cliente que ya introdujo sus datos al cliente Habitual
	 */
	public String registrarClienteHabitual() {
		String passwordRandom = "";
		
		logger.info("\n************registrarClienteHabitual*****************************"
				+ "\n****************************************\n" + "*****************************************\n"
				+ "****************** clienteManager.getCliente() *******************************\n"
				+ "\n*****************************************" + "\n*****************************************\n"
				+ clienteManager.getCliente().toString());
		
		ClienteHabitual clihab = new ClienteHabitual(clienteManager.getCliente());
		passwordRandom = UtilityService.getPasswordAlphanumeric(8);
		clihab.setPassword(DigestUtils.sha512Hex(passwordRandom + Constants.passwordSalt).toLowerCase());
		
		logger.info("\n*****************************************\n*****************************************\n"
				+ "***************registrarClienteHabitual()**************************\n"
				+ "*****************************************\n"
				+ "******passwordRandom*******" + passwordRandom + "****************************\n"
				+ "*****************************************\n"
				+ "clihab.getPassword()\n"
				+ clihab.getPassword()
				+ "\n*****************************************");
		
		logger.info("\n************registrarClienteHabitual*****************************"
				+ "\n****************************************\n" + "*****************************************\n"
				+ "****************** clihab.toString() *******************************\n"
				+ "\n*****************************************" + "\n*****************************************\n"
				+ clihab.toString());
		
		if (controllerBean.doRegistrarClienteHabitualBD(clihab)) {
			logger.info("\n************registrarClienteHabitual*****************************"
					+ "\n****************************************\n" + "*****************************************\n"
					+ "****************** clihab.toString()  *******************************\n"
					+ "\n*******************true**********************" + "\n*****************************************\n"
					+ clihab.toString());
			
			String navigation = "clihab/clientHabitualEdit?faces-redirect=true";
			
			clienteHabitualManager.setClienteHabitual(clihab);
			//aquí definimos un cliente a la reserva y también recontamos el importe en setCliente()
			reservaManager.getReserva().setCliente(clienteHabitualManager.getClienteHabitual());
			clienteManager.setCliente(new Cliente());
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You are registed successfully!"));
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Your password for access: " + passwordRandom));
			FacesContext.getCurrentInstance().addMessage(null, 
			        new FacesMessage(FacesMessage.SEVERITY_ERROR, "You need finish your reservation!", null));

			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			
			HttpSession session = SessionUtility.getSession();
			session.setAttribute("clienteHabitualSession", clihab);
			
			return navigation;
			
		} else {
			logger.info("\n************registrarClienteHabitual*****************************"
					+ "\n****************************************\n" + "*****************************************\n"
					+ "****************** registrarClienteHabitual *******************************\n"
					+ "\n************************false*****************" + "\n*****************************************\n");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Register error!"));
			return null;
		}
	}

}

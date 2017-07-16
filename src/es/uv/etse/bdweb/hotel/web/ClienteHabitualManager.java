package es.uv.etse.bdweb.hotel.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import es.uv.etse.bdweb.hotel.common.Constants;
import es.uv.etse.bdweb.hotel.domain.ClienteHabitual;
import es.uv.etse.bdweb.hotel.domain.Habitacion;
import es.uv.etse.bdweb.hotel.domain.Promocion;
import es.uv.etse.bdweb.hotel.domain.Reserva;
import es.uv.etse.bdweb.hotel.domain.TipoHabitacion;
import es.uv.etse.bdweb.hotel.ejb.ControllerBean;
import es.uv.etse.bdweb.hotel.web.util.SessionUtility;

@ManagedBean(name = "clihabmanager")
@SessionScoped
public class ClienteHabitualManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ClienteHabitualManager.class.getName());

	private ClienteHabitual clienteHabitual;
	private String passInput;
	private String dniInput;
	private int attemptLogin;

	@EJB
	private ControllerBean controllerBean;

	@ManagedProperty(value = "#{resmanager}")
	private ReservaManager reservaManager;

	@PostConstruct
	private void init() {
		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n"
				+ "******************ClienteHabitualManager===INIT*******************************\n"
				+ "*****************************************\n*****************************************"
				+ "\n*****************************************");
		this.clienteHabitual = new ClienteHabitual();
		this.attemptLogin = 0;

		/*
		 * "Autologin" para el cliente habitual
		 */
//		this.dniInput = "12345678D";
//		this.passInput = "12345";
//		validateClienteHabitual();

	}

	// must povide the setter method for inject resmanager Managed Bean
	public void setReservaManager(ReservaManager reservaManager) {
		this.reservaManager = reservaManager;
	}

	public ClienteHabitual getClienteHabitual() {
		return clienteHabitual;
	}

	public void setClienteHabitual(ClienteHabitual clienteHabitual) {
		this.clienteHabitual = clienteHabitual;
	}

	public String getPassInput() {
		return passInput;
	}

	public void setPassInput(String passInput) {
		this.passInput = passInput;
	}

	public String getDniInput() {
		return dniInput;
	}

	public void setDniInput(String dniInput) {
		this.dniInput = dniInput;
	}

	public String comprobarContrasena() {

		String navigation;

		if (attemptLogin >= 3) {
			attemptLogin = 0;
			this.clienteHabitual = null;
			this.passInput = null;
			this.dniInput = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Too many incorrects attempts!"));
			return null;
		}

		attemptLogin++;

		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n" + passInput + Constants.passwordSalt + "\n"
				+ DigestUtils.sha512Hex(passInput + Constants.passwordSalt)
				+ "\n*****************************************");

		if (clienteHabitual.getPassword()
				.equals(DigestUtils.sha512Hex(passInput + Constants.passwordSalt).toLowerCase())) {
			HttpSession session = SessionUtility.getSession();
			session.setAttribute("clienteHabitualSession", clienteHabitual);

			if (reservaManager.getReserva().getHabitacion() == null
					|| reservaManager.getReserva().getFechaFinal() == null
					|| reservaManager.getReserva().getFechaInicio() == null
					|| reservaManager.getReserva().getImporte().signum() <= 0) {
				return null;
			} else {
				navigation = "/clihab/clientHabitualEdit?faces-redirect=true";
			}

			return navigation;
		} else {
			this.passInput = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Incorrect  Password!"));
			return null;
		}
	}

	public String validateClienteHabitual() {
		logger.info("\n*****************************************\n*****************************************\n"
				+ "***************validateClienteHabitual()**************************\n"
				+ "*****************************************\n" + "******dniInput*******" + dniInput
				+ "****************************\n" + "*****************************************\n" + passInput
				+ Constants.passwordSalt + "\n" + DigestUtils.sha512Hex(passInput + Constants.passwordSalt)
				+ "\n*****************************************");

		ClienteHabitual clihab = new ClienteHabitual();
		clihab = controllerBean.buscarClienteHabitualBD(dniInput);

		if (clihab != null) {
			this.clienteHabitual = clihab;

			logger.info("\n*****************************************\n*****************************************\n"
					+ "***************validateClienteHabitual()**************************\n"
					+ "*****************************************\n" + "******clihab*******" + clihab.toString()
					+ "****************************\n");

			return comprobarContrasena();

		} else {
			logger.info("\n*****************************************\n*****************************************\n"
					+ "***************validateClienteHabitual()**************************\n"
					+ "*****************************************\n"
					+ "******clihab*******is null****************************\n");
			this.passInput = null;
			this.dniInput = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Incorrect DNI y/o Password!"));
			return null;
		}
	}

	public String logoutClienteHabitual() {
		String navigation = "/index?faces-redirect=true";
		HttpSession session = SessionUtility.getSession();
		
		if (session != null) {
			session.invalidate();
			return navigation;
		}
		return null;
	}

	public String crearReservaPromocion(Promocion promocion) {
		String navigation = "";
		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n"
				+ "ClienteHabitualManager ---> crearReservaPromocion() \n"
				+ "\n*****************************************" + "\nid: " + promocion.toString());

		if (promocion != null && (promocion.getFechaInicio().isAfter(LocalDate.now())
				|| promocion.getFechaInicio().isEqual(LocalDate.now()))) {
			LocalDate fechaEntrada = promocion.getFechaInicio();
			LocalDate fechaSalida = promocion.getFechaFinal();
			TipoHabitacion tipoHabitacion = promocion.getTipoHabitacion();

			/*
			 * buscamos en la BD alugúna habitación disponible para la reserva
			 * con promoción
			 */
			Habitacion habitacion = controllerBean.buscarHabitacionDePromocionBD(fechaEntrada, fechaSalida,
					tipoHabitacion);

			if (habitacion != null) {
				reservaManager.crearReserva(habitacion, fechaEntrada, fechaSalida);
				reservaManager.getReserva().setPromocion(promocion);
				navigation = "/clihab/clientHabitualEdit?faces-redirect=true";
				return navigation;
			}
			else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No rooms available!"));
			}
		}

		logger.info("\n*****************************************\n"
				+ "******************PROMOCION ERROR***********************\n"
				+ "\n*****************************************");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Promotion error!"));
		navigation = "/errorMessage";
		return navigation;
	}

}

package es.uv.etse.bdweb.hotel.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import es.uv.etse.bdweb.hotel.common.Constants;
import es.uv.etse.bdweb.hotel.domain.Recepcionista;
import es.uv.etse.bdweb.hotel.domain.Reserva;
import es.uv.etse.bdweb.hotel.ejb.RecepcionBean;
import es.uv.etse.bdweb.hotel.web.util.SessionUtility;

@ManagedBean(name = "recepcion")
@SessionScoped
public class RecepcionManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RecepcionManager.class.getName());

	private String passInput;
	private String emailInput;

	private List<Recepcionista> listaRecepcionistas;
	private List<Reserva> listaReservas;
	// inicialicamos hashmapa de la lista de ocupación
	private LinkedHashMap<String, String> listaOcupacion = new LinkedHashMap<String, String>();

	private Date dateVencidas;
	private Date dateOcupacion;
	private Integer ano;
	private Integer mes;
	private BigDecimal gananciasPorMes;
	private Integer descuento;
	private Boolean descuentoAplicado = false;

	@EJB
	private RecepcionBean recepcionBean;

	@PostConstruct
	private void init() {
		logger.info("\n*****************************************\n" + "*****************************************\n"
				+ "***************RecepcionManager===INIT**************************\n"
				+ "\n*****************************************");

		/*
		 * "Autologin" para el cliente habitual
		 */
//		this.emailInput = "isaw@uv.es";
//		this.passInput = "12345";
//		validateRecepcionista();
		
		// la lista de todos recepcionistas tenemos siempre
		this.listaRecepcionistas = mostrarTodosRecepcionistas();
	}

	public String getPassInput() {
		return passInput;
	}

	public void setPassInput(String passInput) {
		this.passInput = passInput;
	}

	public String getEmailInput() {
		return emailInput;
	}

	public void setEmailInput(String emailInput) {
		this.emailInput = emailInput;
	}

	public List<Recepcionista> getListaRecepcionistas() {
		return listaRecepcionistas;
	}

	public void setListaRecepcionistas(List<Recepcionista> listaRecepcionistas) {
		this.listaRecepcionistas = listaRecepcionistas;
	}

	public List<Reserva> getListaReservas() {
		return listaReservas;
	}

	public void setListaReservas(List<Reserva> listaReservas) {
		this.listaReservas = listaReservas;
	}

	public Date getDateVencidas() {
		return dateVencidas;
	}

	public void setDateVencidas(Date dateVencidas) {
		this.dateVencidas = dateVencidas;
	}

	public Date getDateOcupacion() {
		return dateOcupacion;
	}

	public void setDateOcupacion(Date dateOcupacion) {
		this.dateOcupacion = dateOcupacion;
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

	public LinkedHashMap<String, String> getListaOcupacion() {
		return listaOcupacion;
	}

	public void setListaOcupacion(LinkedHashMap<String, String> listaOcupacion) {
		this.listaOcupacion = listaOcupacion;
	}

	public BigDecimal getGananciasPorMes() {
		return gananciasPorMes;
	}

	public void setGananciasPorMes(BigDecimal gananciasPorMes) {
		this.gananciasPorMes = gananciasPorMes;
	}

	public Integer getDescuento() {
		return descuento;
	}

	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}

	public Boolean getDescuentoAplicado() {
		return descuentoAplicado;
	}

	public void setDescuentoAplicado(Boolean descuentoAplicado) {
		this.descuentoAplicado = descuentoAplicado;
	}

	/*
	 * validación del los datos introducidos por el recepcionista
	 */
	public String validateRecepcionista() {
		Recepcionista recepcionista = new Recepcionista();
		recepcionista = recepcionBean.buscarRecepcionistaBD(emailInput);

		if (recepcionista != null) {

			logger.info("\n*****************************************\n*****************************************\n"
					+ "***************validateRececionista()**************************\n"
					+ "*****************************************\n" + "******recepcionista*******"
					+ recepcionista.toString() + "****************************\n");

			logger.info("\n*****************************************\n*****************************************\n"
					+ "*****************************************\n" + passInput + Constants.passwordSalt + "\n"
					+ DigestUtils.sha512Hex(passInput + Constants.passwordSalt)
					+ "\n*****************************************");

			if (recepcionista.getPassword()
					.equals(DigestUtils.sha512Hex(passInput + Constants.passwordSalt).toLowerCase())) {
				HttpSession session = SessionUtility.getSession();
				session.setAttribute("recepcionistaSession", recepcionista);

				return "/reception/index?faces-redirect=true";
			}

		}

		logger.info("\n*****************************************\n*****************************************\n"
				+ "***************validateRececionista()**************************\n"
				+ "*****************************************\n"
				+ "******recepcionista*******is null****************************\n");
		this.passInput = null;
		this.emailInput = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Incorrect email y/o password!"));

		return null;
	}

	/*
	 * el método de logout de recepcionista
	 */
	public String logoutRecepcionista() {
		String navigation = "/reception?faces-redirect=true";
		HttpSession session = SessionUtility.getSession();

		if (session != null) {
			session.invalidate();
			return navigation;
		}
		return null;
	}

	/*
	 * mostrar todos los Recepcionistas que existen en la BD
	 */
	public List<Recepcionista> mostrarTodosRecepcionistas() {
		return recepcionBean.mostrarTodosRecepcionistasBD();
	}

	/*
	 * el método que muestra las reservas vencidas
	 */
	public String mostrarReservasVencidas() {
		String navigation = "/reception/listReservesOverdues?faces-redirect=true";
		// formateamos la fecha de ocupacíon introdicida de "Date" a "LocalDate"
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = this.dateVencidas.toInstant();
		LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();

		this.listaReservas = recepcionBean.mostrarReservasVencidasBD(localDate);
		return navigation;
	}

	/*
	 * aquí definimos la fecha de hoy para ponerlo al maxdate y mindate del
	 * campos de introducir fecha para las operaciones
	 */
	public String dateToday() {
		LocalDate fechaToday = LocalDate.now();
		// formateamos la fecha de hoy "LocalDate" a un String
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
		String formattedString = fechaToday.format(formatter);
		return formattedString;
	}

	/*
	 * Convertir la fecha de la consulta a un String para mostar en la vista
	 * XHTML
	 */
	public String mostarFechaEnXHTML(Date date) {
		// formateamos la fecha de ocupacíon introdicida de "Date" a un String"
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedString = sdf.format(date);
		return formattedString;
	}

	/*
	 * Convertir las varable de la fecha para consultar ganancias a un String,
	 * para mostar en la vista XHTML
	 */
	public String mostarMesGananciasEnXHTML() {
		LocalDate fecha = LocalDate.of(this.ano, this.mes, 1);
		// formateamos la fecha de hoy "LocalDate" a un String
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMMM");
		String formattedString = fecha.format(formatter);
		return formattedString;
	}

	/*
	 * mostrar ocupacion del Hotel
	 */
	public String mostrarOcupacion() {
		String navigation = "/reception/listOccupation?faces-redirect=true";
		// formateamos la fecha de ocupacíon introdicida de "Date" a "LocalDate"
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = this.dateOcupacion.toInstant();
		LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();

		List<Object[]> listaOcupacionObjects = recepcionBean.buscarHabitacionesPorEstadoBD(localDate);

		// convertimos el array de objetos a LinkedHashMap
		for (Object[] result : listaOcupacionObjects) {
			this.listaOcupacion.put(result[0].toString(), result[1].toString());
			// logger.info("\n\n" + result[0].toString());
			// logger.info("\n\n" + result[1].toString());
		}
		return navigation;
	}

	/*
	 * el método de consultar las ganancias en un mes concreto
	 */
	public String consultarGanancias() {
		String navigation = "/reception/consultEarnings?faces-redirect=true";

		this.gananciasPorMes = (BigDecimal) recepcionBean.consultarGananciasBD(this.ano, this.mes);

		return navigation;
	}

	/*
	 * el método de aplicar un descuento específico a cada cliente habitual
	 */
	public String aplicarDescuento() {
		String navigation = "/reception/applyDiscount?faces-redirect=true";

		this.descuentoAplicado = recepcionBean.aplicarDescuentoBD(this.descuento);
		
		if (descuentoAplicado)
			logger.info("\n***************aplicarDescuento*******TRUE*******************"
				+ "\n*****************************************" + descuentoAplicado);
		
		

		return navigation;
	}
}
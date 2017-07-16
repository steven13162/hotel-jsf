package es.uv.etse.bdweb.hotel.web.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import es.uv.etse.bdweb.hotel.domain.ClienteHabitual;
import es.uv.etse.bdweb.hotel.domain.Recepcionista;

public class SessionUtility {

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static Recepcionista getRecepcionista(){
		HttpSession session = getSession();
		if (session != null)
		{
			Recepcionista recepcionista;
			recepcionista = (Recepcionista) session.getAttribute("recepcionistaSession");
			return recepcionista;
		}
		else return null;
	}
	public static ClienteHabitual getClienteHabitual() {

		HttpSession session = getSession();
		if (session != null)
		{
			ClienteHabitual clihab;
			clihab = (ClienteHabitual) session.getAttribute("clienteHabitualSession");
			return  clihab;
		}
		else return null;

	}

	public static Long getClienteHabitualId() {

		HttpSession session = getSession();
		if (session != null)
		{
			ClienteHabitual clihab;
			clihab = (ClienteHabitual) session.getAttribute("clienteHabitualSession");
			return  (clihab.getId() != null) ? clihab.getId() : null;
		}
		else return null;

	}
	
	public static String getClienteHabitualDNI() {

		HttpSession session = getSession();
		if (session != null)
		{
			ClienteHabitual clihab;
			clihab = (ClienteHabitual) session.getAttribute("clienteHabitualSession");
			return  (clihab.getDni() != null) ? clihab.getDni() : null;
		}
		else return null;

	}

}

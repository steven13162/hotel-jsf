package es.uv.etse.bdweb.hotel.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.uv.etse.bdweb.hotel.DAO.AbstractDAOFactory;
import es.uv.etse.bdweb.hotel.DAO.ClienteHabitualDAO;
import es.uv.etse.bdweb.hotel.DAO.HabitacionDAO;
import es.uv.etse.bdweb.hotel.DAO.ReservaDAO;
import es.uv.etse.bdweb.hotel.domain.ClienteHabitual;
import es.uv.etse.bdweb.hotel.domain.Habitacion;
import es.uv.etse.bdweb.hotel.domain.Reserva;

@Stateless
@Path("/api")
public class RESTService {

	// http://localhost:8080/project-hotel/rest/api/habs-disponiles/2017-07-10/2017-07-15/INDIVIDUAL
	@GET
	@Path("/habs-disponiles/{fechaEntrada}/{fechaSalida}/{tipoHabitacion}")
	@Produces("application/json")
	public String getHabsDisponibles(@PathParam("fechaEntrada") String fechaEntrada,
			@PathParam("fechaSalida") String fechaSalida, @PathParam("tipoHabitacion") String tipoHabitacion) {

		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		HabitacionDAO hDAO = myFactory.getHabitacionDAO();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fechaEntradaLocalDate = LocalDate.parse(fechaEntrada, formatter);
		LocalDate fechaSalidaLocalDate = LocalDate.parse(fechaSalida, formatter);

		List<Habitacion> resultList = hDAO.getHabitacionByFormaBusqueda(fechaEntradaLocalDate, fechaSalidaLocalDate,
				tipoHabitacion);

		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> listFinal = new HashMap<>();

		String jsonToString = "";

		listFinal.put("reservas:", resultList);

		try {
			jsonToString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(listFinal);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
		return jsonToString;

	}
	
	// http://localhost:8080/project-hotel/rest/api/cli-habit/12345678D
	@GET
	@Path("/cli-habit/{dni}")
	@Produces("application/json")
	public String getClienteHabitual(@PathParam("dni") String dni) {

		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ClienteHabitualDAO chDAO = myFactory.getClienteHabitualDAO();
		ClienteHabitual resultList = chDAO.getClienteHabitualByDNI(dni);

		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> listFinal = new HashMap<>();

		String jsonToString = "";

		listFinal.put("clienteHabitual:", resultList);

		try {
			jsonToString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(listFinal);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
		return jsonToString;
	}
	
	// http://localhost:8080/project-hotel/rest/api/res-pend/4
	@GET
	@Path("/res-pend/{idCliHab}")
	@Produces("application/json")
	public String getReservasPendientes(@PathParam("idCliHab") String idCliHab) {

		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ReservaDAO rDAO = myFactory.getReservaDAO();
		
		//aquí definimos intervalo de la fecha busqueda reservas
		LocalDate fechaHasta = LocalDate.now();
		LocalDate fechaDesde = fechaHasta.minusYears(2);
		
		List<Reserva> resultList = rDAO.getReservasRecientes(fechaDesde, fechaHasta, Long.parseLong(idCliHab));

		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> listFinal = new HashMap<>();

		String jsonToString = "";

		listFinal.put("reservasPendientes:", resultList);

		try {
			jsonToString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(listFinal);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
		return jsonToString;
	}

}
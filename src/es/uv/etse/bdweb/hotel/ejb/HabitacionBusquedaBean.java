package es.uv.etse.bdweb.hotel.ejb;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

import es.uv.etse.bdweb.hotel.DAO.AbstractDAOFactory;
import es.uv.etse.bdweb.hotel.DAO.HabitacionDAO;
import es.uv.etse.bdweb.hotel.DAO.TipoHabitacionDAO;
import es.uv.etse.bdweb.hotel.common.RoomType;
import es.uv.etse.bdweb.hotel.domain.Habitacion;

/**
 * Session Bean implementation class HabitacionBusquedaBean
 */

@Stateless
public class HabitacionBusquedaBean {

	private static final Logger logger = Logger.getLogger(HabitacionBusquedaBean.class.getName());
	
	@PostConstruct
	private void init() {
	}

	@PreDestroy
	private void clean() {
	}
    
	/*
	 * lanzamos al DAO para buscar las habitaciones según los datos del formulario del usuario
	 */
    public List<Habitacion> buscarHabitacionesPorFormaBD(LocalDate fechaEntrada, LocalDate fechaSalida,
    		String tipoHabitacion) {
		logger.info("HabitacionBusquedaBean==============buscarHabitacionesPorForma()");
		logger.info("==============================================================");
		logger.info("===========myFactory==================================================");
		logger.info("=============================hDAO================================");
		logger.info("==============================================================");

		AbstractDAOFactory myFactory= AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		HabitacionDAO hDAO = myFactory.getHabitacionDAO();
			
		List<Habitacion> lista = hDAO.getHabitacionByFormaBusqueda(fechaEntrada, fechaSalida, tipoHabitacion);

		return lista;
    }
    
    /*
     * obtener los tipos de habitasiones que posee el hotel, para tenerlo en el formulario ya 
     */
    public LinkedHashMap<RoomType, BigDecimal> getTiposHabitaciones(){
    	AbstractDAOFactory myFactory= AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		TipoHabitacionDAO thDAO = myFactory.getTipoHabitacionDAO();
		
		return thDAO.getTiposHabitaciones();
    }
	
}

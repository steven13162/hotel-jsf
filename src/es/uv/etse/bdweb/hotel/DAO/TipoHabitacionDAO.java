package es.uv.etse.bdweb.hotel.DAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import es.uv.etse.bdweb.hotel.common.RoomType;

public interface TipoHabitacionDAO {
	
	public LinkedHashMap<RoomType, BigDecimal> getTiposHabitaciones();
	
	public List<Object[]> getTiposHabitacionesAndCountDisponiblesByFecha(LocalDate fecha);

}

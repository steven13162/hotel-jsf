package es.uv.etse.bdweb.hotel.DAO;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import es.uv.etse.bdweb.hotel.common.RoomType;

public interface TipoHabitacionDAO {
	
	public LinkedHashMap<RoomType, BigDecimal> getTiposHabitaciones();

}

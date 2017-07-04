package es.uv.etse.bdweb.hotel.DAO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import es.uv.etse.bdweb.hotel.common.RoomType;
import es.uv.etse.bdweb.hotel.domain.TipoHabitacion;

public class TipoHabitacionDAOImpl extends DAOImpl<Integer, TipoHabitacion> implements TipoHabitacionDAO {

	protected TipoHabitacionDAOImpl(EntityManager em) {
		super(em, TipoHabitacion.class);
	}

	@Override
	public LinkedHashMap<RoomType, BigDecimal> getTiposHabitaciones() {
		LinkedHashMap<RoomType, BigDecimal> mapa = new LinkedHashMap<>();

		List<TipoHabitacion> lista =  this.findAll();
		
		for (TipoHabitacion tipo : lista) {
			mapa.put(tipo.getTipo(), tipo.getPrecio());
		}

		return mapa;
	}
}

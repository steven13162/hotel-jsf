package es.uv.etse.bdweb.hotel.DAO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import es.uv.etse.bdweb.hotel.common.ReserveState;
import es.uv.etse.bdweb.hotel.domain.Habitacion;

public class HabitacionDAOImpl extends DAOImpl<Long, Habitacion> implements HabitacionDAO {

	protected HabitacionDAOImpl(EntityManager em) {
		super(em, Habitacion.class);
	}

	@Override
	public Habitacion getHabitacionById(Long id) {
		return this.getById(id);
	}

	@Override
	public List<Habitacion> getHabitaciones() {
		return this.findAll();
	}

	@Override
	public void createHabitacion(Habitacion h) {
		this.create(h);
	}

	@Override
	public void deleteHabitacion(Habitacion h) {
		this.delete(h);
	}

	@Override
	public void deleteHabitacionById(Long id) {
		this.deleteById(id);
	}

	@Override
	public List<Habitacion> getHabitacionByFormaBusqueda(LocalDate fechaEntrada, LocalDate fechaSalida, String tipoHabitacion) {

		String join = "INNER JOIN e.tipoHabitacion t";
		String criteria = "t.tipo = '"+ tipoHabitacion +"'"
				+ " AND e.id NOT IN (SELECT DISTINCT r.habitacion FROM Reserva r"
				+ " WHERE ((r.fechaInicio >= '" + fechaEntrada + "' AND r.fechaInicio < '" + fechaSalida + "')"
				+ " OR (r.fechaInicio < '" + fechaEntrada + "' AND r.fechaFinal > '" + fechaEntrada + "'))"
				+ " AND (r.estado = '" + ReserveState.ACTIVA.getEstado() + "' OR r.estado = '" + ReserveState.PROGRESA.getEstado() + "'))";
		
		return this.findByCriteria(join, criteria);
	}

	@Override
	public List<Habitacion> getHabitacionByEstado(String estado) {
		return this.findByCriteria("habitacionOcupada = " + estado);
	}
	
}

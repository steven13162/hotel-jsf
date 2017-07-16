package es.uv.etse.bdweb.hotel.DAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import es.uv.etse.bdweb.hotel.common.ReserveState;
import es.uv.etse.bdweb.hotel.domain.Reserva;

public class ReservaDAOImpl extends DAOImpl<Long, Reserva> implements ReservaDAO {

	protected ReservaDAOImpl(EntityManager em) {
		super(em, Reserva.class);
	}

	@Override
	public Reserva getReservaById(Long id) {
		return this.getById(id);
	}

	@Override
	public List<Reserva> getReservas() {
		return this.findAll();
	}

	@Override
	public void createReserva(Reserva r) {
		this.create(r);
	}

	@Override
	public void updateReserva(Reserva r) {
		this.update(r);
	}
	
	@Override
	public void deleteReserva(Reserva r) {
		this.delete(r);
	}

	@Override
	public void deleteReservaById(Long id) {
		this.deleteById(id);
	}

	@Override
	public List<Reserva> getRservaByHabitacionId(Long id) {
		return this.findByCriteria(id.toString());
	}

	@Override
	public List<Reserva> getRservaByClienteDNI(String dni) {
		return this.findByCriteria(dni);
	}

	@Override
	public List<Reserva> getReservaByEstado(String estado) {
		return this.findByCriteria(estado);
	}

	@Override
	public List<Reserva> getRservaByPromocionId(Long id) {
		return this.findByCriteria(id.toString());
	}

	@Override
	public List<Reserva> getReservaByFechaIncio(String date) {
		return this.findByCriteria(date);
	}

	@Override
	public List<Reserva> getReservaByFechaFinal(String date) {
		return this.findByCriteria(date);
	}

	@Override
	public List<Reserva> getReservaByFechas(String dates) {
		return this.findByCriteria(dates);
	}
	
	@Override
	public Boolean getReservaDisponibleByIdAndFechas(Long id, LocalDate fechaEntrada, LocalDate fechaSalida) {
		
		String join = "INNER JOIN e.habitacion h";
		String criteria = "h.id = '" + id
				+ "' AND ((e.fechaInicio >= '" + fechaEntrada + "' AND e.fechaInicio < '" + fechaSalida + "')"
				+ " OR (e.fechaInicio < '" + fechaEntrada + "' AND e.fechaFinal > '" + fechaEntrada + "'))"
				+ " AND (e.estado = '" + ReserveState.ACTIVA.getEstado()
				+ "' OR e.estado = '" + ReserveState.PROGRESA.getEstado() + "'))";
		
		List<Reserva> lista = this.findByCriteria(join, criteria);
		if (lista.isEmpty()) return true;
		else return false;
	}

	@Override
	public List<Reserva> getReservasRecientes(LocalDate fechaDesde, LocalDate fechaHasta, Long id) {

		String criteria = "(e.estado = '" + ReserveState.ACTIVA.getEstado() + "' OR (e.estado = '"
				+ ReserveState.CERRADA.getEstado() + "' AND (e.fechaFinal BETWEEN '" + fechaDesde + "' AND '" + fechaHasta + "')))"
				+ " AND e.cliente = '" + id + "' ORDER BY e.fechaInicio DESC";
		
		List<Reserva> lista = this.findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<Reserva> getReservasCancelables(LocalDate fechaHasta, Long id) {

		String criteria = "e.estado = '" + ReserveState.ACTIVA.getEstado() + "'"
				+ " AND e.fechaInicio > '" + fechaHasta + "'"
				+ " AND e.cliente = '" + id + "' ORDER BY e.fechaInicio ASC";
		
		List<Reserva> lista = this.findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<Reserva> getReservasVencidas(LocalDate fecha) {

		String criteria = "e.estado = '" + ReserveState.ACTIVA.getEstado() + "'"
				+ " AND e.fechaInicio = '" + fecha + "'";
		
		List<Reserva> lista = this.findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public Object getGananciasPorMes(Integer ano, Integer mes){
		
		String query = "SELECT SUM(r.importe) FROM Reserva r"
				+ " WHERE MONTH(r.fechaFinal) = '"+ mes +"' AND YEAR(r.fechaFinal) = '"+ ano +"'"
				+ " AND res_estado = '" + ReserveState.CERRADA.getEstado() + "'";
		
		return this.findByNativeQuery(query);
	}

}

package es.uv.etse.bdweb.hotel.DAO;

import java.time.LocalDate;
import java.util.List;

import es.uv.etse.bdweb.hotel.domain.Reserva;

public interface ReservaDAO {

	public Reserva getReservaById(Long id);

	public List<Reserva> getReservas();

	public void createReserva(Reserva r);
	
	public void updateReserva(Reserva t);

	public void deleteReserva(Reserva r);

	public void deleteReservaById(Long id);
	
	public List<Reserva> getRservaByHabitacionId(Long id);

	public List<Reserva> getRservaByClienteDNI(String dni);

	public List<Reserva> getReservaByEstado(String estado);
	
	public List<Reserva> getRservaByPromocionId(Long id);
	
	public List<Reserva> getReservaByFechaIncio(String date);

	public List<Reserva> getReservaByFechaFinal(String date);

	public List<Reserva> getReservaByFechas(String dates);
	
	public Boolean getReservaDisponibleByIdAndFechas(Long id, LocalDate fechaEntrada, LocalDate fechaSalida);
	
	public List<Reserva> getReservasRecientes(LocalDate fechaDesde, LocalDate fechaHasta, Long id);
}

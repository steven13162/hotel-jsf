package es.uv.etse.bdweb.hotel.DAO;

import java.time.LocalDate;
import java.util.List;

import es.uv.etse.bdweb.hotel.domain.Habitacion;

public interface HabitacionDAO {
	
	public Habitacion getHabitacionById(Long id);

	public List<Habitacion> getHabitaciones();

	public void createHabitacion(Habitacion h);

	public void deleteHabitacion(Habitacion h);

	public void deleteHabitacionById(Long id);

	public List<Habitacion> getHabitacionByFormaBusqueda(LocalDate fechaEntrada, LocalDate fechaSalida, String tipoHabitacion);

	public List<Habitacion> getHabitacionByEstado(String estado);
	
}

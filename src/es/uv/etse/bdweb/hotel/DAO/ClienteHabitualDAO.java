package es.uv.etse.bdweb.hotel.DAO;

import java.util.List;

import es.uv.etse.bdweb.hotel.domain.ClienteHabitual;

public interface ClienteHabitualDAO {

	public ClienteHabitual getClienteHabitualById(Long id);

	public List<ClienteHabitual> getClientesHabituales();

	public void createClienteHabitual(ClienteHabitual c);
	
	public void updateClienteHabitual(ClienteHabitual c);

	public void deleteClienteHabitual(ClienteHabitual c);

	public void deleteClienteHabitualById(Long id);

	public ClienteHabitual getClienteHabitualByDNI(String dni);

	public List<ClienteHabitual> getClienteHabitualByTipo(String tipo);
	
	public void setDescuentoParaTodosClientesHabituales(Integer descuento);
}

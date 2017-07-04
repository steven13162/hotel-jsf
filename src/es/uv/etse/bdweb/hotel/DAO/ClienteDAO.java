package es.uv.etse.bdweb.hotel.DAO;

import java.util.List;

import es.uv.etse.bdweb.hotel.domain.Cliente;

public interface ClienteDAO {
	public Cliente getClienteById(Long id);

	public List<Cliente> getClientes();

	public void createCliente(Cliente c);
	
	public void updateCliente(Cliente c);

	public void deleteCliente(Cliente c);

	public void deleteClienteById(Long id);

	public Cliente getClienteByDNI(String dni);

	public List<Cliente> getClienteByTipo(String tipo);

}

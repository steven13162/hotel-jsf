package es.uv.etse.bdweb.hotel.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import es.uv.etse.bdweb.hotel.domain.Cliente;
import es.uv.etse.bdweb.hotel.domain.ClienteHabitual;

public class ClienteDAOImpl extends DAOImpl<Long, Cliente> implements ClienteDAO {

	protected ClienteDAOImpl(EntityManager em) {
		super(em, Cliente.class);
	}

	@Override
	public Cliente getClienteById(Long id) {
		return this.getById(id);
	}

	@Override
	public List<Cliente> getClientes() {
		return this.findAll();
	}

	@Override
	public void createCliente(Cliente c) {
		this.create(c);
	}

	@Override
	public void updateCliente(Cliente c) {
		this.update(c);
	}
	
	@Override
	public void deleteCliente(Cliente c) {
		this.delete(c);
	}

	@Override
	public void deleteClienteById(Long id) {
		this.deleteById(id);
	}

	//ese método nos debe devolver solo un objeto Cliente, porque el DNI es único en la tabla 'clientes'
	@Override
	public Cliente getClienteByDNI(String dni) {
		List<Cliente> lista =  this.findByCriteria("dni = '" + dni + "'");
		if (lista.isEmpty()) return null;
        else return lista.get(0);
	}

	@Override
	public List<Cliente> getClienteByTipo(String tipo) {
		return this.findByCriteria(tipo);
	}

}

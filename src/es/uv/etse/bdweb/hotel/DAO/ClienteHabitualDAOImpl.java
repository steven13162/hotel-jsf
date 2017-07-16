package es.uv.etse.bdweb.hotel.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import es.uv.etse.bdweb.hotel.common.ReserveState;
import es.uv.etse.bdweb.hotel.domain.ClienteHabitual;

public class ClienteHabitualDAOImpl extends DAOImpl<Long, ClienteHabitual> implements ClienteHabitualDAO {

	protected ClienteHabitualDAOImpl(EntityManager em) {
		super(em, ClienteHabitual.class);
	}
	
	@Override
	public ClienteHabitual getClienteHabitualById(Long id) {
		return this.getById(id);
	}

	@Override
	public List<ClienteHabitual> getClientesHabituales() {
		return this.findAll();
	}

	@Override
	public void createClienteHabitual(ClienteHabitual c) {
		this.create(c);
	}

	@Override
	public void updateClienteHabitual(ClienteHabitual c) {
		this.update(c);
	}
	
	@Override
	public void deleteClienteHabitual(ClienteHabitual c) {
		this.delete(c);
	}

	@Override
	public void deleteClienteHabitualById(Long id) {
		this.deleteById(id);
	}

	//ese método nos debe devolver solo un objeto Cliente, porque el DNI es único en la tabla 'clientes'
	@Override
	public ClienteHabitual getClienteHabitualByDNI(String dni) {
		List<ClienteHabitual> lista =  this.findByCriteria("dni = '" + dni + "'");
		if (lista.isEmpty()) return null;
        else return lista.get(0);
	}

	@Override
	public List<ClienteHabitual> getClienteHabitualByTipo(String tipo) {
		return this.findByCriteria(tipo);
	}
	
	@Override
	public void setDescuentoParaTodosClientesHabituales(Integer descuento){
		//UPDATE bdweb_hotel.clientes_habituales
//		SET cli_descuento = '10';
		
		String query = "UPDATE ClienteHabitual SET descuento = '" + descuento + "'";
		
		this.updateByNativeQuery(query);
	}
}

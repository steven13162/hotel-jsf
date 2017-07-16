package es.uv.etse.bdweb.hotel.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import es.uv.etse.bdweb.hotel.domain.Recepcionista;

public class RecepcionistaDAOImpl extends DAOImpl<Long, Recepcionista> implements RecepcionistaDAO {

	protected RecepcionistaDAOImpl(EntityManager em) {
		super(em, Recepcionista.class);
	}

	@Override
	public Recepcionista getRecepcionistaById(Long id) {
		return this.getById(id);
	}

	@Override
	public List<Recepcionista> getRecepcionistas() {
		return this.findAll();
	}

	@Override
	public void createRecepcionista(Recepcionista r) {
		this.create(r);
	}

	@Override
	public void updateRecepcionista(Recepcionista r) {
		this.update(r);
	}
	
	@Override
	public void deleteRecepcionista(Recepcionista r) {
		this.delete(r);
	}

	@Override
	public void deleteRecepcionistaById(Long id) {
		this.deleteById(id);
	}

	//ese método nos debe devolver solo un objeto Cliente, porque el DNI es único en la tabla 'clientes'
	@Override
	public Recepcionista getRecepcionistaByEmail(String email){
		List<Recepcionista> lista =  this.findByCriteria("email = '" + email + "'");
		if (lista.isEmpty()) return null;
        else return lista.get(0);
	}

}

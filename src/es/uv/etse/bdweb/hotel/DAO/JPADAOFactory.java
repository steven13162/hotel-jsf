package es.uv.etse.bdweb.hotel.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPADAOFactory extends AbstractDAOFactory {
	
	private EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bdweb_hotel");
		return emf.createEntityManager();
	}

	@Override
	public ClienteDAO getClienteDAO() {
		return new ClienteDAOImpl(getEntityManager());
	}

	@Override
	public ClienteHabitualDAO getClienteHabitualDAO() {
		return new ClienteHabitualDAOImpl(getEntityManager());
	}

	@Override
	public HabitacionDAO getHabitacionDAO() {
		return new HabitacionDAOImpl(getEntityManager());
	}

	@Override
	public PromocionDAO getPromocionDAO() {
		return new PromocionDAOImpl(getEntityManager());
	}

	@Override
	public RecepcionistaDAO getRecepcionistaDAO() {
		return new RecepcionistaDAOImpl(getEntityManager());
	}
	
	@Override
	public ReservaDAO getReservaDAO() {
		return new ReservaDAOImpl(getEntityManager());
	}

	@Override
	public TipoHabitacionDAO getTipoHabitacionDAO() {
		return new TipoHabitacionDAOImpl(getEntityManager());
	}
}

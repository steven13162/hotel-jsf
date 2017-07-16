package es.uv.etse.bdweb.hotel.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import es.uv.etse.bdweb.hotel.domain.Promocion;

public class PromocionDAOImpl extends DAOImpl<Long, Promocion> implements PromocionDAO {

	protected PromocionDAOImpl(EntityManager em) {
		super(em, Promocion.class);
	}

	@Override
	public Promocion getPromocionById(Long id) {
		return this.getById(id);
	}

	@Override
	public List<Promocion> getPromociones() {
		return this.findAll();
	}

	@Override
	public void createPromocion(Promocion p) {
		this.create(p);
	}

	@Override
	public void deletePromocion(Promocion p) {
		this.delete(p);
	}

	@Override
	public void deletePromocionById(Long id) {
		this.deleteById(id);
	}

	@Override
	public List<Promocion> getPromocionByFechaIncio(String date) {
		return this.findByCriteria(date);
	}

	@Override
	public List<Promocion> getPromocionByFechaFinal(String date) {
		return this.findByCriteria(date);
	}

	@Override
	public List<Promocion> getPromocionByFechas(String dates) {
		return this.findByCriteria(dates);
	}
	
	@Override
	public List<Promocion> getPromocionesByFechaInicioDesc() {
		return this.findAllOrderByDesc("fechaInicio");
	}

}

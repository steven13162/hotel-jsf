package es.uv.etse.bdweb.hotel.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class DAOImpl<K, T> implements DAO<K, T> {

	private EntityManager em;
	private Class<T> entityClass;
	
	protected DAOImpl(EntityManager em, Class<T> entityClass) {
		this.em = em;
		this.entityClass = entityClass;
		this.em.getTransaction().begin();
	}
	
	@Override
	public T getById(K id) {
		return em.find(entityClass, id);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		Query q = em.createQuery("from " + this.entityClass.getName());
		return q.getResultList();	
	}

	@Override
	public void create(T entity) {
		if (entity != null) {
			em.persist(entity);
			em.getTransaction().commit();
		}
	}

	@Override
	public void update(T entity) {
		if (entity != null) {
			em.merge(entity);
			em.getTransaction().commit();
		}
	}

	@Override
	public void delete(T entity) {
		if (entity != null) {
			em.remove(entity);
			em.getTransaction().commit();
		}
	}

	@Override
	public void deleteById(K entityId) {
		T entity = em.find(entityClass, entityId);
		if (entity != null) {
			em.remove(entity);
			em.getTransaction().commit();	
		}
	}

	@Override
	public List<T> findByCriteria(String criteria) {
		String query  = "SELECT e FROM " + this.entityClass.getName() + " e WHERE " + criteria;
		List<T> resultList = (List<T>) em.createQuery(query, this.entityClass).getResultList();
		return resultList;
	}
	
	@Override
	public List<T> findByCriteria(String join, String criteria) {
		String query = "SELECT e FROM " + this.entityClass.getName() + " e " + join + " WHERE " + criteria;
		List<T> resultList = (List<T>) em.createQuery(query, this.entityClass).getResultList();
		return resultList;
	}

}

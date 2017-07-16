package es.uv.etse.bdweb.hotel.ejb;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

import es.uv.etse.bdweb.hotel.DAO.AbstractDAOFactory;
import es.uv.etse.bdweb.hotel.DAO.PromocionDAO;
import es.uv.etse.bdweb.hotel.domain.Promocion;

/**
 * Session Bean implementation class PromocionesBusquedaBean
 */

@Stateless
public class PromocionesBusquedaBean {

	private static final Logger logger = Logger.getLogger(HabitacionBusquedaBean.class.getName());
	
	@PostConstruct
	private void init() {
	}

	@PreDestroy
	private void clean() {
	}

	public List<Promocion> getAllPromocionesBD(){
		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		PromocionDAO pDAO = myFactory.getPromocionDAO();
		try {
			 return pDAO.getPromocionesByFechaInicioDesc();
		} catch (Exception e) {
			logger.info("Error search promotion");
		}
		return null;
	}	
	
}

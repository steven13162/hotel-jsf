package es.uv.etse.bdweb.hotel.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import es.uv.etse.bdweb.hotel.domain.Promocion;
import es.uv.etse.bdweb.hotel.ejb.PromocionesBusquedaBean;

@ManagedBean(name = "promo")
@ApplicationScoped
public class PromocionManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReservasBusquedaManager.class.getName());
	
	private List<Promocion> listaPromociones;
	
    @EJB
    private PromocionesBusquedaBean promocionesBusquedaBean;
 
	@PostConstruct
	private void init() {
		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n"
				+ "******************PromocionManager===INIT*******************************\n"
				+ "*****************************************\n*****************************************"
				+ "\n*****************************************");
	}
    
    public List<Promocion> getListaPromociones() {
		return listaPromociones;
	}

	public void setListaPromociones(List<Promocion> listaPromociones) {
		this.listaPromociones = listaPromociones;
	}

	public String mostrarPromociones(){
    	String navigation = "/listPromotions?faces-redirect=true";
		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n"
				+ "mostrarPromos() \n"
				+ "\n*****************************************");
		this.listaPromociones = promocionesBusquedaBean.getAllPromocionesBD();
		return navigation;
    }
	
	public Boolean esPromocionActiva(Promocion promocion){
		if (promocion.getFechaInicio().isAfter(LocalDate.now())
				|| promocion.getFechaInicio().isEqual(LocalDate.now()))
			return true;
		else
			return false;
	}
	
}

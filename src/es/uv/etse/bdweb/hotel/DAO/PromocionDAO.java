package es.uv.etse.bdweb.hotel.DAO;

import java.util.List;

import es.uv.etse.bdweb.hotel.domain.Promocion;

public interface PromocionDAO {
	public Promocion getPromocionById(Long id);

	public List<Promocion> getPromociones();

	public void createPromocion(Promocion p);

	public void deletePromocion(Promocion p);

	public void deletePromocionById(Long id);

	public List<Promocion> getPromocionByFechaIncio(String date);

	public List<Promocion> getPromocionByFechaFinal(String date);

	public List<Promocion> getPromocionByFechas(String dates);

}

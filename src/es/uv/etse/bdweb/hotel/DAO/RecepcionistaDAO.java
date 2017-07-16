package es.uv.etse.bdweb.hotel.DAO;

import java.util.List;

import es.uv.etse.bdweb.hotel.domain.Recepcionista;

public interface RecepcionistaDAO {
	public Recepcionista getRecepcionistaById(Long id);

	public List<Recepcionista> getRecepcionistas();

	public void createRecepcionista(Recepcionista r);
	
	public void updateRecepcionista(Recepcionista r);

	public void deleteRecepcionista(Recepcionista r);

	public void deleteRecepcionistaById(Long id);

	public Recepcionista getRecepcionistaByEmail(String email);

}

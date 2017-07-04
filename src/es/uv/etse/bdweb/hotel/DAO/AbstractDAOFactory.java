package es.uv.etse.bdweb.hotel.DAO;

public abstract class AbstractDAOFactory {
	
	public enum TYPE {JPA,XML}
	
	//Definimos un getDAO para cada entidad
	
	public abstract ClienteDAO getClienteDAO();

	public abstract ClienteHabitualDAO getClienteHabitualDAO();
	
	public abstract HabitacionDAO getHabitacionDAO();
	
	public abstract PromocionDAO getPromocionDAO();
	
	public abstract ReservaDAO getReservaDAO();
	
	public abstract TipoHabitacionDAO getTipoHabitacionDAO();
	
	@SuppressWarnings("incomplete-switch")
	public static AbstractDAOFactory getDAOFactory(TYPE t){
		switch(t){
			case JPA:
				return new JPADAOFactory();
			/*case XML:
				return new XMLDAOFactory();*/
		}
		return null;
	}
}

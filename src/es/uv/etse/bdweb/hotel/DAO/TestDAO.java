package es.uv.etse.bdweb.hotel.DAO;

import java.time.LocalDate;
import java.util.List;

import es.uv.etse.bdweb.hotel.common.ReserveState;
import es.uv.etse.bdweb.hotel.common.RoomType;
import es.uv.etse.bdweb.hotel.domain.Habitacion;

public class TestDAO {

	public static void main(String[] args) {
//    	List<Habitacion> lista = new ArrayList<Habitacion>();
		/*
		 * SELECT h.*, t.* FROM habitaciones h
			INNER JOIN tipos_habitaciones t ON h.tipos_habitaciones_tip_hab_id = t.tip_hab_id
			WHERE tip_hab_tipo = "INDIVIDUAL"
			AND h.hab_id NOT IN (SELECT DISTINCT r.habitaciones_hab_id FROM reservas r
			WHERE ((r.res_fecha_inicio >= "2017-06-1" AND r.res_fecha_inicio < "2017-06-13")
			OR (r.res_fecha_inicio < "2017-06-1" AND r.res_fecha_final > "2017-06-1"))
			AND (r.res_estado = "ACTIVA" OR r.res_estado = "PROGRESA")
			) ORDER BY h.hab_numero

		 */

//		LocalDate fechaEntrada = LocalDate.of(2017,6,10);
//		Integer estanciaDias = 10;
//		String tipoHabitacion = RoomType.INDIVIDUAL.getTipo();
//				
//		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
//		HabitacionDAO hDAO = myFactory.getHabitacionDAO();
//
//		List<Habitacion> lista = hDAO.getHabitacionByFormaBusqueda(fechaEntrada, estanciaDias, tipoHabitacion);
//		
//		for (Habitacion h: lista){
//			System.out.println(h.toString());
//		}
		
		
		//Funciona esa fábrica con persistence de jdbc!  
//		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
//		ClienteDAO cDAO = myFactory.getClienteDAO();
//
//		List<Cliente> lista = cDAO.getClientes();
//		
//		for (Cliente c: lista){
//			System.out.println(c.getApellidos());
//		}
		
	}
}

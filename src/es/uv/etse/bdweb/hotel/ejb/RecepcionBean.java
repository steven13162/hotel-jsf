package es.uv.etse.bdweb.hotel.ejb;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import es.uv.etse.bdweb.hotel.DAO.AbstractDAOFactory;
import es.uv.etse.bdweb.hotel.DAO.ClienteHabitualDAO;
import es.uv.etse.bdweb.hotel.DAO.RecepcionistaDAO;
import es.uv.etse.bdweb.hotel.DAO.ReservaDAO;
import es.uv.etse.bdweb.hotel.DAO.TipoHabitacionDAO;
import es.uv.etse.bdweb.hotel.domain.Recepcionista;
import es.uv.etse.bdweb.hotel.domain.Reserva;

/**
 * Session Bean implementation class RecepcionBean
 */

@Stateless
public class RecepcionBean {

	private static final Logger logger = Logger.getLogger(RecepcionBean.class.getName());
	
	@PostConstruct
	private void init() {
	}

	public Recepcionista buscarRecepcionistaBD(String email){
		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		RecepcionistaDAO recepDAO = myFactory.getRecepcionistaDAO();
		try {
			 return recepDAO.getRecepcionistaById(new Long(1));
		} catch (Exception e) {
			logger.info("\nError search receptionist\n");
		}
		return null;
	}
	
	
	public List<Recepcionista> mostrarTodosRecepcionistasBD(){
		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		RecepcionistaDAO recepDAO = myFactory.getRecepcionistaDAO();
		try {
			 return recepDAO.getRecepcionistas();
		} catch (Exception e) {
			logger.info("\nError search receptionist\n");
		}
		return null;
	}	
	
	public List<Reserva> mostrarReservasVencidasBD(LocalDate fecha){
		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ReservaDAO rDAO = myFactory.getReservaDAO();
		try {
			 return rDAO.getReservasVencidas(fecha);
		} catch (Exception e) {
			logger.info("\nError search reserves!\n");
		}
		return null;
	}
	
    public List<Object[]> buscarHabitacionesPorEstadoBD(LocalDate fecha) {
		logger.info("HabitacionBusquedaBean==============buscarHabitacionesPorEstado()");
		logger.info("==============================================================");
		logger.info("===========myFactory==================================================");
		logger.info("=============================hDAO================================");
		logger.info("==============================================================");
		
		AbstractDAOFactory myFactory= AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		TipoHabitacionDAO thDAO = myFactory.getTipoHabitacionDAO();
		
		// está hecho para buscar las habitaciones por el estado: OCUPADA o DISPONIBLE		
		List<Object[]> lista = thDAO.getTiposHabitacionesAndCountDisponiblesByFecha(fecha);
		return lista;
    }
    
    public Object consultarGananciasBD(Integer ano, Integer mes){
		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ReservaDAO rDAO = myFactory.getReservaDAO();
		try {
			 return rDAO.getGananciasPorMes(ano, mes);
		} catch (Exception e) {
			logger.info("\nError consult profits!\n");
		}
		return null;
    }
    
    public Boolean aplicarDescuentoBD(Integer descuento){
		AbstractDAOFactory myFactory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.TYPE.JPA);
		ClienteHabitualDAO chDAO = myFactory.getClienteHabitualDAO();
		try {
			 chDAO.setDescuentoParaTodosClientesHabituales(descuento);
			 return true;
		} catch (Exception e) {
			logger.info("\nError apply discount!\n");
		}
		return false;
    }
    
	
//	public void saveReservasPendientesToCSV(){
//		try {
//			File csvFile = new File("csv/csvReservasPendientes.csv");
//			FileOutputStream is = new FileOutputStream(csvFile);
//			OutputStreamWriter osw = new OutputStreamWriter(is);
//			
//			CsvWriter writer = new CsvWriter(osw, new CsvWriterSettings());
//			writer.writeHeaders(new String[] {"id","firstName", "lastName", "birthDate", "hireDate", "gender"});
//			writer.writeRow("id", "getFirstName()","getLastName()", "getBirthDate()", "getHireDate()", "getGender()");
//			writer.close();
//			logger.info("El objeto se ha escrito a CSV con éxito: csv/csvReservasPendientes.csv");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }	
//	}
    
}

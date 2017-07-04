package es.uv.etse.bdweb.hotel.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import es.uv.etse.bdweb.hotel.common.Constants;
import es.uv.etse.bdweb.hotel.common.UtilityService;
import es.uv.etse.bdweb.hotel.domain.Cliente;
import es.uv.etse.bdweb.hotel.domain.Habitacion;
import es.uv.etse.bdweb.hotel.domain.Reserva;
import es.uv.etse.bdweb.hotel.web.util.SessionUtility;

@ManagedBean(name = "resmanager")
@SessionScoped
public class ReservaManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReservaManager.class.getName());
	
	private Reserva reserva;
    private Reserva reservaInfo;
    
    private Boolean cobrarReserva = false;
	
	@PostConstruct
	private void init() {
		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n"
				+ "******************ReservaManager===INIT*******************************\n"
				+ "*****************************************\n*****************************************"
				+ "\n*****************************************");
		this.reserva = new Reserva();
		 //porque la variable de "reserva" será borrada después que acabe el proceso de reservar
		//aunqué tenemos que mostrar algunos datos al usuario
		this.reservaInfo = new Reserva();
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

    public Reserva getReservaInfo() {
		return reservaInfo;
	}

	public void setReservaInfo(Reserva reservaInfo) {
		this.reservaInfo = reservaInfo;
	}

	public Boolean getCobrarReserva() {
		return cobrarReserva;
	}

	public void setCobrarReserva(Boolean cobrarReserva) {
		this.cobrarReserva = cobrarReserva;
	}

	public void crearReserva(Habitacion habitacion, LocalDate fechaEntrada, LocalDate fechaSalida) {
//    	Reserva reserva = new Reserva();
    	
        this.reserva.setFechaInicio(fechaEntrada);
        this.reserva.setFechaFinal(fechaSalida);
        this.reserva.setHabitacion(habitacion);
        
		HttpSession session = SessionUtility.getSession();
		
		if (session != null && session.getAttribute("clienteHabitualSession") != null) {
			this.reserva.setCliente((Cliente) session.getAttribute("clienteHabitualSession"));
		}
		else {
			this.reserva.setCliente(null);
		}
		
		this.reserva.setImporte();
        this.reservaInfo = reserva;
        this.cobrarReserva = false;
        
       logger.info("\n*****************************************\n*****************************************\n"
        		+ "*********ReservaManager******crearReserva()**************************\n"
        		+ "******************reserva creada*******************************\n"
        		+ this.reserva.toString() +"\n"
        		+ "*****************************************\n*****************************************"
        		+ "\n*****************************************");
    }
}

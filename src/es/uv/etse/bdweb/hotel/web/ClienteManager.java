package es.uv.etse.bdweb.hotel.web;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import es.uv.etse.bdweb.hotel.domain.Cliente;

@ManagedBean(name = "climanager")
@SessionScoped
public class ClienteManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ClienteHabitualManager.class.getName());
	
	private Cliente cliente;
	
	@PostConstruct
	private void init() {
		logger.info("\n*****************************************\n*****************************************\n"
				+ "*****************************************\n"
				+ "****************** ClienteManager===INIT*******************************\n"
				+ "*****************************************\n*****************************************"
				+ "\n*****************************************");
		this.cliente = new Cliente();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
}

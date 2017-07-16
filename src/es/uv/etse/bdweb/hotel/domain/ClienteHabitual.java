package es.uv.etse.bdweb.hotel.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ClienteHabitual
 *
 */
@Entity
@Table(name = "clientes_habituales")
//@DiscriminatorValue("cliente_habitual")

public class ClienteHabitual extends Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	public ClienteHabitual(Cliente cliente) {
		this.setApellidos(cliente.getApellidos());
		this.setDireccion(cliente.getDireccion());
		this.setDni(cliente.getDni());
		this.setEmail(cliente.getEmail());
		this.setNombre(cliente.getNombre());
		this.setNumeroMovil(cliente.getNumeroMovil());
		this.setNumeroTarjeta(cliente.getNumeroTarjeta());
	}

	@Column(name = "cli_newsletter")
	private Boolean newsletter = true;

	@Column(name = "cli_descuento")
	private Integer descuento = 5;

	public ClienteHabitual() {
		super();
	}

	public Boolean getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Boolean newsletter) {
		this.newsletter = newsletter;
	}

	public Integer getDescuento() {
		return descuento;
	}

	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}

//	@Override
//	public String toString(){
//		return "ClienteHabitual: id: "+ getId() + ", nombre: " + getNombre() + ", apellidos: " + getApellidos()
//				+ ", direccion: " + getDireccion() + ", dni: " + getDni() + ", email: " + getEmail()
//				+ ", numeroMovil: " + getNumeroMovil() + ", numeroTarjeta: " + getNumeroTarjeta()
//				+ ", password: " + getPassword() + ", descuento: " + descuento + ", newsletter: " + newsletter
//				+ descuento + ", tipoCliente: " + getTipoCliente();

	
	
//	@Override
//	public String toString() {
//		return "ClienteHabitual: id: " + getId() + ", nombre: " + getNombre() + ", apellidos: " + getApellidos()
//				+ ", direccion: " + getDireccion() + ", dni: " + getDni() + ", email: " + getEmail() + ", numeroMovil: "
//				+ getNumeroMovil() + ", numeroTarjeta: " + getNumeroTarjeta() + ", password: " + getPassword()
//				+ ", descuento: " + descuento + ", newsletter: " + newsletter;
//	}
}

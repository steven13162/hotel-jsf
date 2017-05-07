package es.uv.etse.bdweb.hotel.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ClienteHabitual
 *
 */
@Entity
@Table(name="clientes_habituales")
@DiscriminatorValue("cliente_habitual")

public class ClienteHabitual extends Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="cli_newsletter")
	private Boolean newsletter;
	
	@Column(name="cli_descuento")
	private Integer descuento;
	
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
   
}

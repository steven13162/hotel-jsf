package es.uv.etse.bdweb.hotel.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Cliente
 *
 */
@Entity
@Table(name="clientes")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name = "cli_tipo")
@DiscriminatorValue("cliente")
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name="cli_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="cli_nombre")
	private String nombre;
	
	@Column(name="cli_apellidos")
	private String apellidos;

	@Column(name="cli_password")
	private String password;
	
	@Column(name="cli_direccion")
	private String direccion;
	
	@Column(name="cli_dni")
	private String dni;
	
	@Column(name="cli_email")
	private String email;
	
	@Column(name="cli_numero_movil")
	private String numeroMovil;
	
	@Column(name="cli_numero_tarjeta")
	private String numeroTarjeta;
	
	@OneToMany(mappedBy="cliente")
	private List<Reserva> listReservas;
	 
	public Cliente() {
		super();
	}
   
}

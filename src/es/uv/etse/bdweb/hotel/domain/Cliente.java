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
//@DiscriminatorColumn(name = "cli_tipo")
//@DiscriminatorValue("cliente")
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
	
//	@Column(name="cli_tipo", insertable = false, updatable = false)
//	private String tipoCliente = "cliente";
	
	@OneToMany(mappedBy="cliente")
	private List<Reserva> listReservas;
	 
	public Cliente() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumeroMovil() {
		return numeroMovil;
	}

	public void setNumeroMovil(String numeroMovil) {
		this.numeroMovil = numeroMovil;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public List<Reserva> getListReservas() {
		return listReservas;
	}

	public void setListReservas(List<Reserva> listReservas) {
		this.listReservas = listReservas;
	}
//
//	public String getTipoCliente() {
//		return tipoCliente;
//	}
//
//	public void setTipoCliente(String tipoCliente) {
//		this.tipoCliente = tipoCliente;
//	}
	
//	@Override
//	public String toString(){
//		return "Cliente: id:"+ id + ", nombre:" + nombre + ", apellidos:" + apellidos
//				+ ", direccion:" + direccion + ", dni:" + dni + ", email:" + email
//				+ ", numeroMovil:" + numeroMovil + ", numeroTarjeta" + numeroTarjeta
//				+ ", password" + password + ", tipoCliente" + tipoCliente;
		
		@Override
		public String toString(){
			return "Cliente: id:"+ id + ", nombre:" + nombre + ", apellidos:" + apellidos
					+ ", direccion:" + direccion + ", dni:" + dni + ", email:" + email
					+ ", numeroMovil:" + numeroMovil + ", numeroTarjeta" + numeroTarjeta
					+ ", password" + password;
	}
   
}

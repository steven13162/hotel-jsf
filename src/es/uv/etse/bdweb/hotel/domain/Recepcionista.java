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
 * Entity implementation class for Entity: Recepcionista
 *
 */
@Entity
@Table(name = "recepcionistas")
public class Recepcionista implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "recep_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "recep_nombre")
	private String nombre;

	@Column(name = "recep_apellidos")
	private String apellidos;

	@Column(name = "recep_password")
	private String password;

	@Column(name = "recep_email")
	private String email;

	public Recepcionista() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	@Override
//	public String toString() {
//		return "Recepcionista: id:" + id + ", nombre:" + nombre + ", apellidos:" + apellidos + ", email:" + email
//				+ ", password" + password;
//	}

}

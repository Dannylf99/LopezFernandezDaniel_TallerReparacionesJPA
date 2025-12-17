package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre_usuario;
	private String password;
	String rol;
	private String dni;

	public Usuario(String nombre_usuario, String dni, String password, String rol) {
		this.nombre_usuario = nombre_usuario;
		this.password = password;
		this.dni = dni;
		this.rol = rol;

	}

	public Long getId_usuario() {
		return id;
	}

	public void setId_usuario(Long id_usuario) {
		this.id = id_usuario;
	}

	
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public /*Rol*/String getRol() {
		return rol;
	}

	public void setRol(String rol)  { //Rol rol) {

		this.rol = rol;
	}
	
	@Override
	public String toString() {
	    return "Usuario{" +
	            "id_usuario=" + id +
	            ", nombre_usuario='" + nombre_usuario + '\'' +
	            ", dni='" + dni + '\'' +
	            ", rol='" + rol + '\'' +
	            '}';
	}

}


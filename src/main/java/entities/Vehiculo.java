package entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String matricula;
	private String marca;
	private String modelo;
	private Long cliente_id;

	public Vehiculo(String matricula, String marca, String modelo, Long cliente_id) {
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.cliente_id = cliente_id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Long getId_vehiculo() {
		return id;
	}

	public void setId_vehiculo(Long id_vehiculo) {
		this.id = id_vehiculo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Long getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Long cliente_id) {
		this.cliente_id = cliente_id;
	}

	@Override
	public String toString() {
	    return "Vehiculo{" +
	            "id_vehiculo=" + id +
	            ", matricula='" + matricula + '\'' +
	            ", marca='" + marca + '\'' +
	            ", modelo='" + modelo + '\'' +
	            ", cliente_id=" + cliente_id +
	            '}';
	}

}

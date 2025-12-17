package entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reparacion{
	
	private String descripcion;
	private Date fecha_entrada;
	private double coste_estimado;
	private String estado;
	private Long vehiculo_id;
	private Long usuario_id;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Reparacion(String descripcion, Date fecha_entrada, double coste_estimado, String estado,
			Long vehiculo_id, Long usuario_id) {
		this.descripcion = descripcion;
		this.fecha_entrada = fecha_entrada;
		this.coste_estimado = coste_estimado;
		this.estado = estado;
		this.vehiculo_id = vehiculo_id;
		this.usuario_id = usuario_id;
	}

	public Long getId_reparacion() {
		return id;
	}



	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha_entrada() {
		return fecha_entrada;
	}

	public void setFecha_entrada(Date fecha_entrada) {
		this.fecha_entrada = fecha_entrada;
	}

	public double getCoste_estimado() {
		return coste_estimado;
	}

	public void setCoste_estimado(double coste_estimado) {
		this.coste_estimado = coste_estimado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getVehiculo_id() {
		return vehiculo_id;
	}

	public void setVehiculo_id(Long vehiculo_id) {
		this.vehiculo_id = vehiculo_id;
	}

	public Long getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}

	@Override
	public String toString() {
	    return "Reparacion id=" + id +
	           ", descripcion='" + descripcion + '\'' +
	           ", fechaEntrada=" + fecha_entrada +
	           ", costeEstimado=" + coste_estimado +
	           ", estado='" + estado + '\'' +
	           ", vehiculoId=" + vehiculo_id +
	           ", usuarioId=" + usuario_id + '}';
	}
}



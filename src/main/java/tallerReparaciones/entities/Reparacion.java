package tallerReparaciones.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

@Entity
@Table(name = "reparacion")
public class Reparacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reparacion")
    private Long id;
    
    @Column(length = 120)
    private String descripcion;
    
    @Column(name = "fecha_entrada")
    private LocalDate fechaEntrada;
    
    @Column(name = "coste_estimado")
    private Double costeEstimado;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReparacion estado;
    
    // Relación Many-to-One con Vehiculo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    @JsonIgnoreProperties({"reparaciones", "hibernateLazyInitializer", "handler"})
    private Vehiculo vehiculo;
    
    // Relación Many-to-One con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"reparaciones", "hibernateLazyInitializer", "handler"})
    private Usuario usuario;
    
    // Constructor vacío (obligatorio para JPA)
    public Reparacion() {
    }
    
    // Constructor con parámetros
    public Reparacion(String descripcion, LocalDate fechaEntrada, Double costeEstimado, EstadoReparacion estado) {
        this.descripcion = descripcion;
        this.fechaEntrada = fechaEntrada;
        this.costeEstimado = costeEstimado;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }
    
    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }
    
    public Double getCosteEstimado() {
        return costeEstimado;
    }
    
    public void setCosteEstimado(Double costeEstimado) {
        this.costeEstimado = costeEstimado;
    }
    
    public EstadoReparacion getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoReparacion estado) {
        this.estado = estado;
    }
    
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public String toString() {
        return "Reparacion{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", fechaEntrada=" + fechaEntrada +
                ", costeEstimado=" + costeEstimado +
                ", estado=" + estado +
                '}';
    }
}
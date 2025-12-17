package tallerReparaciones.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long id;
    
    @Column(nullable = false, unique = true, length = 10)
    private String matricula;
    
    @Column(length = 40)
    private String marca;
    
    @Column(length = 50)
    private String modelo;
    
    // Relación Many-to-One con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"vehiculos", "hibernateLazyInitializer", "handler"})
    private Cliente cliente;
    
    // Relación One-to-Many con Reparacion
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("vehiculo")
    private List<Reparacion> reparaciones = new ArrayList<>();
    
    // Constructor vacío (obligatorio para JPA)
    public Vehiculo() {
    }
    
    // Constructor con parámetros
    public Vehiculo(String matricula, String marca, String modelo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public List<Reparacion> getReparaciones() {
        return reparaciones;
    }
    
    public void setReparaciones(List<Reparacion> reparaciones) {
        this.reparaciones = reparaciones;
    }
    
    // Métodos de utilidad para la relación bidireccional
    public void addReparacion(Reparacion reparacion) {
        reparaciones.add(reparacion);
        reparacion.setVehiculo(this);
    }
    
    public void removeReparacion(Reparacion reparacion) {
        reparaciones.remove(reparacion);
        reparacion.setVehiculo(null);
    }
    
    @Override
    public String toString() {
        return "Vehiculo{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                '}';
    }
}
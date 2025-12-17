package tallerReparaciones.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;
    
    @Column(nullable = false, length = 45)
    private String nombre;
    
    @Column(nullable = false, unique = true, length = 10)
    private String dni;
    
    @Column(nullable = false, length = 45)
    private String email;
    
    @Column(name = "telefono")
    private Integer telefono;
    
    // Relación One-to-Many con Vehiculo
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehiculo> vehiculos = new ArrayList<>();
    
    // Constructor vacío (obligatorio para JPA)
    public Cliente() {
    }
    
    // Constructor con parámetros
    public Cliente(String nombre, String dni, String email, Integer telefono) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
    }
    
    // Getters y Setters
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
    
    public Integer getTelefono() {
        return telefono;
    }
    
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
    
    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }
    
    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }
    
    // Métodos de utilidad para la relación bidireccional
    public void addVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
        vehiculo.setCliente(this);
    }
    
    public void removeVehiculo(Vehiculo vehiculo) {
        vehiculos.remove(vehiculo);
        vehiculo.setCliente(null);
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}
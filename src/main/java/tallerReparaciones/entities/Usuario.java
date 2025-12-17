package tallerReparaciones.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;
    
    @Column(name = "nombre_usuario", nullable = false, length = 45)
    private String nombreUsuario;
    
    @Column(nullable = false, unique = true, length = 10)
    private String dni;
    
    @Column(nullable = false, length = 200)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;
    
    // Relación One-to-Many con Reparacion
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Reparacion> reparaciones = new ArrayList<>();
    
    
    public Usuario() {
    }
    
    
    public Usuario(String nombreUsuario, String dni, String password, Rol rol) {
        this.nombreUsuario = nombreUsuario;
        this.dni = dni;
        this.password = password;
        this.rol = rol;
    }
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Rol getRol() {
        return rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public List<Reparacion> getReparaciones() {
        return reparaciones;
    }
    
    public void setReparaciones(List<Reparacion> reparaciones) {
        this.reparaciones = reparaciones;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", dni='" + dni + '\'' +
                ", rol=" + rol +
                '}';
    }
}
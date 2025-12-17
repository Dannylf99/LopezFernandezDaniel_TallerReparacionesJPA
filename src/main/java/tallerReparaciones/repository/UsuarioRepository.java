package tallerReparaciones.repository;

import tallerReparaciones.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Consulta derivada: Buscar usuario por DNI
    Usuario findByDni(String dni);
    
    // Consulta derivada: Buscar usuario por DNI y contraseña (para login)
    Optional<Usuario> findByDniAndPassword(String dni, String password);
    
    // Consulta derivada: Verificar si existe un usuario con ese DNI
    boolean existsByDni(String dni);
    
    // Consulta derivada: Buscar usuarios por rol
    List<Usuario> findByRol(Rol rol);
    
    // Consulta JPQL: Contar reparaciones por usuario (mecánico)
    @Query("SELECT u.nombreUsuario, COUNT(r) FROM Usuario u " +
           "LEFT JOIN u.reparaciones r " +
           "WHERE u.rol = 'MECANICO' " +
           "GROUP BY u.id, u.nombreUsuario")
    List<Object[]> countReparacionesByMecanico();
}
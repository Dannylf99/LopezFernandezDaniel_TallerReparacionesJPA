package tallerReparaciones.repository;

import tallerReparaciones.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Consulta derivada: Buscar cliente por DNI
    Optional<Cliente> findByDni(String dni);
    
    // Consulta derivada: Verificar si existe un cliente con ese DNI
    boolean existsByDni(String dni);
    
    // Consulta JPQL personalizada: Contar vehículos de un cliente
    @Query("SELECT COUNT(v) FROM Vehiculo v WHERE v.cliente.id = :clienteId")
    Long countVehiculosByClienteId(Long clienteId);
    
    // Consulta JPQL: Buscar cliente con sus vehículos (evita N+1 queries)
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.vehiculos WHERE c.id = :id")
    Optional<Cliente> findByIdWithVehiculos(Long id);
}
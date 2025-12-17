package tallerReparaciones.repository;

import tallerReparaciones.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    
    // Consulta derivada: Buscar vehículo por matrícula
    Optional<Vehiculo> findByMatricula(String matricula);
    
    // Consulta derivada: Verificar si existe un vehículo con esa matrícula
    boolean existsByMatricula(String matricula);
    
    // Consulta derivada: Buscar todos los vehículos de un cliente
    List<Vehiculo> findByClienteId(Long clienteId);
    
    // Consulta JPQL: Buscar vehículos de un cliente con el cliente cargado
    @Query("SELECT v FROM Vehiculo v JOIN FETCH v.cliente WHERE v.cliente.id = :clienteId")
    List<Vehiculo> findByClienteIdWithCliente(Long clienteId);
    
    // Consulta JPQL: Contar vehículos por marca
    @Query("SELECT v.marca, COUNT(v) FROM Vehiculo v GROUP BY v.marca")
    List<Object[]> countByMarca();
}
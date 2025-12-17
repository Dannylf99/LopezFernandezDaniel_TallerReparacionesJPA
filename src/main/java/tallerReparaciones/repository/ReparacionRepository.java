package tallerReparaciones.repository;

import tallerReparaciones.entities.EstadoReparacion;
import tallerReparaciones.entities.Reparacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReparacionRepository extends JpaRepository<Reparacion, Long> {
    
    // CU1: Buscar reparaciones finalizadas
    List<Reparacion> findByEstado(EstadoReparacion estado);
    
    // Consulta derivada: Buscar reparaciones por vehículo
    List<Reparacion> findByVehiculoId(Long vehiculoId);
    
    // Consulta derivada: Buscar reparaciones por usuario/mecánico
    List<Reparacion> findByUsuarioId(Long usuarioId);
    
    // Consulta JPQL: Buscar reparaciones con vehículo y usuario cargados (evita N+1)
    @Query("SELECT r FROM Reparacion r " +
           "LEFT JOIN FETCH r.vehiculo v " +
           "LEFT JOIN FETCH r.usuario u " +
           "WHERE r.estado = :estado")
    List<Reparacion> findByEstadoWithDetails(@Param("estado") EstadoReparacion estado);
    
    // CU6: Contar reparaciones por estado (para estadísticas)
    @Query("SELECT r.estado, COUNT(r) FROM Reparacion r GROUP BY r.estado")
    List<Object[]> countByEstado();
    
    // CU6: Calcular coste medio por estado
    @Query("SELECT r.estado, AVG(r.costeEstimado) FROM Reparacion r GROUP BY r.estado")
    List<Object[]> avgCosteByEstado();
    
    // CU6: Contar reparaciones por mecánico
    @Query("SELECT u.nombreUsuario, COUNT(r) FROM Reparacion r " +
           "JOIN r.usuario u " +
           "WHERE u.rol = 'MECANICO' " +
           "GROUP BY u.id, u.nombreUsuario")
    List<Object[]> countByMecanico();
    
    // CU6: Contar reparaciones por cliente
    @Query("SELECT c.nombre, COUNT(r) FROM Reparacion r " +
           "JOIN r.vehiculo v " +
           "JOIN v.cliente c " +
           "GROUP BY c.id, c.nombre")
    List<Object[]> countByCliente();
    
    // Consulta para obtener el total de reparaciones
    @Query("SELECT COUNT(r) FROM Reparacion r")
    Long countTotal();
    
    // Consulta para obtener coste total
    @Query("SELECT SUM(r.costeEstimado) FROM Reparacion r")
    Double sumCosteTotal();
    
    // Buscar reparaciones por matrícula del vehículo
    @Query("SELECT r FROM Reparacion r " +
           "JOIN r.vehiculo v " +
           "WHERE v.matricula = :matricula")
    List<Reparacion> findByVehiculoMatricula(@Param("matricula") String matricula);
}
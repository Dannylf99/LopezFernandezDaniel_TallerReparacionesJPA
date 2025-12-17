package tallerReparaciones.service;

import tallerReparaciones.entities.*;
import tallerReparaciones.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReparacionService {
    
    private final ReparacionRepository reparacionRepository;
    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;
    
    public ReparacionService(ReparacionRepository reparacionRepository,
                           VehiculoRepository vehiculoRepository,
                           UsuarioRepository usuarioRepository) {
        this.reparacionRepository = reparacionRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
    }
    
    // CU1: Ver reparaciones finalizadas
    public List<Reparacion> findFinalizadas() {
        return reparacionRepository.findByEstado(EstadoReparacion.FINALIZADO);
    }
    
    // CU1: Ver reparaciones finalizadas con detalles (vehiculo y usuario cargados)
    public List<Reparacion> findFinalizadasWithDetails() {
        return reparacionRepository.findByEstadoWithDetails(EstadoReparacion.FINALIZADO);
    }
    
    // Listar todas las reparaciones
    public List<Reparacion> findAll() {
        return reparacionRepository.findAll();
    }
    
    // Buscar reparación por ID
    public Optional<Reparacion> findById(Long id) {
        return reparacionRepository.findById(id);
    }
    
    // Buscar reparaciones por estado
    public List<Reparacion> findByEstado(EstadoReparacion estado) {
        return reparacionRepository.findByEstado(estado);
    }
    
    // Buscar reparaciones por vehículo
    public List<Reparacion> findByVehiculoId(Long vehiculoId) {
        return reparacionRepository.findByVehiculoId(vehiculoId);
    }
    
    // Buscar reparaciones por matrícula
    public List<Reparacion> findByVehiculoMatricula(String matricula) {
        return reparacionRepository.findByVehiculoMatricula(matricula);
    }
    
    // Buscar reparaciones por usuario/mecánico
    public List<Reparacion> findByUsuarioId(Long usuarioId) {
        return reparacionRepository.findByUsuarioId(usuarioId);
    }
    
    // CU3: Registrar nueva reparación
    public Reparacion registrar(Long vehiculoId, Long usuarioId, String descripcion, Double costeEstimado) {
        // Validar que el vehículo existe
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + vehiculoId));
        
        // Validar que el usuario existe
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId));
        
        // Crear la reparación con estado inicial PENDIENTE
        Reparacion reparacion = new Reparacion();
        reparacion.setDescripcion(descripcion);
        reparacion.setFechaEntrada(LocalDate.now());
        reparacion.setCosteEstimado(costeEstimado);
        reparacion.setEstado(EstadoReparacion.PENDIENTE);
        reparacion.setVehiculo(vehiculo);
        reparacion.setUsuario(usuario);
        
        return reparacionRepository.save(reparacion);
    }
    
    // CU3: Registrar reparación (versión alternativa con objeto completo)
    public Reparacion save(Reparacion reparacion) {
        // Validaciones
        if (reparacion.getVehiculo() == null || reparacion.getVehiculo().getId() == null) {
            throw new IllegalArgumentException("Debe especificar un vehículo válido");
        }
        if (reparacion.getUsuario() == null || reparacion.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Debe especificar un usuario válido");
        }
        
        // Verificar que existen
        vehiculoRepository.findById(reparacion.getVehiculo().getId())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
        usuarioRepository.findById(reparacion.getUsuario().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        // Si es nueva, establecer estado PENDIENTE y fecha actual
        if (reparacion.getId() == null) {
            reparacion.setEstado(EstadoReparacion.PENDIENTE);
            if (reparacion.getFechaEntrada() == null) {
                reparacion.setFechaEntrada(LocalDate.now());
            }
        }
        
        return reparacionRepository.save(reparacion);
    }
    
    // CU4: Cambiar estado de reparación
    public Reparacion cambiarEstado(Long id, EstadoReparacion nuevoEstado) {
        Reparacion reparacion = reparacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reparación no encontrada con ID: " + id));
        
        reparacion.setEstado(nuevoEstado);
        return reparacionRepository.save(reparacion);
    }
    
    // Actualizar reparación completa
    public Reparacion update(Long id, Reparacion reparacionActualizada) {
        Reparacion reparacion = reparacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reparación no encontrada con ID: " + id));
        
        reparacion.setDescripcion(reparacionActualizada.getDescripcion());
        reparacion.setCosteEstimado(reparacionActualizada.getCosteEstimado());
        reparacion.setEstado(reparacionActualizada.getEstado());
        
        return reparacionRepository.save(reparacion);
    }
    
    // Eliminar reparación
    public void delete(Long id) {
        if (!reparacionRepository.existsById(id)) {
            throw new IllegalArgumentException("Reparación no encontrada con ID: " + id);
        }
        reparacionRepository.deleteById(id);
    }
    
    // CU6: Estadísticas - Contar reparaciones por estado
    public List<Object[]> countByEstado() {
        return reparacionRepository.countByEstado();
    }
    
    // CU6: Estadísticas - Coste medio por estado
    public List<Object[]> avgCosteByEstado() {
        return reparacionRepository.avgCosteByEstado();
    }
    
    // CU6: Estadísticas - Contar reparaciones por mecánico
    public List<Object[]> countByMecanico() {
        return reparacionRepository.countByMecanico();
    }
    
    // CU6: Estadísticas - Contar reparaciones por cliente
    public List<Object[]> countByCliente() {
        return reparacionRepository.countByCliente();
    }
    
    // CU6: Estadísticas - Total de reparaciones
    public Long countTotal() {
        return reparacionRepository.countTotal();
    }
    
    // CU6: Estadísticas - Coste total
    public Double sumCosteTotal() {
        return reparacionRepository.sumCosteTotal();
    }
}
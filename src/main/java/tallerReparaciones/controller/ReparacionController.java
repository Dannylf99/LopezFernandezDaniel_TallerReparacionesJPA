package tallerReparaciones.controller;

import tallerReparaciones.entities.EstadoReparacion;
import tallerReparaciones.entities.Reparacion;
import tallerReparaciones.service.ReparacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reparaciones")
public class ReparacionController {
    
    private final ReparacionService reparacionService;
    
    public ReparacionController(ReparacionService reparacionService) {
        this.reparacionService = reparacionService;
    }
    
    /**
     * CU1: Ver reparaciones finalizadas
     * GET /api/reparaciones/finalizadas
     */
    @GetMapping("/finalizadas")
    public ResponseEntity<List<Reparacion>> getReparacionesFinalizadas() {
        return ResponseEntity.ok(reparacionService.findFinalizadasWithDetails());
    }
    
    /**
     * Listar todas las reparaciones
     * GET /api/reparaciones
     */
    @GetMapping
    public ResponseEntity<List<Reparacion>> getAllReparaciones() {
        return ResponseEntity.ok(reparacionService.findAll());
    }
    
    /**
     * Obtener reparación por ID
     * GET /api/reparaciones/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reparacion> getReparacionById(@PathVariable Long id) {
        return reparacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Buscar reparaciones por estado
     * GET /api/reparaciones/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reparacion>> getReparacionesByEstado(@PathVariable EstadoReparacion estado) {
        return ResponseEntity.ok(reparacionService.findByEstado(estado));
    }
    
    /**
     * Buscar reparaciones por matrícula
     * GET /api/reparaciones/vehiculo/{matricula}
     */
    @GetMapping("/vehiculo/{matricula}")
    public ResponseEntity<List<Reparacion>> getReparacionesByMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(reparacionService.findByVehiculoMatricula(matricula));
    }
    
    /**
     * CU3: Registrar reparación
     * POST /api/reparaciones
     * Body: {
     *   "descripcion": "Cambio de aceite",
     *   "costeEstimado": 150.00,
     *   "vehiculo": { "id": 1 },
     *   "usuario": { "id": 2 }
     * }
     */
    @PostMapping
    public ResponseEntity<?> createReparacion(@RequestBody ReparacionRequest request) {
        try {
            Reparacion nuevaReparacion = reparacionService.registrar(
                    request.getVehiculoId(),
                    request.getUsuarioId(),
                    request.getDescripcion(),
                    request.getCosteEstimado()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReparacion);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * CU4: Cambiar estado de reparación
     * PUT /api/reparaciones/{id}/estado
     * Body: { "estado": "EN_REPARACION" }
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody EstadoRequest request) {
        try {
            Reparacion reparacionActualizada = reparacionService.cambiarEstado(id, request.getEstado());
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Estado actualizado correctamente");
            response.put("reparacion", reparacionActualizada);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Actualizar reparación completa
     * PUT /api/reparaciones/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReparacion(@PathVariable Long id, @RequestBody Reparacion reparacion) {
        try {
            Reparacion reparacionActualizada = reparacionService.update(id, reparacion);
            return ResponseEntity.ok(reparacionActualizada);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Eliminar reparación
     * DELETE /api/reparaciones/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReparacion(@PathVariable Long id) {
        try {
            reparacionService.delete(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Reparación eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    // Clases internas para recibir datos
    public static class ReparacionRequest {
        private String descripcion;
        private Double costeEstimado;
        private Long vehiculoId;
        private Long usuarioId;
        
        public ReparacionRequest() {}
        
        public String getDescripcion() {
            return descripcion;
        }
        
        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
        
        public Double getCosteEstimado() {
            return costeEstimado;
        }
        
        public void setCosteEstimado(Double costeEstimado) {
            this.costeEstimado = costeEstimado;
        }
        
        public Long getVehiculoId() {
            return vehiculoId;
        }
        
        public void setVehiculoId(Long vehiculoId) {
            this.vehiculoId = vehiculoId;
        }
        
        public Long getUsuarioId() {
            return usuarioId;
        }
        
        public void setUsuarioId(Long usuarioId) {
            this.usuarioId = usuarioId;
        }
    }
    
    public static class EstadoRequest {
        private EstadoReparacion estado;
        
        public EstadoRequest() {}
        
        public EstadoReparacion getEstado() {
            return estado;
        }
        
        public void setEstado(EstadoReparacion estado) {
            this.estado = estado;
        }
    }
}
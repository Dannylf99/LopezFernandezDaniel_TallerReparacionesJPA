package tallerReparaciones.controller;

import tallerReparaciones.entities.Vehiculo;
import tallerReparaciones.service.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    
    private final VehiculoService vehiculoService;
    
    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }
    
    /**
     * CU5: Listar todos los vehículos
     * GET /api/vehiculos
     */
    @GetMapping
    public ResponseEntity<List<Vehiculo>> getAllVehiculos() {
        return ResponseEntity.ok(vehiculoService.findAll());
    }
    
    /**
     * CU5: Obtener un vehículo por ID
     * GET /api/vehiculos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> getVehiculoById(@PathVariable Long id) {
        return vehiculoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * CU5: Buscar vehículo por matrícula
     * GET /api/vehiculos/matricula/{matricula}
     */
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Vehiculo> getVehiculoByMatricula(@PathVariable String matricula) {
        return vehiculoService.findByMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * CU5: Actualizar vehículo
     * PUT /api/vehiculos/{id}
     * Body: { "matricula": "1234ABC", "marca": "Toyota", "modelo": "Corolla" }
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo vehiculoActualizado = vehiculoService.update(id, vehiculo);
            return ResponseEntity.ok(vehiculoActualizado);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * CU5: Eliminar vehículo
     * DELETE /api/vehiculos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehiculo(@PathVariable Long id) {
        try {
            vehiculoService.delete(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Vehículo eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * CU5: Cambiar propietario del vehículo
     * PUT /api/vehiculos/{vehiculoId}/propietario/{nuevoClienteId}
     */
    @PutMapping("/{vehiculoId}/propietario/{nuevoClienteId}")
    public ResponseEntity<?> cambiarPropietario(
            @PathVariable Long vehiculoId,
            @PathVariable Long nuevoClienteId) {
        try {
            Vehiculo vehiculoActualizado = vehiculoService.cambiarPropietario(vehiculoId, nuevoClienteId);
            return ResponseEntity.ok(vehiculoActualizado);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
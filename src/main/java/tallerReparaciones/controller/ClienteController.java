package tallerReparaciones.controller;

import tallerReparaciones.entities.Cliente;
import tallerReparaciones.entities.Vehiculo;
import tallerReparaciones.service.ClienteService;
import tallerReparaciones.service.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    
    private final ClienteService clienteService;
    private final VehiculoService vehiculoService;
    
    public ClienteController(ClienteService clienteService, VehiculoService vehiculoService) {
        this.clienteService = clienteService;
        this.vehiculoService = vehiculoService;
    }
    
    /**
     * CU5: Listar todos los clientes
     * GET /api/clientes
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }
    
    /**
     * CU5: Obtener un cliente por ID
     * GET /api/clientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * CU5: Crear un cliente
     * POST /api/clientes
     * Body: { "nombre": "Juan Pérez", "dni": "12345678A", "email": "juan@email.com", "telefono": 600111222 }
     */
    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteService.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * CU5: Actualizar un cliente
     * PUT /api/clientes/{id}
     * Body: { "nombre": "Juan Pérez", "dni": "12345678A", "email": "juan@email.com", "telefono": 600111222 }
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = clienteService.update(id, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * CU5: Borrar un cliente
     * DELETE /api/clientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        try {
            clienteService.delete(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Cliente eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * CU5: Listar vehículos de un cliente
     * GET /api/clientes/{id}/vehiculos
     */
    @GetMapping("/{id}/vehiculos")
    public ResponseEntity<List<Vehiculo>> getVehiculosDeCliente(@PathVariable Long id) {
        // Verificar que el cliente existe
        if (clienteService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vehiculoService.findByClienteId(id));
    }
    
    /**
     * CU5: Registrar vehículo asociado a un cliente
     * POST /api/clientes/{id}/vehiculos
     * Body: { "matricula": "1234ABC", "marca": "Toyota", "modelo": "Corolla" }
     */
    @PostMapping("/{id}/vehiculos")
    public ResponseEntity<?> addVehiculoACliente(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo nuevoVehiculo = vehiculoService.save(id, vehiculo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVehiculo);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
package tallerReparaciones.controller;

import tallerReparaciones.service.ReparacionService;
import tallerReparaciones.service.UsuarioService;
import tallerReparaciones.service.VehiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {
    
    private final ReparacionService reparacionService;
    private final UsuarioService usuarioService;
    private final VehiculoService vehiculoService;
    
    public EstadisticasController(ReparacionService reparacionService,
                                 UsuarioService usuarioService,
                                 VehiculoService vehiculoService) {
        this.reparacionService = reparacionService;
        this.usuarioService = usuarioService;
        this.vehiculoService = vehiculoService;
    }
    
    /**
     * CU6: Número de reparaciones por estado
     * GET /api/estadisticas/reparacionesPorEstado
     * Respuesta: {
     *   "PENDIENTE": 10,
     *   "EN_REPARACION": 5,
     *   "FINALIZADO": 20
     * }
     */
    @GetMapping("/reparacionesPorEstado")
    public ResponseEntity<Map<String, Object>> getReparacionesPorEstado() {
        List<Object[]> resultados = reparacionService.countByEstado();
        
        Map<String, Object> estadisticas = new HashMap<>();
        for (Object[] resultado : resultados) {
            estadisticas.put(resultado[0].toString(), resultado[1]);
        }
        
        return ResponseEntity.ok(estadisticas);
    }
    
    /**
     * CU6: Coste medio por estado
     * GET /api/estadisticas/costeMedioPorEstado
     * Respuesta: {
     *   "PENDIENTE": 350.50,
     *   "EN_REPARACION": 450.75,
     *   "FINALIZADO": 500.00
     * }
     */
    @GetMapping("/costeMedioPorEstado")
    public ResponseEntity<Map<String, Object>> getCosteMedioPorEstado() {
        List<Object[]> resultados = reparacionService.avgCosteByEstado();
        
        Map<String, Object> estadisticas = new HashMap<>();
        for (Object[] resultado : resultados) {
            estadisticas.put(resultado[0].toString(), resultado[1]);
        }
        
        return ResponseEntity.ok(estadisticas);
    }
    
    /**
     * CU6: Número de reparaciones realizadas por cada mecánico
     * GET /api/estadisticas/reparacionesPorMecanico
     * Respuesta: {
     *   "mecanico1": 15,
     *   "mecanico2": 12
     * }
     */
    @GetMapping("/reparacionesPorMecanico")
    public ResponseEntity<Map<String, Object>> getReparacionesPorMecanico() {
        List<Object[]> resultados = reparacionService.countByMecanico();
        
        Map<String, Object> estadisticas = new HashMap<>();
        for (Object[] resultado : resultados) {
            estadisticas.put(resultado[0].toString(), resultado[1]);
        }
        
        return ResponseEntity.ok(estadisticas);
    }
    
    /**
     * CU6: Número de reparaciones por cliente
     * GET /api/estadisticas/reparacionesPorCliente
     * Respuesta: {
     *   "Carlos Pérez": 5,
     *   "María López": 3
     * }
     */
    @GetMapping("/reparacionesPorCliente")
    public ResponseEntity<Map<String, Object>> getReparacionesPorCliente() {
        List<Object[]> resultados = reparacionService.countByCliente();
        
        Map<String, Object> estadisticas = new HashMap<>();
        for (Object[] resultado : resultados) {
            estadisticas.put(resultado[0].toString(), resultado[1]);
        }
        
        return ResponseEntity.ok(estadisticas);
    }
    
    /**
     * CU6: Vehículos registrados por marca
     * GET /api/estadisticas/vehiculosPorMarca
     * Respuesta: {
     *   "Toyota": 8,
     *   "Honda": 6
     * }
     */
    @GetMapping("/vehiculosPorMarca")
    public ResponseEntity<Map<String, Object>> getVehiculosPorMarca() {
        List<Object[]> resultados = vehiculoService.countByMarca();
        
        Map<String, Object> estadisticas = new HashMap<>();
        for (Object[] resultado : resultados) {
            estadisticas.put(resultado[0].toString(), resultado[1]);
        }
        
        return ResponseEntity.ok(estadisticas);
    }
    
    /**
     * CU6: Estadísticas generales del sistema
     * GET /api/estadisticas/general
     * Respuesta: {
     *   "totalReparaciones": 50,
     *   "costeTotal": 25000.00,
     *   "costeMedio": 500.00
     * }
     */
    @GetMapping("/general")
    public ResponseEntity<Map<String, Object>> getEstadisticasGenerales() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        Long totalReparaciones = reparacionService.countTotal();
        Double costeTotal = reparacionService.sumCosteTotal();
        
        estadisticas.put("totalReparaciones", totalReparaciones);
        estadisticas.put("costeTotal", costeTotal);
        
        if (totalReparaciones > 0 && costeTotal != null) {
            estadisticas.put("costeMedio", costeTotal / totalReparaciones);
        } else {
            estadisticas.put("costeMedio", 0.0);
        }
        
        return ResponseEntity.ok(estadisticas);
    }
    
    /**
     * CU6: Todas las estadísticas en una sola llamada
     * GET /api/estadisticas
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEstadisticas() {
        Map<String, Object> todasEstadisticas = new HashMap<>();
        
        // Reparaciones por estado
        List<Object[]> porEstado = reparacionService.countByEstado();
        Map<String, Object> estadoPorEstado = new HashMap<>();
        for (Object[] resultado : porEstado) {
            estadoPorEstado.put(resultado[0].toString(), resultado[1]);
        }
        todasEstadisticas.put("reparacionesPorEstado", estadoPorEstado);
        
        // Coste medio por estado
        List<Object[]> costeMedio = reparacionService.avgCosteByEstado();
        Map<String, Object> costePorEstado = new HashMap<>();
        for (Object[] resultado : costeMedio) {
            costePorEstado.put(resultado[0].toString(), resultado[1]);
        }
        todasEstadisticas.put("costeMedioPorEstado", costePorEstado);
        
        // Reparaciones por mecánico
        List<Object[]> porMecanico = reparacionService.countByMecanico();
        Map<String, Object> estadoPorMecanico = new HashMap<>();
        for (Object[] resultado : porMecanico) {
            estadoPorMecanico.put(resultado[0].toString(), resultado[1]);
        }
        todasEstadisticas.put("reparacionesPorMecanico", estadoPorMecanico);
        
        // Generales
        Long totalReparaciones = reparacionService.countTotal();
        Double costeTotal = reparacionService.sumCosteTotal();
        todasEstadisticas.put("totalReparaciones", totalReparaciones);
        todasEstadisticas.put("costeTotal", costeTotal);
        
        return ResponseEntity.ok(todasEstadisticas);
    }
}
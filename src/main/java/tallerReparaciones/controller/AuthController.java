package tallerReparaciones.controller;

import tallerReparaciones.entities.Usuario;
import tallerReparaciones.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UsuarioService usuarioService;
    
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    /**
     * CU2: Login
     * POST /api/auth/login
     * Body: { "dni": "12345678A", "password": "admin123" }
     * Respuesta: { "mensaje": "Login correcto", "usuario": {...} }
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar credenciales
            Usuario usuario = usuarioService.login(request.getDni(), request.getPassword())
                    .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));
            
            response.put("mensaje", "Login correcto");
            response.put("usuario", usuario);
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            response.put("mensaje", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    // Clase interna para recibir los datos del login
    public static class LoginRequest {
        private String dni;
        private String password;
        
        public LoginRequest() {}
        
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
    }
}
package tallerReparaciones.service;


import tallerReparaciones.entities.Rol;
import tallerReparaciones.entities.Usuario;
import tallerReparaciones.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    // CU2: Login - Validar credenciales
    public Optional<Usuario> login(String dni, String password) {
        return usuarioRepository.findByDniAndPassword(dni, password);
    }
    
    // Buscar usuario por DNI
    public Optional<Usuario> findByDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }
    
    // Buscar usuario por ID
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    // Listar todos los usuarios
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    // Listar usuarios por rol
    public List<Usuario> findByRol(Rol rol) {
        return usuarioRepository.findByRol(rol);
    }
    
    // Crear nuevo usuario
    public Usuario save(Usuario usuario) {
        // Validación: verificar que el DNI no exista
        if (usuario.getId() == null && usuarioRepository.existsByDni(usuario.getDni())) {
            throw new IllegalArgumentException("Ya existe un usuario con el DNI: " + usuario.getDni());
        }
        
        // TODO: En un escenario real, aquí se debería hashear la contraseña
        // usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        return usuarioRepository.save(usuario);
    }
    
    // Actualizar usuario
    public Usuario update(Long id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
        
        usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuario.setRol(usuarioActualizado.getRol());
        
        // Solo actualizar DNI si es diferente y no existe
        if (!usuario.getDni().equals(usuarioActualizado.getDni())) {
            if (usuarioRepository.existsByDni(usuarioActualizado.getDni())) {
                throw new IllegalArgumentException("Ya existe un usuario con el DNI: " + usuarioActualizado.getDni());
            }
            usuario.setDni(usuarioActualizado.getDni());
        }
        
        // Solo actualizar contraseña si se proporciona una nueva
        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
            // TODO: Hashear la contraseña
            usuario.setPassword(usuarioActualizado.getPassword());
        }
        
        return usuarioRepository.save(usuario);
    }
    
    // Eliminar usuario
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
    
    // CU6: Estadísticas - Contar reparaciones por mecánico
    public List<Object[]> countReparacionesByMecanico() {
        return usuarioRepository.countReparacionesByMecanico();
    }
}
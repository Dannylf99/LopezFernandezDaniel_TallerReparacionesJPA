package tallerReparaciones.service;

import tallerReparaciones.entities.Cliente;
import tallerReparaciones.entities.Vehiculo;
import tallerReparaciones.repository.ClienteRepository;
import tallerReparaciones.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehiculoService {
    
    private final VehiculoRepository vehiculoRepository;
    private final ClienteRepository clienteRepository;
    
    public VehiculoService(VehiculoRepository vehiculoRepository, ClienteRepository clienteRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.clienteRepository = clienteRepository;
    }
    
    // CU5: Listar todos los vehículos
    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }
    
    // CU5: Buscar vehículo por ID
    public Optional<Vehiculo> findById(Long id) {
        return vehiculoRepository.findById(id);
    }
    
    // CU5: Buscar vehículo por matrícula
    public Optional<Vehiculo> findByMatricula(String matricula) {
        return vehiculoRepository.findByMatricula(matricula);
    }
    
    // CU5: Listar vehículos de un cliente
    public List<Vehiculo> findByClienteId(Long clienteId) {
        return vehiculoRepository.findByClienteId(clienteId);
    }
    
    // CU5: Registrar vehículo asociado a un cliente
    public Vehiculo save(Long clienteId, Vehiculo vehiculo) {
        // Validar que el cliente existe
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + clienteId));
        
        // Validar que la matrícula no exista
        if (vehiculo.getId() == null && vehiculoRepository.existsByMatricula(vehiculo.getMatricula())) {
            throw new IllegalArgumentException("Ya existe un vehículo con la matrícula: " + vehiculo.getMatricula());
        }
        
        // Asociar el vehículo al cliente
        vehiculo.setCliente(cliente);
        
        return vehiculoRepository.save(vehiculo);
    }
    
    // CU5: Actualizar vehículo
    public Vehiculo update(Long id, Vehiculo vehiculoActualizado) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + id));
        
        // Actualizar campos
        vehiculo.setMarca(vehiculoActualizado.getMarca());
        vehiculo.setModelo(vehiculoActualizado.getModelo());
        
        // Solo actualizar matrícula si es diferente y no existe
        if (!vehiculo.getMatricula().equals(vehiculoActualizado.getMatricula())) {
            if (vehiculoRepository.existsByMatricula(vehiculoActualizado.getMatricula())) {
                throw new IllegalArgumentException("Ya existe un vehículo con la matrícula: " + vehiculoActualizado.getMatricula());
            }
            vehiculo.setMatricula(vehiculoActualizado.getMatricula());
        }
        
        return vehiculoRepository.save(vehiculo);
    }
    
    // CU5: Cambiar propietario del vehículo
    public Vehiculo cambiarPropietario(Long vehiculoId, Long nuevoClienteId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + vehiculoId));
        
        Cliente nuevoCliente = clienteRepository.findById(nuevoClienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + nuevoClienteId));
        
        vehiculo.setCliente(nuevoCliente);
        return vehiculoRepository.save(vehiculo);
    }
    
    // CU5: Eliminar vehículo
    public void delete(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new IllegalArgumentException("Vehículo no encontrado con ID: " + id);
        }
        vehiculoRepository.deleteById(id);
    }
    
    // CU6: Estadísticas - Contar vehículos por marca
    public List<Object[]> countByMarca() {
        return vehiculoRepository.countByMarca();
    }
}

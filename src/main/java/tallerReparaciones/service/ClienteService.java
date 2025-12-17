package tallerReparaciones.service;


import tallerReparaciones.entities.Cliente;
import tallerReparaciones.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    // CU5: Listar todos los clientes
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    // CU5: Buscar cliente por ID
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }
    
    // CU5: Buscar cliente por DNI
    public Optional<Cliente> findByDni(String dni) {
        return clienteRepository.findByDni(dni);
    }
    
    // CU5: Crear nuevo cliente
    public Cliente save(Cliente cliente) {
        // Validación: verificar que el DNI no exista
        if (cliente.getId() == null && clienteRepository.existsByDni(cliente.getDni())) {
            throw new IllegalArgumentException("Ya existe un cliente con el DNI: " + cliente.getDni());
        }
        return clienteRepository.save(cliente);
    }
    
    // CU5: Actualizar cliente existente
    public Cliente update(Long id, Cliente clienteActualizado) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
        
        // Actualizar campos
        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setEmail(clienteActualizado.getEmail());
        cliente.setTelefono(clienteActualizado.getTelefono());
        
        // Solo actualizar DNI si es diferente y no existe
        if (!cliente.getDni().equals(clienteActualizado.getDni())) {
            if (clienteRepository.existsByDni(clienteActualizado.getDni())) {
                throw new IllegalArgumentException("Ya existe un cliente con el DNI: " + clienteActualizado.getDni());
            }
            cliente.setDni(clienteActualizado.getDni());
        }
        
        return clienteRepository.save(cliente);
    }
    
    // CU5: Eliminar cliente
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
    
    // CU6: Obtener cliente con sus vehículos
    public Optional<Cliente> findByIdWithVehiculos(Long id) {
        return clienteRepository.findByIdWithVehiculos(id);
    }
    
    // CU6: Contar vehículos de un cliente
    public Long countVehiculos(Long clienteId) {
        return clienteRepository.countVehiculosByClienteId(clienteId);
    }
}
package com.example.PruebaSpringDataJPA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	// Ejemplos de consultas derivadas
	Cliente findByNombre(String nombre);
	List<Cliente> findByEdadGreaterThan(int edad);
	
	// Genera automaticamente
	// Persona save(Persona persona);
	// findAll(),
	// findById(id),
	// delete(entity),
	// count()
	// exitsById(id)
}

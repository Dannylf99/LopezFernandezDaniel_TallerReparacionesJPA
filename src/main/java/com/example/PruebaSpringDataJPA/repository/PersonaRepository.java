package com.example.PruebaSpringDataJPA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PruebaSpringDataJPA.entities.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
	// Ejemplos de consultas derivadas
	Persona findByNombre(String nombre);
	List<Persona> findByEdadGreaterThan(int edad);
	
	// Genera automaticamente
	Persona save(Persona persona);
	// findAll(),
	// findById(id),
	// delete(entity),
	// count()
	// exitsById(id)
}

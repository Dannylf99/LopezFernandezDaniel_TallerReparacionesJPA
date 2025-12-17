package com.example.PruebaSpringDataJPA.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PruebaSpringDataJPA.entities.Persona;
import com.example.PruebaSpringDataJPA.repository.PersonaRepository;

@RestController
@RequestMapping("/personas")
public class PersonaController {

	private final PersonaRepository repo;
	
	public PersonaController(PersonaRepository repo) {
		this.repo = repo;
	}
	
	// Get
	
	@GetMapping
	public List<Persona> getAll(){
		return this.repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Persona getById(@PathVariable Long id) {
		return repo.findById(id).orElse(null);
	}
	
	// Post
	
	@PostMapping
	public Persona create(@RequestBody Persona persona) {
		return repo.save(persona);
	};
	

	// Put
	
	@PutMapping("/{id}")
	public Persona update(@PathVariable Long id, @RequestBody Persona persona) {
		Persona p = repo.findById(id).orElseThrow();
		p.setNombre(persona.getNombre());
		p.setEdad(persona.getEdad());
		repo.save(p);
		return p;
	}
	
	// Delete
	@DeleteMapping("/{id}")
	public List<Persona> delete(@PathVariable Long id) {
		repo.deleteById(id);
		System.out.println("Persona con id: " + id +" eliminada correctamente.");
		return repo.findAll();
	}
}

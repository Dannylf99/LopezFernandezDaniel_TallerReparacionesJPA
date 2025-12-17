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

import com.example.PruebaSpringDataJPA.repository.ClienteRepository;

import entities.Cliente;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	private final ClienteRepository repo;
	
	public ClienteController(ClienteRepository repo) {
		this.repo = repo;
	}
	
	// Get
	
	@GetMapping
	public List<Cliente> getAll(){
		return this.repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Cliente getById(@PathVariable Long id) {
		return repo.findById(id).orElse(null);
	}
	
	// Post
	
	@PostMapping
	public Cliente create(@RequestBody Cliente persona) {
		return repo.save(persona);
	};
	

	// Put
	
	@PutMapping("/{id}")
	public Cliente update(@PathVariable Long id, @RequestBody Cliente persona) {
		Cliente p = repo.findById(id).orElseThrow();
		p.setNombre(persona.getNombre());
		p.setEdad(persona.getEdad());
		repo.save(p);
		return p;
	}
	
	// Delete
	@DeleteMapping("/{id}")
	public List<Cliente> delete(@PathVariable Long id) {
		repo.deleteById(id);
		System.out.println("Persona con id: " + id +" eliminada correctamente.");
		return repo.findAll();
	}
}

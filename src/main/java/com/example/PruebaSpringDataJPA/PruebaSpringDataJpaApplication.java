package com.example.PruebaSpringDataJPA;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.PruebaSpringDataJPA.repository.ClienteRepository;

import entities.Cliente;

@SpringBootApplication
public class PruebaSpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaSpringDataJpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner test(ClienteRepository pRepo) {
		return args -> {
			// INSERT
			pRepo.save(new Cliente("Daniel", 46));
			pRepo.save(new Cliente("Sara1", 25));
			pRepo.save(new Cliente("Sara2", 20));

			// SELECT ALL
			System.out.println("Personas en BD:");
			pRepo.findAll().forEach( p ->
				System.out.println(p.getId() +  " - " + p.getNombre())
			);

			// SELECT BY ID
			Cliente p = pRepo.findById(1L).orElse(null);
			System.out.println("Persona con ID 1:" + 
					(p != null ? 
							p.getNombre() :
								"no existe"));
			
			// UPDATE
			p.setEdad(25);
			pRepo.save(p);
			
			// DELETE
			pRepo.deleteById(3L);
			System.out.println("Persona con id 1L borrada de la BD");
			
			
			// CONSULTA DERIVADA
			//Persona porNombre = pRepo.findByNombre("Cristina");
			
			//System.out.println("Buscando Cristina -> " + porNombre.getEdad());
		};
	}
}

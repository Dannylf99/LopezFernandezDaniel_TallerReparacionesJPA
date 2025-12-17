package tallerReparaciones.PruebaSpringDataJPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot
 * Gestión de Taller de Reparaciones
 * 
 * @author Daniel López Fernández
 * Tarea 3 - DWES - 2º DAW
 */
@SpringBootApplication
public class PruebaSpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaSpringDataJpaApplication.class, args);
		
		System.out.println("\n=================================================");
		System.out.println("SISTEMA DE GESTIÓN DE TALLER DE REPARACIONES");
		System.out.println("=================================================");
		System.out.println("Aplicación iniciada correctamente");
		System.out.println("Servidor corriendo en: http://localhost:8080");
		System.out.println("Endpoints disponibles:");
		System.out.println("   - GET    /api/reparaciones/finalizadas");
		System.out.println("   - POST   /api/auth/login");
		System.out.println("   - POST   /api/reparaciones");
		System.out.println("   - PUT    /api/reparaciones/{id}/estado");
		System.out.println("   - GET    /api/clientes");
		System.out.println("   - GET    /api/estadisticas/reparacionesPorEstado");
		System.out.println("=================================================\n");
	}

}
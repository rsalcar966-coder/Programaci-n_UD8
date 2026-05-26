package com.daw.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.JuegoEntity;
import com.daw.services.JuegoService;
import com.daw.services.exceptions.JuegoException;
import com.daw.services.exceptions.JuegoNotFoundException;

@RestController
@RequestMapping("/juegos")
public class JuegoController {
	
	@Autowired
	private JuegoService servicioJuegos;
	
	// --- ENDPOINTS DE CONSULTA (GET) ---
	@GetMapping
	public ResponseEntity<?> obtenerTodos(){
		return ResponseEntity.ok(this.servicioJuegos.findAll());
	}
	
	@GetMapping("/{idJuego}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("idJuego") long idJuego){
		try {
			return ResponseEntity.ok(this.servicioJuegos.findById(idJuego));
		}
		catch(JuegoNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/genero")
	public ResponseEntity<?> buscarPorGenero(@RequestParam("genero") String gen){
		return ResponseEntity.ok(this.servicioJuegos.findByGenero(gen));
	}
	
	@GetMapping("/nombre")
	public ResponseEntity<?> buscarPorNombre(@RequestParam("nombre") String nom){
		return ResponseEntity.ok(this.servicioJuegos.findByNombre(nom));
	}
	
	@GetMapping("/plataforma")
	public ResponseEntity<?> buscarPorPlataforma(@RequestParam("plataforma") String plat){
		return ResponseEntity.ok(this.servicioJuegos.findByPlataforma(plat));
	}
	
	@GetMapping("/base")
	public ResponseEntity<?> obtenerJuegosBase(){
		return ResponseEntity.ok(this.servicioJuegos.findBase());
	}

	@GetMapping("/expansiones")
	public ResponseEntity<?> obtenerExpansiones(){
		return ResponseEntity.ok(this.servicioJuegos.findExpansiones());
	}
	
	@GetMapping("/dlc")
	public ResponseEntity<?> obtenerDlcs(){
		return ResponseEntity.ok(this.servicioJuegos.findDlcs());
	}
	
	@GetMapping("/precio")
	public ResponseEntity<?> buscarPorRangoPrecios(@RequestParam("min") Double min, @RequestParam("max") Double max){
		try {
			return ResponseEntity.ok(this.servicioJuegos.findByPrecioRango(min, max));
		}
		catch(JuegoException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/exitos")
	public ResponseEntity<?> obtenerTopExitos(){
		return ResponseEntity.ok(this.servicioJuegos.findTop5Exitos());
	}

	// --- ENDPOINTS DE MODIFICACIÓN (POST, PUT, DELETE) ---
	@PostMapping
	public ResponseEntity<?> registrarNuevoJuego(@RequestBody JuegoEntity entidadJuego){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.servicioJuegos.create(entidadJuego));
		}
		catch(JuegoException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/{idJuego}")
	public ResponseEntity<?> actualizarJuego(@PathVariable("idJuego") long idJuego, @RequestBody JuegoEntity entidadJuego){
		try {
			return ResponseEntity.ok(this.servicioJuegos.update(idJuego, entidadJuego));
		}
		catch(JuegoNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		catch(JuegoException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{idJuego}")
	public ResponseEntity<?> eliminarJuego(@PathVariable("idJuego") long idJuego){
		try {
			this.servicioJuegos.delete(idJuego);
			return ResponseEntity.ok().build();
		}
		catch(JuegoNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PutMapping("/{idJuego}/completar")
	public ResponseEntity<?> alternarEstadoCompletado(@PathVariable("idJuego") long idJuego){
		try {
			return ResponseEntity.ok(this.servicioJuegos.toggleCompletar(idJuego));
		}
		catch(JuegoNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PutMapping("/descuento")
	public ResponseEntity<?> aplicarRebajaMasiva(@RequestParam("genero") String gen, @RequestParam("porcentaje") Double porc){
		try {
			return ResponseEntity.ok(this.servicioJuegos.aplicarDescuento(gen, porc));
		}
		catch(JuegoException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
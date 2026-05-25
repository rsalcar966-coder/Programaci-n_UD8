package com.daw.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daw.persistence.entities.TareaEntity;
import com.daw.services.TareaService;
import com.daw.services.exceptions.TareaException;
import com.daw.services.exceptions.TareaNotFoundException;

@RestController
@RequestMapping("/tareas")
public class TareaController {
	
	@Autowired
	private TareaService tareaService;
	
	@GetMapping
	public ResponseEntity<?> list(){
		return ResponseEntity.ok(this.tareaService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") long id){
		try {
			return ResponseEntity.ok(this.tareaService.findById(id));
		}
		catch(TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody TareaEntity tareaEntity){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.tareaService.create(tareaEntity));
		}
		catch(TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody TareaEntity tareaEntity){
		try {
			return ResponseEntity.ok(this.tareaService.update(id, tareaEntity));
		}
		catch(TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		catch(TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id){
		try {
			this.tareaService.delete(id);
			return ResponseEntity.ok().build();
		}
		catch(TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	@PutMapping("/{id}/iniciar")
	public ResponseEntity<?> iniciarTarea(@PathVariable("id") long id){
		try {
			return ResponseEntity.ok(this.tareaService.iniciarTarea(id));
		}
		catch(TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		catch(TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@PutMapping("/{id}/completar")
	public ResponseEntity<?> completarTarea(@PathVariable("id") long id){
		try {
			return ResponseEntity.ok(this.tareaService.completarTarea(id));
		}
		catch(TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		catch(TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@GetMapping("/pendientes")
	public ResponseEntity<?> getPendientes(){
		return ResponseEntity.ok(this.tareaService.findPendientes());
	}
	
	@GetMapping("/en_progreso")
	public ResponseEntity<?> getEnProgreso(){
		return ResponseEntity.ok(this.tareaService.findEnProgreso());
	}
	
	@GetMapping("/completadas")
	public ResponseEntity<?> getCompletadas(){
		return ResponseEntity.ok(this.tareaService.findCompletadas());
	}
	
	@GetMapping("/vencidas")
	public ResponseEntity<?> getVencidas(){
		return ResponseEntity.ok(this.tareaService.findVencidas());
	}
	
	@GetMapping("/no_vencidas")
	public ResponseEntity<?> getNoVencidas(){
		return ResponseEntity.ok(this.tareaService.findNoVencidas());
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getByTitulo(@RequestParam("titulo") String titulo){
		return ResponseEntity.ok(this.tareaService.findByTitulo(titulo));
	}
}
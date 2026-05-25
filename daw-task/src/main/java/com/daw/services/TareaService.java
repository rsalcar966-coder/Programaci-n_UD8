package com.daw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.TareaEntity;
import com.daw.persistence.entities.enums.Estado;
import com.daw.persistence.repositories.TareaRepository;
import com.daw.services.exceptions.TareaException;
import com.daw.services.exceptions.TareaNotFoundException;

@Service
public class TareaService {
	
	@Autowired
	private TareaRepository tareaRepository;
	
	public List<TareaEntity> findAll() {
		return this.tareaRepository.findAll();
	}
	
	public TareaEntity findById(long idTarea) {
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("No se encuentra la tarea con id: " + idTarea);
		}
		return this.tareaRepository.findById(idTarea).get();
	}
	
	public TareaEntity create(TareaEntity t) {
		if(t.getFechaCreacion() != null) {
			throw new TareaException("No se puede modificar la fecha de creación de una tarea.");
		}
		if(t.getEstado() != null) {
			throw new TareaException("No se puede modificar el estado de una tarea.");
		}
		if(t.getFechaVencimiento() == null || t.getFechaVencimiento().isBefore(LocalDate.now())) {
			throw new TareaException("La fecha de vencimiento no puede ser anterior a la fecha actual.");
		}
		
		t.setId(0L);
		t.setFechaCreacion(LocalDate.now());
		t.setEstado(Estado.PENDIENTE);
		
		return this.tareaRepository.save(t);
	}
	
	public TareaEntity update(long idTarea, TareaEntity t) {
		if(t.getId() != idTarea) {
			throw new TareaException("El ID del body y el ID del path no coinciden.");
		}
		
		TareaEntity tareaBd = this.findById(idTarea);
		
		if(t.getFechaCreacion() != null) {
			throw new TareaException("No se puede modificar la fecha de creación de una tarea.");
		}
		if(t.getEstado() != null) {
			throw new TareaException("No se puede modificar el estado de una tarea.");
		}
		if(t.getFechaVencimiento() != null && t.getFechaVencimiento().isBefore(LocalDate.now())) {
			throw new TareaException("La fecha de vencimiento no puede ser anterior a la fecha actual.");
		}
		
		tareaBd.setTitulo(t.getTitulo());
		tareaBd.setDescripcion(t.getDescripcion());
		if (t.getFechaVencimiento() != null) {
			tareaBd.setFechaVencimiento(t.getFechaVencimiento());
		}
		
		return this.tareaRepository.save(tareaBd);
	}
	
	public void delete(long idTarea) {
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("No se encuentra la tarea con id: " + idTarea);
		}
		this.tareaRepository.deleteById(idTarea);
	}
	
	public TareaEntity iniciarTarea(long idTarea) {		
		TareaEntity t = this.findById(idTarea);
		
		if(!t.getEstado().equals(Estado.PENDIENTE)) {
			throw new TareaException("Solo se pueden iniciar tareas PENDIENTES.");
		}
		
		t.setEstado(Estado.EN_PROGRESO);
		return this.tareaRepository.save(t);
	}
	
	public TareaEntity completarTarea(long idTarea) {
		TareaEntity t = this.findById(idTarea);
		
		if(!t.getEstado().equals(Estado.EN_PROGRESO)) {
			throw new TareaException("Solo se pueden completar tareas EN_PROGRESO.");
		}
		
		t.setEstado(Estado.COMPLETADA);
		return this.tareaRepository.save(t);
	}
	
	public List<TareaEntity> findPendientes() {
		return this.tareaRepository.findByEstado(Estado.PENDIENTE);
	}
	
	public List<TareaEntity> findEnProgreso() {
		return this.tareaRepository.findByEstado(Estado.EN_PROGRESO);
	}
	
	public List<TareaEntity> findCompletadas() {
		return this.tareaRepository.findByEstado(Estado.COMPLETADA);
	}
	
	public List<TareaEntity> findVencidas() {
		return this.tareaRepository.findByFechaVencimientoBefore(LocalDate.now());
	}
	
	public List<TareaEntity> findNoVencidas() {
		return this.tareaRepository.findByFechaVencimientoAfter(LocalDate.now());
	}
	
	public List<TareaEntity> findByTitulo(String titulo) {
		return this.tareaRepository.findByTituloContainingIgnoreCase(titulo);
	}
}
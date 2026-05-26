package com.daw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.JuegoEntity;
import com.daw.persistence.entities.enums.TipoJuego;
import com.daw.persistence.repositories.JuegoRepository;
import com.daw.services.exceptions.JuegoException;
import com.daw.services.exceptions.JuegoNotFoundException;

@Service
public class JuegoService {
	
	@Autowired
	private JuegoRepository juegoRepo;
	
	public List<JuegoEntity> findAll() {
		return this.juegoRepo.findAll();
	}
	
	public JuegoEntity findById(long idBuscado) {
		if(!this.juegoRepo.existsById(idBuscado)) {
			throw new JuegoNotFoundException("No hemos encontrado ningún juego con el identificador: " + idBuscado);
		}
		return this.juegoRepo.findById(idBuscado).get();
	}
	
	public JuegoEntity create(JuegoEntity nuevoJuego) {
		if(nuevoJuego.getFechaLanzamiento() == null) {
			nuevoJuego.setFechaLanzamiento(LocalDate.now());
		}
		
		nuevoJuego.setId(0L); 
		nuevoJuego.setCompletado(false); 
		
		return this.juegoRepo.save(nuevoJuego);
	}
	
	public JuegoEntity update(long idPath, JuegoEntity datosActualizados) {
		if(datosActualizados.getId() != idPath) {
			throw new JuegoException("Error: El ID de la URL y el proporcionado en el JSON no son iguales.");
		}
		
		JuegoEntity juegoExistente = this.findById(idPath);
		
		juegoExistente.setNombre(datosActualizados.getNombre());
		juegoExistente.setGenero(datosActualizados.getGenero());
		juegoExistente.setPlataforma(datosActualizados.getPlataforma());
		juegoExistente.setPrecio(datosActualizados.getPrecio());
		juegoExistente.setDescargas(datosActualizados.getDescargas());
		juegoExistente.setFechaLanzamiento(datosActualizados.getFechaLanzamiento());
		juegoExistente.setTipo(datosActualizados.getTipo());
		
		return this.juegoRepo.save(juegoExistente);
	}
	
	public void delete(long idParaBorrar) {
		if(!this.juegoRepo.existsById(idParaBorrar)) {
			throw new JuegoNotFoundException("No hemos encontrado ningún juego con el identificador: " + idParaBorrar);
		}
		this.juegoRepo.deleteById(idParaBorrar);
	}
	
	public JuegoEntity toggleCompletar(long idJuego) {		
		JuegoEntity juegoActual = this.findById(idJuego);
		boolean estadoCompletado = juegoActual.isCompletado();
		
		juegoActual.setCompletado(!estadoCompletado);
		
		return this.juegoRepo.save(juegoActual);
	}
	
	public List<JuegoEntity> aplicarDescuento(String generoEspecifico, Double descuentoPorcentual) {
		if(descuentoPorcentual < 0.0 || descuentoPorcentual > 1.0) {
			throw new JuegoException("El porcentaje aplicado debe estar estrictamente entre 0.0 y 1.0");
		}
		
		List<JuegoEntity> listaJuegos = this.juegoRepo.findByGeneroIgnoreCase(generoEspecifico);
		
		for(JuegoEntity juegoItem : listaJuegos) {
			Double rebaja = juegoItem.getPrecio() * descuentoPorcentual;
			juegoItem.setPrecio(juegoItem.getPrecio() - rebaja);
		}
		
		return this.juegoRepo.saveAll(listaJuegos);
	}
	
	public List<JuegoEntity> findByGenero(String genero) {
		return this.juegoRepo.findByGeneroIgnoreCase(genero);
	}
	
	public List<JuegoEntity> findByNombre(String nombre) {
		return this.juegoRepo.findByNombreContainingIgnoreCase(nombre);
	}
	
	public List<JuegoEntity> findByPlataforma(String plataforma) {
		return this.juegoRepo.findByNombreContainingIgnoreCase(plataforma);
	}
	
	public List<JuegoEntity> findExpansiones() {
		return this.juegoRepo.findByTipo(TipoJuego.EXPANSION);
	}
	
	public List<JuegoEntity> findDlcs() {
		return this.juegoRepo.findByTipo(TipoJuego.DLC);
	}
	
	public List<JuegoEntity> findBase() {
		return this.juegoRepo.findByTipo(TipoJuego.BASE);
	}
	
	public List<JuegoEntity> findByPrecioRango(Double limiteInferior, Double limiteSuperior) {
		if (limiteInferior > limiteSuperior) {
			throw new JuegoException("El precio mínimo insertado supera al precio máximo.");
		}
		return this.juegoRepo.findByPrecioBetween(limiteInferior, limiteSuperior);
	}
	
	public List<JuegoEntity> findTop5Exitos() {
		return this.juegoRepo.findTop5ByOrderByDescargasDesc();
	}
}
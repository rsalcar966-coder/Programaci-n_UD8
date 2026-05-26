package com.daw.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.persistence.entities.JuegoEntity;
import com.daw.persistence.entities.enums.TipoJuego;

public interface JuegoRepository extends JpaRepository<JuegoEntity, Long> {

	List<JuegoEntity> findByGeneroIgnoreCase(String genero);

	List<JuegoEntity> findByNombreContainingIgnoreCase(String nombre);

	List<JuegoEntity> findByPlataformaContainingIgnoreCase(String plataforma);

	List<JuegoEntity> findByTipo(TipoJuego tipo);

	List<JuegoEntity> findByPrecioBetween(double min, double max);

	List<JuegoEntity> findTop5ByOrderByDescargasDesc();

}

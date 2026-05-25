package com.daw.persistence.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.daw.persistence.entities.TareaEntity;
import com.daw.persistence.entities.enums.Estado;

public interface TareaRepository extends JpaRepository<TareaEntity, Long> {
    
    List<TareaEntity> findByEstado(Estado estado);
    List<TareaEntity> findByFechaVencimientoBefore(LocalDate fecha);
    List<TareaEntity> findByFechaVencimientoAfter(LocalDate fecha);
    List<TareaEntity> findByTituloContainingIgnoreCase(String titulo);
}
package com.daw.persistence.entities;

import java.time.LocalDate;

import com.daw.persistence.entities.enums.TipoJuego;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "juego")
@Getter
@Setter
@NoArgsConstructor
public class JuegoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nombre;
	private String genero;
	private String plataforma;
	private double precio;
	private int descargas;
	private boolean completado;

	@Column(name = "fecha_lanzamiento")
	private LocalDate fechaLanzamiento;

	@Enumerated(EnumType.STRING)
	private TipoJuego tipo;

}

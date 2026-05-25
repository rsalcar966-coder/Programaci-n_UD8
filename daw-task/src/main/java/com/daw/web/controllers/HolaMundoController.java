package com.daw.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/holamundo")
public class HolaMundoController {
	
	@GetMapping("/hola")
	public String saludar() {
		return "Holaaaaaaaaa";
	}
	
	@GetMapping("/saludo")
	public String saludar(@RequestParam(defaultValue = "Señor X") String nombre) {
		return "Holaaaaaaaaa " + nombre + ", ¿qué tal?";
	}
	
	@GetMapping("/numeros")
	public String numeros(@RequestParam int min, @RequestParam int max) {
		String result = "Los números entre el " + min + " y el " + max + " son: ";
		
		for(int i=min+1; i<max; i++) {
			result = result + i + " ";
		}
		
		return result;
	}

}

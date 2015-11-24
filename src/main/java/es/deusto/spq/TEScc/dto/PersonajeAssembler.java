package es.deusto.spq.TEScc.dto;

import es.deusto.spq.TEScc.servidor.jdo.Personaje;

public class PersonajeAssembler {
	private static PersonajeAssembler instancia;
	
	public static synchronized PersonajeAssembler getInstance() {
		if(instancia == null) {
			instancia = new PersonajeAssembler();
		}
		
		return instancia;
	}
	
	public PersonajeDTO entityDTO(Personaje personaje) {
		return new PersonajeDTO(personaje.getApodo(), personaje.getFechaCreacion(), personaje.getFechaUltMod(),
				personaje.getVida(), personaje.getVelocidad(), personaje.getArmadura(), personaje.getDiscrecion(),
				personaje.getAtaque(), personaje.getDefensa(), personaje.getTipo());
	}
}

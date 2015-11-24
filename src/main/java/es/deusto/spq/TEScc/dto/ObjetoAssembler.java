package es.deusto.spq.TEScc.dto;

import es.deusto.spq.TEScc.servidor.jdo.Objeto;

public class ObjetoAssembler {

	private static ObjetoAssembler instancia;
	
	public static synchronized ObjetoAssembler getInstance() {
		if(instancia == null) {
			instancia = new ObjetoAssembler();
		}
		
		return instancia;
	}
	
	public ObjetoDTO entityDTO(Objeto objeto) {
		return new ObjetoDTO(objeto.getNombre(), objeto.getVida(), objeto.getVelocidad(), objeto.getArmadura(),
				objeto.getDiscrecion(), objeto.getAtaque(), objeto.getDefensa(), objeto.getTipo());
	}
}

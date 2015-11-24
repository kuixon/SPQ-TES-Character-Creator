package es.deusto.spq.TEScc.servidor;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import es.deusto.spq.TEScc.dto.ObjetoAssembler;
import es.deusto.spq.TEScc.dto.ObjetoDTO;
import es.deusto.spq.TEScc.dto.PersonajeAssembler;
import es.deusto.spq.TEScc.dto.PersonajeDTO;
import es.deusto.spq.TEScc.servidor.jdo.Objeto;
import es.deusto.spq.TEScc.servidor.jdo.Personaje;
import es.deusto.spq.TEScc.servidor.jdo.Usuario;

public class ServicioTES {
	private static ServicioTES instancia;
	
	private ServicioTES() {
	}
	
	public static synchronized ServicioTES getInstance() {
		if (instancia == null) {
			instancia = new ServicioTES();
		}
		
		return instancia;
	}
	
	public boolean registro(String nombre, String username, String contrasena) {
		return DAODatabase.getInstance().registro(nombre, username, contrasena);
	}
	
	public boolean existeUsuario(String username) {
		return DAODatabase.getInstance().existeUsuario(username);
	}
	
	public boolean login(String username, String password) {
		return DAODatabase.getInstance().login(username, password);
	}
	
	public void darseBaja(String username) {
		DAODatabase.getInstance().darseBaja(username);
	}
	
	public Set<ObjetoDTO> getObjetos() {
		Set<Objeto> objetos = DAODatabase.getInstance().getObjetos();
		Set<ObjetoDTO> objetosDTO = new HashSet<ObjetoDTO>();
		
		for(Iterator<Objeto> i = objetos.iterator(); i.hasNext();)
			objetosDTO.add(ObjetoAssembler.getInstance().entityDTO(i.next()));		
		
		return objetosDTO;
	}
	
	public void guardarPersonaje(String username, String apodo, String fechaCreacion, String fechaUltMod,
			int vida, int velocidad, int armadura, int discrecion,
			int ataque, int defensa, int tipo, String[] objs) {
		DAODatabase.getInstance().guardarPersonaje(username, apodo, fechaCreacion, fechaUltMod, vida, velocidad, armadura, discrecion, ataque, defensa, tipo, objs);
	}
	
	public Set<PersonajeDTO> getPersonajesPorUsuario(String username) {
		Set<Personaje> personajes = DAODatabase.getInstance().getPersonajesPorUsuario(username);
		Set<PersonajeDTO> personajesDTO = new HashSet<PersonajeDTO>();
		
		for(Iterator<Personaje> i = personajes.iterator(); i.hasNext();)
			personajesDTO.add(PersonajeAssembler.getInstance().entityDTO(i.next()));
		
		return personajesDTO;
	}
	
	public PersonajeDTO getPersonaje(String apodo) {
		return PersonajeAssembler.getInstance().entityDTO(DAODatabase.getInstance().getPersonaje(apodo));
	}
	
	public void deletePersonaje(String apodo) {
		DAODatabase.getInstance().deletePersonaje(apodo);
	}
	
	public Set<ObjetoDTO> getObjetosPorPersonaje(String apodo) {
		Set<Objeto> objetos = DAODatabase.getInstance().getObjetosPorPersonaje(apodo);
		Set<ObjetoDTO> objetosDTO = new HashSet<ObjetoDTO>();
		
		for(Iterator<Objeto> i = objetos.iterator(); i.hasNext();)
			objetosDTO.add(ObjetoAssembler.getInstance().entityDTO(i.next()));
		
		return objetosDTO;
	}
}

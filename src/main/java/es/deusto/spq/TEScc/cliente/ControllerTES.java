package es.deusto.spq.TEScc.cliente;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Set;

import es.deusto.spq.TEScc.dto.ObjetoDTO;
import es.deusto.spq.TEScc.dto.PersonajeDTO;
import es.deusto.spq.TEScc.cliente.RMIServiceLocator;

public class ControllerTES {
	
	private static ControllerTES instancia;
	private RMIServiceLocator rmi;
	
	public static synchronized ControllerTES getInstance(RMIServiceLocator rmi) {
		if(instancia == null) {
			instancia = new ControllerTES(rmi);
		}
		
		return instancia;
	}
	
	public ControllerTES(RMIServiceLocator rmi) {
		this.rmi = rmi;
	}

	public boolean registro(String nombre, String username, String contrasena) throws RemoteException {
		return rmi.getService().registro(nombre, username, contrasena);
	}
	
	public boolean existeUsuario(String username) throws RemoteException {
		return rmi.getService().existeUsuario(username);
	}
	
	public boolean login(String username, String password) throws RemoteException {
		return rmi.getService().login(username, password);
	}
	
	public void darseBaja(String username) throws RemoteException {
		rmi.getService().darseBaja(username);
	}
	
	public Set<ObjetoDTO> getObjetos() throws RemoteException {
		return rmi.getService().getObjetos();
	}
	
	public void guardarPersonaje(String username, String apodo,
			String fechaCreacion, String fechaUltMod, int vida, int velocidad,
			int armadura, int discrecion, int ataque, int defensa, int tipo,
			String[] objs) throws RemoteException {
		rmi.getService().guardarPersonaje(username, apodo, fechaCreacion, fechaUltMod, vida, velocidad, armadura, discrecion, ataque, defensa, tipo, objs);
	}
	
	public Set<PersonajeDTO> getPersonajesPorUsuario(String username) throws RemoteException {
		Set<PersonajeDTO> personajes = rmi.getService().getPersonajesPorUsuario(username);
		for(Iterator<PersonajeDTO> i = personajes.iterator(); i.hasNext();) {
			PersonajeDTO per = i.next();
			System.out.println(per.getApodo());
		}
		return personajes;
	}
	
	public PersonajeDTO getPersonaje(String apodo) throws RemoteException {
		return rmi.getService().getPersonaje(apodo);
	}
	
	public void deletePersonaje(String apodo) throws RemoteException {
		rmi.getService().deletePersonaje(apodo);
	}
	
	public Set<ObjetoDTO> getObjetosPorPersonaje(String apodo) throws RemoteException {
		return rmi.getService().getObjetosPorPersonaje(apodo);
	}
}

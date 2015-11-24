package es.deusto.spq.TEScc.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

import es.deusto.spq.TEScc.dto.ObjetoDTO;
import es.deusto.spq.TEScc.dto.PersonajeDTO;
import es.deusto.spq.TEScc.servidor.IFacade;

public class Facade extends UnicastRemoteObject implements IFacade {

	private static final long serialVersionUID = 1L;
	
	private String serverName;
	
	public Facade(String serverName) throws RemoteException {
		this.serverName = serverName;
	}

	public boolean registro(String nombre, String username, String contrasena)
			throws RemoteException {
		return ServicioTES.getInstance().registro(nombre, username, contrasena);
	}

	public boolean existeUsuario(String username) throws RemoteException {
		return ServicioTES.getInstance().existeUsuario(username);
	}

	public boolean login(String username, String password)
			throws RemoteException {
		return ServicioTES.getInstance().login(username, password);
	}

	public void darseBaja(String username) throws RemoteException {
		ServicioTES.getInstance().darseBaja(username);
	}

	public Set<ObjetoDTO> getObjetos() throws RemoteException {
		return ServicioTES.getInstance().getObjetos();
	}

	public void guardarPersonaje(String username, String apodo,
			String fechaCreacion, String fechaUltMod, int vida, int velocidad,
			int armadura, int discrecion, int ataque, int defensa, int tipo,
			String[] objs) throws RemoteException {
		ServicioTES.getInstance().guardarPersonaje(username, apodo, fechaCreacion, fechaUltMod, vida, velocidad, armadura, discrecion, ataque, defensa, tipo, objs);
	}

	public Set<PersonajeDTO> getPersonajesPorUsuario(String username)
			throws RemoteException {
		Set<PersonajeDTO> personajes = ServicioTES.getInstance().getPersonajesPorUsuario(username);
		for(Iterator<PersonajeDTO> i = personajes.iterator(); i.hasNext();) {
			PersonajeDTO per = i.next();
		}
		return personajes;
	}

	public PersonajeDTO getPersonaje(String apodo) throws RemoteException {
		return ServicioTES.getInstance().getPersonaje(apodo);
	}

	public void deletePersonaje(String apodo) throws RemoteException {
		ServicioTES.getInstance().deletePersonaje(apodo);		
	}

	public Set<ObjetoDTO> getObjetosPorPersonaje(String apodo)
			throws RemoteException {
		return ServicioTES.getInstance().getObjetosPorPersonaje(apodo);
	}
}

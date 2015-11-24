package es.deusto.spq.TEScc.servidor.jdo;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Usuario {
	String nombre;
	@PrimaryKey
	String username;
	String contrasena;
	Set<Personaje> personajes = new HashSet<Personaje>();
	
	public Usuario() {
		
	}

	public Usuario(String nombre, String username, String contrasena) {
		this.nombre = nombre;
		this.username = username;
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public void aniadirPersonaje(Personaje personaje) {
		personajes.add(personaje);
	}

	public void eliminarPersonaje(Personaje personaje) {
		personajes.remove(personaje);
	}

	public int numeroDePersonajes() {
		return personajes.size();
	}	
	
	public Set<Personaje> getPersonajes() {
		return this.personajes;
	}

	public void setPersonajes(Set<Personaje> personajes) {
		this.personajes = personajes;
	}
	
	public boolean equals(Usuario usuario) {
		return this.username.equals(usuario.getUsername());
	}
}

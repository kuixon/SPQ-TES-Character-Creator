package es.deusto.spq.TEScc.servidor.jdo;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(detachable = "true")
public class Objeto {
	public static final int YELMO = 0;
	public static final int ARMADURA = 1;
	public static final int BRAZALETE = 2;
	public static final int BOTAS = 3;
	
	int id;
	String nombre;
	int vida;
	int velocidad;
	int armadura;
	int discrecion;
	int ataque;
	int defensa;
	int tipo;
	Set<Personaje> personajes = new HashSet<Personaje>();
	
	public Objeto() {
	}

	public Objeto(String nombre, int vida, int velocidad, int armadura, int discrecion,
			int ataque, int defensa, int tipo) {
		this.nombre = nombre;
		this.vida = vida;
		this.velocidad = velocidad;
		this.armadura = armadura;
		this.discrecion = discrecion;
		this.ataque = ataque;
		this.defensa = defensa;
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public int getArmadura() {
		return armadura;
	}

	public void setArmadura(int armadura) {
		this.armadura = armadura;
	}

	public int getDiscrecion() {
		return discrecion;
	}

	public void setDiscrecion(int discrecion) {
		this.discrecion = discrecion;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}
	
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
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
	
	public boolean equals(Objeto objeto) {
		return this.nombre.equals(objeto.getNombre());
	}
}

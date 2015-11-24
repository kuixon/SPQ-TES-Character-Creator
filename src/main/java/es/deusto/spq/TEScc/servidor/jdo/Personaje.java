package es.deusto.spq.TEScc.servidor.jdo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(detachable = "true")
public class Personaje {
	public static final int ELFO = 0;
	public static final int ORCO = 1;
	
	int id;
	String apodo;
	String fechaCreacion;
	String fechaUltMod;
	int vida;
	int velocidad;
	int armadura;
	int discrecion;
	int ataque;
	int defensa;
	int tipo;
	Usuario usuario;
	Set<Objeto> objetos = new HashSet<Objeto>();
	
	public Personaje() {
	}

	public Personaje(String apodo, String fechaCreacion, String fechaUltMod,
			int vida, int velocidad, int armadura, int discrecion,
			int ataque, int defensa, int tipo, Usuario usuario) {
		this.apodo = apodo;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltMod = fechaUltMod;
		this.vida = vida;
		this.velocidad = velocidad;
		this.armadura = armadura;
		this.discrecion = discrecion;
		this.ataque = ataque;
		this.defensa = defensa;
		this.tipo = tipo;
		this.usuario = usuario;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaUltMod() {
		return fechaUltMod;
	}

	public void setFechaUltMod(String fechaUltMod) {
		this.fechaUltMod = fechaUltMod;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void aniadirObjeto(Objeto objeto) {
		objetos.add(objeto);
	}

	public void eliminarObjeto(Objeto objeto) {
		objetos.remove(objeto);
	}

	public int numeroDeObjetos() {
		return objetos.size();
	}	
	
	public Set<Objeto> getObjetos() {
		return this.objetos;
	}

	public void setObjetos(Set<Objeto> objetos) {
		this.objetos = objetos;
	}
	
	public void eliminarObjetos() {
		for(Iterator<Objeto> i = objetos.iterator(); i.hasNext();) {
			Objeto obj = i.next();
			this.eliminarObjeto(obj);
		}
	}
	
	public boolean equals(Personaje personaje) {
		return this.apodo.equals(personaje.getApodo());
	}
}

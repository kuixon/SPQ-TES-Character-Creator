package es.deusto.spq.TEScc.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ObjetoDTO implements Serializable {
	public static final int YELMO = 0;
	public static final int ARMADURA = 1;
	public static final int BRAZALETE = 2;
	public static final int BOTAS = 3;
	
	String nombre;
	int vida;
	int velocidad;
	int armadura;
	int discrecion;
	int ataque;
	int defensa;
	int tipo;
	
	public ObjetoDTO() {
	}

	public ObjetoDTO(String nombre, int vida, int velocidad, int armadura, int discrecion,
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
	
	public boolean equals(ObjetoDTO objeto) {
		return this.nombre.equals(objeto.getNombre());
	}
}

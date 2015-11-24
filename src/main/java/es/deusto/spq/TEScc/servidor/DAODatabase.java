package es.deusto.spq.TEScc.servidor;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.spq.TEScc.servidor.jdo.Objeto;
import es.deusto.spq.TEScc.servidor.jdo.Personaje;
import es.deusto.spq.TEScc.servidor.jdo.Usuario;

public class DAODatabase {
	private static DAODatabase instance;
	private PersistenceManagerFactory pmf;
	private Usuario usuario = null;
	private Personaje personaje = null;
	
	final Logger logger = LoggerFactory.getLogger(DAODatabase.class);
	
	public static synchronized DAODatabase getInstance() {
		if(instance == null) {
			instance = new DAODatabase();
		}

		return instance;
	}

	private DAODatabase() {

		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = null;
		Transaction tx = null;
		logger.info("- Store objects in the DB");		

		try {

			pm = pmf.getPersistenceManager();

			tx = pm.currentTransaction();		

			tx.begin();


			tx.commit();
		} catch (Exception ex) {
			logger.error(" $ Error storing objects in the DB: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
	}

	private boolean storeObject(Object object) throws Exception {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			logger.info("   * Storing an object: " + object);
			pm.makePersistent(object);
			tx.commit();
			return true;
		} catch (Exception ex) {
			logger.error("   $ Error storing an object: " + ex.getMessage());
			return false;
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}

	public boolean registro(String nombre, String username, String contrasena) {
		Usuario usu= new Usuario(nombre, username, contrasena);
		try {
			return this.storeObject(usu);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void getUsuario(String username) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			logger.info("   * Querying usuario: " + username);

			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + Usuario.class.getName() + " WHERE username == '" + username + "'");
			query.setUnique(true);
			this.usuario = (Usuario) query.execute();
			tx.commit();
			logger.info("Nombre del usuario: " + this.usuario.getNombre());
			
		} catch (Exception ex) {
			logger.error("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}		
	}
	
	public Set<Personaje> getPersonajesPorUsuario(String username) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Set<Personaje> personajes = new HashSet<Personaje>();
		try {
			logger.info("   * Querying usuario: " + username);
			tx.begin();			
			Query query = pm.newQuery("SELECT FROM " + Personaje.class.getName());
			
			for (Personaje personaje : (Collection<Personaje>)query.execute()) {
				if(personaje.getUsuario().getUsername().equals(username)){
					personajes.add(personaje);
				}
			}
			
			tx.commit();
			for(Iterator<Personaje> i = personajes.iterator(); i.hasNext();) {
				Personaje per = i.next();
				logger.info("Apodo del personaje: " + per.getApodo());
			}
			return personajes;
		} catch (Exception ex) {
			logger.error("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return null;
	}
	
	public Set<Objeto> getObjetosPorPersonaje(String apodo) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Set<Objeto> objetos = new HashSet<Objeto>();
		try {
			logger.info("   * Querying obetos de: " + apodo);
			
			tx.begin();			
			Query query = pm.newQuery("SELECT FROM " + Personaje.class.getName() + " WHERE apodo == '" + apodo + "'");
			query.setUnique(true);
			Personaje per = (Personaje) query.execute();
			
			for(Iterator<Objeto> i = per.getObjetos().iterator(); i.hasNext();) {
				Objeto objeto = i.next();
				logger.info("------Exito: " + objeto.getNombre());
				objetos.add(objeto);
			}
			logger.info("ANTES DEL TX");
			for(Iterator<Objeto> i = objetos.iterator(); i.hasNext();) {
				Objeto obj = i.next();
				logger.info(obj.getNombre());
			}
	        tx.commit();
	        logger.info("DESPUES DEL TX");
			for(Iterator<Objeto> i = objetos.iterator(); i.hasNext();) {
				Objeto obj = i.next();
				logger.info(obj.getNombre());
			}
			return objetos;
		} catch (Exception ex) {
			logger.error("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return null;
	}

	public Personaje getPersonaje(String apodo) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Personaje per = null;
		try {
			logger.info("   * Querying personaje: " + apodo);
			
			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + Personaje.class.getName() + " WHERE apodo == '" + apodo + "'");
			query.setUnique(true);
			per = (Personaje) query.execute();
			tx.commit();
			logger.info("Nombre del personaje: " + per.getApodo());
		} catch (Exception ex) {
			logger.error("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return per;		
	}

	public int getIdPersonaje(String apodo) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		int i = -1;
		try {
			logger.info("   * Querying personaje: " + apodo);

			tx.begin();
			Query query = pm.newQuery("SELECT id FROM " + Personaje.class.getName() + " WHERE apodo == '" + apodo + "'");
			query.setUnique(true);
			i = ((Long)query.execute()).intValue();
			tx.commit();
			logger.info("Numero del personaje: " + apodo + " -> " + i);
		} catch (Exception ex) {
			logger.error("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return i;
	}
	
	public Objeto getObjeto(String nombre) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Objeto obj = null;
		try {
			logger.info("   * Querying objeto: " + nombre);

			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + Objeto.class.getName() + " WHERE nombre == '" + nombre + "'");
			query.setUnique(true);
			obj = (Objeto) query.execute();
			tx.commit();
			logger.info("Nombre del objeto: " + obj.getNombre());
		} catch (Exception ex) {
			logger.error("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return obj;		
	}

	public boolean existeUsuario(String username)
	{
		this.getUsuario(username);
		if(this.usuario != null)
		{
			return true;
		} else {
			return false;
		}
	}

	public boolean existePersonaje(String apodo)
	{
		Personaje per = this.getPersonaje(apodo);
		if(per != null)
		{
			return true;
		} else {
			return false;
		}
	}

	public boolean existeObjeto(String nombre)
	{
		Objeto obj = this.getObjeto(nombre);
		if(obj != null)
		{
			return true;
		} else {
			return false;
		}
	}

	public boolean login(String username, String password)
	{
		if(this.usuario.getContrasena().equals(password))
		{
			this.usuario = null;
			return true;
		}
		else
		{
			return false;
		}
	}

	public void darseBaja(String username)
	{
		this.deleteUsuario(username);
	}

	public int numeroUsuarios() {
		int num = 0;
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			logger.info(" * Querying numero de usuarios");

			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + Usuario.class.getName());
			for (Usuario usuario : (Collection<Usuario>) query.execute()) {
				logger.info(usuario.getNombre()+"-"+usuario.getUsername()+"-"+usuario.getContrasena());
				num++;
			}

			tx.commit();
		} catch (Exception ex) {
			logger.error("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return num;
	}
	
	public void updateUsuario(Usuario usuario) {
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    
	    try {
	    	tx.begin();
	    	pm.makePersistent(usuario);
	    	tx.commit();
	     } catch (Exception ex) {
		   	logger.info("   $ Error retreiving an extent: " + ex.getMessage());
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}
				
	   		pm.close();
	     }
	}
	
	public void updatePersonaje(Personaje personaje) {
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    
	    try {
	    	tx.begin();
	    	pm.makePersistent(personaje);
	    	tx.commit();
	     } catch (Exception ex) {
		   	logger.error("   $ Error retreiving an extent: " + ex.getMessage());;
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}
				
	   		pm.close();
	     }
	}
	
	public void updateObjeto(Objeto objeto) {
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    
	    try {
	    	tx.begin();
	    	pm.makePersistent(objeto);
	    	tx.commit();
	     } catch (Exception ex) {
		   	logger.error("   $ Error retreiving an extent: " + ex.getMessage());
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}
				
	   		pm.close();
	     }
	}

	public void deleteUsuario(String username) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			logger.info("   * Querying usuario: " + username);

			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + Usuario.class.getName() + " WHERE username == '" + username + "'");
			query.setUnique(true);
			this.usuario = (Usuario) query.execute();
			pm.deletePersistent(this.usuario);
			tx.commit();
			logger.info("Borrando el usuario...");
			logger.info("======================");
			logger.info("Numero de usuarios: "+numeroUsuarios());
		} catch (Exception ex) {
			logger.error("   $ Error deleting an usuario: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}
	
	public void deletePersonaje(String nombrePersonaje) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			logger.info("   * Querying personaje: " + nombrePersonaje);
			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + Personaje.class.getName() + " WHERE apodo == '" + nombrePersonaje + "'");
			query.setUnique(true);
			this.personaje = (Personaje) query.execute();
			pm.deletePersistent(this.personaje);
			tx.commit();
			logger.info("Borrando el personaje...");
		} catch (Exception ex) {
			logger.error("   $ Error al borrar un personaje: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			pm.close();
		}
	}
	
	public void deleteObjeto(String nombre) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Objeto obj;
		try {
			logger.info("   * Querying objeto: " + nombre);
			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + Objeto.class.getName() + " WHERE nombre == '" + nombre + "'");
			query.setUnique(true);
			obj = (Objeto) query.execute();
			pm.deletePersistent(obj);
			tx.commit();
			logger.info("Borrando el objeto...");
		} catch (Exception ex) {
			logger.error("   $ Error al borrar un objeto: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			pm.close();
		}
	}

	public void guardarPersonaje(String username, String apodo, String fechaCreacion, String fechaUltMod,
			int vida, int velocidad, int armadura, int discrecion,
			int ataque, int defensa, int tipo, String[] objs) {
		
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {
			logger.info("   * Querying usuario: " + username);

			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + Usuario.class.getName() + " WHERE username == '" + username + "'");
			query.setUnique(true);
			this.usuario = (Usuario) query.execute();
			tx.commit();
			
			if(this.existePersonaje(apodo)) {
				Personaje per = null;
				tx.begin();
				Query query2 = pm.newQuery("SELECT FROM " + Personaje.class.getName() + " WHERE apodo == '" + apodo + "'");
				query2.setUnique(true);
				per = (Personaje) query2.execute();
				per.setFechaUltMod(fechaUltMod);
				per.setVida(vida);
				per.setVelocidad(velocidad);
				per.setArmadura(armadura);
				per.setDiscrecion(discrecion);
				per.setAtaque(ataque);
				per.setDefensa(defensa);
				per.setTipo(tipo);
				per.eliminarObjetos();
				
				Objeto obj = null;
				for(int i=0; i<objs.length; i++) {
					if(!objs[i].equals("")) {
						Query query3 = pm.newQuery("SELECT FROM " + Objeto.class.getName() + " WHERE nombre == '" + objs[i] + "'");
						query3.setUnique(true);
						obj = (Objeto) query3.execute();
						per.aniadirObjeto(obj);
						obj.aniadirPersonaje(per);
						pm.makePersistent(obj);
					}
				}
		    	pm.makePersistent(per);
		    	pm.makePersistent(this.usuario);
		    	tx.commit();

			} else {
				Personaje per = new Personaje(apodo, fechaCreacion, fechaUltMod,
						vida, velocidad, armadura, discrecion,
						ataque, defensa, tipo, this.usuario);

				Objeto obj = null;
				for(int i=0; i<objs.length; i++) {
					if(!objs[i].equals("")) {
						tx.begin();
						Query query3 = pm.newQuery("SELECT FROM " + Objeto.class.getName() + " WHERE nombre == '" + objs[i] + "'");
						query3.setUnique(true);
						obj = (Objeto) query3.execute();
						tx.commit();
						per.aniadirObjeto(obj);
						obj.aniadirPersonaje(per);
						tx.begin();
						pm.makePersistent(obj);
						tx.commit();
					}
				}

				logger.info("   * Storing an object: " + per);
				tx.begin();
				pm.makePersistent(per);
				tx.commit();
				this.usuario.aniadirPersonaje(per);
				tx.begin();
		    	pm.makePersistent(this.usuario);
		    	tx.commit();
			}
		} catch (Exception ex) {
			logger.error("   $ Error al guardar personaje: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}

	public void crearObjeto(String nombre, int vida, int velocidad, int armadura, int discrecion,
			int ataque, int defensa, int tipo) {
		Objeto obj = new Objeto(nombre, vida, velocidad, armadura, discrecion, ataque, defensa, tipo);
		try {
			this.storeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Set<Objeto> getObjetos() {
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
		Set<Objeto> objetos = new HashSet<Objeto>();
		
		try {
			logger.info(" * Querying objetos...");
			
	    	tx.begin();
	    	Query query = pm.newQuery("SELECT FROM " + Objeto.class.getName());
	    	
	    	logger.info("EN DAO---------------------");
	    	for (Objeto objeto : (Collection<Objeto>)query.execute()) {
				objetos.add(objeto);
				logger.info(objeto.getNombre());
			}
	    	logger.info("ANTES DEL TX");
			for(Iterator<Objeto> i = objetos.iterator(); i.hasNext();) {
				Objeto obj = i.next();
				logger.info(obj.getNombre());
			}
	        tx.commit();
	        logger.info("DESPUES DEL TX");
			for(Iterator<Objeto> i = objetos.iterator(); i.hasNext();) {
				Objeto obj = i.next();
				logger.info(obj.getNombre());
			}
	        return objetos;
		} catch (Exception ex) {
		   	logger.error("   $ Error retreiving an extent: " + ex.getMessage());
	    } finally {
		  	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}
				
	   		pm.close();
	    }
		return null;
	}
}

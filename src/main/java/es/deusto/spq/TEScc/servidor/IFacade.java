package es.deusto.spq.TEScc.servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import es.deusto.spq.TEScc.dto.ObjetoDTO;
import es.deusto.spq.TEScc.dto.PersonajeDTO;

/**Esta clase es la interfaz de nuestra aplicacion. Esta interfaz, contiene la cabecera de todos los
 * metodos que se utilizaran. La llamada a estos metodos siempre se realizara desde la parte clienta
 * y esta interfaz hara el papel de mediador entre cliente/servidor. Se formara una especia de
 * recorrido pasando por varias clases (una de las cuales es esta) desde la parte clienta hasta la 
 * parte servidora. 
 * @author salgu_000
 *
 */
public interface IFacade extends Remote {
	
	/**Este metodo almacenara en la base de datos un nuevo usuario, lo registrara. Para ello, recibe
	 * por parametro los datos del usuario a registrar. La llamda a este metodo la realizaremos desde
	 * la clase PantallaRegistro.
	 * @param nombre: Nombre del usuario a registrar.
	 * @param username: Nombre de usuario del usuario a registrar.
	 * @param contrasena: Contraseña del usuario a registrar.
	 * @return true/false: Si nos devuelve true es que el registro se ha realizado correctamente. Si
	 * por el contrario nos devulve false, es que no se ha podido realizar el registro porque el 
	 * usuario a registrar ya existia.
	 * @throws RemoteException
	 */
	public boolean registro(String nombre, String username, String contrasena) throws RemoteException;
	
	/**Este metodo lo utilizaremos para saber si el usuario que le pasamos por parametro ya esta
	 * almacenado en la base de datos (para saber si existe).
	 * @param username: Nombre de usuario a buscar
	 * @return true/false: Este metodo nos devuelve true si existe el usuario y false si no existe
	 * @throws RemoteException
	 */
	public boolean existeUsuario(String username) throws RemoteException;
	
	/**Este metodo nos servira para saber si la contraseña introducida se corresponde a la del usuario
	 * en cuestion. Es por eso que recibe el nombre del usuario y la contraseña por parametros (para
	 * realizar la comprobacion).
	 * @param username: Nombre de usuario del usuario a realizar la comprobacion.
	 * @param password: Contraseña del usuario a realizar la comprobacion.
	 * @return true/false: Si nos devuelve true es que la contraseña se corresponde con la contraseña
	 * de ese usuario. Si, por el contrario, nos devuelve false es que la contraseña introducida no es
	 * la de ese usuario.
	 * @throws RemoteException
	 */
	public boolean login(String username, String password) throws RemoteException;
	
	/**Este metodo eliminara del sistema al usuario que le pasemos por parametro (le dara de baja).
	 * @param username: Nombre de usuario del usuario a dar de baja de la aplicacion
	 * @throws RemoteException
	 */
	public void darseBaja(String username) throws RemoteException;
	
	/**Este metodo nos devolvera todos los objetos de la aplicacion almacenados en un hash set.
	 * @return Set<ObjetoDTO>: Todos los objetos de la aplicacion.
	 * @throws RemoteException
	 */
	public Set<ObjetoDTO> getObjetos() throws RemoteException;
	
	/**Este metodo almacenara un perosnaje en la base de datos con sus correspondientes datos (estos
	 * datos los recibira el metodo por parametro).
	 * @param username: Nombre de usuario del usuario.
	 * @param apodo: Nombre del personaje.
	 * @param fechaCreacion: Fecha en la que el personaje es creado.
	 * @param fechaUltMod: Fecha en la que el personaje es modificado.
	 * @param vida: Nivel de vida del personaje.
	 * @param velocidad: Nivel de velocidad del personaje.
	 * @param armadura: Nivel de armadura del perosnaje.
	 * @param discrecion: Nivel de discrecion del personaje.
	 * @param ataque: Nivel de ataque del personaje.
	 * @param defensa: Nivel de defensa del personaje.
	 * @param tipo: Tipo de personaje (0 -> Elfo // 1 -> Orco).
	 * @param objs: Nombre de los objetos que lleva el personaje.
	 * @throws RemoteException
	 */
	public void guardarPersonaje(String username, String apodo, String fechaCreacion, String fechaUltMod,
			int vida, int velocidad, int armadura, int discrecion,
			int ataque, int defensa, int tipo, String[] objs) throws RemoteException;
	
	/**Este metodo nos servira para obtener todos los personajes que tiene un usuario.
	 * @param username: Nombre de usuario del usuario para el que queremos obtener sus personajes.
	 * @return Set<PersonajeDTO>: Personajes que tiene el usuario creados hasta el momento almacenados
	 * en un hash set.
	 * @throws RemoteException
	 */
	public Set<PersonajeDTO> getPersonajesPorUsuario(String username) throws RemoteException;
	
	/**Este metodo nos permitira obtener un personaje conociendo su apodo (nombre).
	 * @param apodo: Nombre del personaje.
	 * @return PersonajeDTO: Personaje obtenido tras realizar la busqueda utilizando su nombre.
	 * @throws RemoteException
	 */
	public PersonajeDTO getPersonaje(String apodo) throws RemoteException;
	
	/**Este metodo eliminara un personaje de la base de datos.
	 * @param apodo: Nombre del personaje que sera eliminado.
	 * @throws RemoteException
	 */
	public void deletePersonaje(String apodo) throws RemoteException;
	
	/**Este metodo nos permitira obtener todos los objetos que lleva un personaje. Para ello,
	 * tendremos que pasarle por parametro el nombre del personaje.
	 * @param apodo: Nombre del personaje.
	 * @return Set<ObjetoDTO>: Objetos que tiene equipados el personaje almacenados en un hash set.
	 * @throws RemoteException
	 */
	public Set<ObjetoDTO> getObjetosPorPersonaje(String apodo) throws RemoteException;
}

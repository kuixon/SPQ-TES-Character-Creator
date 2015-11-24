package es.deusto.spq.TEScc.cliente;

import java.rmi.RMISecurityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.spq.TEScc.servidor.IFacade;

/**Esta clase será la referente al RMIServiceLocator. Esta clase tiene dos atributos: service y
 * serviceKey. 
 * 1. Service es un objeto de la clase IFacade. La clase IFacade es la interfaz de nuestra
 * aplicacion y contendra todos los metodos que utilizaremos en el proyecto y que seran llamados desde
 * la parte clienta de nuestra aplicacion.
 * 2. ServiceKey sera un atributo de tipo String que contendra el valor de la clave para poder
 * establecer la conexion con el servidor.
 * @author salgu_000
 *
 */
public class RMIServiceLocator {
	private IFacade service;
	private String serviceKey;

	final Logger logger = LoggerFactory.getLogger(RMIServiceLocator.class);

    /**Constructor vacio de la clase
     * 
     */
    public RMIServiceLocator() {
    }

    /**Constructor por parametros de la clase. Este constructor recibe tres parametros, cada uno de
     * los cuales nos indicara la IP, puerto y nombre del servidor al que nos deberemos de conectar.
     * Tras arrancar el servidor, este estara esperando a que se intente establecer una conexion
     * desde la parte clienta. En el momento que se inicia la parte clienta, se intentara establecer
     * la conexion con el servidor que permanecera en espera. Para ello, desde la parte clienta, se
     * creara un objeto de esta clase y se llamara a este metodo pasandole por parametro la IP, puerto
     * y nombre del servidor al que nos queremos conectar. Una vez vez establecida la conexion, este
     * metodo inicializara el atributo de esta misma clase, service, con la IFacade que nos devuelve la
     * conexion.
     * @param ip: IP a la que conectarse
     * @param port: Puerto al que conectarse
     * @param name: Nombre del servidor
     * @throws Exception
     */
    public void setService(String ip, String port, String name) throws Exception {
    	if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
    	
    	serviceKey = "//" + ip + ":" + port + "/" + name; //"//127.0.0.1:1099/TESccServer"
    	
    	service = (IFacade) java.rmi.Naming.lookup(serviceKey);
    	logger.info("- Conectado al servidor: //" + ip + ":" + port + "/" + name);
    }
    
    /**Este metodo nos devolvera el atributo de esta misma clase. Por eso, solamente tiene sentido
     * hacer la llamada a este metodo despues de haberla hecho al setService().
     * @return service: IFacade obtenida tras haber realizado la llamada al metodo setService()
     */
    public IFacade getService() {
    	return service;
    }
}

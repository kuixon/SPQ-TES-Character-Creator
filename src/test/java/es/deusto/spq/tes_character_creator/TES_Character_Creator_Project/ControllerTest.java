package es.deusto.spq.tes_character_creator.TES_Character_Creator_Project;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.databene.contiperf.Required;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;

import es.deusto.spq.TEScc.cliente.ControllerTES;
import es.deusto.spq.TEScc.cliente.RMIServiceLocator;
import es.deusto.spq.TEScc.dto.ObjetoDTO;
import es.deusto.spq.TEScc.dto.PersonajeDTO;
import es.deusto.spq.TEScc.servidor.DAODatabase;
import es.deusto.spq.TEScc.servidor.Facade;
import es.deusto.spq.TEScc.servidor.IFacade;
import es.deusto.spq.TEScc.servidor.ServicioTES;
import es.deusto.spq.TEScc.servidor.Servidor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PerfTest(invocations = 2)
@Required(max = 500, average = 250)
public class ControllerTest {
	
	private static String cwd = ControllerTES.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	private static RMIServiceLocator rmi = new RMIServiceLocator();
	
	final static Logger logger = LoggerFactory.getLogger(ControllerTES.class);
	
	@Rule public ContiPerfRule rule = new ContiPerfRule();
	
	public static junit.framework.Test suite() {
		 return new JUnit4TestAdapter(ControllerTest.class);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DAODatabase.getInstance().registro("Paco Paquito", "Paco", "1234");
		DAODatabase.getInstance().registro("Pepe Pepito", "Pepe", "1234");
		DAODatabase.getInstance().registro("Poiler S-Poiler", "Poiler", "1234");
		DAODatabase.getInstance().registro("Bob Bobo", "Bob", "1234");
		DAODatabase.getInstance().crearObjeto("Yelmo Elfico", 0, 25, 10, 25, 15, 15, ObjetoDTO.YELMO);
		DAODatabase.getInstance().guardarPersonaje("Pepe", "Spoiler", "10/10/10", "10/10/10", 25, 25, 25, 25, 25, 25, 0, (new String[] { "Yelmo Elfico" }));
		
		// Launch the RMI registry
		class RMIRegistryRunnable implements Runnable {

			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(1099);
					logger.info("RMI registry ready.");
				} catch (Exception e) {
					logger.warn("Exception starting RMI registry:");
					e.printStackTrace();
				}	
			}
		}
		
		rmiRegistryThread = new Thread(new RMIRegistryRunnable());
		rmiRegistryThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
		class RMIServerRunnable implements Runnable {

			public void run() {
				logger.info("This is a test to check how mvn test executes this test without external interaction; JVM properties by program");
				logger.info("**************: " + cwd);
				System.setProperty("java.rmi.server.codebase", "file:" + cwd);
				System.setProperty("java.security.policy", "target\\classes\\security\\java.policy");

				if (System.getSecurityManager() == null) {
					System.setSecurityManager(new RMISecurityManager());
				}

				String name = "//127.0.0.1:1099/TESccServer";
				logger.info(" * TestServer name: " + name);

				try {
					IFacade service = new Facade("TESccServer");
					Naming.rebind(name, service);
				} catch (RemoteException re) {
					logger.error(" # Server RemoteException: " + re.getMessage());
					re.printStackTrace();
					System.exit(-1);
				} catch (MalformedURLException murle) {
					logger.error(" # Server MalformedURLException: " + murle.getMessage());
					murle.printStackTrace();
					System.exit(-1);
				}
			}
		}
		rmiServerThread = new Thread(new RMIServerRunnable());
		rmiServerThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
		rmi.setService("127.0.0.1", "1099", "TESccServer");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		DAODatabase.getInstance().deleteUsuario("Paco");
		DAODatabase.getInstance().deleteUsuario("Pepe");
		DAODatabase.getInstance().deleteUsuario("Poiler");
		DAODatabase.getInstance().deleteUsuario("Bob");
		DAODatabase.getInstance().deletePersonaje("Spoiler");
		DAODatabase.getInstance().deleteObjeto("Yelmo Elfico");
		try	{
			rmiServerThread.join();
			rmiRegistryThread.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Vamos a comprobar que no se puede realizar el registro de un usuario que ya esta almacenado
	 * en la base de datos. Lo hemos introducido en la base de datos para hacer este test
	 */
	@Test
	public void testRegistroUsuarioExistente() {
		try {
			assertFalse(ControllerTES.getInstance(rmi).registro("Paco Paquito", "Paco", "1234"));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Vamos a comprobar si existe un personaje con el nombre Spoiler. En nuestro caso, si va a existir
	 * porque lo hemos introducido en la base de datos para hacer este test
	 */
	@Test
	public void testGetPersonaje() {
		String nombreAbuscar = "Spoiler";
		PersonajeDTO expected = new PersonajeDTO();
		expected.setApodo(nombreAbuscar);
		PersonajeDTO actual = null;
		try {
			actual = ControllerTES.getInstance(rmi).getPersonaje(nombreAbuscar);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * Vamos a comprobar si existe un usuario con el username Pepe. En nuestro caso, si va a
	 * existir porque lo hemos introducido en la base de datos para hacer este test
	 */
	@Test
	public void testExisteUsuario() {
		String nombreUsuario = "Pepe";
		try {
			assertTrue(ControllerTES.getInstance(rmi).existeUsuario(nombreUsuario));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}

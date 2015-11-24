package es.deusto.spq.tes_character_creator.TES_Character_Creator_Project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.spq.TEScc.servidor.DAODatabase;
import es.deusto.spq.TEScc.servidor.jdo.Objeto;
import es.deusto.spq.TEScc.servidor.jdo.Personaje;

@PerfTest(invocations = 2)
@Required(max = 35, average = 30)
public class DAOTest {
	final Logger logger = LoggerFactory.getLogger(DAOTest.class);
	
	@Rule public ContiPerfRule rule = new ContiPerfRule();
	
	public static junit.framework.Test suite() {
		 return new JUnit4TestAdapter(DAOTest.class);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DAODatabase.getInstance().registro("Paco Paquito", "Paco", "1234");
		DAODatabase.getInstance().registro("Pepe Pepito", "Pepe", "1234");
		DAODatabase.getInstance().registro("Poiler S-Poiler", "Poiler", "1234");
		DAODatabase.getInstance().registro("Bob Bobo", "Bob", "1234");
		DAODatabase.getInstance().crearObjeto("Yelmo Elfico", 0, 25, 10, 25, 15, 15, Objeto.YELMO);
		DAODatabase.getInstance().guardarPersonaje("Pepe", "Spoiler", "10/10/10", "10/10/10", 25, 25, 25, 25, 25, 25, 0, (new String[] { "Yelmo Elfico" }));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		DAODatabase.getInstance().deleteUsuario("Paco");
		DAODatabase.getInstance().deleteUsuario("Pepe");
		DAODatabase.getInstance().deleteUsuario("Poiler");
		DAODatabase.getInstance().deleteUsuario("Bob");
		DAODatabase.getInstance().deletePersonaje("Spoiler");
		DAODatabase.getInstance().deleteObjeto("Yelmo Elfico");
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Vamos a comprobar que tenemos 4 personajes en nuestra base de datos.
	 */
	@Test
	public void testContarUsuarios() {
		logger.info("--------Starting tests--------");
		int expected = 4;
		int actual = DAODatabase.getInstance().numeroUsuarios();
		assertEquals(expected, actual);
	}
	
	/**
	 * Vamos a comprobar que no se puede realizar el registro de un usuario que ya esta almacenado
	 * en la base de datos. Lo hemos introducido en la base de datos para hacer este test
	 */
	@Test
	public void testRegistroUsuarioExistente() {
		assertFalse(DAODatabase.getInstance().registro("Paco Paquito", "Paco", "1234"));
	}
	
	/**
	 * Vamos a comprobar si existe un personaje con el nombre Spoiler. En nuestro caso, si va a existir
	 * porque lo hemos introducido en la base de datos para hacer este test
	 */
	@Test
	public void testGetPersonaje() {
		String nombreAbuscar = "Spoiler";
		Personaje expected = new Personaje();
		expected.setApodo(nombreAbuscar);
		Personaje actual = DAODatabase.getInstance().getPersonaje(nombreAbuscar);
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * Vamos a comprobar si existe un objeto con el nombre Yelmo Elfico. En nuestro caso, 
	 * si va a existir porque lo hemos introducido en la base de datos para hacer este test
	 */
	@Test
	public void testGetObjeto() {
		String nombreAbuscar = "Yelmo Elfico";
		Objeto expected = new Objeto();
		expected.setNombre(nombreAbuscar);
		Objeto actual = DAODatabase.getInstance().getObjeto(nombreAbuscar);
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * Vamos a comprobar si existe un usuario con el username Pepe. En nuestro caso, si va a
	 * existir porque lo hemos introducido en la base de datos para hacer este test
	 */
	@Test
	public void testExisteUsuario() {
		String nombreUsuario = "Pepe";
		assertTrue(DAODatabase.getInstance().existeUsuario(nombreUsuario));
	}
}

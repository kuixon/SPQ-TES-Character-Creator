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

import es.deusto.spq.TEScc.dto.ObjetoDTO;
import es.deusto.spq.TEScc.dto.PersonajeDTO;
import es.deusto.spq.TEScc.servidor.DAODatabase;
import es.deusto.spq.TEScc.servidor.ServicioTES;

@PerfTest(invocations = 2)
@Required(max = 40, average = 30)
public class FacadeTest {
	final Logger logger = LoggerFactory.getLogger(FacadeTest.class);
	
	@Rule public ContiPerfRule rule = new ContiPerfRule();
	
	public static junit.framework.Test suite() {
		 return new JUnit4TestAdapter(FacadeTest.class);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DAODatabase.getInstance().registro("Paco Paquito", "Paco", "1234");
		DAODatabase.getInstance().registro("Pepe Pepito", "Pepe", "1234");
		DAODatabase.getInstance().registro("Poiler S-Poiler", "Poiler", "1234");
		DAODatabase.getInstance().registro("Bob Bobo", "Bob", "1234");
		DAODatabase.getInstance().crearObjeto("Yelmo Elfico", 0, 25, 10, 25, 15, 15, ObjetoDTO.YELMO);
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
	 * Vamos a comprobar que no se puede realizar el registro de un usuario que ya esta almacenado
	 * en la base de datos. Lo hemos introducido en la base de datos para hacer este test
	 */
	@Test
	public void testRegistroUsuarioExistente() {
		assertFalse(ServicioTES.getInstance().registro("Paco Paquito", "Paco", "1234"));
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
		PersonajeDTO actual = ServicioTES.getInstance().getPersonaje(nombreAbuscar);
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

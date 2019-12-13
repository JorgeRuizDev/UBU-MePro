package juego.control;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.CombinableMatcher.either;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import juego.modelo.Jugador;
import juego.modelo.Tablero;
import static juego.modelo.Color.BLANCO;
import static juego.modelo.Color.NEGRO;

/**
 * Tests sobre la implementación del árbitro del tres en raya.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @version 1.0
 */
public class ArbitroTresEnRayaTest {

	/** Arbitro sin registro. */
	private ArbitroTresEnRaya arbitro;

	/**
	 * Inicializa el árbitro antes de cada test.
	 */
	@BeforeEach
	void inicializar() {
		arbitro = new ArbitroTresEnRaya();
	}

	/**
	 * Prueba que guarda los jugadores y asigna el turno aleatoriamente a uno de los
	 * dos. 
	 * 
	 * Dado que se selecciona aleatoriamente el turno, se comprueba simplemente
	 * que uno de los dos jugadores es el que tiene el turno. Se ejecuta varias
	 * veces para asegurarnos que se prueba con ambos valores.
	 */
	@Test
	@DisplayName("Probar el correcto registro de jugadores")
	void probarRegistroCorrectoJugadoresConTurnoAlSegundoJugador() {
		for (int i = 0; i < 10; i++) {
			// Inicializamos
			ArbitroTresEnRaya arbitroLocal = new ArbitroTresEnRaya();
			arbitroLocal.registrarJugador("Juan");
			arbitroLocal.registrarJugador("Pepe");
			// Comprobamos
			Jugador conTurno = arbitroLocal.obtenerTurno();
			assertAll( 
					() -> assertThat("El jugador con turno se tiene que llamar Pepe o Juan", conTurno.obtenerNombre(), either(is("Pepe")).or(is("Juan"))),
					() -> assertThat("El color asigando al turno no es correcto", conTurno.obtenerColor(), either(is(BLANCO)).or(is(NEGRO)))
			);
		}
	}

	/**
	 * Comprueba que el turno no se inicializa si solo hemos registrado al primer
	 * jugador.
	 */
	@Test
	@DisplayName("Probar que no hay turno con solo un registro")
	void probarTurnoPendienteConSoloUnJugadorRegistrado() {
		// Inicializamos
		assertNull(arbitro.obtenerTurno(), "El turno debe ser null hasta que no se registren dos jugadores");
		arbitro.registrarJugador("Juan");
		assertNull(arbitro.obtenerTurno(), "El turno debe ser null hasta que no se registre el segundo jugador");
	}

	/**
	 * Comprueba el estado inicial de un árbitro justo después de instanciar. El
	 * árbitro debe tener un tablero vacío asignado, y no puede tener turno
	 * asignado, ni estar acabado o existir ya un ganador.
	 */
	@Test
	@DisplayName("Probar el estado inicial de un árbitro antes de empezar a registrar jugadores")
	void probarEstadoInicial() {
		assertAll(() -> assertNull(arbitro.obtenerGanador(), "No puede haber un ganador al comenzar una partida"),
				() -> assertNotNull(arbitro.obtenerTablero(), "Un árbitro se debe inicializar sin tablero"),
				() -> assertThat("No puede estar acabada una partida recién iniciada", arbitro.estaAcabado(),
						is(false)),
				() -> assertNull(arbitro.obtenerTurno(), "No hay turno si no se ha comenzado a registrar jugadores"));
	}

	/**
	 * Prueba la legalidad de colocar en todas las posiciones de un tablero vacío.
	 */
	@Test
	@DisplayName("Probar la legalidad de colocar en tablero vacío")
	void probarLegalidadMovimientosEnTableroVacio() {
		System.out.println("filas "+" columna ");
		Tablero tablero = arbitro.obtenerTablero();
		System.out.println("filas " );
		for (int fila = 0; fila < tablero.obtenerNumeroFilas(); fila++) {
			System.out.println ("for");
			for (int columna = 0; columna < tablero.obtenerNumeroColumnas(); columna++) {
				System.out.println("filas " + fila +" columna " + columna);
				assertTrue(arbitro.esMovimientoLegal(fila, columna),
						"Colocar en una celda vacía debería ser siempre legal");
			}
		}
	}

	/**
	 * Prueba la legalida de colocar en una celda del tablero cambiado el turno a
	 * continuación.
	 */
	@Test
	@DisplayName("Probar la legalidad de colocar cambiando el turno a continuación")
	void probarMovimientoLegalCambiandoTurno() {
		registrarJugadores();
		Jugador conTurno = arbitro.obtenerTurno();
		arbitro.jugar(0, 0);
		// Debe cambiar el turno al realizar un movimiento legal...
		Jugador siguienteTurno = arbitro.obtenerTurno();
		assertAll(() -> assertThat("No se ha cambiado el turno", siguienteTurno, not(is(conTurno))),
				() -> assertFalse(arbitro.esMovimientoLegal(0, 0), "No es legal colocar en la misma posición"));

	}

	/**
	 * Registra un par de jugadores.
	 */
	private void registrarJugadores() {
		arbitro.registrarJugador("Juan");
		arbitro.registrarJugador("Pepe");
	}

	/**
	 * Comprueba una partida finalizada con tablas y sin ganador.
	 * 
	 * <pre>
	 * OX-
	 * -OX
	 * --O
	 * </pre>
	 */
	@Test
	@DisplayName("Comprobar partida con tablas")
	void probarPartidaConTablas() {
		final int[][] movimientos = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 2, 0 }, { 1, 1 }, { 2, 2 }, { 0, 2 }, { 1, 2 },
				{ 2, 1 } };
		registrarYRealizarMovimientos(movimientos);
		assertAll(() -> assertThat(arbitro.estaAcabado(), is(true)), () -> assertNull(arbitro.obtenerGanador()));

	}

	/**
	 * Comprueba una partida finalizada con victoria en diagonal NO_SE.
	 * 
	 * Suponiendo que empiezan blancas (aunque dado que el inicio es aleatorio, con negras
	 * debería ser exactamente equivalente).
	 * 
	 * <pre>
	 * OX-
	 * -OX
	 * --O
	 * </pre>
	 */
	@Test
	@DisplayName("Comprobar partida con victoria en diagonal en NO_SE")
	void probarPartidaConVictoriaEnDiagonalNOSE() {
		final int[][] MOVIMIENTOS = { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 2 }, { 2, 2 } };
		registrarYRealizarMovimientos(MOVIMIENTOS);
		assertAll(() -> assertThat(arbitro.estaAcabado(), is(true)), () -> assertNotNull(arbitro.obtenerGanador()));

	}

	/**
	 * Comprueba una partida finalizada con victoria en diagonal SO_NE.
	 * 
	 * Suponiendo que empiezan blancas (aunque dado que el inicio es aleatorio, con negras
	 * sería exactamente equivalente).
	 * 
	 * <pre>
	 * -XO
	 * XO-
	 * O--
	 * </pre>
	 */
	@Test
	@DisplayName("Comprobar partida con victoria en diagonal en SO_NE")
	void probarPartidaConVictoriaEnDiagonalSONE() {
		final int[][] movimientos = { { 0, 2 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 2, 0 } };
		registrarYRealizarMovimientos(movimientos);
		assertAll(() -> assertThat(arbitro.estaAcabado(), is(true)), () -> assertNotNull(arbitro.obtenerGanador()));
	}

	/**
	 * Comprueba una partida finalizada con victoria de blancas en vertical en medio del
	 * tablero.
	 * 
	 * Suponiendo que empiezan blancas (aunque el inicio es aleatorio, con negras
	 * sería exactamente equivalente).
	 * 
	 * <pre>
	 * -OX
	 * XO-
	 * -O--
	 * </pre>
	 */
	@Test
	@DisplayName("Comprobar partida con victoria en vertical")
	void probarPartidaConVictoriaEnVerticalEnMedioDelTablero() {
		final int[][] movimientos = { { 0, 1 }, { 0, 2 }, { 1, 1 }, { 1, 0 }, { 2, 1 } };
		registrarYRealizarMovimientos(movimientos);
		assertAll(() -> assertThat(arbitro.estaAcabado(), is(true)), () -> assertNotNull(arbitro.obtenerGanador()));
	}

	/**
	 * Comprueba una partida finalizada con victoria en horizontal en medio del
	 * tablero.
	 * 
	 * Suponiendo que empiezan blancas (aunque dado que el inicio es aleatorio, con negras
	 * sería exactamente equivalente).
	 * 
	 * <pre>
	 * -X-
	 * OOO
	 * X--
	 * </pre>
	 */
	@Test
	@DisplayName("Comprobar partida con victoria en horizontal")
	void probarPartidaConVictoriaEnHorizontalEnMedioDelTablero() {
		final int[][] movimientos = { { 1, 0 }, { 0, 1 }, { 1, 1 }, { 2, 0 }, { 1, 2 } };
		registrarYRealizarMovimientos(movimientos);
		assertAll(() -> assertThat(arbitro.estaAcabado(), is(true)), () -> assertNotNull(arbitro.obtenerGanador()));
	}

	/**
	 * Registra jugadores y realiza una serie de movimientos.
	 * 
	 * @param movimientos movimientos
	 */
	private void registrarYRealizarMovimientos(final int[][] movimientos) {
		registrarJugadores();
		for (int[] movimiento : movimientos) {
			arbitro.jugar(movimiento[0], movimiento[1]);
		}
	}

}

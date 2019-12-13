package juego.modelo;

import static juego.modelo.Color.NEGRO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import juego.modelo.pieza.Alfil;
import juego.modelo.pieza.Caballo;
import juego.modelo.pieza.Dama;
import juego.modelo.pieza.Peon;
import juego.modelo.pieza.Pieza;
import juego.modelo.pieza.Rey;
import juego.modelo.pieza.Torre;
import juego.util.Sentido;

/**
 * Pruebas unitarias sobre la pieza.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20181008
 * 
 */
@DisplayName("Tests sobre Pieza")
public class PiezaTest {

	/**
	 * Test del constructor.
	 */
	@DisplayName("Constructor con estado inicial correcto")
	@Test
	void constructor() {
		Pieza pieza = new Peon(Color.BLANCO);
		assertAll(() -> assertThat("Color mal inicializado", pieza.obtenerColor(), is(Color.BLANCO)),
				() -> assertNull(pieza.obtenerCelda(), "La pieza inicialmente no debe estar en una celda."));
	}

	/**
	 * Coloca la pieza en la celda.
	 */
	@DisplayName("Coloca la pieza en la celda")
	@Test
	void colocarEnCelda() {
		Pieza pieza = new Dama(Color.BLANCO);
		Celda celda = new Celda(0, 0);
		pieza.establecerCelda(celda);
		assertAll(() -> assertThat("Pieza mal asociada a celda", pieza.obtenerCelda(), is(celda)),
				() -> assertThat("Celda no debería estar asociada a la pieza", celda.obtenerPieza(), is(nullValue())));
	}

	/**
	 * Prueba del método toString.
	 */
	@DisplayName("Formato de texto")
	@Test
	void probarToString() {
		Pieza pieza = new Peon(Color.BLANCO);
		assertThat("Texto mal construido o estado incorrecto", pieza.toString().replaceAll("\\s", ""),
				is('P' + "-" + Color.BLANCO + "-true"));

		pieza = new Peon(Color.NEGRO);
		assertThat("Texto mal construido o estado incorrecto", pieza.toString().replaceAll("\\s", ""),
				is('P' + "-" + Color.NEGRO + "-true"));

		Celda celda = new Celda(3, 4);
		pieza = new Dama(Color.BLANCO);
		pieza.establecerCelda(celda);
		assertThat("Texto mal construido o estado incorrecto", pieza.toString().replaceAll("\\s", ""),
				is('D' + "-" + Color.BLANCO + "-true"));
	}

	/**
	 * Comprueba que al marcar el movimiento cambia de estado.
	 */
	@DisplayName("Cambio de estado al marcar el movimiento")
	@Test
	void probarCambioEstadoMovimiento() {
		Pieza pieza = new Peon(Color.BLANCO);
		pieza.marcarPrimerMovimiento();
		assertThat("No cambia de estado", pieza.esPrimerMovimiento(), is(false));
		assertThat("Texto mal construido o estado incorrecto tras realizar primer movimiento",
				pieza.toString().replaceAll("\\s", ""), is('P' + "-" + Color.BLANCO + "-false"));
	}

	/**
	 * Comprueba movimientos básicos de un peón blanco.
	 */
	@DisplayName("Comprobar movimientos de un peón blanco")
	@Test
	void comprobarMovimientosDelPeonBlanco() {
		Pieza pieza = new Peon(Color.BLANCO);
		pieza.establecerCelda(new Celda(6, 0));
		assertAll("movimientosPeon",
				() -> assertThat("Movimiento simple de peón",
						pieza.esCorrectoMoverA(new Celda(5, 0), Sentido.VERTICAL_N, false), is(true)),
				() -> assertThat("Movimiento doble de peón",
						pieza.esCorrectoMoverA(new Celda(4, 0), Sentido.VERTICAL_N, false), is(true)),
				() -> assertThat("Movimiento doble de peón con pieza entre medias",
						pieza.esCorrectoMoverA(new Celda(4, 0), Sentido.VERTICAL_N, true), is(false)),
				() -> assertThat("Movimiento inválido de peón",
						pieza.esCorrectoMoverA(new Celda(3, 0), Sentido.VERTICAL_N, false), is(false)),
				() -> assertThat("Movimiento inválido de peón",
						pieza.esCorrectoMoverA(new Celda(5, 1), Sentido.VERTICAL_N, false), is(false)));
	}
	
	/**
	 * Comprueba movimientos básicos de un peón negro.
	 */
	@DisplayName("Comprobar movimientos de un peón negro")
	@Test
	void comprobarMovimientosDelPeonNegro() {
		Pieza pieza = new Peon(Color.NEGRO);
		pieza.establecerCelda(new Celda(1, 0));
		assertAll("movimientosPeon",
				() -> assertThat("Movimiento simple de peón",
						pieza.esCorrectoMoverA(new Celda(2, 0), Sentido.VERTICAL_S, false), is(true)),
				() -> assertThat("Movimiento doble de peón",
						pieza.esCorrectoMoverA(new Celda(3, 0), Sentido.VERTICAL_S, false), is(true)),
				() -> assertThat("Movimiento doble de peón con pieza entre medias",
						pieza.esCorrectoMoverA(new Celda(3, 0), Sentido.VERTICAL_S, true), is(false)),
				() -> assertThat("Movimiento inválido de peón",
						pieza.esCorrectoMoverA(new Celda(4, 0), Sentido.VERTICAL_S, false), is(false)),
				() -> assertThat("Movimiento inválido de peón",
						pieza.esCorrectoMoverA(new Celda(2, 1), Sentido.VERTICAL_N, false), is(false)));
	}
	
	/**
	 * Comprueba movimientos básicos de un alfil en mitad del tablero.
	 */
	@DisplayName("Comprobar movimientos de un alfil en mitad del tablero")
	@Test
	void comprobarMovimientosDelAlfil() {
		Pieza pieza = new Alfil(Color.BLANCO);
		pieza.establecerCelda(new Celda(3, 3));
		assertAll("movimientosAlfil",
				() -> assertThat("Movimiento simple de alfil en DIAGONAL_NO sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 0), Sentido.DIAGONAL_NO, false), is(true)),
				() -> assertThat("Movimiento simple de alfil en DIAGONAL_NE sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 6), Sentido.DIAGONAL_NE, false), is(true)),
				() -> assertThat("Movimiento simple de alfil en DIAGONAL_SO sin piezas",
						pieza.esCorrectoMoverA(new Celda(6, 0), Sentido.DIAGONAL_SO, false), is(true)),
				() -> assertThat("Movimiento simple de alfil en DIAGONAL_SE sin piezas",
						pieza.esCorrectoMoverA(new Celda(7, 7), Sentido.DIAGONAL_SE, false), is(true)),
				() -> assertThat("Movimiento simple de alfil en DIAGONAL_NO sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 0), Sentido.DIAGONAL_NO, true), is(false)),
				() -> assertThat("Movimiento simple de alfil en DIAGONAL_NE sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 6), Sentido.DIAGONAL_NE, true), is(false)),
				() -> assertThat("Movimiento simple de alfil en DIAGONAL_SO sin piezas",
						pieza.esCorrectoMoverA(new Celda(6, 0), Sentido.DIAGONAL_SO, true), is(false)),
				() -> assertThat("Movimiento simple de alfil en DIAGONAL_SE sin piezas",
						pieza.esCorrectoMoverA(new Celda(7, 7), Sentido.DIAGONAL_SE, true), is(false))
				);
	}
	
	/**
	 * Comprueba movimientos inválidos de un alfil en mitad del tablero.
	 */
	@DisplayName("Comprobar movimientos inválidos de un alfil en mitad del tablero")
	@Test
	void comprobarMovimientosInvalidosDelAlfil() {
		Pieza pieza = new Alfil(Color.BLANCO);
		pieza.establecerCelda(new Celda(3, 3));
		assertAll("movimientosInvalidosAlfil",
				() -> assertThat("Movimiento en HORIZONTAL_E",
						pieza.esCorrectoMoverA(new Celda(3, 7), Sentido.HORIZONTAL_E, false), is(false)),
				() -> assertThat("Movimiento en HORIZONTAL_O",
						pieza.esCorrectoMoverA(new Celda(3, 0), Sentido.HORIZONTAL_O, false), is(false)),
				() -> assertThat("Movimiento en VERTICAL_N",
						pieza.esCorrectoMoverA(new Celda(0, 3), Sentido.VERTICAL_N, false), is(false)),
				() -> assertThat("Movimiento en VERTICAL_S",
						pieza.esCorrectoMoverA(new Celda(7, 3), Sentido.VERTICAL_S, false), is(false)),
				() -> assertThat("Movimiento en salto de caballo",
						pieza.esCorrectoMoverA(new Celda(2, 5), null, false), is(false))
				);
	}
	
	/**
	 * Comprueba movimientos básicos de una torre en mitad del tablero.
	 */
	@DisplayName("Comprobar movimientos de una torre en mitad del tablero")
	@Test
	void comprobarMovimientosDeTorre() {
		Pieza pieza = new Torre(Color.BLANCO);
		pieza.establecerCelda(new Celda(3, 3));
		assertAll("movimientosTorre",
				() -> assertThat("Movimiento simple de torre en HORIZONTAL_O sin piezas",
						pieza.esCorrectoMoverA(new Celda(3, 0), Sentido.HORIZONTAL_O, false), is(true)),
				() -> assertThat("Movimiento simple de torre en HORIZONTAL_E sin piezas",
						pieza.esCorrectoMoverA(new Celda(3, 7), Sentido.HORIZONTAL_E, false), is(true)),
				() -> assertThat("Movimiento simple de tore en VERTICAL_N sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 3), Sentido.VERTICAL_N, false), is(true)),
				() -> assertThat("Movimiento simple de torre en VERTICAL_S sin piezas",
						pieza.esCorrectoMoverA(new Celda(7, 3), Sentido.VERTICAL_S, false), is(true)),
				() -> assertThat("Movimiento simple de torre en HORIZONTAL_O con piezas",
						pieza.esCorrectoMoverA(new Celda(3, 0), Sentido.HORIZONTAL_O, true), is(false)),
				() -> assertThat("Movimiento simple de torre en HORIZONTAL_E con piezas",
						pieza.esCorrectoMoverA(new Celda(3, 7), Sentido.HORIZONTAL_E, true), is(false)),
				() -> assertThat("Movimiento simple de tore en VERTICAL_N con piezas",
						pieza.esCorrectoMoverA(new Celda(0, 3), Sentido.VERTICAL_N, true), is(false)),
				() -> assertThat("Movimiento simple de torre en VERTICAL_S con piezas",
						pieza.esCorrectoMoverA(new Celda(7, 3), Sentido.VERTICAL_S, true), is(false))
				);
	}
	
	/**
	 * Comprueba movimientos inválidos de una torre en mitad del tablero.
	 */
	@DisplayName("Comprobar movimientos inválidos de una torre en mitad del tablero")
	@Test
	void comprobarMovimientosInvalidosDeTorre() {
		Pieza pieza = new Torre(Color.BLANCO);
		pieza.establecerCelda(new Celda(3, 3));
		assertAll("movimientosInvalidosTorre",
				() -> assertThat("Movimiento en DIAGONAL_NE",
						pieza.esCorrectoMoverA(new Celda(3, 7), Sentido.DIAGONAL_NE, false), is(false)),
				() -> assertThat("Movimiento en DIAGONAL_NO",
						pieza.esCorrectoMoverA(new Celda(3, 0), Sentido.DIAGONAL_NO, false), is(false)),
				() -> assertThat("Movimiento en DIAGONAL_SE",
						pieza.esCorrectoMoverA(new Celda(0, 3), Sentido.DIAGONAL_SE, false), is(false)),
				() -> assertThat("Movimiento en DIAGONAL_SO",
						pieza.esCorrectoMoverA(new Celda(7, 3), Sentido.DIAGONAL_SO, false), is(false)),
				() -> assertThat("Movimiento en salto de caballo",
						pieza.esCorrectoMoverA(new Celda(2, 5), null, false), is(false))
				);
	}
	
	/**
	 * Comprueba movimientos básicos de una dama en mitad del tablero.
	 */
	@DisplayName("Comprobar movimientos de una dama en mitad del tablero")
	@Test
	void comprobarMovimientosDeDama() {
		Pieza pieza = new Dama(Color.BLANCO);
		pieza.establecerCelda(new Celda(3, 3));
		assertAll("movimientosDama",
				() -> assertThat("Movimiento simple de dama en HORIZONTAL_O sin piezas",
						pieza.esCorrectoMoverA(new Celda(3, 0), Sentido.HORIZONTAL_O, false), is(true)),
				() -> assertThat("Movimiento simple de dama en HORIZONTAL_E sin piezas",
						pieza.esCorrectoMoverA(new Celda(3, 7), Sentido.HORIZONTAL_E, false), is(true)),
				() -> assertThat("Movimiento simple de dama en VERTICAL_N sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 3), Sentido.VERTICAL_N, false), is(true)),
				() -> assertThat("Movimiento simple de dama en VERTICAL_S sin piezas",
						pieza.esCorrectoMoverA(new Celda(7, 3), Sentido.VERTICAL_S, false), is(true)),
				() -> assertThat("Movimiento simple de dama en DIAGONAL_NO sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 0), Sentido.DIAGONAL_NO, false), is(true)),
				() -> assertThat("Movimiento simple de dama en DIAGONAL_NE sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 6), Sentido.DIAGONAL_NE, false), is(true)),
				() -> assertThat("Movimiento simple de dama en DIAGONAL_SO sin piezas",
						pieza.esCorrectoMoverA(new Celda(6, 0), Sentido.DIAGONAL_SO, false), is(true)),
				() -> assertThat("Movimiento simple de dama en DIAGONAL_SE sin piezas",
						pieza.esCorrectoMoverA(new Celda(7, 7), Sentido.DIAGONAL_SE, false), is(true)),	
				
				// movimientos con piezas
				() -> assertThat("Movimiento simple de dama en HORIZONTAL_O con piezas",
						pieza.esCorrectoMoverA(new Celda(3, 0), Sentido.HORIZONTAL_O, true), is(false)),
				() -> assertThat("Movimiento simple de dama en HORIZONTAL_E con piezas",
						pieza.esCorrectoMoverA(new Celda(3, 7), Sentido.HORIZONTAL_E, true), is(false)),
				() -> assertThat("Movimiento simple de dama en VERTICAL_N con piezas",
						pieza.esCorrectoMoverA(new Celda(0, 3), Sentido.VERTICAL_N, true), is(false)),
				() -> assertThat("Movimiento simple de dama en VERTICAL_S con piezas",
						pieza.esCorrectoMoverA(new Celda(7, 3), Sentido.VERTICAL_S, true), is(false)),
				() -> assertThat("Movimiento simple de dama en DIAGONAL_NO sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 0), Sentido.DIAGONAL_NO, true), is(false)),
				() -> assertThat("Movimiento simple de dama en DIAGONAL_NE sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 6), Sentido.DIAGONAL_NE, true), is(false)),
				() -> assertThat("Movimiento simple de dama en DIAGONAL_SO sin piezas",
						pieza.esCorrectoMoverA(new Celda(6, 0), Sentido.DIAGONAL_SO, true), is(false)),
				() -> assertThat("Movimiento simple de dama en DIAGONAL_SE sin piezas",
						pieza.esCorrectoMoverA(new Celda(7, 7), Sentido.DIAGONAL_SE, true), is(false))
				);
	}
	
	/**
	 * Comprueba movimientos inválidos de una dama en mitad del tablero.
	 */
	@DisplayName("Comprobar movimientos inválidos de una dama en mitad del tablero")
	@Test
	void comprobarMovimientosInvalidosDeDama() {
		Pieza pieza = new Dama(Color.BLANCO);
		pieza.establecerCelda(new Celda(3, 3));
		assertAll("movimientosInvalidosDama",
				() -> assertThat("Movimiento en salto de caballo",
						pieza.esCorrectoMoverA(new Celda(2, 5), null, false), is(false))
				);
	}
	
	/**
	 * Comprueba movimientos de un caballo en mitad del tablero.
	 */
	@DisplayName("Comprobar movimientos de una caballo en mitad del tablero")
	@Test
	void comprobarMovimientosDeCaballo() {
		Pieza pieza = new Caballo(Color.BLANCO);
		pieza.establecerCelda(new Celda(3, 3));
		assertAll("movimientosCaballo",
				() -> assertThat("Movimiento en salto de caballo NE",
						pieza.esCorrectoMoverA(new Celda(1, 4), null, false), is(true)),
				() -> assertThat("Movimiento en salto de caballo NE",
						pieza.esCorrectoMoverA(new Celda(2, 5), null, false), is(true)),
				() -> assertThat("Movimiento en salto de caballo NO",
						pieza.esCorrectoMoverA(new Celda(1, 2), null, false), is(true)),
				() -> assertThat("Movimiento en salto de caballo NO",
						pieza.esCorrectoMoverA(new Celda(2, 1), null, false), is(true)),
				
				() -> assertThat("Movimiento en salto de caballo SE",
						pieza.esCorrectoMoverA(new Celda(4, 5), null, false), is(true)),
				() -> assertThat("Movimiento en salto de caballo SE",
						pieza.esCorrectoMoverA(new Celda(5, 4), null, false), is(true)),
				() -> assertThat("Movimiento en salto de caballo NE",
						pieza.esCorrectoMoverA(new Celda(4, 1), null, false), is(true)),
				() -> assertThat("Movimiento en salto de caballo NE",
						pieza.esCorrectoMoverA(new Celda(5, 2), null, false), is(true))
				);
	}
	
	
	/**
	 * Comprueba movimientos inválidos de un caballo en mitad del tablero.
	 */
	@DisplayName("Comprobar movimientos inválidos de un caballo en mitad del tablero")
	@Test
	void comprobarMovimientosInvalidosDeCaballo() {
		Pieza pieza = new Caballo(Color.BLANCO);
		pieza.establecerCelda(new Celda(3, 3));
		assertAll("movimientosInvalidosCaballo",
				() -> assertThat("Movimiento simple de caballo en HORIZONTAL_O sin piezas",
						pieza.esCorrectoMoverA(new Celda(3, 0), Sentido.HORIZONTAL_O, false), is(false)),
				() -> assertThat("Movimiento simple de caballo en HORIZONTAL_E sin piezas",
						pieza.esCorrectoMoverA(new Celda(3, 7), Sentido.HORIZONTAL_E, false), is(false)),
				() -> assertThat("Movimiento simple de caballo en VERTICAL_N sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 3), Sentido.VERTICAL_N, false), is(false)),
				() -> assertThat("Movimiento simple de caballo en VERTICAL_S sin piezas",
						pieza.esCorrectoMoverA(new Celda(7, 3), Sentido.VERTICAL_S, false), is(false)),
				() -> assertThat("Movimiento simple de caballo en DIAGONAL_NO sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 0), Sentido.DIAGONAL_NO, false), is(false)),
				() -> assertThat("Movimiento simple de caballo en DIAGONAL_NE sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 6), Sentido.DIAGONAL_NE, false), is(false)),
				() -> assertThat("Movimiento simple de caballo en DIAGONAL_SO sin piezas",
						pieza.esCorrectoMoverA(new Celda(6, 0), Sentido.DIAGONAL_SO, false), is(false)),
				() -> assertThat("Movimiento simple de caballo en DIAGONAL_SE sin piezas",
						pieza.esCorrectoMoverA(new Celda(7, 7), Sentido.DIAGONAL_SE, false), is(false)),	
				
				// movimientos con piezas
				() -> assertThat("Movimiento simple de caballo en HORIZONTAL_O con piezas",
						pieza.esCorrectoMoverA(new Celda(3, 0), Sentido.HORIZONTAL_O, true), is(false)),
				() -> assertThat("Movimiento simple de caballo en HORIZONTAL_E con piezas",
						pieza.esCorrectoMoverA(new Celda(3, 7), Sentido.HORIZONTAL_E, true), is(false)),
				() -> assertThat("Movimiento simple de caballo en VERTICAL_N con piezas",
						pieza.esCorrectoMoverA(new Celda(0, 3), Sentido.VERTICAL_N, true), is(false)),
				() -> assertThat("Movimiento simple de caballo en VERTICAL_S con piezas",
						pieza.esCorrectoMoverA(new Celda(7, 3), Sentido.VERTICAL_S, true), is(false)),
				() -> assertThat("Movimiento simple de caballo en DIAGONAL_NO sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 0), Sentido.DIAGONAL_NO, true), is(false)),
				() -> assertThat("Movimiento simple de caballo en DIAGONAL_NE sin piezas",
						pieza.esCorrectoMoverA(new Celda(0, 6), Sentido.DIAGONAL_NE, true), is(false)),
				() -> assertThat("Movimiento simple de dama en DIAGONAL_SO sin piezas",
						pieza.esCorrectoMoverA(new Celda(6, 0), Sentido.DIAGONAL_SO, true), is(false)),
				() -> assertThat("Movimiento simple de dama en DIAGONAL_SE sin piezas",
						pieza.esCorrectoMoverA(new Celda(7, 7), Sentido.DIAGONAL_SE, true), is(false))
				);
	}
	
	/**
	 * Comprueba movimientos de un rey en mitad del tablero.
	 */
	@DisplayName("Comprobar movimientos de un rey en mitad del tablero")
	@Test
	void comprobarMovimientosDeRey() {
		Pieza pieza = new Rey(Color.BLANCO);
		pieza.establecerCelda(new Celda(3, 3));
		assertAll("movimientosRey",
				() -> assertThat("Movimiento de rey en DIAGONAL_NE",
						pieza.esCorrectoMoverA(new Celda(2, 4), Sentido.DIAGONAL_NE, false), is(true)),
				() -> assertThat("Movimiento de rey en DIAGONAL_NO",
						pieza.esCorrectoMoverA(new Celda(2, 2), Sentido.DIAGONAL_NO, false), is(true)),
				() -> assertThat("Movimiento de rey en DIAGONAL_SO",
						pieza.esCorrectoMoverA(new Celda(4, 2), Sentido.DIAGONAL_SO, false), is(true)),
				() -> assertThat("Movimiento de rey en DIAGONAL_SE",
						pieza.esCorrectoMoverA(new Celda(4, 4), Sentido.DIAGONAL_SE, false), is(true)),
				//
				() -> assertThat("Movimiento de rey en HORIZONTAL_E",
						pieza.esCorrectoMoverA(new Celda(3, 4), Sentido.HORIZONTAL_E, false), is(true)),
				() -> assertThat("Movimiento de rey en HORIZONTAL_O",
						pieza.esCorrectoMoverA(new Celda(3, 2), Sentido.HORIZONTAL_O, false), is(true)),
				() -> assertThat("Movimiento de rey en VERTICAL_N",
						pieza.esCorrectoMoverA(new Celda(2, 3), Sentido.VERTICAL_N, false), is(true)),
				() -> assertThat("Movimiento de rey en VERTICAL_S",
						pieza.esCorrectoMoverA(new Celda(4, 3), Sentido.VERTICAL_S, false), is(true))				
				
				);
	}
	
	/**
	 * Comprueba movimientos inválidos de un rey en mitad del tablero.
	 */
	@DisplayName("Comprobar movimientos inválidos de un rey en mitad del tablero")
	@Test
	void comprobarMovimientosInvalidosDeRey() {
		Pieza pieza = new Rey(Color.BLANCO);
		pieza.establecerCelda(new Celda(3, 3));
		assertAll("movimientosInvalidosRey",
				() -> assertThat("Movimiento de rey en DIAGONAL_NE",
						pieza.esCorrectoMoverA(new Celda(1, 5), Sentido.DIAGONAL_NE, false), is(false)),
				() -> assertThat("Movimiento de rey en DIAGONAL_NO",
						pieza.esCorrectoMoverA(new Celda(1, 1), Sentido.DIAGONAL_NO, false), is(false)),
				() -> assertThat("Movimiento de rey en DIAGONAL_SO",
						pieza.esCorrectoMoverA(new Celda(5, 1), Sentido.DIAGONAL_SO, false), is(false)),
				() -> assertThat("Movimiento de rey en DIAGONAL_SE",
						pieza.esCorrectoMoverA(new Celda(5, 5), Sentido.DIAGONAL_SE, false), is(false)),
				//
				() -> assertThat("Movimiento de rey en HORIZONTAL_E",
						pieza.esCorrectoMoverA(new Celda(3, 5), Sentido.HORIZONTAL_E, false), is(false)),
				() -> assertThat("Movimiento de rey en HORIZONTAL_O",
						pieza.esCorrectoMoverA(new Celda(3, 1), Sentido.HORIZONTAL_O, false), is(false)),
				() -> assertThat("Movimiento de rey en VERTICAL_N",
						pieza.esCorrectoMoverA(new Celda(1, 3), Sentido.VERTICAL_N, false), is(false)),
				() -> assertThat("Movimiento de rey en VERTICAL_S",
						pieza.esCorrectoMoverA(new Celda(5, 3), Sentido.VERTICAL_S, false), is(false)),			
				//
				() -> assertThat("Movimiento en salto de caballo NE",
						pieza.esCorrectoMoverA(new Celda(5, 2), null, false), is(false))
				);
	}
	
	/**
	 * Comprobar el método toChar.
	 */
	@DisplayName("Comprobar el método toChar")
	@Test
	void probarToChar() {
		assertAll("toChar",
				() -> assertThat("Letra de peón incorrecta.", new Peon(NEGRO).toChar(), is('P')),
				() -> assertThat("Letra de torre incorrecta.", new Torre(NEGRO).toChar(), is('T')),
				() -> assertThat("Letra de caballo incorrecta.", new Caballo(NEGRO).toChar(), is('C')),
				() -> assertThat("Letra de alfil incorrecta.", new Alfil(NEGRO).toChar(), is('A')),
				() -> assertThat("Letra de dama incorrecta.", new Dama(NEGRO).toChar(), is('D')),
				() -> assertThat("Letra de rey incorrecta.", new Rey(NEGRO).toChar(), is('R'))
				);
	}
	

}

package juego.control;

import static juego.modelo.Color.BLANCO;
import static juego.modelo.Color.NEGRO;
import static juego.modelo.Tipo.ALFIL;
import static juego.modelo.Tipo.CABALLO;
import static juego.modelo.Tipo.DAMA;
import static juego.modelo.Tipo.PEON;
import static juego.modelo.Tipo.REY;
import static juego.modelo.Tipo.TORRE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import juego.modelo.Celda;
import juego.modelo.Pieza;
import juego.modelo.Tablero;

/**
 * Pruebas unitarias sobre el estado inicial del árbitro una vez colocadas las
 * piezas y antes de iniciar la partida.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20190813
 */
@DisplayName("Test sobre el estado inicial una vez colocadas las piezas.")
public class ArbitroColocacionPiezasTest {

	/** Arbitro. */
	private Arbitro arbitro;

	/** Tablero. */
	private Tablero tablero;

	/**
	 * Proveedor de piezas negras en fila superior.
	 * 
	 * @return coordenadas y piezas en fila superior de piezas negras
	 */
	static Stream<Arguments> coordenadasAndPiezaNegraProvider() {
		return Stream.of(arguments(0, 0, new Pieza(TORRE, NEGRO)), arguments(0, 1, new Pieza(CABALLO, NEGRO)),
				arguments(0, 2, new Pieza(ALFIL, NEGRO)), arguments(0, 3, new Pieza(DAMA, NEGRO)),
				arguments(0, 4, new Pieza(REY, NEGRO)), arguments(0, 5, new Pieza(ALFIL, NEGRO)),
				arguments(0, 6, new Pieza(CABALLO, NEGRO)), arguments(0, 7, new Pieza(TORRE, NEGRO)));
	}

	/**
	 * Proveedor de piezas blancas en fila inferior.
	 * 
	 * @return coordenadas y piezas en fila inferior de piezas blancas
	 */
	static Stream<Arguments> coordenadasAndPiezaBlancaProvider() {
		return Stream.of(arguments(7, 0, new Pieza(TORRE, BLANCO)), arguments(7, 1, new Pieza(CABALLO, BLANCO)),
				arguments(7, 2, new Pieza(ALFIL, BLANCO)), arguments(7, 3, new Pieza(DAMA, BLANCO)),
				arguments(7, 4, new Pieza(REY, BLANCO)), arguments(7, 5, new Pieza(ALFIL, BLANCO)),
				arguments(7, 6, new Pieza(CABALLO, BLANCO)), arguments(7, 7, new Pieza(TORRE, BLANCO)));
	}

	/**
	 * Inicialización de piezas nuevas sobre el tablero.
	 */
	@BeforeEach
	void colocarPiezas() {
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		arbitro.colocarPiezas();
	}

	/**
	 * Comprueba que se colocan 8 peones negros en la parte superior del tablero.
	 * 
	 * @param fila    fila
	 * @param columna columna
	 */
	//  @formatter:off
	/* Partimos de un tablero que debe tener este estado.
	*   a  b  c  d  e  f  g  h  
	* 8 TN CN AN DN RN AN CN TN 
	* 7 PN PN PN PN PN PN PN PN
	* 6 -- -- -- -- -- -- -- --
	* 5 -- -- -- -- -- -- -- --
	* 4 -- -- -- -- -- -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 PB PB PB PB PB PB PB PB 
	* 1 TB CB AB DB RB AB CB TB
	*/
	// @formatter:on
	@DisplayName("Comprobar peones negros en la segunda fila")
	@ParameterizedTest
	@CsvSource({ "1,1", "1,2", "1,3", "1,4", "1,5", "1,6", "1,7" })
	void comprobarColocacionInicialDePeonesNegros(int fila, int columna) {
		Celda celda = tablero.obtenerCelda(fila, columna);
		assertThat("Celda vacia", celda.estaVacia(), is(false));
		assertThat("Color de pieza colocada incorrecta", celda.obtenerColorDePieza(), is(NEGRO));
		assertThat("Tipo de pieza incorrecto", celda.obtenerPieza().obtenerTipo(), is(PEON));
	}

	/**
	 * Comprueba que se colocan 8 piezas negras en la parte superior del tablero.
	 * 
	 * @param fila    	fila
	 * @param columna 	columna
	 * @param pieza 	pieza
	 */
	//  @formatter:off
	/* Partimos de un tablero que debe tener este estado.
	*   a  b  c  d  e  f  g  h  
	* 8 TN CN AN DN RN AN CN TN 
	* 7 PN PN PN PN PN PN PN PN
	* 6 -- -- -- -- -- -- -- --
	* 5 -- -- -- -- -- -- -- --
	* 4 -- -- -- -- -- -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 PB PB PB PB PB PB PB PB 
	* 1 TB CB AB DB RB AB CB TB
	*/
	// @formatter:on
	@DisplayName("Comprobar piezas negras en la primera fila")
	@ParameterizedTest
	@MethodSource("coordenadasAndPiezaNegraProvider")
	void comprobarPiezasPrimeraFila(int fila, int columna, Pieza pieza) {
		Celda celda = tablero.obtenerCelda(fila, columna);
		assertThat("Celda vacia", celda.estaVacia(), is(false));
		assertThat("Color de pieza colocada incorrecta", celda.obtenerColorDePieza(), is(NEGRO));
		assertThat("Tipo de pieza incorrecto", celda.obtenerPieza().obtenerTipo(), is(pieza.obtenerTipo()));
	}

	/**
	 * Comprueba que se colocan 8 peones blancos en la parte inferior del tablero.
	 *
	 * @param fila    fila
	 * @param columna columna
	 */
	// @formatter:off
	/* Partimos de un tablero que debe tener este estado.
	*   a  b  c  d  e  f  g  h  
	* 8 TN CN AN DN RN AN CN TN 
	* 7 PN PN PN PN PN PN PN PN
	* 6 -- -- -- -- -- -- -- --
	* 5 -- -- -- -- -- -- -- --
	* 4 -- -- -- -- -- -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 PB PB PB PB PB PB PB PB 
	* 1 TB CB AB DB RB AB CB TB
	*/
	// @formatter:on
	@DisplayName("Comprobar peones blancos en la penúltima fila")
	@ParameterizedTest
	@CsvSource({ "6,0", "6,1", "6,2", "6,3", "6,4", "6,5", "6,6", "6,7" })
	void comprobarColocacionInicialDePeonesBlancos(int fila, int columna) {
		Celda celda = tablero.obtenerCelda(fila, columna);
		assertThat("Celda vacia", celda.estaVacia(), is(false));
		assertThat("Color de pieza colocada incorrecta", celda.obtenerColorDePieza(), is(BLANCO));
		assertThat("Tipo de pieza incorrecto", celda.obtenerPieza().obtenerTipo(), is(PEON));
	}

	/**
	 * Comprueba que se colocan 8 piezas blancas en la parte inferior del tablero.
	 * 
	 * @param fila    fila
	 * @param columna columna
	 * @param pieza   pieza
	 */
	//  @formatter:off
	/* Partimos de un tablero que debe tener este estado.
	*   a  b  c  d  e  f  g  h  
	* 8 TN CN AN DN RN AN CN TN 
	* 7 PN PN PN PN PN PN PN PN
	* 6 -- -- -- -- -- -- -- --
	* 5 -- -- -- -- -- -- -- --
	* 4 -- -- -- -- -- -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 PB PB PB PB PB PB PB PB 
	* 1 TB CB AB DB RB AB CB TB
	*/
	// @formatter:on
	@DisplayName("Comprobar piezas blancas en la última fila")
	@ParameterizedTest
	@MethodSource("coordenadasAndPiezaBlancaProvider")
	void comprobarPiezasUltimaFila(int fila, int columna, Pieza pieza) {
		Celda celda = tablero.obtenerCelda(fila, columna);
		assertThat("Celda vacia", celda.estaVacia(), is(false));
		assertThat("Color de pieza colocada incorrecta", celda.obtenerColorDePieza(), is(BLANCO));
		assertThat("Tipo de pieza incorrecto", celda.obtenerPieza().obtenerTipo(), is(pieza.obtenerTipo()));
	}

	/**
	 * Comprueba el estado inicial del tablero inyectado al árbitro con las piezas
	 * colocadas.
	 */
	@DisplayName("Comprueba el estado inicial del tablero inyectado con piezas colocadas")
	@Test
	void comprobarEstadoInicialConTableroConPiezasColocadas() {
		assertAll("numeroPiezas",
				() -> assertThat("El número de jugadas debería ser cero", arbitro.obtenerNumeroJugada(),is(0)),
				() -> assertThat("Número de piezas negras incorrecto", tablero.obtenerNumeroPiezas(NEGRO), is(16)),
				() -> assertThat("Número de piezas blancas incorrecto", tablero.obtenerNumeroPiezas(BLANCO), is(16)),
				() -> assertThat("Turno inicial siempre es de blancas", arbitro.obtenerTurno(), is(BLANCO)));
	}

	/**
	 * Comprueba el estado inicial respecto a situación de jaque y jaque mate.
	 */
	@DisplayName("Estado inicial sin jaques ni jaque mate")
	@Test
	void comprobarEstadoInicialArbitroConPiezasColocadas() {
		assertAll("situacionesJaque",
				() -> assertThat("La partida no puede iniciarse en jaque para rey negro", arbitro.estaEnJaque(NEGRO),
						is(false)),
				() -> assertThat("La partida no puede iniciarse en jaque para rey blanco",
						arbitro.estaEnJaque(BLANCO), is(false)));
	}

	/**
	 * Comprueba el cambio de turno sucesivo. Ya se han colocado las piezas 
	 * y el turno debería estar inicializado a BLANCO.
	 */
	@DisplayName("Comprueba el cambio de turno correcto.")
	@Test
	void comprobarCambioTurno() {
		assertThat("Turno inicial es de blancas", arbitro.obtenerTurno(), is(BLANCO));
		arbitro.cambiarTurno();
		assertThat("Turno pasa a negras", arbitro.obtenerTurno(), is(NEGRO));
		arbitro.cambiarTurno();
		assertThat("Turno pasa a blancas", arbitro.obtenerTurno(), is(BLANCO));
	}
}

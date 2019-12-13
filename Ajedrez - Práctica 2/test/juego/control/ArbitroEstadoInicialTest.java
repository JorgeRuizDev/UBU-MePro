package juego.control;

import static juego.modelo.Color.BLANCO;
import static juego.modelo.Color.NEGRO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import juego.modelo.Celda;
import juego.modelo.CoordenadasIncorrectasException;
import juego.modelo.Tablero;

/**
 * Pruebas unitarias sobre el estado inicial del árbitro. Este conjunto de
 * pruebas se centra en verificar el estado del árbitro justo después de
 * instanciar.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20190813
 */
@DisplayName("Tests sobre el estado inicial del árbitro")
public class ArbitroEstadoInicialTest {

	/** Árbitro de testing. */
	private Arbitro arbitro;

	/** Tablero de testing. */
	private Tablero tablero;

	/** Generación del árbitro para testing. */
	@BeforeEach
	void inicializar() {
		// Inyección de tablero para testing...
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
	}

	/**
	 * Comprobacion de inicialización correcta del tablero, sin colocar ninguna
	 * pieza, con un tablero vacío y sin turno incialmente.
	 */
	// @formatter:off
	 /* Partimos de un tablero vacío como el que se muestra:
	 *   a b c d e f g h  
	 * 8 - - - - - - - - 
	 * 7 - - - - - - - - 
	 * 6 - - - - - - - - 
	 * 5 - - - - - - - - 
	 * 4 - - - - - - - - 
	 * 3 - - - - - - - - 
	 * 2 - - - - - - - - 
	 * 1 - - - - - - - -
	 */
	 // @formatter:on
	@DisplayName("Comprueba el estado inicial del tablero vacío")
	@Test
	void comprobarEstadoInicialConTablero() {
		assertAll("estadoInicial",
				() -> assertThat("El número de jugadas debería ser cero", arbitro.obtenerNumeroJugada(),is(0)),
				() -> assertThat("No debería haber piezas negras", tablero.obtenerNumeroPiezas(NEGRO), is(0)),
				() -> assertThat("No debería haber piezas blancas", tablero.obtenerNumeroPiezas(BLANCO), is(0)),
				() -> assertNull(arbitro.obtenerTurno(), "El turno no se asigna hasta colocar piezas"));
	}

	/**
	 * Comprueba que se convierte bien la jugada a formato texto en
	 * notación algebraica clásica.
	 * 
	 * @param filaO fila origen
	 * @param colO columna origen
	 * @param filaD fila destino
	 * @param colD columna destino
	 * @param texto texto 
	 * @throws CoordenadasIncorrectasException si hay algún error con las coordenadas
	 */
	@DisplayName("Comprueba la conversión de jugadas a formato coordenadas en notación algebrica")
	@ParameterizedTest
	@CsvSource({ "0,0,0,1,a8b8", "0,0,7,7,a8h1", "0,7,4,4,h8e4", "7,0,3,3,a1d5"})
	void comprobarConversionCoordenadasATexto(int filaO, int colO, int filaD, int colD, String texto) 
			throws CoordenadasIncorrectasException {
		Celda origen = tablero.obtenerCelda(filaO, colO);
		Celda destino = tablero.obtenerCelda(filaD,  colD);
		assertThat(arbitro.obtenerJugadaEnNotacionAlgebraica(origen, destino), is(texto));
	}
}

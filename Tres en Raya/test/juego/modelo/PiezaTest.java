package juego.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * Tests sobre la implementación de la pieza.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @version 1.0
 */
public class PiezaTest {

	/**
	 * Comprueba la correcta inicialización de una piezas. Las piezas se inicializan
	 * con un color. Inicialmente no están asociadas a ninguna celda.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Constructor de pieza correcto con cada color")
	void probarConstructor(Color color) {
		Pieza pieza = new Pieza(color);
		assertAll(() -> assertThat("Color incorrecto", pieza.obtenerColor(), is(color)),
				() -> assertNull(pieza.obtenerCelda(), "Celda incorrecta"));
	}

	/**
	 * Comprueba que la colocación en una celda funciona bien.
	 */
	@Test
	@DisplayName("Correcta asignación de celda.")
	void probarAsignacionPieza() {
		// Inicializaciones
		Celda celda = new Celda(0, 0);
		Pieza pieza = new Pieza(Color.BLANCO);
		// Colocamos una pieza en la celda
		pieza.establecerCelda(celda); 
		// Comprobamos...
		assertThat("Celda incorrectamente asignada", pieza.obtenerCelda(), is(celda));
	}

	/**
	 * Comprueba que colocando en una celda nula se desasigna la celda.
	 */
	@Test
	@DisplayName("Correcto vaciado de celda de una pieza")
	void probarVaciadoDePieza() {
		// Inicializaciones
		Celda celda = new Celda(0, 0);
		Pieza pieza = new Pieza(Color.BLANCO);
		// Colocamos una pieza en la celda y luego la quitamos
		pieza.establecerCelda(celda); // de color blanco
		pieza.establecerCelda(null); // se sustituye por un nulo
		// Comprobamos...
		assertNull(pieza.obtenerCelda(), "Valor nulo incorrectamente asignado");
	}

	/**
	 * Comprueba la correcta formación del String correspondiente al método
	 * toString.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Formato de textos correctos en el método toString")
	void probarMetodoToString(Color color) {
		// Inicializamos
		Pieza pieza = new Pieza(color);
		// Construimos el texto esperado con el formato <letraColor>
		String texto = String.format("%c", color.toChar());
		assertEquals(texto, pieza.toString(), "Texto incorrecto generado.");
	}

}

package juego.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests sobre la implementación de la celda.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @version 1.0
 */
public class CeldaTest {

	/**
	 * Comprueba la correcta inicialización de fila y columna de una celda. Las
	 * celdas instanciadas deberían estar vacías sin pieza colocada.
	 * 
	 * @param fila    fila
	 * @param columna columna
	 */
	@ParameterizedTest
	@CsvSource({ "0, 0", "1, 1", "2, 1", "3, 2", "4, 3", "5, 5", "6, 8" }) // Pares fila y columna
	@DisplayName("Constructor de celda correcto")
	void probarConstructor(int fila, int columna) {
		Celda celda = new Celda(fila, columna);
		assertAll(() -> assertThat("Fila incorrecta", celda.obtenerFila(), is(fila)),
				() -> assertThat("Columna incorrecta", celda.obtenerColumna(), is(columna)),
				() -> assertThat("Celda no está vacía", celda.estaVacia(), is(true)),
				() -> assertNull(celda.obtenerPieza(), "La celda debe instanciarse sin pieza asignada con valor null"));
	}

	/**
	 * Comprueba que la colocación de una pieza funciona bien.
	 */
	@Test
	@DisplayName("Correcta asignación de pieza y cambio de estado")
	void probarAsignacionPieza() {
		// Inicializaciones
		Celda celda = new Celda(0, 0);
		Pieza pieza = new Pieza(Color.BLANCO);
		// Colocamos una pieza en la celda
		celda.establecerPieza(pieza); // de color blanco
		// Comprobamos...
		assertAll(() -> assertThat("Pieza incorrectamente asignada", celda.obtenerPieza(), is(pieza)),
				() -> assertThat("La celda no debería estar vacía si tiene pieza", celda.estaVacia(), is(false)));
	}

	/**
	 * Comprueba que colocando un nulo se vacía una celda.
	 */
	@Test
	@DisplayName("Correcto vaciado de una celda y cambio de estado")
	void probarVaciadoDePieza() {
		// Inicializaciones
		Celda celda = new Celda(0, 0);
		Pieza pieza = new Pieza(Color.BLANCO);
		// Colocamos una pieza en la celda y luego la quitamos
		celda.establecerPieza(pieza); // de color blanco
		celda.establecerPieza(null); // se sustituye por un nulo
		// Comprobamos...
		assertAll(() -> assertNull(celda.obtenerPieza(), "Valor nulo incorrectamente asignado"),
				() -> assertThat("La celda debería estar vacía si tiene pieza", celda.estaVacia(), is(true)));
	}

	/**
	 * Comprueba la correcta formación del String correspondiente al método
	 * toString.
	 * 
	 * @param fila    fila
	 * @param columna columna
	 */
	@ParameterizedTest
	@CsvSource({ "0, 0", "1, 1", "2, 1", "3, 2", "4, 3", "5, 5", "6, 8" }) // Pares fila y columna
	@DisplayName("Formato de textos correctos en el método toString")
	void probarMetodoToString(int fila, int columna) {
		// Inicializamos
		Celda celda = new Celda(fila, columna);
		// Construimos el texto esperado con el formato (fila/columna>
		String texto = String.format("(%d/%d)",  fila, columna);
		assertEquals(texto, celda.toString(),"Texto incorrecto generado.");
	}

}

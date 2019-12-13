package juego.modelo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import juego.util.Direccion;

/**
 * Tests sobre la implementación del tablero.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @version 1.0
 */
public class TableroTest {
	/** Coordenadas en un tablero de 3x3. */
	static final int[][] COORDENADAS_3x3 = { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 0 }, { 1, 1 }, { 1, 2 }, { 2, 0 },
			{ 2, 1 }, { 2, 2 } };

	/**
	 * Comprueba la correcta inicialización en tamaños de un tablero rectángular.
	 * 
	 * @param filas    filas
	 * @param columnas columnas
	 */
	@ParameterizedTest
	@CsvSource({ "1, 1", "2, 1", "3, 2", "4, 3", "5, 5", "6, 8" }) // Pares fila y columna
	@DisplayName("Constructor de tablero en tamaño correcto")
	void probarConstructor(int filas, int columnas) {
		Tablero tablero = new Tablero(filas, columnas);

		assertAll(() -> assertThat("Número de filas incorrectas", tablero.obtenerNumeroFilas(), is(filas)),
				() -> assertThat("Número de columnas incorrectas", tablero.obtenerNumeroColumnas(), is(columnas)));
	}

	/**
	 * Comprueba el correcto estado inicial vacío de un tablero, no completo y sin
	 * piezas de color blanco o negro.
	 * 
	 * @param filas    filas
	 * @param columnas columnas
	 */
	@ParameterizedTest
	@CsvSource({ "1, 1", "2, 1", "3, 2", "4, 3", "5, 5", "6, 8" }) // Pares fila y columna
	@DisplayName("Constructor de tablero con tablero no completo y sin piezas de cualquier color")
	void probarConstructorTableroNoCompletoSinPiezas(int filas, int columnas) {
		Tablero tablero = new Tablero(filas, columnas);

		assertAll(
				() -> assertFalse(tablero.estaCompleto(),
						"El tablero no puede estar completo si se acaba de instanciar"),
				() -> assertThat("Número de piezas blanco incorrecto", tablero.obtenerNumeroPiezas(Color.BLANCO),
						is(0)),
				() -> assertThat("Número de piezas negras incorrecto", tablero.obtenerNumeroPiezas(Color.NEGRO),
						is(0)));
	}

	/**
	 * Comprueba la correcta inicialización en tamaños menores que uno. En estos
	 * casos se debe acotar a tamaño 1 en la dimensión que corresponda.
	 * 
	 * @param filas    filas
	 * @param columnas columnas
	 * @param filasEsperadas filasEsperadas
	 * @param columnasEsperadas columnasEsperadas
	 */
	@ParameterizedTest
	@CsvSource({ "0, 1, 1, 1", "-1, 0, 1, 1", "0, -1, 1, 1" }) // Pares fila/columna y filaEsperada/columnaEsperada
	@DisplayName("Constructor de tablero con valores acotados")
	void probarConstructorConValoresAcotados(int filas, int columnas, int filasEsperadas, int columnasEsperadas) {
		Tablero tablero = new Tablero(filas, columnas);

		assertAll(() -> assertThat("Número de filas incorrectas", tablero.obtenerNumeroFilas(), is(filasEsperadas)),
				() -> assertThat("Número de columnas incorrectas", tablero.obtenerNumeroColumnas(),
						is(columnasEsperadas)));
	}

	/**
	 * Comprueba la correcta inicialización de celdas vacías y diferentes en un
	 * tablero. Se entiende por diferentes a que son objetos independientes con
	 * distinta identidad.
	 * 
	 * @param filas    filas
	 * @param columnas columnas
	 */
	@ParameterizedTest
	@CsvSource({ "1, 1", "2, 1", "3, 2", "4, 3", "5, 5", "6, 8" }) // Pares fila y columna
	@DisplayName("Constructor inicializa con celdas diferentes")
	void probarInicializacionCeldas(int filas, int columnas) {
		// Inicializamos
		Tablero tablero = new Tablero(filas, columnas);
		List<Celda> celdas = new ArrayList<>();
		// Comprobamos que todas las celdas del tabler están vacías y que son objetos
		// "diferentes"
		// (evitar compartir referencias)
		for (int i = 0; i < tablero.obtenerNumeroFilas(); i++) {
			for (int j = 0; j < tablero.obtenerNumeroColumnas(); j++) {
				Celda celda = tablero.obtenerCelda(i, j);
				assertTrue(tablero.estaEnTablero(i, j), "Las coordenadas utilizadas no están en el tablero");
				assertAll(() -> assertNotNull(celda, "La celda no puede ser nula"),
						() -> assertTrue(celda.estaVacia(), "La celda iniciamente debe estar vacía"),
						() -> assertThat("La celda no puede estar contenida previamente", celdas, not(hasItem(celda))));
				celdas.add(celda); // agregamos la celda
			}
		}
	}

	/**
	 * Comprueba que la colocación de una pieza en una celda es correcta. Se
	 * comprueba que se engancha tanto la pieza a la celda como la celda a la pieza.
	 */
	@Test
	@DisplayName("Correcto funcionamiento de colocar")
	void probarColocarEnTablero() {
		// Inicializaciones
		Tablero tablero = new Tablero(3, 3);
		Pieza pieza = new Pieza(Color.BLANCO);
		// Colocamos una pieza en la celda
		Celda celda = tablero.obtenerCelda(1, 1);
		tablero.colocar(pieza, celda); // de color blanco
		// Comprobamos el doble enganche..
		assertAll(() -> assertThat("Celda incorrectamente asignada a la pieza", pieza.obtenerCelda(), is(celda)),
				() -> assertThat("Pieza incorrectamente asignada a la celda", celda.obtenerPieza(), is(pieza)));
	}

	/**
	 * Comprueba el correcto número de piezas tras llenar un tablero de 3x3
	 * colocando alternativamente piezas blancas y negras.
	 * 
	 * El tablero contendrá finalmente:
	 * <p>
	 * {@code OXO}
	 * <p>
	 * {@code XOX}
	 * <p>
	 * {@code OXO} 
	 */
	@Test
	@DisplayName("Colocación de piezas llenando el tablero")
	void probarRellenadoDelTablero() {
		// Inicializamos
		Tablero tablero = new Tablero(3, 3);
		rellenarTableroTresPorTres(COORDENADAS_3x3, tablero);
		// Comprobamos
		assertAll(
				() -> assertThat("Número de piezas blancas incorrecto", tablero.obtenerNumeroPiezas(Color.BLANCO),
						is(5)),
				() -> assertThat("Número de piezas negras incorrecto", tablero.obtenerNumeroPiezas(Color.NEGRO), is(4)),
				() -> assertTrue(tablero.estaCompleto(), " El tablero no está completo"));
	}
	
	/**
	 * Comprueba el correcto formato de la conversión a cadena de texto.
	 * 
	 * El tablero contendrá finalmente:
	 * <p>
	 * {@code OXO}
	 * <p>
	 * {@code XOX}
	 * <p>
	 * {@code OXO}
	 */
	@Test
	@DisplayName("Conversion a texto del tablero")
	void probarConversionATexto() {
		// Inicializamos
		Tablero tablero = new Tablero(3, 3);
		rellenarTableroTresPorTres(COORDENADAS_3x3, tablero);
		// Comprobamos
		String esperado =  "OXO" + "\n" + "XOX"+ "\n" + "OXO";
		assertEquals(esperado, tablero.toString(), "Texto incorrecto generado.");
	}
	
	
	
	/**
	 * Comprueba el correcto formato de la conversión a cadena de texto con un tablero vacío.
	 * 
	 * El tablero contendrá finalmente:
	 * <p>
	 * {@code ---}
	 * <p>
	 * {@code ---}
	 * <p>
	 * {@code ---}
	 */
	@Test
	@DisplayName("Conversion a texto del tablero vacío")
	void probarConversionATextoTableroVacio() {
		// Inicializamos
		Tablero tablero = new Tablero(3, 3);
		// En este caso NO rellenamos el tablero con piezas
		// Comprobamos
		final String vacio = "---";
		String esperado = vacio + "\n" + vacio + "\n" + vacio;
		assertEquals(esperado, tablero.toString(), "Texto incorrecto generado.");
	}

	/**
	 * Comprueba el correcto conteo de piezas del mismo color en la misma dirección.
	 * 
	 * El tablero contendrá:
	 * <p>
	 * {@code OXO}
	 * <p>
	 * {@code XOX}
	 * <p>
	 * {@code OXO}
	 * }
	 * 
	 * @param fila fila
	 * @param columna columna
	 * @param direccion direccion
	 * @param numeroPiezas número de piezas del mismo color contiguas en la dirección dada
	 */
	@ParameterizedTest
	@MethodSource("crearParametrosCeldaDireccionYNumeroPiezas")
	@DisplayName("Conteo de piezas del mismo color en distintas direcciones")
	void probarConteoDePiezasDelMismoColorEnUnaDireccion(final int fila, final int columna, Direccion direccion,
			final int numeroPiezas) {
		// Inicializamos
		Tablero tablero = new Tablero(3, 3);
		rellenarTableroTresPorTres(COORDENADAS_3x3, tablero);
		final Celda celda = tablero.obtenerCelda(fila, columna);
		// Comprobamos
		assertAll(() -> assertThat("Número de piezas blancas incorrecto", tablero.contarPiezas(celda, direccion),
				is(numeroPiezas)), () -> assertTrue(tablero.estaCompleto(), " El tablero no está completo"));
	}

	/**
	 * Fuente de datos para comprobar conteo correcto de piezas en distintas
	 * direcciones.
	 * 
	 * @see TableroTest#generar(int, int, int...)
	 * @return flujo de argumentos con coordenadas, direccion y numero de piezas
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> crearParametrosCeldaDireccionYNumeroPiezas() {
		List<Arguments> arguments = new ArrayList<>();
		// Para cada celda Ej: (0,0) comprobamos el número de piezas iguales
		// en cada una de las cuatro direcciones horizonal-vertical-NOSE-SONE. Ej: 1, 1, 3, 1
		arguments.addAll(generar(0, 0, 1, 1, 3, 1));
		arguments.addAll(generar(0, 1, 1, 1, 2, 2));
		arguments.addAll(generar(0, 2, 1, 1, 1, 3));
		// Segunda fila
		arguments.addAll(generar(1, 0, 1, 1, 2, 2));
		arguments.addAll(generar(1, 1, 1, 1, 3, 3));
		arguments.addAll(generar(1, 2, 1, 1, 2, 2));
		// Tercera fila
		arguments.addAll(generar(2, 0, 1, 1, 1, 3));
		arguments.addAll(generar(2, 1, 1, 1, 2, 2));
		arguments.addAll(generar(2, 2, 1, 1, 3, 1));
		return  arguments.stream();
	}
	
	
	/**
	 * Genera la lista de argumentos a comparar para cada dirección.
	 * 
	 * @param fila fila 
	 * @param columna columna
	 * @param numeroPiezas array con el numero de piezas para cada direccion concreta
	 * @return devuelve una lista (tupla) de la forma [fila, columna, direccion, numeroPiezas]
	 */
	private static List<Arguments> generar(int fila, int columna, int... numeroPiezas) {
		List<Arguments> list = new ArrayList<>();
		list.add(Arguments.arguments(fila, columna, Direccion.HORIZONTAL, numeroPiezas[0]));
		list.add(Arguments.arguments(fila, columna, Direccion.VERTICAL, numeroPiezas[1]));
		list.add(Arguments.arguments(fila, columna, Direccion.DIAGONAL_NO_SE, numeroPiezas[2]));
		list.add(Arguments.arguments(fila, columna, Direccion.DIAGONAL_SO_NE, numeroPiezas[3]));
		return list;
	}

	/**
	 * Rellena un tablero con piedras blancas o negras en las coordenadas indicadas.
	 * 
	 * @param coordenadas coordenadas de celdas
	 * @param tablero tablero
	 */
	private void rellenarTableroTresPorTres(final int[][] coordenadas, Tablero tablero) {
		// Rellenamos el tablero alternativamente de piezas blancos o negras hasta
		// llenarlo completamente (9 piezas)
		for (int i = 0; i < coordenadas.length; i++) {
			// si la suma de las coordenadas es par, blancas, si no, negras
			Color color = ((coordenadas[i][0] + coordenadas[i][1]) % 2 == 0) ? Color.BLANCO : Color.NEGRO;
			Pieza pieza = new Pieza(color);
			Celda celda = tablero.obtenerCelda(coordenadas[i][0], coordenadas[i][1]);
			tablero.colocar(pieza, celda);
		}
	}
	

	/**
	 * Comprueba que al consultar celdas fuera de un tablero devuelve null.
	 * 
	 * @param fila   fila
	 * @param columna columna
	 */
	@ParameterizedTest
	@CsvSource({ "-1, -1", "-1, 0", "0, -1", "3, 0", "0, 3", "2, -1", "3, 3", "4, 5" }) // Pares fila y columna
	@DisplayName("Constructor de tablero en tamaño correcto")
	void probarConsultaCeldaConValorNuloSiLasCoordenadasEstanFuera(int fila, int columna) {
		Tablero tablero = new Tablero(3,3);
		assertNull(tablero.obtenerCelda(fila,  columna),"Las coordenadas están fuera del tablero y debe devolver null");
	}
	

	/**
	 * Comprueba el correcto conteo de piezas a cero en un tablero vacío.
	 */
	@Test
	@DisplayName("Conteo de piezas del mismo color a cero en distintas direcciones en un tablero vacío")
	void probarConteoDePiezasEnTableorVacio() {
		// Inicializamos
		Tablero tablero = new Tablero(3, 3);
		// No rellenamos en este caso.
		// Comprobamos...	
		for (int fila = 0; fila < tablero.obtenerNumeroFilas(); fila++) {
			for (int columna = 0; columna < tablero.obtenerNumeroColumnas(); columna++) {
				for (Direccion direccion : Direccion.values()) {
					Celda celda = tablero.obtenerCelda(fila, columna);
					assertThat("El número de piezas debería ser cero en un tablero vacío", tablero.contarPiezas(celda, direccion), is(0));
				}
			}
		}
	}
}

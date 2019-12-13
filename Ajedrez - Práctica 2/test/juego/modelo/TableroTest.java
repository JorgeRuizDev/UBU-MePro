package juego.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import juego.modelo.pieza.Dama;
import juego.modelo.pieza.Peon;
import juego.modelo.pieza.Pieza;
import juego.util.Sentido;

/**
 * Pruebas unitarias sobre el tablero.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20181008
 * 
 */
@DisplayName("Tests sobre Tablero")
public class TableroTest {

	/** Tablero de testing. */
	private Tablero tablero;

	/** Inicializa valores para cada test. */
	@BeforeEach
	void inicializar() {
		tablero = new Tablero();
	}

	/**
	 * Rellenado del tablero de piezas hasta ver que está completo.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error con las
	 *                                         coordenadas
	 */
	@DisplayName("Rellena el tablero de piezas hasta completarlo")
	@Test
	void rellenarTableros() throws CoordenadasIncorrectasException {
		Tablero tableroLocal = new Tablero();

		Color[] colores = Color.values();
		for (Color color : colores) {
			Pieza pieza = new Peon(color);
			for (int ii = 0; ii < tableroLocal.obtenerNumeroFilas(); ii++) {
				for (int jj = 0; jj < tableroLocal.obtenerNumeroColumnas(); jj++) {
					Celda celda = tableroLocal.obtenerCelda(ii, jj);
					tableroLocal.colocar(pieza, celda);
					assertThat("Celda mal asignada.", pieza.obtenerCelda(), is(celda));
					assertThat("Pieza mal asignada.", celda.obtenerPieza(), is(pieza));
					assertFalse("La celda está vacía", celda.estaVacia());
				}
			}
			assertThat("Número de piezas incorrecto para color " + color, tableroLocal.obtenerNumeroPiezas(color),
					is(64));
		}

	}

	/**
	 * Revisa la consulta de celdas en distintas posiciones del tablero.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error con las
	 *                                         coordenadas
	 */
	@DisplayName("Comprueba la consulta de celdas en posiciones correctas e incorrectas del tablero")
	@Test
	void comprobarAccesoACeldas() throws CoordenadasIncorrectasException {
		Tablero tableroLocal = new Tablero();
		// coordenadas incorrectas
		int[][] coordenadasIncorrectas = { { -1, -1 }, { 7, -1 }, { -1, 7 }, { 8, 8 } };
		for (int i = 0; i < coordenadasIncorrectas.length; i++) {
			final int ii = i; // TODO
			assertThrows(CoordenadasIncorrectasException.class,
					() -> tableroLocal.obtenerCelda(coordenadasIncorrectas[ii][0], coordenadasIncorrectas[ii][1]),
					"La celda no debería estar contenida en el tablero devolviendo un nulo");
		}
		// coordenadas correctas
		int[][] coordenadasCorrectas = { { 4, 3 }, { 0, 0 }, { 7, 7 }, { 0, 7 }, { 7, 0 } };
		for (int i = 0; i < coordenadasCorrectas.length; i++) {
			assertNotNull("La celda sí debería estar contenida en el tablero, no debería lanzar excepción",
					tableroLocal.obtenerCelda(coordenadasCorrectas[i][0], coordenadasCorrectas[i][1]));
		}
	}

	/**
	 * Comprueba la detección del sentido de movimiento entre dos celdas origen y
	 * destino que están en diagonal.
	 */
	@DisplayName("Detección del sentido en el movimiento diagonal entre dos celdas")
	@Test
	void comprobarSentidoEnMovimientoDiagonal() {
		assertAll(
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(0, 0), new Celda(5, 5)),
						is(Sentido.DIAGONAL_SE)),
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(0, 1), new Celda(4, 5)),
						is(Sentido.DIAGONAL_SE)),
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(2, 2), new Celda(0, 0)),
						is(Sentido.DIAGONAL_NO)),
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(5, 5), new Celda(0, 0)),
						is(Sentido.DIAGONAL_NO)),
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(5, 5), new Celda(3, 7)),
						is(Sentido.DIAGONAL_NE)),
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(2, 2), new Celda(0, 4)),
						is(Sentido.DIAGONAL_NE)),
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(4, 6), new Celda(6, 4)),
						is(Sentido.DIAGONAL_SO)),
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(2, 4), new Celda(6, 0)),
						is(Sentido.DIAGONAL_SO)));
	}

	/** Genera la cadena de texto correcta para un tablero vacío. */
	@DisplayName("Comprueba la generación de la cadena de texto en toString con tablero vacío")
	@Test
	void comprobarCadenaTextoConTableroVacio() {
		String cadenaEsperada = "8	-- -- -- -- -- -- -- --" + "7	-- -- -- -- -- -- -- --"
				+ "6	-- -- -- -- -- -- -- --" + "5	-- -- -- -- -- -- -- --" + "4	-- -- -- -- -- -- -- --"
				+ "3	-- -- -- -- -- -- -- --" + "2	-- -- -- -- -- -- -- --" + "1	-- -- -- -- -- -- -- --"
				+ "	a  b  c  d  e  f  g  h";

		cadenaEsperada = cadenaEsperada.replaceAll("\\s", "");
		// eliminamos espacios/tabuladores para comparar
		String salida = tablero.toString().replaceAll("\\s", "");
		assertEquals(cadenaEsperada, salida, "La cadena de texto generada para un tablero vacío es incorecta.");
	}

	/**
	 * Genera la cadena de texto correcta para un tablero con algunos peones
	 * colocados en las esquinas del tablero.
	 *
	 * @throws CoordenadasIncorrectasException si hay algún error con las
	 *                                         coordenadas
	 */
	@DisplayName("Comprueba la generación de la cadena de texto en toString con tablero con algunos peones en las esquinas")
	@Test
	void comprobarCadenaTextoConTableroConPeonesEnEsquinas() throws CoordenadasIncorrectasException {
		String cadenaEsperada = "8	PN -- -- -- -- -- -- PN" + "7	-- -- -- -- -- -- -- --"
				+ "6	-- -- -- -- -- -- -- --" + "5	-- -- -- -- -- -- -- --" + "4	-- -- -- -- -- -- -- --"
				+ "3	-- -- -- -- -- -- -- --" + "2	-- -- -- -- -- -- -- --" + "1	PB -- -- -- -- -- -- PB"
				+ "	a  b  c  d  e  f  g  h";
		cadenaEsperada = cadenaEsperada.replaceAll("\\s", "");
		tablero.colocar(new Peon(Color.NEGRO), tablero.obtenerCelda(0, 0));
		tablero.colocar(new Peon(Color.NEGRO), tablero.obtenerCelda(0, 7));
		tablero.colocar(new Peon(Color.BLANCO), tablero.obtenerCelda(7, 0));
		tablero.colocar(new Peon(Color.BLANCO), tablero.obtenerCelda(7, 7));
		// eliminamos espacios/tabuladores para comparar
		String salida = tablero.toString().replaceAll("\\s", "");
		assertEquals(cadenaEsperada, salida,
				"La cadena de texto generada para un tablero con peones en las esquinas es incorecta.");
	}

	/**
	 * Genera la cadena de texto correcta para un tablero con algunos peones y
	 * damas.
	 *
	 * @throws CoordenadasIncorrectasException si hay algún error con las
	 *                                         coordenadas
	 */
	@DisplayName("Comprueba la generación de la cadena de texto en toString con tablero con algunos peones y damas")
	@Test
	void comprobarCadenaTextoConTableroConPeonesYDamas() throws CoordenadasIncorrectasException {
		String cadenaEsperada = "8	PN -- -- -- -- -- -- PN" + "7	-- -- -- -- -- -- -- --"
				+ "6	-- -- -- -- -- -- -- --" + "5	-- -- -- DN DN -- -- --" + "4	-- -- -- DB DB -- -- --"
				+ "3	-- -- -- -- -- -- -- --" + "2	-- -- -- -- -- -- -- --" + "1	PB -- -- -- -- -- -- PB"
				+ "	a  b  c  d  e  f  g  h";
		cadenaEsperada = cadenaEsperada.replaceAll("\\s", "");
		tablero.colocar(new Peon(Color.NEGRO), tablero.obtenerCelda(0, 0));
		tablero.colocar(new Peon(Color.NEGRO), tablero.obtenerCelda(0, 7));
		tablero.colocar(new Dama(Color.NEGRO), tablero.obtenerCelda(3, 3));
		tablero.colocar(new Dama(Color.NEGRO), tablero.obtenerCelda(3, 4));

		tablero.colocar(new Peon(Color.BLANCO), tablero.obtenerCelda(7, 0));
		tablero.colocar(new Peon(Color.BLANCO), tablero.obtenerCelda(7, 7));
		tablero.colocar(new Dama(Color.BLANCO), tablero.obtenerCelda(4, 3));
		tablero.colocar(new Dama(Color.BLANCO), tablero.obtenerCelda(4, 4));

		// eliminamos espacios/tabuladores para comparar
		String salida = tablero.toString().replaceAll("\\s", "");
		assertEquals(cadenaEsperada, salida,
				"La cadena de texto generada para un tablero con peones y damas es incorecta.");
	}

	/**
	 * Comprueba la detección del sentido de movimiento entre dos celdas origen y
	 * destino que estén en horizontal o vertical.
	 */
	@DisplayName("Detección del sentido en el movimiento horizontal o vertical entre dos celdas")
	@Test
	void comprobarSentidoEnMovimientoHorizonalOVertical() {
		assertAll(
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(0, 0), new Celda(1, 0)),
						is(Sentido.VERTICAL_S)),
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(7, 7), new Celda(6, 7)),
						is(Sentido.VERTICAL_N)),
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(2, 2), new Celda(2, 3)),
						is(Sentido.HORIZONTAL_E)),
				() -> assertThat("Mal detectado el sentido", tablero.obtenerSentido(new Celda(5, 5), new Celda(5, 4)),
						is(Sentido.HORIZONTAL_O)));
	}

	/**
	 * Comprueba que devuelve todas las celdas con independencia del orden.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error con las
	 *                                         coordenadas
	 */
	@DisplayName("Comprueba que la consulta de todas las celdas devuelve efectivamente todas (con independencia del orden)")
	@Test
	void comprobarObtenerCeldas() throws CoordenadasIncorrectasException {
		List<Celda> todas = tablero.obtenerCeldas();
		int encontrada = 0;
		for (int i = 0; i < tablero.obtenerNumeroFilas(); i++) {
			for (int j = 0; j < tablero.obtenerNumeroColumnas(); j++) {
				Celda celda = tablero.obtenerCelda(i, j);
				for (Celda celdaAux : todas) {
					if (celdaAux.tieneCoordenadasIguales(celda)) {
						encontrada++;
						break;
					}
				}
			}
		}
		assertThat("No devuelve todas las celdas", encontrada, is(64));
	}

	/**
	 * Comprueba la conversión de celda a formato texto en notación algebraica.
	 */
	@DisplayName("Conversión de celda a texto en notación algebraica")
	@Test
	void comprobarConversionCeldaATexto() {
		assertAll("conversiones",
				() -> assertThat("Coordenadas incorrectas.",
						tablero.obtenerCoordenadaEnNotacionAlgebraica(new Celda(0, 0)), is("a8")),
				() -> assertThat("Coordenadas incorrectas.",
						tablero.obtenerCoordenadaEnNotacionAlgebraica(new Celda(7, 0)), is("a1")),
				() -> assertThat("Coordenadas incorrectas.",
						tablero.obtenerCoordenadaEnNotacionAlgebraica(new Celda(0, 7)), is("h8")),
				() -> assertThat("Coordenadas incorrectas.",
						tablero.obtenerCoordenadaEnNotacionAlgebraica(new Celda(7, 7)), is("h1")),
				() -> assertThat("Coordenadas incorrectas.",
						tablero.obtenerCoordenadaEnNotacionAlgebraica(new Celda(4, 4)), is("e4")),
				() -> assertThat("Coordenadas incorrectas.",
						tablero.obtenerCoordenadaEnNotacionAlgebraica(new Celda(3, 3)), is("d5")));
	}
	
	/**
	 * Comprueba el lanzamiento de excepciones cuando las coordenadas son incorrectas.
	 * 
	 * @param fila fila
	 * @param columna columna
	 */
	@DisplayName("Lanzamiento de excepciones de celda fuera del tablero a texto en notación algebraica")
	@ParameterizedTest
	@CsvSource({ "-1,-1", "-1,0", "1,8", "0,8", "8,0", "8,8" })
	void comprobarLanzamientoConConversionIncorrectaCeldaATexto(int fila, int columna) {		
		assertThrows(CoordenadasIncorrectasException.class,
				() -> tablero.obtenerCoordenadaEnNotacionAlgebraica(new Celda(-1, 0)),
						"Celda fuera del tablero (" + fila + "," + columna + ")");
	}

	/**
	 * Comprueba la conversión de formato texto en notación algebraica a celda.
	 */
	@DisplayName("Conversión de texto en notación algebraica a celda")
	@Test
	void comprobarConversionTextoACelda() {
		assertAll("conversiones",
				() -> assertTrue(
						tablero.obtenerCeldaParaNotacionAlgebraica("a8").tieneCoordenadasIguales(new Celda(0, 0)),
						"Celda incorrecta."),
				() -> assertTrue(
						tablero.obtenerCeldaParaNotacionAlgebraica("a1").tieneCoordenadasIguales(new Celda(7, 0)),
						"Celda incorrecta."),
				() -> assertTrue(
						tablero.obtenerCeldaParaNotacionAlgebraica("h8").tieneCoordenadasIguales(new Celda(0, 7)),
						"Celda incorrecta."),
				() -> assertTrue(
						tablero.obtenerCeldaParaNotacionAlgebraica("h1").tieneCoordenadasIguales(new Celda(7, 7)),
						"Celda incorrecta."),
				() -> assertTrue(
						tablero.obtenerCeldaParaNotacionAlgebraica("e4").tieneCoordenadasIguales(new Celda(4, 4)),
						"Celda incorrecta."),
				() -> assertTrue(
						tablero.obtenerCeldaParaNotacionAlgebraica("d5").tieneCoordenadasIguales(new Celda(3, 3)),
						"Celda incorrecta."));
	}
	
	/**
	 * Comprueba el lanzamiento de excepciones cuando el texto a convertir es incorrecto.
	 * 
	 * @param texto texto con la notación algebraica
	 */
	@DisplayName("Lanzamiento de excepciones de textos incorrectos fuera del tablero")
	@ParameterizedTest
	@CsvSource({ "i5", "a9", "11", "x9", "aa", "xy", "77" })
	void comprobarLanzamientoConConversionIncorrectaTextoACelda(String texto) {		
		assertThrows(CoordenadasIncorrectasException.class,
				() -> tablero.obtenerCeldaParaNotacionAlgebraica(texto),
						"Texto incorrecto para convertir a celda:" + texto);
	}
	
	

	/**
	 * Comprueba las coordenadas de las celdas entre medias en diagonal descendente.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error en las coordenadas
	 */
	@DisplayName("Comprobar celdas entre medias en diagonal descendente")
	@Test
	void comprobarCeldasEntreMediasEnDiagonalDescendente() throws CoordenadasIncorrectasException {
		List<Celda> entreMedias = tablero.obtenerCeldasEntreMedias(new Celda(0, 0), new Celda(7, 7));
		List<Celda> celdasEsperadas = Arrays.asList(new Celda(1, 1), new Celda(2, 2), new Celda(3, 3), new Celda(4, 4),
				new Celda(5, 5), new Celda(6, 6));
		comprobarListasIgualesEnCoordenadas(celdasEsperadas, entreMedias);
	}

	/**
	 * Comprueba las coordenadas de las celdas entre medias en diagonal ascendente.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error en las coordenadas
	 */
	@DisplayName("Comprobar celdas entre medias en diagonal ascendente")
	@Test
	void comprobarCeldasEntreMediasEnDiagonalAscendente() throws CoordenadasIncorrectasException {
		List<Celda> entreMedias = tablero.obtenerCeldasEntreMedias(new Celda(7, 0), new Celda(0, 7));
		List<Celda> celdasEsperadas = Arrays.asList(new Celda(6, 1), new Celda(5, 2), new Celda(4, 3), new Celda(3, 4),
				new Celda(2, 5), new Celda(1, 6));
		comprobarListasIgualesEnCoordenadas(celdasEsperadas, entreMedias);

	}

	/**
	 * Comprueba celdas entre medias en horizontal hacia el este.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error en las coordenadas
	 */
	@DisplayName("Comprobar las coordenadas de las celdas entre medias en horizontal hacia el este")
	@Test
	void comprobarCeldasEntreMediasEnHorizontalHaciaEste() throws CoordenadasIncorrectasException {
		List<Celda> entreMedias = tablero.obtenerCeldasEntreMedias(new Celda(4, 0), new Celda(4, 7));
		List<Celda> celdasEsperadas = Arrays.asList(new Celda(4, 1), new Celda(4, 2), new Celda(4, 3), new Celda(4, 4),
				new Celda(4, 5), new Celda(4, 6));
		comprobarListasIgualesEnCoordenadas(celdasEsperadas, entreMedias);

	}

	/**
	 * Comprueba las coordenadas de las celdas entre medias en horizontal hacia el
	 * oeste.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error en las coordenadas
	 */
	@DisplayName("Comprobar celdas entre medias en horizontal hacia el oeste")
	@Test
	void comprobarCeldasEntreMediasEnHorizontalHaciaOeste() throws CoordenadasIncorrectasException {
		List<Celda> entreMedias = tablero.obtenerCeldasEntreMedias(new Celda(4, 7), new Celda(4, 0));
		List<Celda> celdasEsperadas = Arrays.asList(new Celda(4, 6), new Celda(4, 5), new Celda(4, 4), new Celda(4, 3),
				new Celda(4, 2), new Celda(4, 1));
		comprobarListasIgualesEnCoordenadas(celdasEsperadas, entreMedias);

	}

	/**
	 * Comprueba las coordenadas de las celdas cuando no están en ningún sentido.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error en las coordenadas
	 */
	@DisplayName("Comprobar celdas que no están en ningún sentido")
	@Test
	void comprobarCeldasEnNingunSentido() throws CoordenadasIncorrectasException {
		assertAll("sinSentido",
				() -> assertThat(tablero.obtenerCeldasEntreMedias(new Celda(0, 0), new Celda(7, 6)).size(), is(0)),
				() -> assertThat(tablero.obtenerCeldasEntreMedias(new Celda(0, 7), new Celda(6, 0)).size(), is(0)),
				() -> assertThat(tablero.obtenerCeldasEntreMedias(new Celda(7, 0), new Celda(0, 6)).size(), is(0)),
				() -> assertThat(tablero.obtenerCeldasEntreMedias(new Celda(7, 7), new Celda(1, 0)).size(), is(0)),
				() -> assertThat(tablero.obtenerCeldasEntreMedias(new Celda(0, 0), new Celda(1, 2)).size(), is(0)),
				() -> assertThat(tablero.obtenerCeldasEntreMedias(new Celda(0, 7), new Celda(2, 6)).size(), is(0)),
				() -> assertThat(tablero.obtenerCeldasEntreMedias(new Celda(0, 0), new Celda(7, 6)).size(), is(0)));

	}

	/**
	 * Comprueba que las dos listas de celdas coinciden en orden y coordenadas.
	 * 
	 * @param esperada celdas esperadas
	 * @param obtenida celdas obtenidas
	 */
	private void comprobarListasIgualesEnCoordenadas(List<Celda> esperada, List<Celda> obtenida) {
		assertThat("Los tamaños de las listas no coinciden y deben ser iguales.", esperada.size(), is(obtenida.size()));
		for (int i = 0; i < esperada.size(); i++) {
			Celda celdaEsperada = esperada.get(i);
			Celda celdaObtenida = obtenida.get(i);
			assertTrue(celdaEsperada.tieneCoordenadasIguales(celdaObtenida), "Celda esperada " + celdaEsperada
					+ " no coincide con " + celdaObtenida + ".\nRevisar celdas y orden de las mismas.");
		}
	}

	/**
	 * Comprueba el lanzamiento de excepciones al comprobar sentido con celdas
	 * origen o destino incorrectas.
	 */
	@DisplayName("Comprobar lanzamiento de excepciones al comprobar sentido")
	@Test
	void comprobarLanzamientoConComprobacionSentido() {
		Tablero tableroLocal = new Tablero();
		// coordenadas incorrectas
		List<Celda> celdasIncorrectas = Arrays.asList(new Celda(0, -1), new Celda(-1, 0), new Celda(8, 0),
				new Celda(0, 8), new Celda(8, 8), new Celda(-1, -1));
		List<Celda> celdasCorrectas = Arrays.asList(new Celda(0, 0), new Celda(1, 1), new Celda(7, 0), new Celda(0, 7),
				new Celda(7, 7), new Celda(1, 1));
		for (int i = 0; i < celdasIncorrectas.size(); i++) {
			final int ii = i; // TODO
			assertThrows(CoordenadasIncorrectasException.class,
					() -> tableroLocal.obtenerSentido(celdasIncorrectas.get(ii), celdasCorrectas.get(ii)),
					"Obtener sentido de celda incorrecta " + celdasIncorrectas.get(i) + " a correcta "
							+ celdasCorrectas.get(i) + " debe generar excepción.");
			assertThrows(CoordenadasIncorrectasException.class,
					() -> tableroLocal.obtenerSentido(celdasCorrectas.get(ii), celdasIncorrectas.get(ii)),
					"Obtener sentido de celda correcta " + celdasCorrectas.get(i) + " a incorrecta "
							+ celdasIncorrectas.get(i) + " debe generar excepción.");
			assertThrows(CoordenadasIncorrectasException.class,
					() -> tableroLocal.obtenerSentido(celdasIncorrectas.get(ii), celdasIncorrectas.get(ii)),
					"Obtener sentido de celda incorrecta " + celdasIncorrectas.get(i) + " a incorrecta "
							+ celdasIncorrectas.get(i) + " debe generar excepción.");
		}
	}
}

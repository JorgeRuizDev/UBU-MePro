package juego.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests sobre la implementación del jugador.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @version 1.0
 */
public class JugadorTest {

	/**
	 * Comprueba la correcta inicialización del jugador. Los jugadores tienen
	 * asignado un nombre y color
	 * 
	 * @param nombre nombre del jugador
	 * @param color  color asignado
	 */
	@ParameterizedTest
	@MethodSource("crearParNombreColor")
	@DisplayName("Constructor de jugador correcto")
	void probarConstructor(String nombre, Color color) {
		Jugador jugador = new Jugador(nombre, color);
		assertAll(() -> assertThat("Nombre incorrecto", jugador.obtenerNombre(), is(nombre)),
				() -> assertThat("Color incorrecto", jugador.obtenerColor(), is(color)));
	}

	/**
	 * Fuente de datos para test con pares nombre y color.
	 * 
	 * @return flujo de argumentos con pares nombre y color
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> crearParNombreColor() {
		return Stream.of(Arguments.arguments("Juan", Color.BLANCO), Arguments.arguments("Pepe", Color.NEGRO));
	}

	/**
	 * Comprueba que genera una pieza del color asignado al jugador.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Generación de pieza del color asignado al jugador")
	void generarPieza(Color color) {
		Jugador jugador = new Jugador("dummy", color);
		Pieza piezaGenerada = jugador.generarPieza();
		assertThat(piezaGenerada.obtenerColor(), is(color));
	}

	/**
	 * Comprueba que genera una pieza del color asignado al jugador y que además
	 * genera cada vez una nueva pieza. Se repite la generación de piezas un número
	 * determinado de veces comprobando que la pieza no se había generado
	 * previamente.
	 * 
	 * @param color color
	 */
	@ParameterizedTest
	@EnumSource(Color.class)
	@DisplayName("Generación de pieza nueva del color asignado al jugador")
	void generarPiezaNuevaCadaVez(Color color) {
		final int LIMITE = 100;
		Jugador jugador = new Jugador("dummy", color);
		List<Pieza> piezasGeneradas = new ArrayList<>();
		// generamos 100 piezas diferentes con el mismo color...
		for (int i = 0; i < LIMITE; i++) {
			Pieza piezaGenerada = jugador.generarPieza();
			assertThat(piezaGenerada.obtenerColor(), is(color));
			if (piezasGeneradas.contains(piezaGenerada)) {
				fail("La pieza ya se había generado previamente y está repetida en la lista.");
			}
			piezasGeneradas.add(piezaGenerada);
		}
	}

}

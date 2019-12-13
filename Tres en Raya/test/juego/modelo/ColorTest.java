package juego.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests sobre la enumeración color.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @version 1.0
 */
public class ColorTest {

	/**
	 * Comprueba la correcta inicialización con los caracteres correspondientes.
	 * Letra 'O' para el color blanco y 'X' para el color negro.
	 */
	@Test
	@DisplayName("Inicialización de letras correcta en color")
	void probarInicializacion() {
		assertAll(() -> assertThat("Letra incorrecta asignada", Color.BLANCO.toChar() , is('O')),
				() -> assertThat("Letra incorrecta asignada", Color.NEGRO.toChar(), is('X')));
	}
}

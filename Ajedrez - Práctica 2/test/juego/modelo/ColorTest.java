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
 * @version 1.0 20181008
 */
@DisplayName("Tests sobre Color")
public class ColorTest {

	/**
	 * Comprueba la correcta inicialización de valores.
	 */
	@Test
	@DisplayName("Comprueba la inicialización de letras correcta en color")
	void probarInicializacion() {
		assertAll(() -> assertThat("Letra incorrecta asignada", Color.BLANCO.toChar() , is('B')),
				() -> assertThat("Letra incorrecta asignada", Color.NEGRO.toChar(), is('N')));
	}
}

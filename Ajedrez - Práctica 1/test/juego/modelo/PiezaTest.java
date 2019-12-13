package juego.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
		Pieza pieza = new Pieza(Tipo.PEON, Color.BLANCO);
		assertAll(() -> assertThat("Color mal inicializado", pieza.obtenerColor(), is(Color.BLANCO)),
				() -> assertThat("Tipo mal inicializado", pieza.obtenerTipo(), is(Tipo.PEON)),
				() -> assertNull(pieza.obtenerCelda(), "La pieza inicialmente no debe estar en una celda."));
	}

	/**
	 * Coloca la pieza en la celda.
	 */
	@DisplayName("Coloca la pieza en la celda")
	@Test
	void colocarEnCelda() {
		Pieza pieza = new Pieza(Tipo.DAMA, Color.BLANCO);
		Celda celda = new Celda(0, 0);
		pieza.establecerCelda(celda);
		assertAll(() -> assertThat("Pieza mal asociada a celda", pieza.obtenerCelda(), is(celda)),
				() -> assertThat("Tipo mal inicializado", pieza.obtenerTipo(), is(Tipo.DAMA)),
				() -> assertThat("Celda no debería estar asociada a la pieza", celda.obtenerPieza(), is(nullValue())));
	}

	/**
	 * Prueba del método toString.
	 */
	@DisplayName("Formato de texto")
	@Test
	void probarToString() {
		Pieza pieza = new Pieza(Tipo.PEON, Color.BLANCO);
		assertThat("Texto mal construido o estado incorrecto", pieza.toString().replaceAll("\\s", ""),
				is (Tipo.PEON + "-" + Color.BLANCO + "-true"));

		pieza = new Pieza(Tipo.PEON, Color.NEGRO);
		assertThat("Texto mal construido o estado incorrecto", pieza.toString().replaceAll("\\s", ""),
				is (Tipo.PEON + "-" + Color.NEGRO + "-true"));

		Celda celda = new Celda(3, 4);
		pieza = new Pieza(Tipo.DAMA, Color.BLANCO);
		pieza.establecerCelda(celda);
		assertThat("Texto mal construido o estado incorrecto", pieza.toString().replaceAll("\\s", ""),
				is (Tipo.DAMA + "-" + Color.BLANCO + "-true"));
	}
	
	/**
	 * Comprueba que al marcar el movimiento cambia de estado.
	 */
	@DisplayName("Cambio de estado al marcar el movimiento")
	@Test
	void probarCambioEstadoMovimiento()  {
		Pieza pieza = new Pieza(Tipo.PEON, Color.BLANCO);
		pieza.marcarPrimerMovimiento();
		assertThat("No cambia de estado", pieza.esPrimerMovimiento(), is(false));
		assertThat("Texto mal construido o estado incorrecto tras realizar primer movimiento", pieza.toString().replaceAll("\\s", ""),
				is (Tipo.PEON + "-" + Color.BLANCO + "-false"));
	}
	
}

package juego.control;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import juego.modelo.Celda;
import juego.modelo.Color;
import juego.modelo.CoordenadasIncorrectasException;
import juego.modelo.Tablero;
import juego.modelo.pieza.Dama;
import juego.modelo.pieza.Peon;
import juego.modelo.pieza.Pieza;
import juego.modelo.pieza.Rey;

/**
 * Pruebas avanzadas para la detección una correcta implementación de la simulación
 * sin provocar cambios en el tablero.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20191123
 */
@DisplayName("Test avanzados de simulación sobre Arbitro")
public class ArbitroSimulacionTest {
	
	/** Tablero. */
	private Tablero tablero;
	
	/** Arbitro. */
	private Arbitro arbitro;

	/**
	 * Inicializa el tablero.
	 * @throws CoordenadasIncorrectasException  si alguna posición es incorrecta
	 */
	//  @formatter:off
	/* Partimos de un tablero que debe tener este estado.
	*   a  b  c  d  e  f  g  h  
	* 8 RN -- -- -- -- -- -- --
	* 7 -- -- -- -- -- -- -- --
	* 6 -- DB PB -- -- -- -- --
	* 5 -- -- -- -- -- -- -- --
	* 4 -- -- -- -- -- -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 -- -- -- -- -- -- -- --
	* 1 RB -- -- -- -- -- -- --
	*/
	// @formatter:on
	@BeforeEach
	void iniciar() throws CoordenadasIncorrectasException {
		// Preparamos tablero
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);

		int[][] posiciones = { { 0, 0 }, { 2, 1 }, { 2, 2 }, { 7, 0 } };
		
		Rey reyNegro = new Rey(Color.NEGRO);
		Rey reyBlanco = new Rey(Color.BLANCO);
		Pieza[] piezas = { reyNegro, new Dama(Color.BLANCO), new Peon(Color.BLANCO), reyBlanco};
		arbitro.colocarPiezas(piezas, posiciones, reyNegro, reyBlanco);

	}
	
	/**
	 * Comprueba que en la simulación sí detecta una situación de jaque positiva.
	 * @throws CoordenadasIncorrectasException si alguna posición es incorrecta 
	 */
	@DisplayName("Comprobar que detecta situación de jaque positivo al simular movimiento")
	@Test
	void comprobarDeteccionDeJaquePositivoMoviendoNegras() throws CoordenadasIncorrectasException {

		// realizamos movimiento con negras
		arbitro.cambiarTurno(); // pasamos turno al negro que quedaría en jaque si mueve de esa forma
		Celda origen = tablero.obtenerCelda(0, 0);
		Celda destino = tablero.obtenerCelda(0, 1);		
		
		// simulamos movimiento
		assertThat("El movimiento no deja al rey en jaque", arbitro.estaEnJaqueTrasSimularMovimientoConTurnoActual(origen, destino), is(true));
	}
	
	/**
	 * Comprueba que en la simulación sí detecta una situación de jaque negativa.
	 * @throws CoordenadasIncorrectasException si alguna posición es incorrecta 
	 */
	@DisplayName("Comprobar que detecta situación de jaque negativo al simular movimiento")
	@Test
	void comprobarDeteccionDeJaqueNegativoMoviendoBlancas() throws CoordenadasIncorrectasException {

		// realizamos movimiento con blancas
		Celda origen = tablero.obtenerCelda(2, 2);
		Celda destino = tablero.obtenerCelda(1, 2); // Corregido de (2,1) a (1,2)
		
		// simulamos mover peón sin efecto en jaque
		assertThat("El movimiento deja al rey en jaque", arbitro.estaEnJaqueTrasSimularMovimientoConTurnoActual(origen, destino), is(false));
	}

	/**
	 * Comprueba que tras simular el número de piezas de cada color no cambia.
	 * @throws CoordenadasIncorrectasException si alguna posición es incorrecta 
	 */

	@DisplayName("Comprobar que no cambia el número de piezas tras simular movimiento")
	@Test
	void comprobarQueNoCambiaNumeroDePiezas() throws CoordenadasIncorrectasException {

		// realizamos movimiento con blancas
		Celda origen = tablero.obtenerCelda(2, 2);
		Celda destino = tablero.obtenerCelda(1, 2);
		
		// el número de piezas y el texto generado del tablero NO debe cambiar
		int numBlancasAntesSimular = tablero.obtenerNumeroPiezas(Color.BLANCO);
		int numNegrasAntesSimular = tablero.obtenerNumeroPiezas(Color.NEGRO);
		
		// simular
		arbitro.estaEnJaqueTrasSimularMovimientoConTurnoActual(origen, destino);
	
		assertThat("El número de piezas blancas ha cambiado", tablero.obtenerNumeroPiezas(Color.BLANCO), is(numBlancasAntesSimular));
		assertThat("El número de piezas negras ha cambiado", tablero.obtenerNumeroPiezas(Color.NEGRO), is(numNegrasAntesSimular));
	}
	
	/**
	 * Comprueba que tras simular el movimiento el texto del tablero no cambia.
	 * @throws CoordenadasIncorrectasException si alguna posición es incorrecta 
	 */
	@DisplayName("Comprobar que el texto del tablero no cambia al simular movimiento")
	@Test
	void comprobarQueCambiaElTextoDelTablero() throws CoordenadasIncorrectasException {

		// realizamos movimiento con blancas
		Celda origen = tablero.obtenerCelda(2, 2);
		Celda destino = tablero.obtenerCelda(1, 2);
				
		// el texto generado del tablero NO debe cambiar
		String tableroEnTextoAntesSimular = tablero.toString();
		
		// simular
		arbitro.estaEnJaqueTrasSimularMovimientoConTurnoActual(origen, destino);
		
		// y comprobar que el esperado es el obtenido
		assertEquals(tableroEnTextoAntesSimular, tablero.toString(), "El texto del tablero ha cambiado");
	}
}


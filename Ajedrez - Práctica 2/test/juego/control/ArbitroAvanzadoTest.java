package juego.control;

import static juego.modelo.Color.BLANCO;
import static juego.modelo.Color.NEGRO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import juego.modelo.Celda;
import juego.modelo.CoordenadasIncorrectasException;
import juego.modelo.Tablero;
import juego.modelo.pieza.Alfil;
import juego.modelo.pieza.Dama;
import juego.modelo.pieza.Peon;
import juego.modelo.pieza.Pieza;
import juego.modelo.pieza.Rey;

/**
 * Pruebas avanzadas para la detección de jaque.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20190813
 */
@DisplayName("Test avanzados de jaque sobre Arbitro")
public class ArbitroAvanzadoTest {
	
	/** Tablero. */
	private Tablero tablero;
	
	/** Arbitro. */
	private Arbitro arbitro;

	/**
	 * Inicializa el tablero.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error con las coordenadas
	 */
	//  @formatter:off
	/* Partimos de un tablero que debe tener este estado.
	*   a  b  c  d  e  f  g  h  
	* 8 RN -- -- -- -- -- -- --
	* 7 PN -- -- -- -- -- -- --
	* 6 -- -- -- AB -- -- -- --
	* 5 -- -- DB -- -- -- -- --
	* 4 -- -- -- -- -- -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 -- -- -- -- -- -- -- --
	* 1 -- -- RB -- -- -- -- --
	*/
	// @formatter:on
	@BeforeEach
	void iniciar() throws CoordenadasIncorrectasException {
		// Preparamos tablero
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		Rey reyNegro = new Rey(NEGRO);
		Rey reyBlanco = new Rey(BLANCO);
		Pieza[] piezas = { reyNegro, new Peon(NEGRO), new Alfil(BLANCO), new Dama(BLANCO), reyBlanco };
		int[][] posiciones = { { 0, 0 }, { 1, 0 }, { 2, 3 }, { 3, 2 }, { 7, 2 } };
		arbitro.colocarPiezas(piezas, posiciones, reyNegro, reyBlanco);
	}

	/**
	 * Comprobar jaque mate con mínima partida. Una vez movida la reina blanca
	 * ningún posible movimiento del peón negro ni del rey negro evita el jaque
	 * mate.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error con las coordenadas
	 */
	@DisplayName("Comprobar jaque mate con mínima partida")
	@Test
	void comprobarJaqueMateConPartidaMinima() throws CoordenadasIncorrectasException {

		// realizamos movimiento
		Celda origen = tablero.obtenerCelda(3, 2);
		Celda destino = tablero.obtenerCelda(2, 2);

		assertThat("El movimiento se detecta como no legal", arbitro.esMovimientoLegal(origen, destino), is(true));
		arbitro.mover(origen, destino);
		arbitro.cambiarTurno();

		// Comprobar que el rey negro está amenazado
		assertThat("Debería detectarse el jaque mate", arbitro.estaEnJaque(NEGRO), is(true));

		// para cualquier movimiento legal del peón o rey negro sigue amenazado
		// por lo tanto está en jaque mate y solo queda rendirse

		int[][] ori = { { 1, 0 }, { 1, 0 }, { 0, 0 }, { 0, 0 } };
		int[][] dest = { { 2, 0 }, { 3, 0 }, { 0, 1 }, { 1, 1 } };

		for (int i = 0; i < ori.length; i++) {
			origen = tablero.obtenerCelda(ori[i][0], ori[i][1]);
			destino = tablero.obtenerCelda(dest[i][0], dest[i][1]);
			arbitro.mover(origen, destino);
			assertThat("Debería detectarse el jaque", arbitro.estaEnJaque(NEGRO), is(true));
			arbitro.mover(destino, origen); // deshacer
		}
	}

	/**
	 * Comprobar movimiento que deja en jaque al propio rey. Se debería detectar
	 * para impedir esta situación en el interfaz en modo texto y gráfico.
	 * 
	 * Movemos el rey negro dejándolo amenazado por el alfil.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error con las coordenadas
	 */
	@DisplayName("Comprobar movimiento que deja en jaque al propio rey")
	@Test
	void comprobarMovimientoQueDejaEnJaqueAlPropioReyConAlfil() throws CoordenadasIncorrectasException {
		// Cambiamos turno al jugado negro
		arbitro.cambiarTurno();

		// realizamos movimiento dejando al rey al alcance de la reina
		Celda origen = tablero.obtenerCelda(0, 0);
		Celda destino = tablero.obtenerCelda(0, 1); // movemos a b8

		assertThat("No se detecta que el rey negro queda en jaque por el alfil",
				arbitro.estaEnJaqueTrasSimularMovimientoConTurnoActual(origen, destino), is(true));

	}
}

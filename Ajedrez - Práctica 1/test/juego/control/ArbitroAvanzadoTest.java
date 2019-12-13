package juego.control;

import static juego.modelo.Color.BLANCO;
import static juego.modelo.Color.NEGRO;
import static juego.modelo.Tipo.ALFIL;
import static juego.modelo.Tipo.DAMA;
import static juego.modelo.Tipo.PEON;
import static juego.modelo.Tipo.REY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import juego.modelo.Celda;
import juego.modelo.Color;
import juego.modelo.Tablero;
import juego.modelo.Tipo;

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
	void iniciar() {
		// Preparamos tablero
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		Tipo[] tipos = { REY, PEON, ALFIL, DAMA, REY };
		Color[] colores = { NEGRO, NEGRO, BLANCO, BLANCO, BLANCO };
		int[][] posiciones = { { 0, 0 }, { 1, 0 }, { 2, 3 }, { 3, 2 }, { 7, 2 } };
		arbitro.colocarPiezas(tipos, colores, posiciones);
	}

	/**
	 * Comprobar jaque mate con mínima partida. Una vez movida la reina blanca
	 * ningún posible movimiento del peón negro ni del rey negro evita el jaque
	 * mate.
	 */

	@DisplayName("Comprobar jaque mate con mínima partida")
	@Test
	void comprobarJaqueMateConPartidaMinima() {

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
	 */
	@DisplayName("Comprobar movimiento que deja en jaque al propio rey")
	@Test
	void comprobarMovimientoQueDejaEnJaqueAlPropioReyConAlfil() {
		// Cambiamos turno al jugado negro
		arbitro.cambiarTurno();

		// realizamos movimiento dejando al rey al alcance de la reina
		Celda origen = tablero.obtenerCelda(0, 0);
		Celda destino = tablero.obtenerCelda(0, 1); // movemos a b8

		assertThat("No se detecta que el rey negro queda en jaque por el alfil",
				arbitro.estaEnJaqueTrasSimularMovimientoConTurnoActual(origen, destino), is(true));

	}
}

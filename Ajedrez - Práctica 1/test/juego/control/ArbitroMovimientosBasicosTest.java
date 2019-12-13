package juego.control;

import static juego.modelo.Color.BLANCO;
import static juego.modelo.Color.NEGRO;
import static juego.modelo.Tipo.ALFIL;
import static juego.modelo.Tipo.CABALLO;
import static juego.modelo.Tipo.DAMA;
import static juego.modelo.Tipo.REY;
import static juego.modelo.Tipo.TORRE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import juego.modelo.Celda;
import juego.modelo.Color;
import juego.modelo.Tablero;
import juego.modelo.Tipo;

/**
 * Pruebas unitarias sobre el árbitro una vez colocadas las piezas.
 * Se prueban los movimientos básicos de la piezas.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20190813
 * 
 */
@DisplayName("Tests de funcionamiento básico en partida del árbitro")
public class ArbitroMovimientosBasicosTest {

	/** Tablero para testing. */
	private Tablero tablero;

	/** Arbitro. */
	private Arbitro arbitro;
	
	/**
	 * Inicialización del tablero antes de cada test.
	 */
	@BeforeEach
	void inicializar() {
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		arbitro.colocarPiezas();
	}

	/**
	 * Comprueba movimientos ilegales de un peón negro al iniciar partida.
	 * Necesita cambiar el turno.
	 *
	 * @param textoOrigen coordenadas celda origen
	 * @param textoDestino coordenadas celda destino
	 */
	@DisplayName("Comprueba movimientos ilegales de un peón negro al iniciar la partida")
	@ParameterizedTest
	@CsvSource({
		// @formatter:off
		"a7, a7", "a7, b6", "a7, a4", "a7, b5", "a7, a8", "a7, b8", "a7, b7",  // peon en esquina izquierda
		"d7, c8", "d7, d8", "d7, e8", "d7, c7", "d7, d7", "d7, e7", "d7, c6", "d7, e6", "d7, d4", // peon negro a mitad
		"h7, h7", "h7, h8", "h7, g8", "h7, g7", "h7, g6", "h7, h4", "h7, g5", "h7, g4", // peon en esquina derecha
	// 	@formatter:on
	})
	void movimientoIlegalPeonNegroAlInicio(String textoOrigen, String textoDestino) {
		Celda origen = tablero.obtenerCeldaParaNotacionAlgebraica(textoOrigen);
		Celda destino = tablero.obtenerCeldaParaNotacionAlgebraica(textoDestino);
		// OJO, cambiamos turno para que pueda mover negras (si no siempre da false por no tener el turno)
		arbitro.cambiarTurno(); 
		assertThat("Jugada ilegal para un peón negro al inicio de partida se reconoce como legal", 
				arbitro.esMovimientoLegal(origen, destino),	is(false));
	}
	
	/**
	 * Comprueba movimientos ilegales de un peón blanco al iniciar partida.
	 * 
	 * @param textoOrigen coordenadas celda origen
	 * @param textoDestino coordenadas celda destino
	 */
	@DisplayName("Comprueba movimientos ilegales de un peón blanco al iniciar la partida")
	@ParameterizedTest
	@CsvSource({
		// @formatter:off
		"a2, a2", "a2, b3", "a2, a5", "a2, b4", "a2, a1", "a2, b1", "a2, b2",  // peon en esquina izquierda
		"d2, c1", "d2, d1", "d2, e1", "d2, c2", "d2, d2", "d2, e2", "d2, c3", "d2, e3", "d2, d5", // peon negro a mitad
		"h2, h2", "h2, h1", "h2, g1", "h2, g2", "h2, g3", "h2, h5", "h2, g4", "h2, g5"  // peon en esquina derecha
	// 	@formatter:on
	})
	void movimientoIlegalPeonBlancoAlInicio(String textoOrigen, String textoDestino) {
		Celda origen = tablero.obtenerCeldaParaNotacionAlgebraica(textoOrigen);
		Celda destino = tablero.obtenerCeldaParaNotacionAlgebraica(textoDestino);
		assertThat("Jugada ilegal para un peón blanco al inicio de partida se reconoce como legal", 
				arbitro.esMovimientoLegal(origen, destino),	is(false));
	}
	
	/**
	 * Comprueba movimientos ilegales de todas las piezas negras no peón, 
	 * a excepción del caballo, al estar bloqueadas inicialmente.
	 * Estas piezas no pueden saltar sobre ninguna pieza.
	 * Necesita cambiar el turno para negras.
	 *
	 * @param textoOrigen coordenadas celda origen
	 */
	@DisplayName("Comprueba movimientos ilegales de piezas negras no peón (a excepción del caballo) al iniciar la partida")
	@ParameterizedTest
	@CsvSource({
		// @formatter:off
		"a8", "c8", "d8", "e8", "f8", "h8" // todas menos los caballos en b8 y g8
	// 	@formatter:on
	})
	void movimientoIlegalPiezasNegrasAlInicio(String textoOrigen) {
		// OJO, cambiamos turno para que pueda mover negras (si no siempre da false por no tener el turno)
		arbitro.cambiarTurno(); 
		Celda origen = tablero.obtenerCeldaParaNotacionAlgebraica(textoOrigen);
		
		for (Celda destino : tablero.obtenerCeldas()) {
			assertThat("Jugada ilegal para una pieza negra al inicio de partida se reconoce como legal", 
					arbitro.esMovimientoLegal(origen, destino),	is(false));
		}		
	}
	
	/**
	 * Comprueba movimientos ilegales de todas las piezas negras no peón, 
	 * a excepción del caballo, al estar bloqueadas inicialmente.
	 * Estas piezas no pueden saltar sobre ninguna pieza.
	 * Necesita cambiar el turno para negras.
	 *
	 * @param textoOrigen coordenadas celda origen
	 */
	@DisplayName("Comprueba movimientos ilegales de piezas blancas no peón (a excepción del caballo) al iniciar la partida")
	@ParameterizedTest
	@CsvSource({
		// @formatter:off
		"a1", "c1", "d1", "e1", "f1", "h1" // todas menos los caballos en b1 y g1
	// 	@formatter:on
	})
	void movimientoIlegalPiezasBlancasAlInicio(String textoOrigen) {
		Celda origen = tablero.obtenerCeldaParaNotacionAlgebraica(textoOrigen);
		for (Celda destino : tablero.obtenerCeldas()) {
			assertThat("Jugada ilegal para una pieza negra al inicio de partida se reconoce como legal", 
					arbitro.esMovimientoLegal(origen, destino),	is(false));
		}		
	}	
	
	/**
	 * Comprobar legalidad de movimiento de piezas en la última fila
	 * simplificando a que no hemos colocado peones delante. Colocamos
	 * el rey negro para cumplir con mínimos.
	 * 
	 * @param textoOrigen coordenadas celda origen
	 * @param textoDestino coordenadas celda destino
	 */
	/* @formatter:off
	*   a  b  c  d  e  f  g  h  
	* 8 -- -- -- -- -- -- -- --
	* 7 -- -- -- -- -- -- -- --
	* 6 -- -- -- -- -- -- -- --
	* 5 -- -- -- -- -- -- -- --
	* 4 -- -- -- -- RN -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 -- -- -- -- -- -- -- --
	* 1 TB CB AB DB RB AB CB TB
	*  @formatter:on
	*/
	@DisplayName("Comprueba movimiento legales en última fila sin peones delante")
	@ParameterizedTest
	@CsvSource({
		// @formatter:off
		"a1, a2", "a1, a4", "a1, a8", // movimientos de TORRE
		"b1, a3", "b1, c3", "b1, d2", // movimientos de CABALLO
		"c1, b2", "c1, a3", "c1, d2", "c1, h6", // movimientos de ALFIL
		"d1, c2", "d1, a4", "d1, d2", "d1, d8", "d1, e2", "d1, h5", // movimientos de DAMA
		"e1, d2", "e1, e2", "e1, f2", // movimientos de REY
		"f1, e2", "f1, a6", "f1, g2", "f1, h3", // movimientos de ALFIL
		"g1, e2", "g1, f3", "g1, h3", // movimientos de CABALLO
		"h1, h2", "h1, h5", "h1, h8"  // movimientos de TORRE
	// 	@formatter:on
	})
	void movimientoLegalPiezas(String textoOrigen, String textoDestino) {
		Tablero tableroLocal = new Tablero();
		Arbitro arbitroLocal = inicializarArbitroSoloConFilaInferiorBlanca(tableroLocal);
		// movimiento
		Celda origen = tableroLocal.obtenerCeldaParaNotacionAlgebraica(textoOrigen);
		Celda destino = tableroLocal.obtenerCeldaParaNotacionAlgebraica(textoDestino);
		assertThat("El movimiento no es legal con la pieza " + origen.obtenerPieza().obtenerTipo(),
				arbitroLocal.esMovimientoLegal(origen, destino), is(true));
	}
	
	/**
	 * Comprobar ilegalidad de movimiento de piezas en la última fila
	 * simplificando a que no hemos colocado peones delante. Colocamos
	 * el rey negro para cumplir con mínimos.
	 * 
	 * @param textoOrigen coordenadas celda origen
	 * @param textoDestino coordenadas celda destino
	 */
	/* @formatter:off
	*   a  b  c  d  e  f  g  h  
	* 8 -- -- -- -- -- -- -- --
	* 7 -- -- -- -- -- -- -- --
	* 6 -- -- -- -- -- -- -- --
	* 5 -- -- -- -- -- -- -- --
	* 4 -- -- -- -- RN -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 -- -- -- -- -- -- -- --
	* 1 TB CB AB DB RB AB CB TB
	*  @formatter:on
	*/
	@DisplayName("Comprueba movimientos ilegales en última fila sin peones delante")
	@ParameterizedTest
	@CsvSource({
		// @formatter:off
		"a1, b2", "a1, b3", "a1, c2", "a1, h8", // movimientos incorrectos de TORRE 
		"b1, a2", "b1, b2", "b1, c2", "b1, b8", "b1, h7", // movimientos incorrectos de CABALLO
		"c1, c2", "c1, c8", "c1, a4", "c1, b3", "c1, d3", "c1, e2", "c1, g8", // movimientos incorrectos de ALFIL
		"d1, b2", "d1, c3", "d1, e3", "d1, f2", // movimientos incorrectos de DAMA
		"e1, c3", "e1, e3", "e1, g3", "e1, d3", "e1, g2", // movimientos incorrectos de REY
		"f1, f2", "f1, d2", "f1, g3", "f1, f8", // movimientos incorrectos de ALFIL
		"g1, f2", "g1, g2", "g1, h2", "g1, c5", // movimientos incorrectos de CABALLO
		"h1, g2", "h1, f3", "h1, g3"  // movimientos incorrectos de TORRE
	// 	@formatter:on
	})
	void movimientoIlegalPiezas(String textoOrigen, String textoDestino) {
		Tablero tableroLocal = new Tablero();
		Arbitro arbitroLocal = inicializarArbitroSoloConFilaInferiorBlanca(tableroLocal);
		// movimiento
		Celda origen = tableroLocal.obtenerCeldaParaNotacionAlgebraica(textoOrigen);
		Celda destino = tableroLocal.obtenerCeldaParaNotacionAlgebraica(textoDestino);
		assertThat("El movimiento es legal con la pieza " + origen.obtenerPieza().obtenerTipo(),
				arbitroLocal.esMovimientoLegal(origen, destino), is(false));
	}

	/**
	 * Inicializa el arbitro con piezas blancas en la fila inferior y el rey negro.
	 * 
	 * @param tableroLocal tablero
	 * @return arbitro con piezas blancas en la última fila del tablero
	 */
	private Arbitro inicializarArbitroSoloConFilaInferiorBlanca(Tablero tableroLocal) {
		Arbitro arbitroLocal = new Arbitro(tableroLocal);
		Tipo[] tipos= { TORRE, CABALLO, ALFIL, DAMA, REY, ALFIL, CABALLO, TORRE, REY };
		Color[] colores = { BLANCO, BLANCO, BLANCO, BLANCO, BLANCO, BLANCO, BLANCO, BLANCO, NEGRO };
		int[][] posiciones = { {7, 0}, {7, 1}, {7, 2}, {7, 3}, {7, 4}, {7, 5}, {7, 6}, {7, 7}, {4,4} };
		arbitroLocal.colocarPiezas(tipos, colores, posiciones);
		return arbitroLocal;
	}
	
	/**
	 * Comprueba movimientos ilegales de celdas vacías en origen.
	 *
	 * @param textoOrigen coordenadas celda origen
	 */
	@DisplayName("Comprueba movimientos ilegales con celdas vacías en origen")
	@ParameterizedTest
	@CsvSource({
		// @formatter:off
		"a3", "c6", "d5", "e4", "f6", "h3" // se toman celdas del medio del tablero
	// 	@formatter:on
	})
	void movimientoIlegalConCeldaOrigenVacia(String textoOrigen) {
		Celda origen = tablero.obtenerCeldaParaNotacionAlgebraica(textoOrigen);
		assertNotNull(origen, "La celda debería existir");
		for (Celda destino : tablero.obtenerCeldas()) {
			assertThat("Jugada ilegal para una celda origen que está vacía", 
					arbitro.esMovimientoLegal(origen, destino),	is(false));
		}		
	}
}

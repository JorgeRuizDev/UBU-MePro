package juego.control;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import juego.modelo.Celda;
import juego.modelo.CoordenadasIncorrectasException;
import juego.modelo.Tablero;

/**
 * Pruebas de partidas completas comprobando la detección de movimientos
 * legales e ilegales junto con la cuenta de jugadas.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20190813
 * 
 */
@DisplayName("Tests de funcionamiento de partidas completas en árbitro")
public class ArbitroPartidasCompletasTest {

	/** Tablero para testing. */
	private Tablero tablero;

	/** Arbitro. */
	private Arbitro arbitro;
	
	/**
	 * Jaque pastor.
	 * Utilizando notación simple y entre comentarios la notación SAN junto
	 * con aclaraciones.
	 */
	final String[] JAQUE_PASTOR = {
			// @formatter:off
			"e2e4", // e4 peón blancas 
			"e7e5", // e5 peón negras
			"d1h5", // Dh5 dama blanca 
			"b8c6", // Cc6 caballo negro
			"f1c4", // Ac4 alfil blanco
			"g8f6", // Cf6 caballo negro
			"h5f7"  // Dxf7++ jaque mate con dama blanca  
		// 	@formatter:on
	};
	
	/**
	 * Partida 2 de Karpov.
	 * Utilizando notación simple y entre comentarios la notación SAN.
	 */
	final String[] KARPOV_PARTIDA_2 = {
		// @formatter:off OJO notación en inglés
		"e2e4", // e4
		"c7c6", // c6
		"d2d4", // d4
		"d7d5", // d5
		"e4e5", // e5
		"c8f5", // Bf5
		"b1c3", // Nc3
		"e7e6", // e6
		"g2g4", // g4
		"f5g6", // Bg6
		"g1e2", // Nge2
		"g8e7", // Ne7
		"e2f4", // Nf4
		"c6c5", // c5
		"d4c5", // dxc5
		"b8d7", // Nd7
		"h2h4", // h4
		"d7e5", // Nxe5
		"f1g2", // Bg2
		"d5d4", // d4
		"h4h5", // h5
		"d4c3", // dxc3
		"d1d8", // Qxd8+
		"a8d8", // Rxd8
		"h5g6", // hxg6
		"e7g6", // N7xg6
		"b2b4", // b4
		"g6f4", // Nxf4
		"c1f4", // Bxf4
		"e5c6", // Nc6
		"a1b1", // Rb1
		"f8e7", // Be7
		"f4e3", // Be3
		"e8g8", // O-O
		// se eliminan los movimientos posteriores
		// @formatter:on
	};	
	
	/** 
	 * Partida 8 de Alekhine sin enroques. 
	 * Utilizando notación simple y entre comentarios la notación SAN.
	 * 
	 */
	static final String[] ALEKHINE_PARTIDA_8 = {
			"e2e4", // e4
			"b7b6", // b6
			"d2d4", // d4
			"c8b7", // Bb7
			"b1c3", // Nc3
			"e7e6", // e6
			"f1d3", // Bd3
			"g8f6", // Nf6
			"c1g5", // Bg5
			"f8e7", // Be7
			"g1h3", // Nh3
			"d7d5", // d5
			"g5f6", // Bxf6
			"g7f6", // gxf6
			"d1g4", // Qg4
			"d5e4", // dxe4
			"g4g7", // Qg7
			"h8f8", // Rf8
			"c3e4", // Nxe4
			"f6f5", // f5
			"e4g5", // Neg5
			"e7f6", // Bf6
			"g7h6", // Qh6
			"d8d4", // Qxd4
			"a1d1", // Rd1
			"d4e5", // Qe5+
			"e1f1", // Kf1
			"f6g7", // Bg7
			"h6h5", // Qh5
			"h7h6", // h6
			"d1e1", // Re1
			"b7e4", // Be4
			"d3e4", // Bxe4
			"f5e4", // fxe4
			"e1e4", // Rxe4
			"e5d5", // Qd5
			"g5e6", // Nxe6
			"d5e4", // Qxe4
			"e6g7", // Nxg7+
			"e8d8", // Kd8
			"h5h6", // Qxh6
			"b8d7", // Nd7
			"h3f4", // Nf4
			"d8c8", // Kc8
			"f4d3", // Nd3
			"c8b7", // Kb7
			"h6f4", // Qf4
			"e4g6", // Qg6
			"g7f5", // Nf5
			"a8e8", // Rae8
			"g2g3", // g3
			"g6c6", // Qc6
			"f1g1", // Kg1
			"d7c5", // Nc5
			"d3e5", // Ne5
			"c6d5", // Qd5
			"e5f3", // Nf3
			"e8e4", // Re4
			"f4g5", // Qg5
			"e4e2", // Re2
			"g1g2", // Kg2
			"e2f2", // Rxf2+
			"g2h3", // Kh3
			"f8h8", // Rh8+
			"f5h4", // N5h4
			"d5f3", // Qxf3
			"h1e1", // Re1
			"f7f6" // f6 BLACK_WON por rendición o tiempo (no por jaque mate)
	};
	
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
	 * Comprueba la legalidad de movimientos en Jaque Pastor.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error con las coordenadas
	 */
	@DisplayName("Comprueba legalidad de los movimientos en Jaque Pastor")
	@Test
	void comprobarLegalidadDeMovimientosEnJaquePastor() throws CoordenadasIncorrectasException { 	
		assertThat("El número de jugadas iniciales debería ser 0", arbitro.obtenerNumeroJugada(),is(0));

		for (String jugada : JAQUE_PASTOR) {
			String textoOrigen = jugada.substring(0, 2);
			String textoDestino = jugada.substring(2, 4);
			Celda origen = tablero.obtenerCeldaParaNotacionAlgebraica(textoOrigen);
			Celda destino = tablero.obtenerCeldaParaNotacionAlgebraica(textoDestino);
			assertThat("Moviendo de " + textoOrigen + " a " + textoDestino,
					arbitro.esMovimientoLegal(origen, destino), is(true));
			arbitro.mover(origen, destino);
			arbitro.cambiarTurno();

		}
		assertThat("El número de jugadas finales es incorrecto", arbitro.obtenerNumeroJugada(),is(7));
	}
	
	/**
	 * Comprueba los primeros 34 movimientos de una partida de Karpov.
	 * Se detiene en el primer enroque corto que se produce 
	 * al no estar implementado.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error con las coordenadas
	 */
	@DisplayName("Comprueba legalidad de los movimientos en KARPOV_PARTIDA_2")
	@Test
	void comprobarLegalidadDeMovimientosEnKARPOV_PARTIDA_2() throws CoordenadasIncorrectasException { 	
		assertThat("El número de jugadas iniciales debería ser 0", arbitro.obtenerNumeroJugada(),is(0));
		for (String jugada : KARPOV_PARTIDA_2) {
			String textoOrigen = jugada.substring(0, 2);
			String textoDestino = jugada.substring(2, 4);
			Celda origen = tablero.obtenerCeldaParaNotacionAlgebraica(textoOrigen);
			Celda destino = tablero.obtenerCeldaParaNotacionAlgebraica(textoDestino);
			if (arbitro.obtenerNumeroJugada() < 33) {
				assertThat("Movimiento " + arbitro.obtenerNumeroJugada() + "  de " + textoOrigen + " a " + textoDestino,
					arbitro.esMovimientoLegal(origen, destino), is(true));
				arbitro.mover(origen, destino);
				arbitro.cambiarTurno();
			}
			else {
				assertThat("Enroque no implementado moviendo de " + textoOrigen + " a " + textoDestino,
						arbitro.esMovimientoLegal(origen, destino), is(false));
				// último movimiento es un enroque que no está recogido como legal
			}
		}
		assertThat("El número de jugadas finales es incorrecto", arbitro.obtenerNumeroJugada(),is(33));

	}
	
	/**
	 * Comprueba la partida completa número 8 de Alekhine.
	 * 
	 * @throws CoordenadasIncorrectasException si hay algún error con las coordenadas
	 */
	@DisplayName("Comprueba legalidad de los movimientos en ALEKHINE_PARTIDA_8")
	@Test
	void comprobarLegalidadDeMovimientosEnALEKHINE_PARTIDA_8() throws CoordenadasIncorrectasException { 
		assertThat("El número de jugadas iniciales debería ser 0", arbitro.obtenerNumeroJugada(),is(0));
		for (String jugada : ALEKHINE_PARTIDA_8) {
			String textoOrigen = jugada.substring(0, 2);
			String textoDestino = jugada.substring(2, 4);
			Celda origen = tablero.obtenerCeldaParaNotacionAlgebraica(textoOrigen);
			Celda destino = tablero.obtenerCeldaParaNotacionAlgebraica(textoDestino);
			assertThat("Moviendo de " + textoOrigen + " a " + textoDestino,
					arbitro.esMovimientoLegal(origen, destino), is(true));
			arbitro.mover(origen, destino);
			arbitro.cambiarTurno();
		}
		assertThat("El número de jugadas finales es incorrecto", arbitro.obtenerNumeroJugada(),is(68));
	}
}

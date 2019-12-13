package juego.control;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import juego.modelo.CoordenadasIncorrectasException;
import juego.modelo.Tablero;
import juego.modelo.pieza.Pieza;

/**
 * Comprobación de instanciación correcta de piezas no compartiendo referencias
 * si no generando objetos diferentes.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20191123
 */
@DisplayName("Test sobre referencias a objetos Pieza una vez colocadas las piezas.")
public class ArbitroInstanciacionPiezasTest {

	/** Arbitro. */
	private Arbitro arbitro;

	/** Tablero. */
	private Tablero tablero;

	/**
	 * Inicialización de piezas nuevas sobre el tablero.
	 */
	@BeforeEach
	void colocarPiezas() {
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		arbitro.colocarPiezas();
	}
	
	
	/**
	 * Comprueba que se genera un objeto peón diferente y que no 
	 * se comparten referencias.
	 * @throws CoordenadasIncorrectasException si alguna posición es incorrecta 
	 */
	//  @formatter:off
	/* Partimos de un tablero que debe tener este estado.
	*   a  b  c  d  e  f  g  h  
	* 8 TN CN AN DN RN AN CN TN 
	* 7 PN PN PN PN PN PN PN PN
	* 6 -- -- -- -- -- -- -- --
	* 5 -- -- -- -- -- -- -- --
	* 4 -- -- -- -- -- -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 PB PB PB PB PB PB PB PB 
	* 1 TB CB AB DB RB AB CB TB
	*/
	// @formatter:on
	@DisplayName("Comprobar peones negros no comparten referencias en la segunda fila")
	@Test
	void comprobarReferenciasDiferentesEnPeonesNegros() throws CoordenadasIncorrectasException {
		int[][] coordenadas = { {1,0}, {1,1}, {1,2}, {1,3}, {1,4}, {1,5}, {1,6}, {1,7} };
		
		for (int i = 0; i < coordenadas.length; i++) {
			int filaOrigen = coordenadas[i][0];
			int columnaOrigen = coordenadas[i][1];
			Pieza origen = tablero.obtenerCelda(filaOrigen, columnaOrigen).obtenerPieza();
			for (int j = 0; j < coordenadas.length; j++) {
				if (i != j) {
					int fila = coordenadas[j][0];
					int columna = coordenadas[j][1];
					Pieza destino = tablero.obtenerCelda(fila, columna).obtenerPieza();
					assertFalse(origen == destino,"Las referencias deben ser diferentes");
				}
			}
		}
	}
	
	/**
	 * Comprueba que se generan objetos diferentes entre los NO peones y que no 
	 * se comparten referencias.
	 * 
	 * @throws CoordenadasIncorrectasException si alguna posición es incorrecta 
	 */
	//  @formatter:off
	/* Partimos de un tablero que debe tener este estado.
	*   a  b  c  d  e  f  g  h  
	* 8 TN CN AN DN RN AN CN TN 
	* 7 PN PN PN PN PN PN PN PN
	* 6 -- -- -- -- -- -- -- --
	* 5 -- -- -- -- -- -- -- --
	* 4 -- -- -- -- -- -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 PB PB PB PB PB PB PB PB 
	* 1 TB CB AB DB RB AB CB TB
	*/
	// @formatter:on
	@DisplayName("Comprobar piezas torre, caballo y alfil no comparten referencias en la primera fila")
	@Test
	void comprobarReferenciasDiferentesEntreParesDePiezasNoPeon() throws CoordenadasIncorrectasException {
		int[][] coordenadas = { {0,0}, {0,1}, {0,2} };
		
		for (int i = 0; i < coordenadas.length; i++) {
			int filaOrigen = coordenadas[i][0];
			int columnaOrigen = coordenadas[i][1];
			// tomamos las coordenadas espejadas...
			int filaDestino = coordenadas[i][0];
			int columnaDestino = Tablero.NUMERO_COLUMNAS - 1 - coordenadas[i][1];
			// tomamos piezas y comparamos referencias
			Pieza origen = tablero.obtenerCelda(filaOrigen, columnaOrigen).obtenerPieza();
			Pieza destino = tablero.obtenerCelda(filaDestino, columnaDestino).obtenerPieza();
			assertFalse(origen == destino,"Las referencias deben ser diferentes");			
		}
	}
	
	
}

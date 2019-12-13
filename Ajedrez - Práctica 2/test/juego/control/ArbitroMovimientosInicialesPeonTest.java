package juego.control;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import juego.modelo.Celda;
import juego.modelo.CoordenadasIncorrectasException;
import juego.modelo.Tablero;
import juego.modelo.pieza.Pieza;

/**
 * Comprobación de movimientos iniciales de peón.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20191123
 */
@DisplayName("Test sobre movimientos iniciales de peón.")
public class ArbitroMovimientosInicialesPeonTest {

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
	 * Comprueba que una vez movido el peón lo marca como movido.
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
	@DisplayName("Comprobar que peón blanco queda marcado una vez movido")
	@Test
	void comprobarQuePeonQuedaMarcadoPrimerMovimentoUnaVezMovido() throws CoordenadasIncorrectasException {
		Celda origen = tablero.obtenerCelda(6, 4);
		Celda destino = tablero.obtenerCelda(4, 4);
		arbitro.mover(origen, destino);
		assertThat("No marca el peón como movido", destino.obtenerPieza().esPrimerMovimiento(), is(false));
	}
	
	/**
	 * Comprueba que cuando se simula el movimiento el peón no queda marcado.
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
	@DisplayName("Comprobar que peón blanco no queda marcado si se simula el movimiento")
	@Test
	void comprobarQuePeonNoQuedaMarcadoAlSimular() throws CoordenadasIncorrectasException {
		Celda origen = tablero.obtenerCelda(6, 4);
		Pieza peon = origen.obtenerPieza();
		assertNotNull(peon, "No puede ser nulo el peón");
		assertThat("El peón tiene que estar en su primer movimiento", peon.esPrimerMovimiento(), is(true));
		
		// simulamos el movimiento, no tiene que contar como movimiento
		Celda destino = tablero.obtenerCelda(4, 4);
		arbitro.estaEnJaqueTrasSimularMovimientoConTurnoActual(origen, destino);
		assertThat("Marca el peón como movido al simular", peon.esPrimerMovimiento(), is(true));
	}

	/**
	 * Comprueba que una vez movido con distancia dos, luego solo puede avanzar uno.
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
	@DisplayName("Comprobar que peón blanco no puede avanzar con distancia 2, más de una vez")
	@Test
	void comprobarQueNoPuedeAvanzarDistanciaDosUnPeonDosVeces() throws CoordenadasIncorrectasException {
		Celda origen = tablero.obtenerCelda(6, 4);
		Celda destino = tablero.obtenerCelda(4, 4);
		arbitro.mover(origen, destino);
		// no cambiamos turno, vuelve a mover blancas
		origen = tablero.obtenerCelda(4, 4);
		destino = tablero.obtenerCelda(2, 4);
		assertThat("Deja volver a avanzar dos una vez realizado un avance de dos",
				arbitro.esMovimientoLegal(origen, destino), is(false));
	}

}

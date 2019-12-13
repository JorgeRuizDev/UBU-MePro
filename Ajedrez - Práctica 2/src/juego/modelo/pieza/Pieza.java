package juego.modelo.pieza;

//Imports
import juego.util.Sentido;
import juego.modelo.Celda;
import juego.modelo.Color;

/**
*	Interfaz que de la pieza.
*	@author Jorge Ruiz Gómez.
*   @since JDK 11.
*   @version 2.0.
*/


public interface Pieza {
	
	
	/**
	 * Método que devuelve true o false si hay una pieza entre medias.
	 * @param destino Celda de destino.
	 * @param sentido Sentido en el que se mueve la pieza.
	 * @param hayPiezasEntreMedias	Booleano que indica si hay piezas entre medias o no.
	 * @return boolean.
	 */
	public boolean esCorrectoMoverA(Celda destino, Sentido sentido, boolean hayPiezasEntreMedias);
	
	/**
	 * Booleano que indica si la pieza se ha movido o no por primera vez.
	 * @return	boolean.
	 */
	public boolean esPrimerMovimiento();
	
	/**
	 * Método que permite establecer la celda en la que se encuentra la pieza.
	 * 
	 * @param celda Celda en la que se coloca la pieza.
	 */
	public void establecerCelda(Celda celda);
	
	/**
	 * Método que altera el estado de una pieza en cuant a su movimiento.
	 * Este método fija el estado de primer movimiento a "Ha sido movida por primera vez"
	 */
	public void marcarPrimerMovimiento();
	
	/**
	 * Método que devuelve la celda actual del tablero en la que se encuentra la pieza.
	 * @return Celda
	 */
	public Celda obtenerCelda();
	
	/**
	 * Método que devuelve el color de la pieza.
	 * @return Enum Color
	 */
	public Color obtenerColor();
	
	/**
	 * Método que devuelve el caracter equivalente al color de la pieza.
	 * @return char (B ó N).
	 */
	public char toChar();
}
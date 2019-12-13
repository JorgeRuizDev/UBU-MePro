package juego.modelo.pieza;

import juego.modelo.Celda;
import juego.modelo.Color;
import juego.util.Sentido;

/**
*	Objeto de tipo Alfil.
*	@author Jorge Ruiz Gómez.
*	@version 2.0.
*	@since JDK 11.
*/

public class Alfil extends PiezaAbstracta{
	
	/**
	 * Constructor de la clase.
	 * Asigna la letra de la pieza y un color.
	 * @param color Color de la pieza.
	 */
	public Alfil(Color color) {
		
		super(color);
		this.letra='A';
	}
	
	
	@Override
	public boolean esCorrectoMoverA(Celda destino, Sentido sentido, boolean hayPiezasEntreMedias) {
		if (this.sonArgumentosNulos(destino, sentido, hayPiezasEntreMedias))
			return false;
		
		return esCorrectoMoverDiagonal(destino, sentido, hayPiezasEntreMedias);
	}

	
}
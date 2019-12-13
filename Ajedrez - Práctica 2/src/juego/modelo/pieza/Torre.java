package juego.modelo.pieza;

import juego.modelo.Celda;
import juego.modelo.Color;
import juego.util.Sentido;

/**
*	Objeto de tipo Torre.
*	@author Jorge Ruiz Gómez.
*	@version 2.0.
*	@since JDK 11.
*/

public class Torre extends PiezaAbstracta{
	
	/**
	 * Constructor de la clase.
	 * Asigna la letra de la pieza y un color.
	 * @param color Color de la pieza.
	 */
	public Torre(Color color) {
		
		super(color);
		this.letra='T';
	}

	@Override
	public boolean esCorrectoMoverA(Celda destino, Sentido sentido, boolean hayPiezasEntreMedias) {
		if (this.sonArgumentosNulos(destino, sentido, hayPiezasEntreMedias))
			return false;
		
		return esCorrectoMoverRecto(destino, sentido, hayPiezasEntreMedias);
	}

}
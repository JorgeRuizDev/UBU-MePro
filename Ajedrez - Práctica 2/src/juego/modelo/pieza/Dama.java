package juego.modelo.pieza;

import juego.modelo.Celda;
import juego.modelo.Color;
import juego.util.Sentido;

/**
*	Objeto de tipo Dama.
*	@author Jorge Ruiz Gómez.
*	@version 2.0.
*	@since JDK 11.
*/

public class Dama extends PiezaAbstracta{
	
	/**
	 * Constructor de la clase.
	 * Asigna la letra de la pieza y un color.
	 * @param color Color de la pieza.
	 */
	public Dama(Color color) {
		
		super(color);
		this.letra='D';
	}
	
	@Override
	public boolean esCorrectoMoverA(Celda destino, Sentido sentido, boolean hayPiezasEntreMedias) {
		
		if (this.sonArgumentosNulos(destino, sentido, hayPiezasEntreMedias))
			return false;
		
		if (super.queSentidoEs(sentido) %2 == 0)
			return super.esCorrectoMoverRecto	(destino, sentido, hayPiezasEntreMedias);
		else
			return super.esCorrectoMoverDiagonal(destino, sentido, hayPiezasEntreMedias);
			
	}
}
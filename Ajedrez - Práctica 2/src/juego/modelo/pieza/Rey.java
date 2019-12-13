package juego.modelo.pieza;

import juego.modelo.Celda;
import juego.modelo.Color;
import juego.util.Sentido;

/**
*	Objeto de tipo Rey.
*	@author Jorge Ruiz Gómez.
*	@version 2.0.
*	@since JDK 11.
*/

public class Rey extends PiezaAbstracta{

	/**
	 * Constructor de la clase.
	 * Asigna la letra de la pieza y un color.
	 * @param color Color de la pieza.
	 */
	public Rey(Color color) {
		
		super(color);
		this.letra='R';
	}
	
	@Override
	public boolean esCorrectoMoverA(Celda destino, Sentido sentido, boolean hayPiezasEntreMedias) {
		if (this.sonArgumentosNulos(destino, sentido, hayPiezasEntreMedias))
			return false;
		
		int horizontal = super.obtenerNumeroDeCeldasHorizontales(super.celda, destino);
		int vertical   = super.obtenerNumeroDeCeldasVerticales(super.celda, destino);

		int v = Math.abs(vertical);												//Valor absoluto del avance en vertical (Para facilitar expresiones)
		int h = Math.abs(horizontal);											//Valor absoluto del avance en horizontal
		
		if((v == 0 && h ==1 ) || (v == 1 && h == 0) || (v == 1 && h == 1)) {	//Si avanza una casilla en todas las direcciones
			return true;
		}
		
		return false;
	}

	
}
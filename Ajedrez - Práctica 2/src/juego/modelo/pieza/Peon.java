package juego.modelo.pieza;

import juego.modelo.Celda;
import juego.modelo.Color;
import juego.util.Sentido;

/**
*	Objeto de tipo Peón.
*	@author Jorge Ruiz Gómez.
*	@version 2.0.
*	@since JDK 11.
*/

public class Peon extends PiezaAbstracta{
	
	/**
	 * Constructor de la clase.
	 * Asigna la letra de la pieza y un color.
	 * @param color Color de la pieza.
	 */
	public Peon(Color color) {
		
		super(color);
		this.letra='P';
	}

	@Override
	public boolean esCorrectoMoverA(Celda destino, Sentido sentido, boolean hayPiezasEntreMedias) {
		
		if (this.sonArgumentosNulos(destino, sentido, hayPiezasEntreMedias))
			return false;		
			
		if (hayPiezasEntreMedias == true)
			return false;
		
		/*
		 No usamos el Sentido en esta función, porque el peón puede moverse en todos los sentidos dependiendo del color. 
		 Como queremos comprobar que un peon pueda realizar ese movimiento, lo hacemos con las casillas en valor absoluto,
		 lo que nos permite comprobar si es un movimiento válido para la pieza sin necesidad de Sentido. 
		 */
		
		int horizontal = super.obtenerNumeroDeCeldasHorizontales(super.celda, destino);
		int vertical   = super.obtenerNumeroDeCeldasVerticales(super.celda, destino);
	
		if (color == Color.BLANCO && (vertical >0 ) )								//Si blanco retrocede
			return false;
		
		if (color == Color.NEGRO && (vertical <0 ) )								//Si negro retrocede
			return false;

		if (this.esPrimerMovimiento() == true && Math.abs(vertical) == 2 && horizontal ==0 && destino.estaVacia()) {	//Primer movimiento (2 casillas vacías)															//Comprobamos si el PEON se mueve 2 casillas en vertical
			return true;
		}
		
		else if (Math.abs(vertical) == 1 && Math.abs(horizontal) == 0 && destino.estaVacia()) {	//Moviemiento en vertical de 1 unidad y no hay enemigo.
			return true;
		} 
	
		else if (Math.abs(vertical) == 1  && Math.abs(horizontal) == 1 && (destino.estaVacia() == false)) {
			if (this.esPiezaAmiga(celda, destino)) {		//Si la celda tiene a un ENEMIGO
				return false;
			}
			else
				return true;
		}
			
		return false;
	
	}
	
}
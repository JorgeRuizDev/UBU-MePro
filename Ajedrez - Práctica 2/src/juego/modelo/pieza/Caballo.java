package juego.modelo.pieza;

import juego.modelo.Celda;
import juego.modelo.Color;
import juego.util.Sentido;

/**
*	Objeto de tipo Caballo.
*	@author Jorge Ruiz Gómez.
*	@version 2.0.
*	@since JDK 11.
*/

public class Caballo extends PiezaAbstracta{
	
	/**
	 * Constructor de la clase.
	 * Asigna la letra de la pieza y un color.
	 * @param color Color de la pieza.
	 */
	public Caballo(Color color) {
		
		super(color);
		this.letra='C';
	}
	
	@Override
	public boolean esCorrectoMoverA(Celda destino, Sentido sentido, boolean hayPiezasEntreMedias) {
		

		return esCorrectoCaballo(destino);
	}
	
	/**
	 * Método esCorrectoMoverA simplificado.
	 * Con el caballo no podemos usar el sentido, ya que no es diagonal.
	 * Con el caballo no necesitamos saber si hay piezas entre medias, ya que este salta.
	 * @param destino Celda de destino
	 * @return Booleano true o false.
	 */
	private boolean esCorrectoCaballo (Celda destino) {
		
		int horizontal = obtenerNumeroDeCeldasHorizontales (super.celda, destino);
		int vertical   = obtenerNumeroDeCeldasVerticales (super.celda, destino);
		
		int v = Math.abs (vertical);										//Obtenemos el valor absoluto de vertical para comparar
		int h = Math.abs(horizontal);										//Obtenemos el valor absoluto de horizontal para comprar
		
		if ((v == 2 && h == 1) || (v == 1 && h == 2))						//Comprobamos que el movimiento es válido (Una L positiva en ambos sentidos)
			return true;
		else
			return false;
	}
}
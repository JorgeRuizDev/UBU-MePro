package juego.modelo;

/**
 * Tipo enumerado que contiene las distintas piezas del tablero.
 * 
 * @author Jorge Ruiz Gómez
 * @see JDK 11
 * @version 1.0
 */

/**
 * Tipo enumerado con todas las piezas que serán usadas en el juego. 
 */
public enum Tipo {
	/**
	 * REY R.
	 */
	
	REY('R'),
	
	/**
	 * DAMA D.
	 */
	DAMA('D'),
	
	/**
	 * TORRE T.
	 */
	
	TORRE('T'),
	
	/**
	 * ALFIL A.
	 */
	ALFIL('A'),
	
	/**
	 * CABALLO C.
	 */
	CABALLO('C'),
	
	/**
	 * PEON P.
	 */
	PEON('P');
	
	/**
	 * Carácter letra usado para asignar la letra del elemento del enum.
	 * 
	 */
	private char letra;
	
	/**
	 * Constructor del tipo enumerado.
	 * @param letra Caracter de la pieza.
	 * @see letra
	 */
	private Tipo(char letra) {
		this.letra = letra;
	}
	
	/**
	 * Devuelve la del enum seleccionado, como el valor primitivo char.
	 * @return letra, caracter del tipo enumerado.
	 */
	public char toChar() {
		
		return this.letra;
	}
	
}
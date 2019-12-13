package juego.modelo;
/**
 * Tipo enumerado que contiene los colores de las piezas del juego.
 * 
 * @author Jorge Ruiz Gómez
 * @since JDK 11
 * @version 2.0
 * 
 */

public enum Color{
	/**
	 * Color blanco (B).
	 */
	BLANCO('B'),
	/**
	 * Color negro (N).
	 */
	NEGRO('N');

	/**
	 * Caracter del enum.
	 * 
	 */
	
	private char caracter;
	
	/**
	 * Setter que añade el caracter correspondiente al color.
	 * @param c char.
	 */
	private Color (char c) {
		
		caracter=c;
	}
	
	/**
	 * Getter que devuelve el caracter correspondiente al return.
	 * 
	 * @return char (B ó N).
	 */
	public char toChar(){
		
		return caracter;
		
	}
}
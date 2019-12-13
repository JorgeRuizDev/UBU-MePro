package juego.modelo;
/**
 * 
 * 
 * @author Jorge Ruiz Gómez
 * 
 * 
 */

public enum Color{
	
	BLANCO('O'),NEGRO('X');

	private char caracter;
	
	private Color (char c) {
		
		caracter=c;
		
	}
	public char toChar(){
		
		return caracter;
		
	}
}
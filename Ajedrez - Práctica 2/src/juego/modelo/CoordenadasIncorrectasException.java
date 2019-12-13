package juego.modelo;
/**
 * Clase destinada al manejo de excepciones del programa.
 * 
 * @author Jorge Ruiz Gómez.
 * @version 2.0.
 * @since JDK11.
 */

@SuppressWarnings("serial")
public class CoordenadasIncorrectasException extends Exception{
	
	/**
	 * Constructor por defecto de la clase.
	 */
	CoordenadasIncorrectasException(){
		
		super();
	}
	
	/**
	 * Constructor.
	 * @param message String con el mensaje de la excepción.
	 */
	CoordenadasIncorrectasException(String message){
		
		super(message);
	}
	
	/**
	 * Constructor.
	 * @param cause Parámetro con otra excepción de tipo Throwable. 
	 */
	CoordenadasIncorrectasException(Throwable cause){
		super(cause);
		
	}
	
	/**
	 * Constructor.
	 * @param cause Parámetro con otra excepción de tipo Throwable.
	 * @param message String con el mensaje de la excepción.
	 */
	CoordenadasIncorrectasException(String message, Throwable cause){
		
		super(message,cause);
	}
}

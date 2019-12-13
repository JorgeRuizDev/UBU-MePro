package juego.modelo;

/**
 * Clase de manipulación de piezas.
 * 
 * @author Jorge Ruiz Gómez.
 * @since JDK 11.
 * @version 1.0.
 * 
 */

public class Pieza{
	
	//Átributos
	/**
	 * Color al que pertenece la pieza.
	 */
	private Color color;
	
	
	/**
	 * Tipo del que es la pieza.
	 */
	private Tipo tipo;
	
	
	/**
	 * Celda en la que está la pieza.
	 */
	private Celda celda;
	
	
	/**
	 * Booleano que almacena si es el primer movimiento o no.
	 */
	private boolean esPrimerMov;
	
	//Constructores
	/**
	 * Constructor de la pieza.
	 * Asigna los elementos pasados, e indica que la pieza nunca se ha movido.
	 * @param color de la pieza y tipo de pieza.
	 * @param tipo Tipo de la pieza.
	 */
	public Pieza(Tipo tipo, Color color) {
		establecerColor(color);
		this.tipo = tipo;
		this.esPrimerMov=true;
	}

	//Métodos
	
	/**
	 * COLORES:
	 * 
	 * Establece el color pasado como variable.
	 * @param color de tipo enumerado Color.
	 * @see color
	 */
	private void establecerColor(Color color) {
		//Establece el color 
		this.color=color;
		
	}
	/**
	 * Se obtiene el color actual de la pieza.
	 * @see color
	 * @return color
	 */
	public Color obtenerColor() {
		
		return color;
		
	}
	
	/**
	 * CELDAS:
	 * Establece el objeto celda en la que se encuentra la pieza.
	 * Es un setter.
	 * 
	 * @param celda de tipo Celda.
	 * @see celda
	 */
	public void establecerCelda (Celda celda){
		
		this.celda=celda;
	}
	/**
	 * Getter que devuelve la celda en la que se encuentra el objeto.
	 * @return Celda celda.
	 * @see celda
	 */
	public Celda obtenerCelda() {
		
		if (this.celda == null) 
			return null;
		else
			return this.celda;
	}
	
	/**
	 * Getter que devuelve el tipo generado de la pieza.
	 * @return enum Pieza pieza.
	 * @see tipo
	 */
	public Tipo obtenerTipo() {
		return this.tipo;
	}
	
	
	/**
	 * Método que marca el primer movimiento.
	 * @see esPrimerMov
	 */
	public void marcarPrimerMovimiento() {
		this.esPrimerMov=false;
	}
	
	
	/**
	 * Booleano que devuelve si una pieza ha sido movida por primera vez o no.
	 * @return es primer booleano.
	 * @see esPrimerMov
	 */
	public boolean esPrimerMovimiento() {
		
		return this.esPrimerMov;
	}
	
	
	/**
	 * Getter que obtiene un String que contiene la distinta información de la celda.
	 * @return String con la información.
	 */	
	public String toString(){
		return obtenerTipo().toString() + "-" + obtenerColor().toString() + "-" + esPrimerMovimiento();
		
	}
}
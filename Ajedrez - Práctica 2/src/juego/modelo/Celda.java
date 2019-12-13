package juego.modelo;

import juego.modelo.pieza.Pieza;

/**
 * Clase que contiene los distintos métodos para crear y manipular las celdas del tablero.
 * 
 * @author Jorge Ruiz Gómez.
 * @since jdk 11.
 * @version 2.0.
 * 
 */

public class Celda{
	//Atributos
	/**
	 * Fila en la que se encuentra la celda.
	 */
	private int fila;
	
	/**
	 * Columna en la que se encuentra la celda.
	 */
	private int columna;
	
	/**
	 * referencia que apunta a la pieza que se guarda en la celda.
	 */
	private Pieza pieza;
	
	//Constructores
	
	/**
	 * Constructor que introduce por teclado la fila y la columna en la que se encuentra la pieza.
	 * @param fila entero con la fila.
	 * @param columna entero con la columna.
	 */
	public Celda(int fila, int columna){
		
		this.fila=fila;
		this.columna=columna;
		this.pieza = null;
	}
	
	
	//Métodos
	/**
	 * Metodo void que elimina la referencia que a apunta a la pieza que se encuentra en la celda. 
	 * Además, elimina la referencia de la piezaz que apunta a esta celda.
	 * @see pieza
	 */
	public void eliminarPieza() {
		if (pieza != null) {
			pieza.establecerCelda(null);
			pieza = null;
			
		}
	}
	
	/**
	 * Getter que devuelve un objeto pieza.
	 * @return devuelve la pieza que se encuentra en el tablero.
	 * @see pieza
	 */
	public Pieza obtenerPieza() {
		return pieza;
	}
	
	/**
	 * Método que coloca una pieza en la celda.
	 * @param pieza objeto.
	 * @see pieza
	 */
	public void establecerPieza(Pieza pieza) {
		this.pieza=pieza;
	}
	
	/**
	 * Booleano que devuelve si existe una pieza en la celda.
	 * @return booleano.
	 */
	public boolean estaVacia() {
		
		if (pieza==null)
			return true;
		else
			return false;
		
	}
	
	/**
	 * Devuelve la fila del tablero en la que se encuentra la fila.
	 * @return integer con en número de fila.
	 * @see fila
	 */
	public int obtenerFila() {
		return fila;	
	}
	/**
	 * Devuelve la fila del tablero en la que se encuentra la columna.
	 * @return integer con el número de columna.
	 * @see columna
	 */
	public int obtenerColumna() {
		
		return columna;
	}
	
	/**
	 * Getter que devuelve el color de la pieza colocada.
	 * @return Color de la pieza.
	 */
	public Color obtenerColorDePieza() {
		if (this.estaVacia())
			return null;
		else
			return pieza.obtenerColor();
	}
	
	/**
	 * Método booleano que devuelve si una celda pasada está en la misma posición que la la celda actual.
	 * @param celda de tipo celda.
	 * @return Booleano true o false.
	 */
	public boolean tieneCoordenadasIguales (Celda celda) {
		boolean resultado=false;
		
		if (this.obtenerFila() == celda.obtenerFila())
			if (this.obtenerColumna() == celda.obtenerColumna())
				resultado=true;
		return resultado;
	}
	
	
	/**
	 * Método que devuelve un string con la fila y columna actual de la celda.
	 * @return String.
	 */
	public String toString() {
		
		return "(" + obtenerFila() + "/" + obtenerColumna() + ")";
	}
}
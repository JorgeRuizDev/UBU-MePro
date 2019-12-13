package juego.modelo;

/**
 * 
 * 
 * @author Jorge Ruiz Gómez
 * 
 * 
 */

public class Celda{
	//Atributos
	private int fila;
	private int columna;
	
	public Pieza pieza;
	
	//Constructores
	public Celda(int fila, int columna){
		
		
		this.fila=fila;
		this.columna=columna;
		
	}
	
	
	//Métodos
	
	public Pieza obtenerPieza() {
		return pieza;
	}
	
	public void establecerPieza(Pieza pieza) {
		
		this.pieza=pieza;
		
	}
	
	public boolean estaVacia() {
		
		if (pieza==null)
			return true;
		else
			return false;
		
	}
	
	public int obtenerFila() {
		return fila;
		
	}
	
	public int obtenerColumna() {
		
		return columna;
	}
	
	public String toString() {
		
		
		//return obtenerFila() + " " + obtenerColumna() + " " + obtenerPieza();
		return "(" + obtenerFila() + "/" + obtenerColumna() + ")";
	}
}
package juego.modelo;

/**
 * 
 * 
 * @author Jorge Ruiz Gómez
 * 
 * 
 */

public class Pieza{
	
	//Átributos
	public Color color;
	public Celda celda;
	//Constructores
	
	public Pieza(Color color) {
		
		//this.color = color;
		//Hace lo mismo que:
		establecerColor(color);
		
		
	}

	//Métodos
	private void establecerColor(Color color) {
		
		//Establece el color 
		this.color=color;
		
	}
	
	public Color obtenerColor() {
		
		return color;
		
	}
	
	public void establecerCelda (Celda celda){
		
		this.celda=celda;
		
	}
	public Celda obtenerCelda() {
		
		
		return celda;
	}
	
	public String toString(){
		return obtenerColor().toChar()+"";
		//return obtenerCelda() + " " +  obtenerColor();
	}
}
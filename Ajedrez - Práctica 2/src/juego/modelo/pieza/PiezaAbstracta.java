package juego.modelo.pieza;
//Imports
import juego.modelo.Celda;
import juego.modelo.Color;
import juego.util.Sentido;

/**
 * Clase abstracta de la que heredarán las distintas piezas del juego.
 * @author Jorge Ruiz Gómez.
 * @version 2.0.
 * @since JDK 11.
 */


public abstract class PiezaAbstracta implements Pieza {
	
	/**
	 * Color de la pieza a heredar.
	 */
	protected Color color;

	/**
	 * Booleano que almacena el estado del primer movimiento
	 */
	private boolean primerMovimiento;
	
	/**
	 * Celda en la que se encuentra la celda.
	 */
	protected Celda celda;
	/**
	 * Letra de la pieza.
	 */
	char letra;
	
	 /**
	  * Constructor de la clase, asigna el color a la pieza.
	  * @param color Color de la pieza a generar. 
	  */
	
	
	public PiezaAbstracta (Color color){
		this.color = color;
		this.primerMovimiento = true;
	}
	/**
	 * Método abstracto que comprueba si el movimiento entre dos celdas de una pieza es correcto.
	 * Sigue las reglas del ajedrez.
	 *  @param destino Celda de destino.
	 *  @param sentido Sentido de la pieza.
	 * 
	 */
	public abstract boolean esCorrectoMoverA(Celda destino, Sentido sentido, boolean hayPiezasEntreMedias);
	
	/**
	 * Método que devuelve la letra asignada a cada pieza. 
	 * @see letra
	 */
	public char toChar(){	
		return letra;
	}
	
	/**
	 * Método booleano que devuelve el estado de la variable primerMovimiento.
	 * @see primerMovimiento
	 * @return booleano, true o false.
	 */
	public boolean esPrimerMovimiento() {
		
		return this.primerMovimiento;
	}
	
	/**
	 * Método que fija el estado de la variable primerMovimiento a false.
	 * @see primerMovimiento
	 */
	public void marcarPrimerMovimiento() {
		this.primerMovimiento = false;
	}
	
	/**
	 * 
	 * Método que colocar una celda pasada en su llamada.
	 * @see celda
	 * @param celda Celda.
	 */
	public void establecerCelda (Celda celda) {
		this.celda = celda;
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
	 * Se obtiene el color actual de la pieza.
	 * @see color
	 * @return color Color a devolver.
	 */
	public Color obtenerColor() {
		
		return color;
		
	}
	

	/**
	 * Método protegido que devuelve el número de casillas en horizontal que hay entre dos celdas.
	 * @param origen  Celda origen.
	 * @param destino Celda destino.
	 * @return	int con el número de casillas.
	 */
	protected int obtenerNumeroDeCeldasHorizontales(Celda origen, Celda destino) {
		return destino.obtenerColumna() - origen.obtenerColumna();
	}
	
	

	/**
	 * Método protegido que devuelve el número de casillas en vertical que hay entre dos celdas.
	 * @param origen  Celda origen.
	 * @param destino Celda destino.
	 * @return	int con el número de casillas.
	 */	
	protected int obtenerNumeroDeCeldasVerticales(Celda origen, Celda destino) {
		return destino.obtenerFila() - origen.obtenerFila();
	}
	
	/**
	 * Método que devuelve un array ordenado con los sentidos.
	 * @return Array con los sentidos ordenados en el sentido de las agujas del reloj.
	 */
	private Sentido [] obtenerSentidos () {
		
		Sentido [] sentidos = {Sentido.VERTICAL_N,Sentido.DIAGONAL_NE,Sentido.HORIZONTAL_E,Sentido.DIAGONAL_SE,
								Sentido.VERTICAL_S,Sentido.DIAGONAL_SO,Sentido.HORIZONTAL_O,Sentido.DIAGONAL_NO};
		
		return sentidos;
	}
	
	/**
	 * Devuelve el índice equivalente de una pieza al array de sentidos.
	 * @param sentido Sentido del que queremos hallar la posición. 
	 * @return Posición del sentido que está en el array Sentidos.
	 */
	protected int queSentidoEs (Sentido sentido) {
		
		Sentido [] sentidos = obtenerSentidos();
		
		int posicion = -1;
		
		for (int i =0 ; i < sentidos.length; i++ ) {	//Tenemos en cuenta si la pieza no es un caballo, ya que no tiene un movimiento que no se ajusta al enum
			if (sentido.equals(sentidos[i]))
				posicion = i;
		}
		return posicion;
	}

	/**
	 * Método que comprueba si el sentido de una recta entre su celda y una celda de destino es recto y no hay piezas entre medias
	 * @param destino	Celda de destino.
	 * @param sentido	Sentido que tiene la pieza a mover.
	 * @param hayPiezasEntreMedias	Booleano que comprueba si las piezas entre medias están vacías o no.
	 * @return ture o false si no es correcto.
	 */
	protected boolean esCorrectoMoverRecto(Celda destino, Sentido sentido, boolean hayPiezasEntreMedias) {
		
		if (hayPiezasEntreMedias == true)
			return false;

		if (queSentidoEs(sentido) %2 == 0) {		//Si el sentido es en línea recta
			
			if (this.esPiezaAmiga(celda, destino))
				return false;
			else return true;
			
		}
			
		return false;
	}
	/**
	 * Método que comprueba si el sentido de una recta entre su celda y una celda de destino es diagonal y no hay piezas entre medias
	 * @param destino	Celda de destino.
	 * @param sentido	Sentido que tiene la pieza a mover.
	 * @param hayPiezasEntreMedias	Booleano que comprueba si las piezas entre medias están vacías o no.
	 * @return true o false.
	 */
	protected boolean esCorrectoMoverDiagonal(Celda destino, Sentido sentido, boolean hayPiezasEntreMedias) {
		
		if (hayPiezasEntreMedias == true)
			return false;

		if (queSentidoEs(sentido) %2 != 0) {		//Si el sentido es en diagonal
			
			if (this.esPiezaAmiga(celda, destino))
				return false;
			else return true;
			
		}
			
		return false;
	}
	
	/**
	 * Método amigable que nos indica si la pieza de una celda es del mismo color que la de otra.
	 * @param origen	Celda Origen.
	 * @param destino	Celda Destino.
	 * @return			Booleano si es correcto o no.
	 */
	protected boolean esPiezaAmiga (Celda origen, Celda destino) {
		
		if (origen != null && destino != null) {
			
			if (destino.estaVacia() == true)
				return false;
			
			if (origen.obtenerColorDePieza() != destino.obtenerColorDePieza()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Método que comprueba si alguno de los parámetros pasados son nulos.
	 * @param destino Celda de destino.
	 * @param sentido Sentido de la pieza.
	 * @param hayPiezasEntreMedias Booleano de si hay piezas entre medias o no en el movimiento. 
	 * @return true o false.
	 */
	protected boolean sonArgumentosNulos(Celda destino, Sentido sentido, Boolean hayPiezasEntreMedias) {
		if (destino == null || sentido == null || hayPiezasEntreMedias == null)
			return true;
		return false;
	}
	

	/**
	 * Getter que obtiene un String que contiene la distinta información de la celda.
	 * @return String con la información.
	 */	
	public String toString(){
		return toChar() + "-" + obtenerColor().toString() + "-" + this.esPrimerMovimiento();
		
	}
}

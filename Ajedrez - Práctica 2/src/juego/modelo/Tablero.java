package juego.modelo;

import juego.modelo.Tablero;
import juego.modelo.pieza.Pieza;
import juego.util.Sentido;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase tablero. Utiliazdo para crear y colocar las piezas en sus correspondientes celdas.
 * Diseñado para el juego del Ajedrez.
 * 
 * @author Jorge Ruiz Gómez
 * @version 2.0
 * @serial Java 11
 * 
 * Clase Tablero.
 */

public class Tablero{
	//Constantes
	/**
	 * Constante que almacena el número de filas.
	 */
	public static final int NUMERO_FILAS=8;
	/**
	 * Constante que almacena el número de columnas.
	 */
	public static final int NUMERO_COLUMNAS=8;
	
	//Atributos
	/**
	 * Lista bidimensional que almacena las celdas del tablero.
	 */
	private List<List<Celda>> celdas;
	
	
	
	//Constructores-------------------------------
	
	/**
	 * Constructor que genera un tablero de 8x8.
	 * Rellena celdas.
	 * @see celdas
	 * 
	 */
	public Tablero () {
		celdas = new ArrayList<List<Celda>>(NUMERO_FILAS);
			
		for (int i=0; i< NUMERO_FILAS; i++) {
			celdas.add(new ArrayList<Celda>(NUMERO_COLUMNAS));
			for (int j=0; j< NUMERO_COLUMNAS;j++) {
				
				celdas.get(i).add(new Celda (i,j));
			}//fin j
		}//fin i
		
		//Comprobaciones de que se ha creado correctamente. 
		assert celdas.size() == NUMERO_FILAS : "CONSTRUCTOR: Número de filas incorrecto" + celdas.size();
		assert celdas.get(0).size() == NUMERO_COLUMNAS : "CONSTRUCTOR: Número de cols incorrecto" + celdas.size();

	}
 //	Métodos-------------------------------------------
	
	/**
	 * Método que coloca una pieza en una celda.
	 * @param pieza a colocar.
	 * @param celda a colocar.
	 * @throws CoordenadasIncorrectasException Lanza una excepcon si la celda no pertenece al tablero.
	 */
	public void colocar(Pieza pieza, Celda celda) throws CoordenadasIncorrectasException {
	
		if (pieza != null && celda != null) {
			if (this.estaEnTablero(celda.obtenerFila(), celda.obtenerColumna())) {
				celda.establecerPieza(pieza);
				pieza.establecerCelda(celda);
			}
			else {
				throw new CoordenadasIncorrectasException();
			}
		}
	}
//---------------------------------
	
	/**
	 * Método que coloca una pieza a partir de un objeto Pieza, y dos coordenadas en el tablero.
	 * @param pieza Pieza.
	 * @param fila de la celda.
	 * @param columna de la celda.
	 * Devuelve una Excepcion si no se encuenta delntro del tablero
	 * @throws CoordenadasIncorrectasException Lanza una excepción si una de las celdas no está en el tablero. 
	 */
	public void colocar (Pieza pieza, int fila, int columna) throws CoordenadasIncorrectasException {
		Celda celda = this.obtenerCelda(fila, columna);
		
		if (celda != null)
			colocar(pieza,celda);
		else 
			throw new RuntimeException ("La pieza no está en el tablero\n");
	}
	
	
//---------------------------------	
	/**
	 * Método que devuelve una Celda. Es necesario comprobar que esté dentro de los límites.
	 * @param fila en la que se desea obtener el objeto.
	 * @param columna en la que se desea obtener el objeto.
	 * @return La celda que se encuentra en las posición.
	 * @throws CoordenadasIncorrectasException Lanza una excepcion si la celda no está en el tablero.
	 */
	public Celda obtenerCelda (int fila,int columna) throws CoordenadasIncorrectasException {

		if (this.estaEnTablero(fila, columna) == true) {
	
			return celdas.get(fila).get(columna);
		}else {
	
			throw new CoordenadasIncorrectasException();
		}

	}

//--------------------------------------------------------
	
	/**
	 * Método que devuelve un objeto Celda a partir de un String que contiene notación algebraica.
	 * 
	 *  Buscamos convertir un dato introducido de la siguiente manera: 
	 *  
	 *  una letra, y un número, la letra es la columna y el número la fila, con las siguientes asociaciones.
	 *  
	 *  
	 * @param texto String a convertir a celda.
	 * @return La celda si el string introducido es correcto (letra entre a y h, número entre 1 y 8), si no, null).
	 * @throws CoordenadasIncorrectasException Lanza una excepcion si la celda no está en el tablero.
	 */
	
	/*
	 * 	 
	 *     a b c d               0 1 2 3
	 *   4                     0
 	 *   3				=>     1
	 *   2                     2
	 *   1                     3
	 * 
	 */
	public Celda obtenerCeldaParaNotacionAlgebraica(String texto) throws CoordenadasIncorrectasException  {
		
		if (texto.length() != 2)									//Comprobamos que el objeto esté compuesto por 2 caracteres
			return null;
		
		char [] textoArray = texto.toCharArray();					//Convertimos el string a un array para obtener la primera letra y el número
		//fila == numero ; Columna == letra
		int fila = textoArray[1] -1 -48;							
		int columna = 	textoArray[0]-97;														
		
		//Comprobamos que fila y columna están dentro de los valores del tablero
		if (!estaEnTablero(fila,columna))
			throw new CoordenadasIncorrectasException();
		else {
			return this.obtenerCelda((int)(56-textoArray[1]), (int)(textoArray[0]-97));
		}
	}
	
//--------------------------------------------------------
	/**
	 * Objeto de tipo celda que convierte el array bidimensional de celdas a uno unidimensional.
	 * @return Devuelve una Lista unidimensional de Celdas con todas las celdas.
	 */
	public List<Celda> obtenerCeldas(){
		List <Celda> celdas = new ArrayList<Celda>(this.obtenerNumeroFilas()*this.obtenerNumeroColumnas());
		
		assert this.celdas != null : "La lista bidimensional de celdas es NULA";
		assert this.obtenerNumeroFilas() >0 : "El número de filas es 0 o menor";
		try {
		for (int i=0; i<this.obtenerNumeroFilas(); i++)
			for (int j=0; j<this.obtenerNumeroColumnas(); j++) {
				
					celdas.add(obtenerCelda(i,j));

			}
		} catch (CoordenadasIncorrectasException e) {
			encadenarExcepciones(e);
		}
		
		
		return celdas;
		
	}

//--------------------------------------------------------
	/**
	 * Método que obtiene una lista con todas las celdas que hay entre dos celdas.
	 * @param origen Celda de origen
	 * @param destino Celda de destino
	 * @return Lista con las celdas. 
	 * @throws CoordenadasIncorrectasException Si la celda de origen o destino no están en el tablero, lanza una excepción.
	 */
	public List <Celda> obtenerCeldasEntreMedias(Celda origen, Celda destino) throws CoordenadasIncorrectasException{
		
		assert origen != null  : "CELD.INTERM: Celda origen nula";
		assert destino != null : "CELD.INTERM: Celda destino nula";
		
		List<Celda> celdas = new ArrayList <Celda>();
		
		//Comprobamos que ambas celdas se encuentran en el tablero.
		
		if (this.estaEnTablero(origen.obtenerFila(), origen.obtenerColumna()) == false ) 
			throw new CoordenadasIncorrectasException();
		if (this.estaEnTablero(destino.obtenerFila(), destino.obtenerColumna()) == false)
			throw new CoordenadasIncorrectasException();
		
		Sentido sentido = obtenerSentido (origen,destino);		//Obtenemos el sentido en el que están ambas celdas.
		
		if (sentido==null)										//Si no es un sentido válido, devolvemos la lista vacía.
			return celdas;
		
		//Calculamos el número de filas y columnas que avanza el movimiento, así como la dirección (ya que tienen signo)
		int horizontal = destino.obtenerColumna() - origen.obtenerColumna();		 //Columna destino -  columna origen
		int vertical   = destino.obtenerFila()    - origen.obtenerFila();    		 //Fila destino    -  fila origen
		
		//Creamos 2 valores temporales con los que recorreremos el tablero
		int tmp_h = origen.obtenerFila();
		int tmp_v = origen.obtenerColumna();
		
		
		int bucle;
		//Calculamos el número de iteraciones del bucle.
		if (Math.abs(vertical)-Math.abs(horizontal) == 0)			//Si es diagonal
			bucle = Math.abs(vertical);								//Ambas son iguales (Avanza en X lo mismo que en Y)
		else 
			bucle = Math.abs(horizontal+vertical);					//Si no es diagonal, uno de los dos es 0, por lo que el bucle es la suma (uno de ellos)
		
		
		for (int i=0; i<bucle-1; i++) {
			tmp_h += sentido.obtenerDesplazamientoEnFilas();
			tmp_v += sentido.obtenerDesplazamientoEnColumnas();
			
			celdas.add(this.obtenerCelda(tmp_h,tmp_v ));
		}
			
		
		return celdas;
	}
	
//--------------------------------------------------------
	/**
	 * Funcion que convierte la coordenada en la que se encuentra la celda, en una en notación algebraica.
	 * 
	 *  Partiendo de un tablero similar al de la derecha, necesitamos convertirlo a un string que contenga la 
	 *  letra equivalente de la columna, y el número equivalente de la fila.
	 * 
	 * 
	 * @param celda, de tipo Celda, pasamos un objeto Celda, y devolvemos su correspondecia con notación algebraica.
	 * @return String con los dos chars calculados.
	 * @throws CoordenadasIncorrectasException Lanza una excepcion si la celda no está en el tablero.
	 */

	/*
	 *     a b c d               0 1 2 3
	 *   4                     0
 	 *   3			<======    1
	 *   2                     2
	 *   1                     3
	 * 
	 * */
	public String obtenerCoordenadaEnNotacionAlgebraica(Celda celda) throws CoordenadasIncorrectasException {

		int fila 	= celda.obtenerFila();
		int columna = celda.obtenerColumna();
		
		//Comprobación que está dentro de los límites.
		if (!estaEnTablero(fila,columna))
			throw new CoordenadasIncorrectasException();
		
		
		return (char)(columna+97)+""+(char)(56-fila);
	}
//--------------------------------------------------------	
	/**
	 * Método booleano que devuelve si una fila está dentro de los límites del tablero o no.
	 * 
	 * No se considera tablero los valores negativos, ni mayores (o iguales) al número de columnas.
	 * @param fila		fila a comprobar.
	 * @param columna	columna a comprobar.
	 * @return Booleano.
	 */
	
	private boolean estaEnTablero (int fila, int columna) {
		
		if ( fila >= obtenerNumeroFilas() || columna >= obtenerNumeroColumnas() || fila < 0 || columna < 0)
			return false;
		else
			return true;
		
	}	
//-----------------------------------------------------------
	/**
	 * 
	 * Esta función utiliza un doble bucle para contar el número de celdas que contienen .
	 * un pieza del mismo color que el pasado como parámetro.
	 * 
	 * @param color Tipo enumerado color.
	 * @return nPiezasColor Devuelve el número de piezas que hay en el tablero cuyo color coincide con el pasado al método.
	 */
	public int obtenerNumeroPiezas (Color color) {
		//personal
		int nPiezasColor=0;
		try {
			for (int i=0; i<obtenerNumeroFilas(); i++) {
				for (int j=0; j<obtenerNumeroColumnas(); j++) {
					
					Celda celda = obtenerCelda (i,j);
					if (celda.estaVacia() == false) {
						if (celda.obtenerPieza().obtenerColor().equals(color))
							nPiezasColor++;
					}
				}//Fin for j
			}//fin for i
		}
		catch (CoordenadasIncorrectasException e) {
			encadenarExcepciones(e);
		}
			return nPiezasColor;
	}
	
	
//---------------------------------		
	
	/**
	 * Función que calcula el número de columnas a partir de la matriz
	 * generada en el constructor Tablero().
	 * 
	 * 
	 * @return Número de columnas.
	 */
	public int obtenerNumeroColumnas() {
		assert celdas !=null : "Celdas es NULO";
		
		return celdas.get(0).size();

	}
//---------------------------------	
	
	/**
	 * Función que calcula el número de filas a partir de la matriz
	 * generada en el constructor Tablero().
	 * 
	 * 
	 * @return Número de filas.
	 */
	public int obtenerNumeroFilas() {
		assert celdas !=null : "Celdas es NULO";

		return celdas.size();
	}

//-----------------------------------------------------------
	/**
	 * Método que calcula el sentido entre dos celdas.
	 * Si no son explicitamente DIAGONALES 1:1 ó movimientos verticales/horizontales, devuelve Null al no estar asociado con nada. 
	 * 
	 * 
	 * @param origen celda.
	 * @param destino celda.
	 * @return Devuelve un Enum Sentido.
	 * @throws CoordenadasIncorrectasException Lanza una excepcion si la celda no está en el tablero.
	 */
	
	public Sentido obtenerSentido(Celda origen, Celda destino) throws CoordenadasIncorrectasException {
		
		if (this.estaEnTablero(origen.obtenerFila(), origen.obtenerColumna()) == false ) 
			throw new CoordenadasIncorrectasException();
		if (this.estaEnTablero(destino.obtenerFila(), destino.obtenerColumna()) == false)
			throw new CoordenadasIncorrectasException();
		
		int horizontal = destino.obtenerColumna() - origen.obtenerColumna();		 //Columna destino -  columna origen
		int vertical   = destino.obtenerFila()    - origen.obtenerFila();    		 //Fila destino    -  fila origen

		Sentido [] a_sentido = {Sentido.VERTICAL_N,Sentido.DIAGONAL_NE,Sentido.HORIZONTAL_E,Sentido.DIAGONAL_SE,
								Sentido.VERTICAL_S,Sentido.DIAGONAL_SO,Sentido.HORIZONTAL_O,Sentido.DIAGONAL_NO};
		
		int id;				//indiceDireccion, se usa para devolver la posición de a_sentido;
		
		//Funcionamiento:
		/* Imaginemos que tenemos un array con 8 posiciones [0-7], y colocamos cada índice la posición en su equivalente en
		 * los 8 puntos cardinales (Sentido [] a_sentido)
		 * 
		 * Una vez los ordenamos como en el array, avanzamos en el sentido de las agujas del reloj y siendo 'O' la
		 * la celda origen, cuando hacemos un moviemiento, horizontal y vertical tienen un valor (Positivo, negativo o 0)
		 * 
		 *  Dependiendo de esos valores tienden a ir a uno de esos cuadrantes, que corresponde con los ÍNDICES IMPARES del array
		 *  o a uno de los ejes, correspondiente con los índices pares
		 *  
		 *  Luego, los movimientos diagonales tienen que ser 1:1, una casilla horizontal por cada casilla vertical, o no son válidos
		 *  Por ello, dependiendo de cada cuadrante, H y V serán iguales, ó H+V = 0 (ya que uno es negativo)
		 * 
		 * 
		 * 
		 * 			[0]									|				  vertical negativa
		 * 		[7]		[1]						C7		|     C1
		 * 	[6]				[2]				------------O---------------- vertical = 0
		 * 		[5]		[3]						C5		|     C3
		 * 			[4]									|				  vertical positiva 
		 * 								h negativa	horizontal = 0  h positiva
		 */
		
		//El siguiente planteamiento adaptado a un algoritmo quedaría tal que así:
		
		if 		(vertical < 0 && horizontal == 0)
			id = 0;	//N
		else if (vertical < 0 && horizontal > 0 && (horizontal + vertical) == 0)
			id = 1; //N E
		else if (vertical == 0 && horizontal > 0)
			id = 2; //E
		else if (vertical > 0 && horizontal > 0 && (horizontal == vertical) )
			id = 3; //S E
		else if (vertical > 0 && horizontal == 0)
			id = 4; //S
		else if (vertical > 0 && horizontal < 0 && (horizontal + vertical) == 0)
			id = 5; //S O
		else if (vertical == 0 && horizontal < 0)
			id = 6; // O
		else if (vertical < 0 && horizontal < 0 && (horizontal == vertical))
			id = 7; //N O
		else
			return null;
		
		return a_sentido [id];
	}
	
//-----------------------------------------------------------	
	/**
	 * Método que concatena distintos elementos para formar el tablero.
	 * 
	 * Necesita de las enumeraciones Tipo.
	 * 
	 * @return String con el resultado..
	 */
	public String toString() {

		//String vacío con el que empezar.
		String devolver="";
		try {
			for (int i=0, a=this.obtenerNumeroFilas();i<this.obtenerNumeroFilas();i++,a--) {
				devolver+=a+"  ";											//Leyenda lateral
				
				for (int j=0; j<this.obtenerNumeroColumnas();j++) {
					
					if (obtenerCelda(i,j)!=null && obtenerCelda(i,j).obtenerPieza()!=null) {
						
						Pieza pieza = obtenerCelda(i,j).obtenerPieza();			//Obtenemos la Pieza de la celda seleccionada
						devolver   += pieza.toChar();
						devolver   += pieza.obtenerColor().toChar();
						
					}
					else 
						devolver+="--";
					devolver +=" ";											//Separación entre 2 piezas
				}//fin j
					devolver+="\n";											//Salto de línea al terminar una línea... 
			}//fin i
			
			devolver += "   a  b  c  d  e  f  g  h \n";						//Leyenda inferior del tablero
			
			
		}
		catch (CoordenadasIncorrectasException e) {
			encadenarExcepciones(e);
		}
			return devolver;
	}
	/**
	 * Este método lanza una excepción de tipo runtime si se lanza una excepción comprobable la cual no
	 * permite la ejecución correcta del programa. 
	 * @param e Excepción de entrada de tipo CoordenadasIncorrectasException.
	 */
	private void encadenarExcepciones(CoordenadasIncorrectasException e) {
		throw new RuntimeException("Se ha detenido la ejecución del programa por una excepcion",e); 
		
	}
	
	
}//Fin tablero	


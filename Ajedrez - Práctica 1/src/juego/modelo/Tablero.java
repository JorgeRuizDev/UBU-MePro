package juego.modelo;
import juego.modelo.Tablero;
import juego.util.Sentido;

/**
 * Clase tablero. Utiliazdo para crear y colocar las piezas en sus correspondientes celdas.
 * Diseñado para el juego del Ajedrez.
 * 
 * @author Jorge Ruiz Gómez
 * @version 1.0
 * @since Java 11
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
	 * Array bidimensional que almacena las celdas del tablero.
	 */
	private Celda [][] celdas;

	//public int filas, columnas;
	
	//Constructores-------------------------------
	
	/**
	 * Constructor que genera un tablero de 8x8.
	 * Rellena celdas.
	 * @see celdas
	 * 
	 */
	public Tablero () {
		celdas = new Celda[NUMERO_FILAS][NUMERO_COLUMNAS];	
		
			
		for (int i=0; i< NUMERO_FILAS; i++) {
			for (int j=0; j< NUMERO_COLUMNAS;j++) {
				
				celdas[i][j] = new Celda (i,j);
			}//fin j
		}//fin i
	}
 //	Métodos-------------------------------------------
	
	/**
	 * Método que coloca una pieza en una celda.
	 * @param pieza a colocar.
	 * @param celda a colocar.
	 */
	public void colocar(Pieza pieza, Celda celda) {
		
		if (pieza != null && celda != null) {
			celda.establecerPieza(pieza);
			pieza.establecerCelda(celda);
		}
	}
//---------------------------------
	
	/**
	 * Método que coloca una pieza a partir de un objeto Pieza, y dos coordenadas en el tablero.
	 * @param pieza Pieza.
	 * @param fila de la celda.
	 * @param columna de la celda.
	 * @throws RuntimeException
	 * Devuelve una Excepcion si no se encuenta delntro del tablero
	 */
	public void colocar (Pieza pieza, int fila, int columna) {
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
	 */
	public Celda obtenerCelda (int fila,int columna) {

		if (this.estaEnTablero(fila, columna) == true)
			return celdas[fila][columna];
		else
			return null;
			

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
	public Celda obtenerCeldaParaNotacionAlgebraica(String texto) {
		
		if (texto.length() != 2)									//Comprobamos que el objeto esté compuesto por 2 caracteres
			return null;
		
		char [] textoArray = texto.toCharArray();					//Convertimos el string a un array para obtener la primera letra y el número
		
		char [] letras = 	{'a','b','c','d','e','f','g','h'};		//Usamos un array con las letras que tendrá el tablero en las columnas
						  //{ 0   1   2   3   4   5   6   7 }		//Correspondencias con el bucle
		
		int fila=0, columna=0;
		
		for (int i = 0; i < letras.length; i++) {					//Obtenemos la columna equivalente a la posición de la letra en el vector
			if (letras[i] == textoArray[0]) {
				columna = i;
				i = letras.length;									//Detención del bucle
			}
			else columna = -999;
		}
		
		fila= textoArray[1] -1 -48;									//obtenemos la fila
																	//(Restamos 1 para conseguir la equivalencia, y 48 para convertir de número en ASCII al número deseado)
		
		
		//Comprobamos que fila y columna están dentro de los valores del tablero
		if (estaEnTablero(fila,columna) == false)
			return null;
		
		//Si están dentro de los valores. Necesitamos invertir la columna para hallar el número deseado, 
		//ya que la forma con la que se representa el tablero
		//y la forma con la que tenemos numeradas las celdas no se corresponde.
		
		
		int filaInvertida[] = {7,6,5,4,3,2,1,0};					//Este método sólo es válido para un tablero con 8 filas.
		
		fila = filaInvertida[fila];
		return this.obtenerCelda(fila, columna);
		
	}
	
//--------------------------------------------------------
	/**
	 * Objeto de tipo celda que convierte el array bidimensional de celdas a uno unidimensional.
	 * @return Devuelve un array unidimensional de Celdas con todas las celdas.
	 */
	public Celda [] obtenerCeldas() {
		Celda [] unidimensional = new Celda[this.obtenerNumeroFilas() * this.obtenerNumeroColumnas()];
		
		if (this.celdas == null)
			return null;
		
		int posicionCelda = 0;
		
		for (int i=0; i<this.obtenerNumeroFilas(); i++) {
			for (int j=0; j < this.obtenerNumeroColumnas(); j++, posicionCelda++) {
				unidimensional[posicionCelda] = celdas[i][j];
			}//fin j
		}//fin i
		
		return unidimensional;
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
	 */

	/*
	 *     a b c d               0 1 2 3
	 *   4                     0
 	 *   3			<======    1
	 *   2                     2
	 *   1                     3
	 * 
	 * */
	public String obtenerCoordenadaEnNotacionAlgebraica(Celda celda) {
		
		char [] letras = {'a','b','c','d','e','f','g','h'};					//array con las letras, la posición es equivalente a la columna directa
		int filaInvertida [] = {8,7,6,5,4,3,2,1};							//array con los números equivalentes a la fila dada		
		
		int fila 	= celda.obtenerFila();
		int columna = celda.obtenerColumna();
		
		//Comprobación que está dentro de los límites.
		if (estaEnTablero(fila,columna) == false)
			return null;
		
		char letra = letras [columna];
		String  filaEquivalente = Integer.toString(filaInvertida[fila]);
		
		return letra + filaEquivalente;
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
	
	public boolean estaEnTablero (int fila, int columna) {
		
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
		
		for (int i=0; i<obtenerNumeroFilas(); i++)
			for (int j=0; j<obtenerNumeroColumnas(); j++) {
				
				Celda celda = obtenerCelda (i,j);
				if (celda.estaVacia() == false) {
					if (celda.obtenerPieza().obtenerColor().equals(color))
						nPiezasColor++;
				}
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
		return celdas[0].length;

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
		return celdas.length;
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
	 */
	
	public Sentido obtenerSentido(Celda origen, Celda destino) {

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
		
		for (int i=0, a=this.obtenerNumeroFilas();i<this.obtenerNumeroFilas();i++,a--) {
			devolver+=a+"  ";											//Leyenda lateral
			
			for (int j=0; j<this.obtenerNumeroColumnas();j++) {
				
				if (celdas[i][j].estaVacia()==false) {
					
					Pieza pieza = celdas[i][j].obtenerPieza();			//Obtenemos la Pieza de la celda seleccionada
					devolver += pieza.obtenerTipo().toChar();
					devolver += pieza.obtenerColor().toChar();
					
				}
				else 
					devolver+="--";
				devolver +=" ";											//Separación entre 2 piezas
			}//fin j
				devolver+="\n";											//Salto de línea al terminar una línea... 
		}//fin i
		
		devolver += "   a  b  c  d  e  f  g  h \n";						//Leyenda inferior del tablero
		
		return devolver;
	}
}//Fin tablero	


package juego.modelo;

import juego.util.Direccion;

/**
 * 
 * 
 * @author Jorge Ruiz Gómez
 * @version 1.0
 * @serial Java 11
 * 
 */

public class Tablero{
	//Atributos
	private Celda [][] celdas;
	
	//public int filas, columnas;
	
	//Constructores-------------------------------
	
	public Tablero (int filas, int columnas) {
		
		if (filas <= 0)
			filas=1;
		if (columnas <= 0)
			columnas=1; 	
		
		
		if (filas > 0 || columnas > 0) {
			celdas= new Celda [filas][columnas];
			
			for (int i=0; i< filas; i++) {
				for (int j=0; j< columnas;j++) {
					
					celdas[i][j] = new Celda (i,j);
				}//fin j
			}//fin i
		}
		
			
	}
 //	Métodos-------------------------------------------
	
	public void colocar(Pieza pieza, Celda celda) {
		
		celda.establecerPieza(pieza);
		pieza.establecerCelda(celda);
		
	}
	
//---------------------------------	
	public Celda obtenerCelda (int fila,int columna) {
		//personal


		if 		( fila <0 || columna <0 )	
			//Límites menores que 0
			return null;
		else if ( columna >= this.obtenerNumeroColumnas() || fila >= this.obtenerNumeroFilas() ) {	
			//límites superiores al tamaño del tablero.
			return null;
		}
		else
			return celdas[fila][columna];
		
		
	}

//---------------------------------		
	
	public boolean estaEnTablero (int fila, int columna) {
		//Personal
		if ( fila >= obtenerNumeroFilas() || columna >= obtenerNumeroColumnas() || fila < 0 || columna < 0)
			return false;
		else
			return true;
		
	}

//-----------------------------------------------------------	
	
	public int contarPiezas(Celda celda,Direccion direccion) {
		//personal
		int n_piezas=0;
		
		//Celdas de origen
		int c_inicial = celda.obtenerColumna();
		int f_inicial = celda.obtenerFila();
		
		//Celdas de operación
		int c_tmp = c_inicial;
		int f_tmp = f_inicial;
		
		int direccion_int = 1;
		
		boolean bucle=true;
		char colorInicial = 0;
		
		if(celda.estaVacia()==false && this.estaEnTablero(c_tmp, f_tmp)) {
			//Obtenemos el color de la primera pieza inicial si existe y está en el tablero
			colorInicial = celda.obtenerPieza().obtenerColor().toChar();
			n_piezas++;
		}
		else
			bucle = false;	//no ejecutamos el bucle porque esa celda está vacía
		
		while (bucle == true){	//Bucle con el que incrementaremos las distintas direcciones para comprobar si existen piezas al final
			
			switch (direccion) {
				//En este switch incrementaremos la direccion con cada iteracion
				case HORIZONTAL:
					c_tmp 	+= direccion_int;
					break;
					
				case VERTICAL:
					f_tmp    += direccion_int;
					break;
					
				case DIAGONAL_SO_NE:
					c_tmp 	 -= direccion_int;
					f_tmp    += direccion_int;
					break;
					
				case DIAGONAL_NO_SE:
					c_tmp 	+= direccion_int;
					f_tmp   += direccion_int;
				break;
			}
			celda = this.obtenerCelda(f_tmp, c_tmp);	//Cargamos una nueva celda con los nuevos valores
			
			if (this.estaEnTablero(f_tmp, c_tmp ) ) {

				
				if (celda.estaVacia()==false && (celda.pieza.obtenerColor().toChar() == colorInicial )) {
					n_piezas++;
				}
				
				//Para consecutivas:
					//Las siguientes comprobaciones se encargar de la no existencia de una ficha igual dentro del tablero.
				
				else if (direccion_int == 1) {
					//Si resulta que estamos dentro del tablero, pero las piezas nos son iguales
					//o no hay, cambiamos la direccion

					direccion_int = -1;			//La convertimos en negativa para cambiar la direccion

					c_tmp=c_inicial;			//Y reseteamos el punto de comienzo para el bucle
					f_tmp=f_inicial;
				}
				else {
					//Si resulta que estamos dentro del tablero, pero ni la pieza existe, ni podemos cambiar la dirección, detenemos la ejecución.
					bucle=false;
				}
			}
			else if (direccion_int == 1 ) {	//Si la direccion con la que avanzamos es positiva

				direccion_int = -1;			//La convertimos en negativa para cambiar la direccion
				
				c_tmp=c_inicial;			//Y reseteamos el punto de comienzo para el bucle
				f_tmp=f_inicial;
			}
			else
				bucle = false;
					
		}//fin while	
		
		return n_piezas;
		
	}


	
//-----------------------------------------------------------
	/**
	 * 
	 * Esta función utiliza un doble bucle para contar el número de celdas que contienen 
	 * un pieza del mismo color que el pasado como parámetro.
	 * 
	 * @param color Tipo enumerado color
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
	 * generada en el constructor Tablero()
	 * 
	 * 
	 * @return Número de columnas.
	 */
	public int obtenerNumeroColumnas() {
		//personal
		return celdas[0].length;

	}
//---------------------------------	
	
	/**
	 * Función que calcula el número de filas a partir de la matriz
	 * generada en el constructor Tablero()
	 * 
	 * 
	 * @return Número de filas.
	 */
	public int obtenerNumeroFilas() {
		//personal
		return celdas.length;
	}

//-----------------------------------------------------------	
	
	public boolean estaCompleto() {
		//personal
		
		boolean completo=true;
		
		for (int i=0;i<this.obtenerNumeroFilas();i++) {
			for (int j=0; j<this.obtenerNumeroColumnas();j++) {
				
				Celda celda = obtenerCelda (i,j);
					if (celda.estaVacia() == true)
						completo=false;
			}//fin j
		}//fin i
		
		
		return completo;
	}
	
//-----------------------------------------------------------	
	
	public String toString() {
		//personal
		
		//Obtenemos la letra equivalente al color de cada pieza
		
		//String vacío con el que empezar.
		String devolver="";
		
		for (int i=0;i<this.obtenerNumeroFilas();i++) {
			for (int j=0; j<this.obtenerNumeroColumnas();j++) {
				
				if (celdas[i][j].estaVacia()==false) {
					
					Pieza piezaString=celdas[i][j].obtenerPieza();		//Obtenemos la Pieza de la celda seleccionada
					Color colorString = piezaString.obtenerColor();		//Obtenemos el color de la Pieza
					
					devolver+=(colorString.toChar());					//Convertimos el color a su carácter correspondiente
				}
				else 
					devolver+="-";
					
			}//fin j
			
			if(i!=(this.obtenerNumeroFilas()-1))						//este IF evita que se añada el último \n al string,, que que es necesario para el Test Unitario
				devolver+="\n";
		}//fin i
		
		
		return devolver;
	}

	public void imprimirTablero(Tablero tablero) {
		System.out.printf("\n%s\n",tablero.toString());
	}
	
	public static void main (String[] args) {
		
		
		Tablero tablero = new Tablero(3,3);
		
	/*	Celda celda00 = tablero.obtenerCelda(0, 0);
		Celda celda11 = tablero.obtenerCelda(1, 1);
		Celda celda22 = tablero.obtenerCelda(2, 2);
		
		Pieza pieza00 = new Pieza(Color.BLANCO);
		Pieza pieza11 = new Pieza(Color.BLANCO);
		Pieza pieza22 = new Pieza(Color.BLANCO);

		tablero.colocar(pieza00,celda00); 
		tablero.colocar(pieza11,celda11); 
		tablero.colocar(pieza22,celda22); 
		
		Direccion direccion2 = Direccion.DIAGONAL_NO_SE;
	*/
		
		Celda celda00 = tablero.obtenerCelda(0,0);
		Celda celda01 = tablero.obtenerCelda(0,1);		
		Celda celda02 = tablero.obtenerCelda(0,2);

		Pieza pieza00 = new Pieza(Color.BLANCO);
		Pieza pieza01 = new Pieza(Color.BLANCO);
		Pieza pieza02 = new Pieza(Color.BLANCO);
		
		tablero.colocar(pieza00,celda00); 
		tablero.colocar(pieza01,celda01); 
		tablero.colocar(pieza02,celda02); 
		
		
		Direccion direccion2 = Direccion.HORIZONTAL;
		
		
		int nPiezas=tablero.contarPiezas(celda00,direccion2);
		System.out.println("Número de piezas: " + nPiezas);
		
		
		tablero.imprimirTablero(tablero);
		
		
	}

}
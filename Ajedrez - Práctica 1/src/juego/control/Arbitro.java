package juego.control;

//IMPORTACIONES
import juego.modelo.Celda;
import juego.modelo.Color;
import juego.modelo.Pieza;
import juego.modelo.Tablero;
import juego.modelo.Tipo;
import juego.util.Sentido;

/**
*
*Árbitro del juego Ajedrez. Permite jugar (mover las distintas piezas) de forma legal.
*Dos jugadrores, Blanco y Negro. 
*
*@author Jorge Ruiz Gómez.
*@since  JDK 11.
*@version 1.0.
*/


public class Arbitro{

	//ATRIBUTOS:
	/**
	 * Objeto Tablero.
	 */
	private Tablero tablero;
	/**
	 * Booleano que comprueba si el tablero está iniciado correctamento, con piezas.
	 */
	private boolean empezar;
	/**
	 * Contador de jugadas.
	 */
	private int n_jugadas = 0;

	/**
	 * Color turno.
	 */
	private Color turno;
	
	/**
	 * Booleano usado durante el simulacro del Jaque, para evitar conflictos con el color de las piezas.
	 */
	private boolean simulacroJaque;
	
	/**
	 * Booleano usado durante la comprobación del jaque para evitar conflicto entre el color de las piezas, y el turno.
	 */
	private boolean simulacroJaqueColor;

	
//--constructores--------------------------------------
	
	/**
	 * Contructor de la clase.
	 * @param tablero Tablero utilizado.
	 * @see tablero
	 * @see n_jugadas
	 * @see empezar
	 * @see turno
	 * @see simulacroJaque
	 * @see simulacroJaqueColor
	 */
	public  Arbitro(Tablero tablero){
		this.tablero = tablero;
		n_jugadas = 0;
		empezar = false;
		turno = Color.BLANCO;
		simulacroJaque = false;
		simulacroJaqueColor = false;
	}

	
//--métodos-----------------------------------------------
	
	/**
	 * Cambia el turno.
	 * @see turno
	 */
	public void cambiarTurno() {
		if (obtenerTurno() == Color.BLANCO)
			this.turno = Color.NEGRO;
		else
			this.turno = Color.BLANCO;
		
	}
	

//---------------------------------------------------------
	
	/**
	 * Devuelve todas las piezas que no son peones ordenadas como NEGRAS.
	 * 
	 * @return Array de tipo Tipo con todas las piezas ordenadas.
	 */
	
	private Tipo [] generarPiezas(){
		
		Tipo [] ret = {Tipo.TORRE,Tipo.CABALLO,Tipo.ALFIL,Tipo.DAMA,Tipo.REY,Tipo.ALFIL,Tipo.CABALLO,Tipo.TORRE};
		
		return ret;
	}
//---------------------------------------------------------
	
	/**
	 * Método que coloca todas las piezas en el tablero, y pasa el turno al jugador BLANCO.
	 */
	public void colocarPiezas() {
		
		Tipo [] todasPiezas = generarPiezas();
		

			
		for (int i=0; i<todasPiezas.length ; i++) {		

			//	NEGRAS:
			Pieza piezaN = new Pieza(todasPiezas[i],Color.NEGRO);			//Creación de las piezas negras en orden
			Pieza peonN = new Pieza(Tipo.PEON,Color.NEGRO);					//Peón Negro
			tablero.colocar(piezaN, 0, i);									//Colocación de la pieza
			tablero.colocar(peonN , 1, i);									//Colocación del peon NEGRO
			
			//	BLANCAS:
			Pieza piezaB = new Pieza (todasPiezas[i],Color.BLANCO);
			Pieza peonB = new Pieza(Tipo.PEON,Color.BLANCO);				
			tablero.colocar(piezaB, tablero.obtenerNumeroFilas() -1, i);
			tablero.colocar(peonB , tablero.obtenerNumeroFilas() -2, i);	
			
			empezar = true;													//Permitimos que comience la partida
		}
	}
//-------------------------------------------------------------------------------
	/**
	 * Método que permite colocar una gran cantidad de piezas de una sola llamada.
	 * 
	 * Se pasarán 3 arrays:
	 * @param tipo			Array unidimiensional con el Tipo de la pieza.
	 * @param color			Array unidimensional con el color de la pieza.
	 * @param coordenadas	Array bidimensional con la fila y la columna en la que se colocará la pieza.
	 */
	public void colocarPiezas(Tipo [] tipo, Color [] color, int [][] coordenadas) {
		
		if ((tipo.length == color.length) && (color.length == coordenadas.length)) {		//Comprobamos que los 3 arrays tengan el mismo tamaño.
			
			for (int i=0; i < tipo.length; i++) {
				
				Pieza pieza = new Pieza (tipo[i], color[i]);								//Generamos la pieza
				
				tablero.colocar(pieza, coordenadas[i][0], coordenadas[i][1]);				//Colocamos la pieza
				
			}//end for
			this.empezar = true;															//Permitimos que se pueda comenzar la partida. 
		}//end if
	}//end method
//-------------------------------------------------------------------------------		
	
	/**
	 * Booleano que comprueba si el movimiento es correcto.
	 * @param origen	Celda de origen.
	 * @param destino	Celda de destino.
	 * @return true si es legal, false si no lo es.
	 */
	public boolean esMovimientoLegal (Celda origen, Celda destino){
		
		//Comprobaciones Inciales
		if (origen == null || destino == null || origen.estaVacia())
			return false;

		
		if (simulacroJaque == false && simulacroJaqueColor == false)		//Si no estamos comprobando un JAQUE
			if (origen.obtenerColorDePieza() != this.obtenerTurno())		
				return false;												//El color del turno no coincide con el de la pieza a mover
																			//Es un movimiento ilegal
		
		if (tablero.obtenerSentido(origen, destino) == null && !esCaballo(origen.obtenerPieza()))				//Si el movimiento no es una diagonal o una recta y la pieza NO ES UN CABALO
			return false;																						//No coincidirá con el sentido de ninguna otra pieza, por lo que no será un movimiento válido.
		/*
			Obtenemos los MOVIMIENTOS de la pieza en cuestión
			la llamada al método nos devolverá la dirección en la que avanza, y el número de veces que lo hace.
			Por ejemplo un caballo puede avanzar +-3 filas y +-2 columnas UNA (1) vez
			Mientras que una torre puede avanzar +-1 filas (N) veces, siendo N el número de iteraciones de un bucle
			se hace así para poder comprobar si hay alguna pieza en las casillas intermedias.
		*/
		int [] movimientos = casillas ( origen.obtenerPieza(), destino);
		
		
		if (movimientos == null) 											//Si el array de movimiento no se ha iniciado, es porque ningun movimiento es válido
			return false;

		//Creamos 2 nuevas variables de posición con las que trabajar
		int nuevaFila    = origen.obtenerFila()   ;		
		int nuevaColumna = origen.obtenerColumna();
		
		for (int i = 0; i < movimientos[2]; i++ ) {
			 nuevaFila    += movimientos[0];		//Avanzamos de fila a partir de la última fila
			 nuevaColumna += movimientos[1];		//Avanzamos de columna a partir de la útlima columna
			
			Celda celda = tablero.obtenerCelda(nuevaFila, nuevaColumna);
			
			if (nuevaFila == destino.obtenerFila() && nuevaColumna == destino.obtenerColumna() && celda.estaVacia() == false)	//Si es la celda de destino y no está vacía
				if (origen.obtenerColorDePieza() != destino.obtenerColorDePieza())												//y la pieza es ENEMIGA
					 return true;							//La reemplazamos
				else 
					return false;							//Es AMIGA, por lo que no es un movimiento válido.
			
			if (celda.estaVacia() == false) 				//Si una de las celdas intermedias está ocupada, no podemos movernos.
				return false;
				
		}	
		return true;										//Si no hay ninguna pieza de por medio, y la celda está vacía, significa que el camino está libre.
	}
		
//-------------------------------------------------------------------------------
	/**
	 * Método privado que nos permite comprobar si una pieza es un caballo rápidamente.
	 * @param pieza a comprbar.
	 * @return true o false.
	 */
	private boolean esCaballo(Pieza pieza) {
		if (pieza.obtenerTipo() == Tipo.CABALLO)
			return true;
		else
			return false;
	}
//-------------------------------------------------------------------------------
	/**
	 * Método que mueve la una pieza de Origen a Destino,comprueba si es primer movimiento e incrementa el número de turnos.
	 * @param origen	Celda de origen.
	 * @param destino	Celda de destino.
	 */
	public void mover (Celda origen, Celda destino) {
		
		Pieza ori  = origen.obtenerPieza();
		
		if (ori.esPrimerMovimiento())						//Comprobamos si es la primera vez que se juega una piez para cambiar su estado.
			ori.marcarPrimerMovimiento();
		
		n_jugadas++;
		
		//Reemplazamos las piezas por las correctas
		destino.eliminarPieza();							//Eliminamos pieza de Celda destino
		tablero.colocar(origen.obtenerPieza(), destino);	//Colocamos la nueva pieza en destino
		origen.eliminarPieza();								//eliminamos pieza de Celda origen
		
		
	}
	
//-------------------------------------------------------------------------------
	
	/**
	 * Con esta función, dado una pieza y una a la que movernos, podemos obtener un array de enteros con la siguiente información:
	 * 
	 * [0]: El número de filas que avanza la pieza por comprobación.
	 * [1]: El número de columnas que avanza la pieza por comprobación.
	 * [2]: El número de comprobaciones que se harán.
	 * 
	 * Cada comprobación teiene como objetivo: Comprobar que no hay entre la PIEZA de origen, y la CELDA destino.
	 * No realizamos la comprobación en este método porque el objetivo es reutilizarlo en los métodos publicos.
	 * 
	 * 
	 * @param p_ori Pieza de origen.
	 * @param c_dest Celda de origen.
	 * @return un array unidimensional de enteros.
	 */
	
	private int [] casillas (Pieza p_ori, Celda c_dest) {
		//	Comprobaciones Inciales
		if (p_ori == null || c_dest == null)										//Comprobamos que ni la pieza ni la celda estén VACÍAS
			return null;
		
		//Obtenemos la celda en la que se encuentra la pieza.
		Celda c_ori = p_ori.obtenerCelda();
		
		//Comprobamos que la pieza que queremos reemplazar no sea una pieza AMIGA
		if (c_dest.estaVacia() == false) 
			if (p_ori.obtenerColor() == c_dest.obtenerColorDePieza())				//Una pieza amiga es una pieza del mismo color.
				return null;
			
		
		/*-----------------------
		 *	Cuerpo
		 *
		 * Creamos un ARRAY de enteros con 3 posiciones
		 * 
		 * La primera posición indica el número de filas a avanzar o retroceder
		 * La segunda posición indica el número de columnas
		 * La tercera, el número de veces que se ha de repetir el movimiento hasta llegar a la celda (Se usará en un bucle para comprobar si hay piezas de por medio)
		 * 
		 * 		{DesplazamientoFilas, DesplazamientoColumnas, NúmeroDeDesplazamientos}
		 * 
		 * El array se calculará para cada pieza, la dirección y color.
		 * */
		
		int [] res = null;								
		       
		       
		//---------------- Sentidos
			
		Sentido movimiento = tablero.obtenerSentido(c_ori, c_dest);				 //Obtenemos el sentido entre ambas piezas
		
		//Array de sentidos ordenados en el sentido dextrógiro
		Sentido [] sentidos = {Sentido.VERTICAL_N,Sentido.DIAGONAL_NE,Sentido.HORIZONTAL_E,Sentido.DIAGONAL_SE,
				Sentido.VERTICAL_S,Sentido.DIAGONAL_SO,Sentido.HORIZONTAL_O,Sentido.DIAGONAL_NO};

		//----------------
		
		
		/* EL MOVIMIENTO DE LA DAMA
		 * La dama según su dirección, puede actuar como una TORRE ó un ALFIL, por ello, vamos
		 * a identificar su movimiento y a devolver en el array los movimientos equivalentes a
		 * la torre o el alfil
		 * 
		 * Para ello trabajaremos con un booleano:
		 * 
		 * TRUE: Si el movimiento es diagonal
		 * FALSE Si el movimiento NO es diagonal
		 * 
		 * En el aray sentidos:
		 * 
		 * PARES:   Movimientos NO diagonales
		 * IIMPARES: Movimientos diagonales
		 * */
		
		boolean damaDiagonal = false;
		
		for (int i =0 ; i < sentidos.length && !esCaballo(p_ori); i++ ) {	//Tenemos en cuenta si la pieza no es un caballo, ya que no tiene un movimiento que no se ajusta al enum
			if (movimiento.equals(sentidos[i])) {
				if (i % 2 == 0)												
					damaDiagonal = false;
				else							
					damaDiagonal = true;
			}//Fin if
		}//Fin for
		
		/*
		Calculamos el número de casillas que avanza en cada dirección.
		
		Horizontal -> Número de columnas que avanza con el movimiento.
		Vertical   -> Número de filas    que avanza con el movimiento.
		*/
		int horizontal = c_dest.obtenerColumna() - c_ori.obtenerColumna();		 //Columna destino -  columna origen
		int vertical   = c_dest.obtenerFila()    - c_ori.obtenerFila();    		 //Fila destino    -  fila origen

		//-----------------------------------------------------
		
		//Obtenemos el array dependiendo de cada pieza:
		
		//TORRE ó DAMA con movimientos que no son en DIAGONAL
		if (p_ori.obtenerTipo() == Tipo.TORRE || (p_ori.obtenerTipo() == Tipo.DAMA && damaDiagonal == false)) 
			res = movTorre (horizontal,vertical,movimiento);

		//ALFIL ó DAMA con movimientos diagonales
		if (p_ori.obtenerTipo() == Tipo.ALFIL || (p_ori.obtenerTipo() == Tipo.DAMA && damaDiagonal == true)) 
			res = movAlfil (horizontal,vertical,movimiento);

		//CABALLO
		if (p_ori.obtenerTipo() == Tipo.CABALLO) 
			res = movCaballo (horizontal,vertical);

		//PEON 
		if (p_ori.obtenerTipo() == Tipo.PEON) 
			res = movPeon(horizontal,vertical,p_ori,c_dest);
		//REY
		if (p_ori.obtenerTipo() == Tipo.REY) 
			res = movRey (horizontal,vertical);
	
		return res;
	}

//------------------------------------
	/**
	 * MOVER TORRE:
	 * 
	 * Método que de vuelve un array de enteros con los movimientos de la pieza TORRE Ó DAMA.
	 * 
	 * @param horizontal	Número de filas que se mueve.
	 * @param vertical		Número de columnas.
	 * @param movimiento	Sentido en el que se mueve.
	 * @return				El array con los movimientos o null si es un movimiento ilegal.
	 */
	private int [] movTorre (int horizontal, int vertical, Sentido movimiento) {
		int [] res = new int [3];
		
		if ((horizontal == 0 && vertical != 0) || (vertical == 0 && horizontal !=0)) {		//Si se mueve en un sentido PAR
			
			res[0] = movimiento.obtenerDesplazamientoEnFilas();					//numero de filas
			res[1] = movimiento.obtenerDesplazamientoEnColumnas();				//numero de columnas
			res[2] = Math.abs(horizontal + vertical);							//Como sólo avanza en una dirección, será el mismo número
																				//Un número será 0, y el otro el número de casillas, que necesitamos que siempre sea positivo.
			
			return res;
		}
		else 
			return null;
	}

//------------------------------------
	/**
	 * Método que de vuelve un array de enteros con los movimientos de la pieza ALFIL Ó DAMA.
	 * @param horizontal	Número de filas.
	 * @param vertical		Número de columnas.
	 * @param movimiento	Sentido en el que se mueve.
	 * @return				El array con los movimientos o null si es un movimiento ilegal.
	 */
	private int [] movAlfil (int horizontal, int vertical,Sentido movimiento) {
		int [] res = new int [3];
		

		if (Math.abs(horizontal) != Math.abs(vertical))						//Si no avanza el mismo número de filas que de columnas (Diagonal), no es válido
			return null;
		
		res[0] = movimiento.obtenerDesplazamientoEnFilas();					//numero de filas
		res[1] = movimiento.obtenerDesplazamientoEnColumnas();				//numero de columnas
		res[2] = Math.abs(horizontal);										//Como se desplaza el mismo número de casillas en diagonal que
																			//en horizontal, sólo ponemos 1
		return res;
	}

//------------------------------------
	/**
	 * Método que de vuelve un array de enteros con los movimientos de la pieza CABALLO.
	 * @param horizontal	Número de filas.
	 * @param vertical		Número de columnas
	 * @return				El array con los movimientos o null si es un movimiento ilegal.
	 */
	private int [] movCaballo (int horizontal, int vertical) {
		int [] res = new int [3];	
		
		int v = Math.abs (vertical);										//Obtenemos el valor absoluto de vertical para comparar
		int h = Math.abs(horizontal);										//Obtenemos el valor absoluto de horizontal para comprar
		
		if ((v == 2 && h == 1) || (v == 1 && h == 2)){						//Comprobamos que el movimiento es válido (Una L positiva en ambos sentidos)
			res[0] = vertical;
			res[1] = horizontal;
			res[2] = 1;														//El caballo sólo se moverá 1 vez del tirón, 															
																			//por lo que no hará falta comprobar si hay piezas de por medio, ya que salta. 							
			return res;
		}
		else
			return null;
	}
	
//------------------------------------
	/**
	 * Método que de vuelve un array de enteros con los movimientos de la pieza PEÓN.
	 * 
	 * @param horizontal	Número de filas.
	 * @param vertical		Número de columnas.
	 * @param p_ori			La pieza que vamos a mover (necesitamos su color).
	 * @param c_dest		La casilla de destino (necesitamos saber si está vacía o no).
	 * @return				El array con los movimientos o null si es un movimiento ilegal.
	 */
	private int [] movPeon (int horizontal, int vertical, Pieza p_ori, Celda c_dest) {
		int [] res = new int [3];	
		
		if (p_ori.obtenerColor()  == Color.BLANCO && (vertical >0 ) )				//Si blanco retrocede
			return null;
		
		if (p_ori.obtenerColor()  == Color.NEGRO && (vertical <0 ) )				//Si negro retrocede
			return null;
		
		if (p_ori.esPrimerMovimiento() == true && Math.abs(vertical) == 2 && horizontal ==0 && c_dest.estaVacia()) {	//Primer movimiento (2 casillas vacías)															//Comprobamos si el PEON se mueve 2 casillas en vertical
			res[0] = vertical/2;											//Una casila por por iteración, mantenemos el signo.
			res[1] = 0;
			res[2] = 2;														//DOS comprobaciones con el FOR del movimiento
			return res;
		}
		
		else if (Math.abs(vertical) == 1 && Math.abs(horizontal) == 0 && c_dest.estaVacia()) {	//Moviemiento en vertical de 1 unidad y no hay enemigo.
			res[0] = vertical;												//Una casila por por iteración, mantenemos el signo.
			res[1] = 0;
			res[2] = 1;														//Una única comprobación
			return res;
		} 
	
		else if (Math.abs(vertical) == 1  && Math.abs(horizontal) == 1 && (c_dest.estaVacia() == false)) {
			if (c_dest.obtenerColorDePieza() != p_ori.obtenerColor()) {		//Si la celda tiene a un ENEMIGO
				res[0] = vertical; 
				res[1] = horizontal;
				res[2] = 1;													
				return res;
			}
		}
		return null;
	}

//------------------------------------	
	/**
	 * Método que de vuelve un array de enteros con los movimientos de la pieza REY.
	 * 
	 * @param horizontal	El número de filas.
	 * @param vertical		El número de columnas.
	 * @return				El array con los movimientos o null si es un movimiento ilegal.
	 */
	private int [] movRey (int horizontal, int vertical) {
		int [] res = new int [3];
		

		int v = Math.abs(vertical);												//Valor absoluto del avance en vertical (Para facilitar expresiones)
		int h = Math.abs(horizontal);											//Valor absoluto del avance en horizontal
		
		if((v == 0 && h ==1 ) || (v == 1 && h == 0) || (v == 1 && h == 1)) {	//Si avanza una casilla en todas las direcciones

			res[0] = vertical;
			res[1] = horizontal;
			res[2] = 1;
			return res;
		}
		else 
			return null;
	}
//-------------------------------------------------------------------------------
	
	/**
	 * Está en Jaque.
	 * Pasado un color, indica si el rey de ese color está en Jaque.
	 * 
	 * Durante la ejecución de este método se utiliza la bandera simulacroJaqueColor
	 * @see simulacroJaqueColor
	 * 
	 * @param color que vamos a comprobar.
	 * @return True o false.
	 */
	
	public boolean estaEnJaque(Color color) {
		simulacroJaqueColor = true;
		
		//Obtenemos la celda en la que se encuentra el rey del color.
			
		Celda rey = null;
		
		for (int i=0; i< tablero.obtenerNumeroFilas(); i++) {			//Escaneamos todo el tablero en busca de un REY de color
			for (int j=0; j< tablero.obtenerNumeroColumnas(); j++) {
				if (tablero.obtenerCelda(i,j).estaVacia() == false)
					if (tablero.obtenerCelda(i, j).obtenerPieza().obtenerTipo() == Tipo.REY)		//Si la pieza de esa celda es un rey
						if (tablero.obtenerCelda(i, j).obtenerPieza().obtenerColor() == color)		//y coincide con el color pasado
							rey = tablero.obtenerCelda(i, j);												
			}//Fin j
		}//Fin i
		
		boolean esJaque = false;
		
		for (int i=0; i< tablero.obtenerNumeroFilas(); i++) {			//Escaneamos todo el tablero hasta obtener una pieza ENEMIGA
			for (int j=0; j< tablero.obtenerNumeroColumnas(); j++) {	
				Celda enemigo = tablero.obtenerCelda(i, j);
				if (enemigo.obtenerColorDePieza() != color) {			//Si la celda cotiene a un enemigo, calculamos si puede llegar a la celda del rey
					if (esMovimientoLegal(enemigo,rey) == true && rey != null) {
						esJaque = true;
					}
				}
			}
		}
		simulacroJaqueColor = false;
		return esJaque; 
	}
	
//-------------------------------------------------------------------------------	
	
	/**
	 * Comprueba si un movimiento pone al rey en jaque.
	 * 
	 * Durante la ejecución de este método se utiliza la bandera simulacroJaque
	 * @see simulacroJaque
	 * 
	 * @param origen		Celda de origen a comprobar.
	 * @param destino		Celda de destino con la que realizaremos el movimiento.
	 * @return				true o false.
	 */
	
	public boolean estaEnJaqueTrasSimularMovimientoConTurnoActual (Celda origen, Celda destino) {
		
		boolean esJaque = false;
		simulacroJaque = true;
		
		if (this.esMovimientoLegal(origen, destino)) {				//Comprobamos que la simulación sea posible
			
			//Dos piezas que contengan los datos de las otras 2
			Pieza piezaOrigen  = new Pieza (origen.obtenerPieza().obtenerTipo(),origen.obtenerColorDePieza());
			
			if(origen.obtenerPieza().esPrimerMovimiento() == false)	//Comprobamos a ver si había sido utilizada
				piezaOrigen.marcarPrimerMovimiento();
			
			Pieza piezaDestino = null;							
			if (destino.estaVacia() == false) {
				piezaDestino = new Pieza(destino.obtenerPieza().obtenerTipo(),destino.obtenerColorDePieza());
				
				if(destino.obtenerPieza().esPrimerMovimiento() == false)
					piezaDestino.marcarPrimerMovimiento();
				
			}
			//System.out.println(1 + tablero.toString());
			tablero.colocar(origen.obtenerPieza(), destino);						//Simulamos un movimiento
			//reducirTurno();
			origen.eliminarPieza();
			
			esJaque = estaEnJaque(destino.obtenerColorDePieza());	//Comprobamos si el rey está en peligro
			//System.out.println(2 + tablero.toString());
			
			//Borramos las 2 piezas que hemos movido
			destino.eliminarPieza();								
			
			
			//Recolocamos las piezas nuevas
			tablero.colocar(piezaOrigen, origen);
			tablero.colocar(piezaDestino,destino);
		}
		else
			esJaque = false;
		//System.out.println(tablero.toString());
		simulacroJaque = false;
		return esJaque;
		
	}
	
//-------------------------------------------------------------------------------	
	
	/**
	 * Método que genera la jugada en notación algebraica.
	 * 
	 * @param origen	Celda de origen.
	 * @param destino	Celda de destino
	 * @return			Si ambas Celdas existen y son válidas, devuelve la jugada algebraica.
	 */
	public String obtenerJugadaEnNotacionAlgebraica(Celda origen, Celda destino) {
		
		if (origen == null || destino == null)								//Si una de las celdas no está iniciada, devolvemos null
			return null;
				
		String jugada = "";													//String con el que vamos a trabajar
		
		jugada += tablero.obtenerCoordenadaEnNotacionAlgebraica(origen);
		jugada += tablero.obtenerCoordenadaEnNotacionAlgebraica(destino);
		
		return jugada;
	}
	
//-------------------------------------------------------------------------------

	/**
	 * Método que devuelve el TURNO ACTUAL.
	 * @return integer con todas las jugadas realizadas por ambos jugadores.
	 * @see n_jugadas
	 */
	public int obtenerNumeroJugada() {
		
		return this.n_jugadas;
	}
	
//-------------------------------------------------------------------------------
	
	/**
	 * Método que devuelve el color del turno actual.
	 * @return Color color (BLACO/NEGRO).
	 * @see empezar
	 * @see turno
	 */
	public Color obtenerTurno(){
		if (this.empezar == false)
			return null;
		else return this.turno;
	}

}//FIN Arbitro
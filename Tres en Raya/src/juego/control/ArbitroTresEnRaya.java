package juego.control;

//IMPORTACIONES
import juego.modelo.Celda;
import juego.modelo.Color;
import juego.modelo.Jugador;
import juego.modelo.Pieza;
import juego.modelo.Tablero;
import juego.util.Direccion;

/**
*
*
*@author Jorge Ruiz Gómez
*
*/


public class ArbitroTresEnRaya{

	private int numeroJugadores;
	private boolean juegoAcabado = false;
	private static int NUM_GANADOR=3;
	
	private Tablero tablero;
	
	private Jugador[] jugadores;
	
	//----------personales
	private int n_turnos = 0;
	
//--constructores--------------------------------------
	
	/**
	 * Contructor de la clase
	 */
	public  ArbitroTresEnRaya(){

		numeroJugadores=2;
		juegoAcabado=false;
		jugadores = new Jugador [numeroJugadores];
		this.tablero = new Tablero(3,3);
	}

	
//--métodos-----------------------------------------------
	
	/**
	 * Esta clase crea un objeto de tipo Jugador con las distintas características dependiendo del nombre pasado como atributo.
	 * @param nombre
	 */
	public void registrarJugador (String nombre) {
		
		Color [] color = {Color.BLANCO, Color.NEGRO} ;			//Array con los elementos del enum Color
		
		for (int i=0; i< numeroJugadores; i++) {
			
			if (jugadores[i] == null) {							//Si no hay un objeto jugador inicializado en el array jugadores[], crea un nuevo jugador
				jugadores[i] = new Jugador(nombre, color[i]);
				i = numeroJugadores; 							//Salida del bucle si se asigna un jugador
			}
		}
	}
	
	
	public Jugador obtenerTurno(){
		
		Jugador turno;											//variable a retornar
		
		
		if (jugadores[0] != null && jugadores[1] != null) {		//Comprobamos que ambos jugadores existen

				if (this.n_turnos % 2 == 0) 					//Los turnos se asignan dependiendo de si la variable n_turnos es par o impar
					turno = jugadores[0];
				else 
					turno = jugadores[1];
				}
		else 
			turno = null;										//Si no existen jugadores, el método devuelve null
		return turno;
	}
	
		
	/**
	 * 
	 * Esta función comprueba quien de los dos jugadores es el ganador
	 * 
	 * Si un jugador tiene NUM_GANADOR piezas consecutivas de su color, gana.
	 * 
	 * 
	 * @return
	 */
	public Jugador obtenerGanador(){
		
		if (jugadores [0] == null || jugadores[1] == null)
			return null;
		
		
		Direccion [] direcciones =										//Array con todos los elementos del Enum Direccion
			{Direccion.HORIZONTAL,Direccion.VERTICAL,Direccion.DIAGONAL_SO_NE, Direccion.DIAGONAL_NO_SE};
		
		Jugador ganador = null;
		
		for (int i=0; i < tablero.obtenerNumeroFilas(); i++) {			//Buscamos todas las filas
			for (int j=0; j < tablero.obtenerNumeroColumnas(); j++) {		//Buscamos todas las columnas
				for (int k=0; k < direcciones.length; k++ ) {			//Buscamos con todas las direcciones del tipo enumerado
					
					if (victoria (tablero.obtenerCelda(j, i),direcciones[k]) != null) {
						ganador = victoria (tablero.obtenerCelda(j, i),direcciones[k]);

																		//Detenemos el bucle
						i = tablero.obtenerNumeroFilas();				
						j = tablero.obtenerNumeroColumnas();
						k = direcciones.length;
					}
				}//for k
			}//for j    
		}//for i
		return ganador;	
	}

	/**
	 * Esta función devuelve un jugador que tenga NUM_GANADOR piezas consecutivas de un mismo color
	 * en una direccion y una celda.
	 * 
	 * @param celda	Celda de entrada
	 * @param dir	Direccion en la que vamos a comprobar las direcciones de las piezas
	 * 
	 * @return 		Calcula el jugador ganador a partir del color de la celda probada
	 */
	
	private Jugador victoria (Celda celda, Direccion dir) {
		
		Color colorGanador;
		//Contamos las piezas para la casilla
		
		if (tablero.contarPiezas (celda,dir) == NUM_GANADOR) {		//Comprueba si hay NUM_GANDORR piezas consecutivas del mismo color que la pieza dada
			colorGanador = celda.obtenerPieza().obtenerColor();		//Obtenemos el color del ganador
			
			for (int i=0; i<this.numeroJugadores ; i++) {			//
				if (colorGanador == jugadores[i].obtenerColor()) {
					return jugadores[i];
				}
			}
			return null;
		}
		else
			return null;

	}
	

	/**
	 * @param x Posición X que quiere el jugador
	 * @param y Posición Y que quiere el jugador
	 * 
	 * Dada una posición del tablero, el jugador que tiene el turno coloca una pieza en una posición dada
	 * Terminada la jugada:
	 * Se comprueba si la jugada ha terminado
	 * El turno cambia al jugador contrario
	 * 
	 */
	
	public void jugar (int x, int y){
		
		Celda celda = tablero.obtenerCelda(y, x);
		
		if (obtenerTurno() != null) {
			Pieza pieza = obtenerTurno().generarPieza();
			tablero.colocar(pieza, celda);
			n_turnos++;
		}
		else 
			System.out.println ("No hay suficientes jugadores para jugar");
	}
	
	
	/**
	 * 
	 * @param x Columna a comprobar
	 * @param y Fila a comprobar
	 * 
	 * La función devuelve si es posible colocar una pieza en una celda vacía.
	 * 
	 * @return true o false dependiendo de si es posible
	 */
	public boolean esMovimientoLegal (int y, int x){
	
		Celda celda;
		
		if (tablero == null)
			return false;
		
		
		
		//Comprobamos que la pieza esté dentro de los límites
		if (tablero.estaEnTablero(y, x) == false)
			return false;
		
		//Comprobamos que la celda no esté vacía
		celda = tablero.obtenerCelda(x,y);
		
		
		
		
		if (celda.estaVacia() == true)
			return true ;	
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @return El tablero que usa el objeto
	 */
	public Tablero obtenerTablero() {
		return this.tablero;
	}
	
	/**
	 * Esta función comprueba si el juego se ha terminado
	 * 
	 * Puede haber dos motivos
	 * 
	 * 1º, un jugador ha ganado
	 * 
	 * 2º, el tablero está completo
	 * 
	 * @return true o false, dependiento del resultado.
	 */
	
	public boolean estaAcabado() {
		
		
		if (tablero.estaCompleto()==true)
			return juegoAcabado = true;
			 
		
		if (obtenerGanador() != null)
			return juegoAcabado = true;
		
		if (obtenerGanador() != null && tablero.estaCompleto() == true)
			return juegoAcabado = true;
		
		if (juegoAcabado == false)			//Comparado simple para evitar Warnings a la hora de compilar por no usar la variable juegoAcabado
			return juegoAcabado = false;
		else
			return juegoAcabado = false;
		
	}
	
}

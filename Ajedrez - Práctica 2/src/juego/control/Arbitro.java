package juego.control;

import java.util.ArrayList;
import java.util.List;

//IMPORTACIONES
import juego.modelo.Celda;
import juego.modelo.Color;
import juego.modelo.CoordenadasIncorrectasException;
import juego.modelo.Tablero;
import juego.modelo.pieza.Alfil;
import juego.modelo.pieza.Caballo;
import juego.modelo.pieza.Dama;
import juego.modelo.pieza.Peon;
import juego.modelo.pieza.Pieza;
import juego.modelo.pieza.Rey;
import juego.modelo.pieza.Torre;
import juego.util.Sentido;

/**
*
*Árbitro del juego Ajedrez. Permite jugar (mover las distintas piezas) de forma legal.
*Dos jugadrores, Blanco y Negro. 
*
*@author Jorge Ruiz Gómez.
*@since  JDK 11.
*@version 2.0.
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
	
	/**
	 * Referencia del rey negro.
	 */
	private Rey reyNegro;
	
	/**
	 * Referencia del rey blanco.
	 */
	private Rey reyBlanco;

	
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
	 * Devuelve todas las piezas que no son peones ordenadas según el color.
	 * @param color Color de la pieza.
	 * @return Lista de tipo Tipo con todas las piezas ordenadas.
	 */
	
	private ArrayList <Pieza> generarPiezas(Color color){

		ArrayList <Pieza> piezas = new ArrayList<Pieza>();
		
		piezas.add(new Torre 	(color));
		piezas.add(new Caballo 	(color));
		piezas.add(new Alfil 	(color));
		piezas.add(new Dama 	(color));
		piezas.add(new Rey 		(color));
		piezas.add(new Alfil 	(color));
		piezas.add(new Caballo 	(color));
		piezas.add(new Torre 	(color));
		
		return piezas;
	}
//---------------------------------------------------------
	
	/**
	 * Método que coloca todas las piezas en el tablero, y pasa el turno al jugador BLANCO.
	 */
	public void colocarPiezas() {
		
		ArrayList <Pieza> blancas = generarPiezas(Color.BLANCO);
		ArrayList <Pieza> negras = generarPiezas(Color.NEGRO);

		
		try {	
			for (int i=0; i<blancas.size() ; i++) {		
	
				//	NEGRAS:
				Pieza piezaN = negras.get(i);									//Creación de las piezas negras en orden
				Pieza peonN  = new Peon (Color.NEGRO);							//Peón Negro
				tablero.colocar(piezaN, 0, i);									//Colocación de la pieza
				tablero.colocar(peonN , 1, i);									//Colocación del peon NEGRO
				
				//	BLANCAS:
				Pieza piezaB = blancas.get(i);
				Pieza peonB  = new Peon (Color.BLANCO);		
				tablero.colocar(piezaB, tablero.obtenerNumeroFilas() -1, i);
				tablero.colocar(peonB , tablero.obtenerNumeroFilas() -2, i);	
				

			}
			colocarReyes();
		}
		catch (CoordenadasIncorrectasException e) {
			encadenarExcepciones(e);
		}

		empezar = true;													//Permitimos que comience la partida
	}
//-------------------------------------------------------------------------------
/**
 * Método que permite pasar las distintas piezas para generar tableros personalizados.
 * @param piezas Array con las distintas Piezas.
 * @param coordenadas Posición donde se colocará cada piezas.
 * @param negro		Referencia a la pieza Rey de color negro pasada.
 * @param blanco	Referencia a la pieza Rey de color blanco pasada.
 * @throws CoordenadasIncorrectasException Excepción lanzada si una de las celdas no está en el tablero. 
 */
	public void colocarPiezas(Pieza [] piezas, int [][] coordenadas, Rey negro, Rey blanco) throws CoordenadasIncorrectasException {
		

		if (piezas.length == coordenadas.length) {		//Comprobamos que los 2 arrays tengan el mismo tamaño.

			for(int i=0; i<piezas.length; i++) {
				tablero.obtenerCelda(coordenadas[i][0], coordenadas[i][1]).eliminarPieza();
				tablero.colocar(piezas[i], tablero.obtenerCelda(coordenadas[i][0], coordenadas[i][1]));
			}
			

			this.empezar = true;															//Permitimos que se pueda comenzar la partida. 
			 colocarReyes(blanco, negro);
		}//end if	
	}//end method
	


//-------------------------------------------------------------------------------		
	
	/**
	 * Booleano que comprueba si el movimiento es correcto.
	 * @param origen	Celda de origen.
	 * @param destino	Celda de destino.
	 * @return true si es legal, false si no lo es.
	 * @throws CoordenadasIncorrectasException Lanza una excepción si las celdas no están en tablero. 
	 */
	public boolean esMovimientoLegal (Celda origen, Celda destino) throws CoordenadasIncorrectasException{
				
		if (algunaCeldaNula (origen,destino) || origen.estaVacia())
			return false;
		
		//Comprobamos que la pieza que queremos reemplazar no sea una pieza AMIGA
		if (destino.estaVacia() == false) 
			if (origen.obtenerColorDePieza() == destino.obtenerColorDePieza())			//Una pieza amiga es una pieza del mismo color.
				return false;
		
		if (esSimulacroJaque() == false && esSimulacroJaqueColor() == false)			//Si no estamos comprobando un JAQUE
			if (origen.obtenerColorDePieza() != this.obtenerTurno())		
				return false;															//El color del turno no coincide con el de la pieza a mover
																						//Es un movimiento ilegal

		List <Celda> celdas = tablero.obtenerCeldasEntreMedias(origen, destino);		//obtenemos las celdas que hay entre el movimiento.
		
		boolean hayPiezasEntreMedias = this.hayPiezasEntreMedias(celdas);				//Comprobamos si tienen piezas entre medias.
		
		Sentido sentido = tablero.obtenerSentido(origen, destino);						//Calculamos el sentido del movimiento.

		return origen.obtenerPieza().esCorrectoMoverA(destino, sentido, hayPiezasEntreMedias);	//Calculamos para la pieza de origen si su movimiento es válido.
}
//-------------------------------------------------------------------------------
	/**
	 * Método que encuentra a los dos reyes de cada color para almacenarlos.
	 * @see reyBlanco
	 * @see reyNegro
	 */
	private void colocarReyes() {
		
		reyBlanco = encontrarRey(Color.BLANCO);
		reyNegro  = encontrarRey(Color.NEGRO );
	}
//-------------------------------------------------------------------------------
	/**
	 * Método que actualiza las referencias de los reyes para almacenarlos.
	 * @param blanco Rey de color blanco.
	 * @param negro  Rey de color negro. 
	 * @see reyBlanco
	 * @see reyNegro
	 */
	private void colocarReyes(Rey blanco, Rey negro) {
		
		this.reyBlanco = blanco;
		this.reyNegro  = negro ;
		
	}
	
//-------------------------------------------------------------------------------
	
	/**
	 * Método que obtiene el rey a partir de un color. 
	 * @param color Color a encontrar el rey.
	 * @return la Pieza del Rey. 
	 */
	private Rey encontrarRey(Color color){
		
		return (Rey) obtenerPiezaReyEscaneo(color);
		
	}
//-------------------------------------------------------------------------------
	/**
	 * Método de tipo getter que permite obtener las referencia de cada rey a partir de un color.
	 * @param color Color del rey que queremos.
	 * @see reyBlanco
	 * @see reyNegro
	 * @return la Pieza del Rey.
	 */
	private Rey obtenerReyColor(Color color){
		
		if 	 (color == Color.BLANCO) {
			return this.reyBlanco;
		}
		else {
			return this.reyNegro;
		}

	}
	
//-------------------------------------------------------------------------------
	/**
	 * Método booleano que devuelve si una de las celdas pasadas es nula.
	 * @param a Celda a comprobar.
	 * @param b Celda a comprobar.
	 * @return true si una de las celdas es nula, false si ninguna lo es. 
	 */
	private boolean algunaCeldaNula(Celda a, Celda b) {
		if (a == null || b== null)
			return true ;
		else
			return false;
	}
//-------------------------------------------------------------------------------
	/**
	 * Método booleano que devuelve si una de las piezas pasadas es nula.
	 * @param a Pieza a comprobar.
	 * @param b Pieza a comprobar.
	 * @return true si una de las celdas es nula, false si ninguna lo es. 
	 */
	private boolean algunaPiezaNula(Pieza a, Pieza b) {
		if (a == null || b== null)
			return true ;
		else
			return false;
	}

//-------------------------------------------------------------------------------
	/**
	 * Método que devuelve si en alguna de las celdas de la lista de celdas no está vacía.
	 * @param celdas Lista  con las celdas.
	 * @return True si una de las celdass no está vacía, False si todas están vacías. 
	 */
	private boolean hayPiezasEntreMedias(List<Celda> celdas) {
		
		for (int i=0; i< celdas.size(); i++)
			if (celdas.get(i).estaVacia() == false)
				return true;
		
		return false;
	}
	
//-------------------------------------------------------------------------------
	/**
	 * Método que mueve la una pieza de Origen a Destino,comprueba si es primer movimiento e incrementa el número de turnos.
	 * @param origen	Celda de origen.
	 * @param destino	Celda de destino.
	 * @throws CoordenadasIncorrectasException Lanza una excepción si las piezas no están en el tablero.
	 */
	public void mover (Celda origen, Celda destino) throws CoordenadasIncorrectasException {
		
		assert origen != null 					: "Árbitro.mover: Origen Nulo";
		assert origen.estaVacia() == false 		: "Árbitro.mover: Origen Vacío";

		if (origen.obtenerPieza().esPrimerMovimiento())						//Comprobamos si es la primera vez que se juega una piez para cambiar su estado.
			origen.obtenerPieza().marcarPrimerMovimiento();
		
		//Reemplazamos las piezas por las correctas
		destino.eliminarPieza();												//Eliminamos pieza de Celda destino
		tablero.colocar(origen.obtenerPieza(), destino);						//Colocamos la nueva pieza en destino
		origen.establecerPieza(null);											//eliminamos pieza de Celda origen
		
		//Aumentamos el contador de jugadas.
		n_jugadas++;
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
		
		marcarSimulacroJaqueColor(true);					//Activamos la bandera de JAQUE.
		
		boolean esJaque = false;

		try {
			for (int i=0; i< tablero.obtenerNumeroFilas(); i++) {			//Escaneamos todo el tablero hasta obtener una pieza ENEMIGA
				for (int j=0; j< tablero.obtenerNumeroColumnas(); j++) {	
					Celda enemigo = tablero.obtenerCelda(i, j);
					if (enemigo.obtenerColorDePieza() != color) {			//Si la celda cotiene a un enemigo, calculamos si puede llegar a la celda del rey
						if (esMovimientoLegal(enemigo,obtenerReyColor(color).obtenerCelda())) {
							esJaque = true;									//Si se puede llegar al REY, ES JAQUE.
						}
					}
				}
			}
			marcarSimulacroJaqueColor(false);
			return esJaque;
		}catch(CoordenadasIncorrectasException e) {
			encadenarExcepciones(e);
		}
		return esJaque;
		}

//-------------------------------------------------------------------------------		
	/**
	 * Método que obtiene la referencia de la pieza del rey a partir de las celdas del tablero.
	 * @param colorReyBuscar Color del rey a buscar.
	 * @return referencia de tipo pieza con el Rey.
	 */
	private Pieza obtenerPiezaReyEscaneo(Color colorReyBuscar) {
		
		try {
			for (int i=0; i< tablero.obtenerNumeroFilas(); i++) {			//Escaneamos todo el tablero en busca de un REY de color
				for (int j=0; j< tablero.obtenerNumeroColumnas(); j++) {
					if (tablero.obtenerCelda(i,j).estaVacia() == false)
						if (tablero.obtenerCelda(i, j).obtenerPieza().toChar() == 'R')		//Si la pieza de esa celda es un rey
							if (tablero.obtenerCelda(i, j).obtenerPieza().obtenerColor() == colorReyBuscar)		//y coincide con el color pasado
								return tablero.obtenerCelda(i, j).obtenerPieza();		
				}//Fin j
			}//Fin i
		}
		catch (CoordenadasIncorrectasException e) {
			encadenarExcepciones(e);
		}
		return null;
		
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
	 * @throws CoordenadasIncorrectasException Lanza una excepción si las Celdas no están en el tablero.
	 */
	
	public boolean estaEnJaqueTrasSimularMovimientoConTurnoActual (Celda origen, Celda destino) throws CoordenadasIncorrectasException {
		
		boolean esJaque = false;
		marcarSimulacroJaque(true);
		
		if (this.esMovimientoLegal(origen, destino)) {				//Comprobamos que la simulación sea posible
			String tableroOriginal = tablero.toString();
			
			esJaque = simularMovimientoJaque(origen,destino);
			
			String tableroTrasSimulacro = tablero.toString();
			
			assert compararTableros(tableroOriginal, tableroTrasSimulacro) == true : "Los tableros no son iguales.";
		}
		else
			esJaque = false;

		marcarSimulacroJaque(false);

		
		return esJaque;
		
	}
//------------------------------------------------------------------------------
	/**
	 * Método que simula un movimiento por Jaque.
	 * @param origen Celda de origen.
	 * @param destino Celda de destino
	 * @return true o false
	 * @throws CoordenadasIncorrectasException Lanza una excepción si una de las celdas no pertenecen al tablero.
	 */
	private boolean simularMovimientoJaque (Celda origen, Celda destino) throws CoordenadasIncorrectasException {

		
		////Creación de pieza de origien
		Pieza nuevaPiezaOrigen = generarPieza(origen.obtenerPieza().toChar(), origen.obtenerColorDePieza());
		copiarMovimientoPieza(origen.obtenerPieza(), nuevaPiezaOrigen);
		
		//Creación de pieza de destino
		Pieza nuevaPiezaDestino = null;	//Inicialización de la pieza
		if(!destino.estaVacia()) {
			nuevaPiezaDestino = generarPieza(destino.obtenerPieza().toChar(), destino.obtenerColorDePieza());
			copiarMovimientoPieza(destino.obtenerPieza(), nuevaPiezaDestino);
		}
			
		//Movimiento de la pieza a simular (Origen a destino)
		tablero.colocar(origen.obtenerPieza(), destino);
		origen.establecerPieza(null);
		
		// Comprobamos si estamos en jaque
		boolean esJaque = estaEnJaque(destino.obtenerColorDePieza());
		
		// Reestablecemos las piezas movidas.
		destino.eliminarPieza();

		if(nuevaPiezaDestino != null)
			tablero.colocar(nuevaPiezaDestino, destino);
		
		tablero.colocar(nuevaPiezaOrigen, origen);
		
		actualizarReferenciasReyes(nuevaPiezaOrigen );
		actualizarReferenciasReyes(nuevaPiezaDestino);

		return esJaque;
	}
//--------------------------------------------------------------------
	/**
	 * Método que actualiza las referencias de global si la pieza pasada es un Rey.
	 * 
	 * @param pieza Pieza a guardar.
	 */
	private void actualizarReferenciasReyes(Pieza pieza) {
		
		if (pieza != null) {
			
			if (pieza.toChar() == 'R') {
				if (pieza.obtenerColor() == Color.BLANCO)
					this.reyBlanco = (Rey) pieza;
				else
					this.reyNegro  = (Rey) pieza;
			}//------------
		}
	}

//-------------------------------------------------------------------------------
	/**
	 * Método privado que permite averiguar si la pieza origina había sido movida,
	 * y si es así, marcar dicho movimiento a la copia. 
	 * @param original Pieza original en la que se obtendrá el movimiento.
	 * @param copia    Pieza copiada en la que se grabará el movimiento.
	 */
	private void copiarMovimientoPieza (Pieza original, Pieza copia){
		if (!algunaPiezaNula(original,copia)) {
			if(original.esPrimerMovimiento() == false)
				copia.marcarPrimerMovimiento();
		}	
	}
//-------------------------------------------------------------------------------
	
	/**
	 * Método que compara dos strings.
	 * @param a Tablero 1.
	 * @param b Tablero 2.
	 * @return true si son iguales.
	 */
	private boolean compararTableros (String a, String b) {
		
		if (a.equals(b))
			return true;
		else
			return false;
		
	}
	
//-------------------------------------------------------------------------------
	/**
	 * Método que alterna la varaible simulacroJaque.
	 * @param simulacro Estado actual del simulacro.
	 * @see simulacroJaque
	 */
	private void marcarSimulacroJaque (boolean simulacro) {
		
		simulacroJaque = simulacro;
	}
//-------------------------------------------------------------------------------	
	/**
	 * Método que obtiene el valor de la vatriable simulacroJaque.
	 * @return true o false.
	 * @see simulacroJaque
	 */
	private Boolean esSimulacroJaque() {
		
		return this.simulacroJaque;
	}
//-------------------------------------------------------------------------------
	/**
	 * Método que alterna la varaible simulacroJaqueColor.
	 * @param simulacro Estado actual del simulacro.
	 * @see simulacroJaqueColor
	 */
	private void marcarSimulacroJaqueColor (boolean simulacro) {
		
		simulacroJaqueColor = simulacro;
	}
//-------------------------------------------------------------------------------	
	/**
	 * Método que obtiene el valor de la vatriable simulacroJaqueColor.
	 * @return true o false.
	 * @see simulacroJaqueColor
	 */
	private boolean esSimulacroJaqueColor() {
		
		return this.simulacroJaqueColor;
	}
//-------------------------------------------------------------------------------
	/**
	 * Método que genera una pieza a partir del char correspondiente y un color.
	 * @param c Char de la pieza.
	 * @param color Color a generar la pieza.
	 * @return Pieza. 
	 */
	private Pieza generarPieza (char c, Color color) {
		
		switch (c) {
		case 'T':
			return new Torre 	(color);
		case 'A':
			return new Alfil 	(color);
		case 'R':
			return new Rey 	 	(color);
		case 'C':
			return new Caballo 	(color);
		case 'P':
			return new Peon 	(color);
		case 'D':
			return new Dama 	(color);
		}
		return null;
	}
	
//-------------------------------------------------------------------------------	
	
	/**
	 * Método que genera la jugada en notación algebraica.
	 * 
	 * @param origen	Celda de origen.
	 * @param destino	Celda de destino
	 * @return			Si ambas Celdas existen y son válidas, devuelve la jugada algebraica.
	 * @throws CoordenadasIncorrectasException Lanza una excepción si las piezas no están en el tablero.
	 */
	public String obtenerJugadaEnNotacionAlgebraica(Celda origen, Celda destino) throws CoordenadasIncorrectasException {
		
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
//------------------------------------------------
	/**
	 * Este método lanza una excepción de tipo runtime si se lanza una excepción comprobable la cual no
	 * permite la ejecución correcta del programa. 
	 * @param e Excepción de entrada de tipo CoordenadasIncorrectasException.
	 */
	private void encadenarExcepciones(CoordenadasIncorrectasException e) {
		throw new RuntimeException("Se ha detenido la ejecución del programa por una excepcion",e); 
		
	}
	
//------------------------------------------------

}//FIN Arbitro
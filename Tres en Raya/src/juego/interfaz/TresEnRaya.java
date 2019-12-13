package juego.interfaz;


// COMPLETAR PAQUETE...

import java.util.Scanner;

import juego.control.ArbitroTresEnRaya;
import juego.modelo.Tablero;

// COMPLETAR IMPORTACIONES....

/**
 * Juego del tres en raya. Ejercicio práctico - Metodología de la Programación.
 * <p>
 * 2º GII - Curso 2019-2020
 * 
 * @author Jorge Ruiz Gómez
 */
public class TresEnRaya {

	/** Número de argumentos máximo. */
	private static final int NUM_ARGUMENTOS = 2;

	/** Arbitro. */
	private static ArbitroTresEnRaya arbitro;
	
	/**
	 * Flujo de ejecución principal del juego.
	 * 
	 * @param args
	 *            nombres de los jugadores
	 */
	public static void main(String[] args) {
		if (args.length == NUM_ARGUMENTOS) {
			mostrarAyuda();
		} else {
			// Inicialización del juego según los argumentos validados
			// COMPLETAR POR EL ALUMNO
			
			arbitro = new ArbitroTresEnRaya();
			Scanner teclado = new Scanner(System.in); // teclado permite leer enteros con el método nextInt
			
			if (arbitro == null) {
				System.out.printf("Problema en la creación del árbitro. Deteniendo la ejecución...\n");
				System.exit(0);
			}
			
			
			System.out.printf("Introduzca el nombre del primer jugador:  ");
			arbitro.registrarJugador(teclado.nextLine());
			
			System.out.printf("Introduzca el nombre del segundo jugador: ");
			arbitro.registrarJugador(teclado.nextLine());
			
			
			
			// Fase de juego, iteramos sobre las distintas jugadas.

			// COMPLETAR POR EL ALUMNO
			
			while (arbitro.estaAcabado() == false) {
				System.out.printf("\n-------------------------------------------------\n");
				System.out.printf("El turno actual será para %s con la pieza %c\n",arbitro.obtenerTurno().toString(), arbitro.obtenerTurno().obtenerColor().toChar());
				
				System.out.printf("Estado actual del tablero:\n\n");
				imprimirTablero(arbitro.obtenerTablero());
				
				boolean jugadaCorrecta=false;
				int x,y;
				do {
					

					System.out.printf("\nIntroduzca la columna en la que desea colocar la ficha: ");
					x=teclado.nextInt();
					System.out.printf("\nIntroduzca la fila en la que desea colocar la ficha:    ");
					y=teclado.nextInt();
					
					//Como las piezas van de 0 a 2, y se muestra una entrada de 1 a 3, es necesario restar 1 unidad
					x--;
					y--;
					
					if (arbitro.esMovimientoLegal(x, y) == false) {
						System.out.printf("La celda introducida no es válida, vuelva a intentarlo\n");
					}
					else
						jugadaCorrecta=true;
					
				
				}while (jugadaCorrecta==false);
				


				arbitro.jugar(x, y);
			}
			
			//Salida por pantalla sobre los resultados de la partida
			
			if (arbitro.obtenerTablero().estaCompleto()==false)
				System.out.printf("\n\nEl ganador es %s.",arbitro.obtenerGanador().toString());
			else
				System.out.println("Nadie ha ganado, TABLAS");
			System.out.println("Tablero Final:");
			imprimirTablero(arbitro.obtenerTablero());
			teclado.close();
		} // número de argumentos correcto
	}

	/**
	 * Muestra la ayuda en línea para la inicialización correcta del juego.
	 */
	private static void mostrarAyuda() {
		// COMPLETAR...
	}

	/**
	 * Muestra el estado actual del tablero.
	 * 
	 * @param tablero
	 *            tablero a pintar en pantalla.
	 */

	
	private static void imprimirTablero(Tablero tablero) {
		
		char [] estadoTablero = tablero.toString().toCharArray();

		
		System.out.print("      ");
		for (int i=0; i < tablero.obtenerNumeroColumnas(); i++) {	//Creacion de la línea superior con los números
			System.out.print(i+1 + "" );
		}
		
		int columna = 1;
			System.out.printf("\n    %d ",columna);					//Imprime el número 1 en el lateral
		
		for (int i=0; i < estadoTablero.length; i++) {
			
			System.out.print(estadoTablero[i]);						//Imprime la letra del tablero
			
			if (estadoTablero[i]=='\n' && columna < tablero.obtenerNumeroFilas())		//Si la última letra es un salto de línea, imprime los números del lateral
				
				System.out.print("    " + ++columna + " ");			//Añade los índices de los laterales con un margen respecto a la consola	
			
		}
		System.out.println();										// último salto de línea

	}

} // TresEnRaya
package juego.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.security.SecureRandom;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import juego.modelo.pieza.Peon;
import juego.modelo.pieza.Pieza;


/**
 * Pruebas unitarias sobre la celda.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20181008
 * 
 */
@DisplayName("Tests sobre Celda")
public class CeldaTest {
	
	/** Generador de aleatorios. */
	private static Random aleatorios = new SecureRandom();

	/**
	 * Test del constructor.
	 */
	@DisplayName("Constructor con estado inicial correcto")
	@Test
	void constructor() {
		final int TAMAÑO = 8;
		for (int fila = 0; fila < TAMAÑO; fila++) {
			for (int columna = 0; columna < TAMAÑO; columna++) {
				Celda celda = new Celda(fila, columna);
				assertThat("Fila mal inicializada", celda.obtenerFila(), is(fila));
				assertThat("Columnna mal inicializada", celda.obtenerColumna(), is(columna));
				assertTrue("Inicialmente no está vacía.", celda.estaVacia());
				assertNull("Tiene pieza cuando debería vale null.", celda.obtenerPieza());
			}
		}
	}

	/**
	 * Comprueba la colocación en celda.
	 */
	@DisplayName("Coloca una pieza en una celda")
	@Test
	void colocarEnCelda() {
		Pieza pieza = new Peon(Color.BLANCO);
		Celda celda = new Celda(0, 0);
		celda.establecerPieza(pieza);
		assertThat("Color mal inicializado", celda.obtenerPieza(), is(pieza));
	}

	/**
	 * Test del método toString.
	 */
	@DisplayName("Formato de texto")
	@Test
	void probarToString() {
		for (int fila = 0; fila < 10; fila++) {
			for (int columna = 0; columna < 10; columna++) {
				Celda celda = new Celda(fila, columna);
				String actual = celda.toString().replaceAll("\\s", "");
				assertThat("Texto incorrecto." + celda.toString(), actual, is("(" + fila + "/" + columna + ")"));
			}
		}
	}

	/** Elimina pieza en celda ocupada. */
	@DisplayName("Elimina una pieza en una celda ocupada")
	@Test
	void eliminarPieza() {
		Celda celda = new Celda(0, 0);
		assertTrue("La celda no está vacía.", celda.estaVacia());
		celda.establecerPieza(new Peon(Color.NEGRO));
		assertFalse("La celda está vacía.", celda.estaVacia());
		celda.eliminarPieza();
		assertNull(celda.obtenerPieza());
		assertTrue("La celda no está vacía.", celda.estaVacia());
	}

	/** Obtiene el color de celdas con piezas. */
	@DisplayName("Obtiene el color de la pieza que ocupa la celda")
	@Test
	void obtenerColorDePiezaEnCeldasOcupadas() {
		Celda celda = new Celda(0, 0);
		for (Color color : Color.values()) {
			celda.establecerPieza(new Peon(color));
			assertThat("Color incorrecto.", celda.obtenerColorDePieza(), is(color));
		}
	}

	/** Obtiene color de celdas vacías. */
	@DisplayName("Obtiene color nulo de la pieza de una celda vacía")
	@Test
	void obtenerColorDePiezaEnCeldasVacías() {
		Celda celda = new Celda(0, 0);
		assertNull("Color incorrecto.", celda.obtenerColorDePieza());
	}

	/** Comprueba la igualdad de coordenadas entre celdas. */
	@DisplayName("Tienen las mismas coordenadas celdas coincidentes en posición")
	@Test
	void comprobarIgualdadDeCoordenadas() {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				Celda celda1 = new Celda(i, j);
				Celda celda2 = new Celda(i, j);
				assertTrue(celda1.tieneCoordenadasIguales(celda2));
				assertTrue(celda2.tieneCoordenadasIguales(celda1));
			}
		}
	}

	/** Comprueba la desigualdad de coordenadas entre celdas. */
	@DisplayName("Tienen diferentes coordenadas celdas NO coincidentes en posición")
	@Test
	void comprobarDesigualdadDeCoordenadas() {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				Celda celda1 = new Celda(i, j);
				int desplazamiento1 = aleatorios.nextInt(10) + 1;
				int desplazamiento2 = aleatorios.nextInt(10) + 1;
				Celda celda2 = new Celda(i + desplazamiento1, j + desplazamiento2);
				assertFalse(celda1.tieneCoordenadasIguales(celda2));
				assertFalse(celda2.tieneCoordenadasIguales(celda1));
				Celda celda3 = new Celda(i, j + desplazamiento2);
				assertFalse(celda1.tieneCoordenadasIguales(celda3));
				assertFalse(celda3.tieneCoordenadasIguales(celda1));
				Celda celda4 = new Celda(i + desplazamiento1, j);
				assertFalse(celda1.tieneCoordenadasIguales(celda4));
				assertFalse(celda4.tieneCoordenadasIguales(celda1));
			}
		}
	}
}

package juego.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias sobre el sentido.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20190812
 * 
 */
@DisplayName("Tests sobre Sentido")
public class SentidoTest {
	
	/**
	 * Correctos desplazamientos para cada sentido.
	 */
	@DisplayName("Comprobar desplazamientos para cada sentido.")
	@Test
	public void probarDesplazamientos() {
		assertAll("letras correctas",
			() -> assertThat("Desplazamiento en filas en diagonal NE mal definido.", 
					Sentido.DIAGONAL_NE.obtenerDesplazamientoEnFilas(), is(-1)),
			
			() -> assertThat("Desplazamiento en columnas en  diagonal NE mal definido.",
					Sentido.DIAGONAL_NE.obtenerDesplazamientoEnColumnas(), is(+1)),
			
			() -> assertThat("Desplazamiento en filas en diagonal NO mal definido.", 
					Sentido.DIAGONAL_NO.obtenerDesplazamientoEnFilas(), is(-1)),
			
			() -> assertThat("Desplazamiento en columnas en diagonal NO mal definido.", 
					Sentido.DIAGONAL_NO.obtenerDesplazamientoEnColumnas(), is(-1)),
			
			() -> assertThat("Desplazamiento en filas en diagonal SE mal definido.", 
					Sentido.DIAGONAL_SE.obtenerDesplazamientoEnFilas(), is(+1)),
			
			() -> assertThat("Desplazamiento en columnas en diagonal SE mal definido.", 
					Sentido.DIAGONAL_SE.obtenerDesplazamientoEnColumnas(), is(+1)),
			
			() -> assertThat("Desplazamiento en filas en diagonal SO mal definido.", 
					Sentido.DIAGONAL_SO.obtenerDesplazamientoEnFilas(), is(+1)),
			
			() -> assertThat("Desplazamiento en columnas en diagonal SO mal definido.", 
					Sentido.DIAGONAL_SO.obtenerDesplazamientoEnColumnas(), is(-1)),
			
			() -> assertThat("Desplazamiento en filas en horizontal E mal definido.", 
					Sentido.HORIZONTAL_E.obtenerDesplazamientoEnFilas(), is(0)),
			
			() -> assertThat("Desplazamiento en columnas en horizontal E mal definido.", 
					Sentido.HORIZONTAL_E.obtenerDesplazamientoEnColumnas(), is(+1)),

			() -> assertThat("Desplazamiento en filas en horizontal O mal definido.", 
					Sentido.HORIZONTAL_O.obtenerDesplazamientoEnFilas(), is(0)),
			
			() -> assertThat("Desplazamiento en columnas en horizontal E mal definido.",
					Sentido.HORIZONTAL_O.obtenerDesplazamientoEnColumnas(), is(-1)),
			
			() -> assertThat("Desplazamiento en filas en vertical N mal definido.", 
					Sentido.VERTICAL_N.obtenerDesplazamientoEnFilas(), is(-1)),
			
			() -> assertThat("Desplazamiento en columnas en vertical N mal definido.", 
					Sentido.VERTICAL_N.obtenerDesplazamientoEnColumnas(), is(0)),
			
			() -> assertThat("Desplazamiento en filas en vertical S mal definido.", 
					Sentido.VERTICAL_S.obtenerDesplazamientoEnFilas(), is(+1)),
			
			() -> assertThat("Desplazamiento en columnas en vertical S mal definido.", 
					Sentido.VERTICAL_S.obtenerDesplazamientoEnColumnas(), is(0))

			);			
	} 
}


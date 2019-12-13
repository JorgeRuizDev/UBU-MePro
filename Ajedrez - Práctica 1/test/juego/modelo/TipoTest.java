package juego.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias sobre el tipo.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20190812
 * 
 */
@DisplayName("Tests sobre Tipo")
public class TipoTest {
	
	/**
	 * Correcta inicialización de letras para cada tipo.
	 */
	@DisplayName("Letras correctas para cada tipo.")
	@Test
	public void probarLetra() {
		assertAll("letras correctas",
			() -> assertThat(Tipo.ALFIL.toChar(), is('A')),
			() -> assertThat(Tipo.DAMA.toChar(), is('D')),
			() -> assertThat(Tipo.REY.toChar(), is('R')),
			() -> assertThat(Tipo.TORRE.toChar(), is('T')),
			() -> assertThat(Tipo.CABALLO.toChar(), is('C'))
			);			
	} 
}

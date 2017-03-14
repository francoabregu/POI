package pruebasEntrega2;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import mocks.MockMapaConDelay;
import tpAnual_dominio.Accion;
import tpAnual_dominio.EnviadorDeMails;
import tpAnual_dominio.Terminal;

public class PruebasDeMails {
	private MockMapaConDelay mapa;
	private Terminal terminal;
	private EnviadorDeMails enviador;

	@Before
	public void setUp(){
		mapa = new MockMapaConDelay(1);
		enviador = Mockito.mock(EnviadorDeMails.class);
	}

	@Test
	public void SiLaBusquedaEsRapidaNoSeEnviaMail() {
		terminal = new Terminal("prueba", mapa, enviador, 5, 0.0, 0.0);
		terminal.habilitarAccion(Accion.notificar_por_mail);
		
		terminal.realizarBusqueda("consulta de prueba");
		
		Mockito.verify(enviador, Mockito.never()).informarBusquedaLenta("prueba", "consulta de prueba", 5, 1);
	}
	
	@Test
	public void SiLaBusquedaEsLentaSeEnviaMail() {
		terminal = new Terminal("prueba", mapa, enviador, 0, 0.0, 0.0);
		terminal.habilitarAccion(Accion.notificar_por_mail);
		
		terminal.realizarBusqueda("consulta de prueba");
		
		Mockito.verify(enviador).informarBusquedaLenta("prueba", "consulta de prueba", 0, 1);
	}

}

package pruebasEntrega3;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import mocks.MockEnviadorEmails;
import mocks.MockProcesoAsincronico;
import mocks.FallarUnaCantidadDeVeces;
import mocks.NuncaFallar;
import mocks.SiempreFallar;
import tpAnual_dominio.EnviadorDeMails;
import tpAnual_dominio.NoHacerNada;
import tpAnual_dominio.NotificarAdmin;
import tpAnual_dominio.ReintentarNVeces;

public class pruebasAccionesAnteFallo {

	private EnviadorDeMails enviador;
	private NoHacerNada accionBoba;
	private NotificarAdmin notificar;
	private ReintentarNVeces reintentar3Veces;
	private MockProcesoAsincronico proceso;
	private FallarUnaCantidadDeVeces fallar2Veces;
	private NuncaFallar nuncaFallar;
	private SiempreFallar siempreFallar;

	@Before
	public void setUp(){
		enviador = new MockEnviadorEmails();
		accionBoba = new NoHacerNada();
		notificar = new NotificarAdmin(enviador);
		reintentar3Veces = new ReintentarNVeces(3);
		
		fallar2Veces = new FallarUnaCantidadDeVeces(2);
		nuncaFallar = new NuncaFallar();
		siempreFallar = new SiempreFallar();
	}
	
	@Test
	public void elProcesoQueNoFallaNoEjecutaAccionDeError() {
		proceso = new MockProcesoAsincronico(nuncaFallar,accionBoba);
		
		proceso.ejecutar();
		
		assertTrue(proceso.seRegistroResultado());
		assertEquals(proceso.cantidadDeFallos(), 0, 0) ;
	}
	
	@Test
	public void elProcesoQueFallaSiempreEjecutaAccionDeError(){
		proceso = new MockProcesoAsincronico(siempreFallar, accionBoba);
		
		proceso.ejecutar();
		
		assertFalse( proceso.seRegistroResultado());
		assertEquals(proceso.cantidadDeFallos(), 1, 0) ;
		
	}
	
	@Test
	public void elProcesoQueFallaNVecesEjecutaAccionDeError(){
		proceso = new MockProcesoAsincronico(fallar2Veces, accionBoba);
		
		proceso.ejecutar();
		
		assertFalse(proceso.seRegistroResultado());
		assertEquals(proceso.cantidadDeFallos(), 1, 0) ;
	}
	
	@Test
	public void anteFalloSeNotificaPorMail(){
		proceso = new MockProcesoAsincronico(siempreFallar, notificar);
		
		proceso.ejecutar();
		
		assertTrue(((MockEnviadorEmails) enviador).seEnvioEmail());
	}
	
	@Test
	public void anteFalloSeReintentaLaCantidadApropiadaDeVeces(){
		proceso = new MockProcesoAsincronico(fallar2Veces, reintentar3Veces);
		
		proceso.ejecutar();
		
		assertEquals(proceso.cantidadDeFallos(),2, 0);
	}
	
	@Test 
	public void siFallaSiempreSoloReintentaLaCantidadApropiada(){
		proceso = new MockProcesoAsincronico(siempreFallar, reintentar3Veces);
		
		proceso.ejecutar();
		
		assertEquals(proceso.cantidadDeFallos(), 4, 0);
	}

}

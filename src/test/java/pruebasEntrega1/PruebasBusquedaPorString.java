package pruebasEntrega1;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import tpAnual_dominio.Banco;
import tpAnual_dominio.CentroGestionParticipacion;
import tpAnual_dominio.Rubro;
import tpAnual_dominio.Servicio;
import tpAnual_dominio.LocalComercial;
import tpAnual_dominio.ParadaDeColectivo;

public class PruebasBusquedaPorString {
	
	Banco bancoNacional;
	LocalComercial laVentanita; 
	CentroGestionParticipacion cgp;
	ParadaDeColectivo parada114;
	
	//FIXME #13
	
	@Before
	public void setup() {
		bancoNacional = new Banco("banco nacional","banco de la republica de los tests",null,0,0);
	
		Rubro muebleria = new Rubro("muebleria",0.3);
		laVentanita = new LocalComercial("la ventanita","muebleria familiar",null,0,0,muebleria);
		
		Servicio asesoramientoLegal = new Servicio("asesoramiento legal");
		cgp = new CentroGestionParticipacion(null,null,null,0,0,null);
		cgp.agregarServicio(asesoramientoLegal);
		
		parada114 = new ParadaDeColectivo("calle mozart","parada de la linea 114 en la calle mozart",null,0,0);
	}
	
	@Test
	public void unLocalCoincideSiCoincideSuRubro() {
		Assert.assertTrue(laVentanita.coincideBusqueda("muebleria"));
	}
	
	@Test
	public void unLocalNoCoincideSiNoCoincideSuRubro() {
		Assert.assertFalse(laVentanita.coincideBusqueda("peluqueria"));
	}
	
	@Test
	public void unCgpCoincideSiTieneServicioConElNombreBuscado() {
		Assert.assertTrue(cgp.coincideBusqueda("asesoramiento"));
	}

	@Test
	public void unCgpNoCoincideSiNoTieneServicioConElNombreBuscado() {
		Assert.assertFalse(cgp.coincideBusqueda("pago"));
	}

	@Test
	public void unBancoCoincideSiCoincideSuNombreODescripcion() {
		Assert.assertTrue(bancoNacional.coincideBusqueda("banco"));
	}
	
	@Test
	public void unBancoNoCoincideSiNoCoincideSuNombreODescripcion() {
		Assert.assertFalse(bancoNacional.coincideBusqueda("relojeria"));
	}
	
	@Test
	public void unaParadaCoincideSiCoincideSuNombreODescripcion() {
		Assert.assertTrue(parada114.coincideBusqueda("mozart"));
	}
	
	@Test
	public void unaParadaNoCoincideSiNoCoincideSuNombreODescripcion() {
		Assert.assertFalse(parada114.coincideBusqueda("viamonte"));
	}
	
}
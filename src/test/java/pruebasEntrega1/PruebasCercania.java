package pruebasEntrega1;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbar.geodds.Point;
import org.uqbar.geodds.Polygon;

import tpAnual_dominio.Banco;
import tpAnual_dominio.CentroGestionParticipacion;
import tpAnual_dominio.LocalComercial;
import tpAnual_dominio.ParadaDeColectivo;
import tpAnual_dominio.Rubro;

public class PruebasCercania {
	
	Banco banco;
	LocalComercial local; 
	CentroGestionParticipacion cgp;
	ParadaDeColectivo parada;
	
	Polygon comuna;
	Point puntoA50mtsDelOrigen;
	Point puntoA250mtsDelOrigen;
	Point puntoA600mtsDelOrigen;
	Point puntoEnELBordeDeLaComuna;
	Point puntoFueraDeLaComuna;

	@Before
	public void init(){		
		Point punta1 = new Point(-50.0, -50.0);
		Point punta2 = new Point(-50.0, 50.0);
		Point punta3 = new Point(50.0, -50.0);
		Point punta4 = new Point(50.0, 50.0);
		comuna = new Polygon(Arrays.asList(punta1,punta2,punta3,punta4));
		
		puntoA50mtsDelOrigen = new Point(-0.000044, 0.000445);
		puntoA250mtsDelOrigen = new Point(-0.000126,0.002247 );
		puntoA600mtsDelOrigen = new Point(-0.000277, 0.005399);
		puntoEnELBordeDeLaComuna = punta1;
		puntoFueraDeLaComuna = new Point(80,80);
		
		banco = new Banco(null,null,null,0,0);
		Rubro muebleria = new Rubro("muebleria",0.3);
		local = new LocalComercial(null,null,null,0,0,muebleria);
		parada = new ParadaDeColectivo(null,null,null,0,0);
		cgp = new CentroGestionParticipacion(null,null,null,0,0,comuna);	
	}

	@Test
	public void unBancoEstaCercaAMenosDeSuRadio() {
		Assert.assertTrue(banco.estaCercaDe(puntoA250mtsDelOrigen));
	}
	
	@Test
	public void unBancoEstaLejosAMasDeSuRadio() {
		Assert.assertFalse(banco.estaCercaDe(puntoA600mtsDelOrigen));
	}
	
	@Test
	public void unLocalEstaCercaDentroDeSuRadio() {
		Assert.assertTrue(local.estaCercaDe(puntoA250mtsDelOrigen));
	}
	
	@Test
	public void unLocalEstaLejosFueraDeSuRadio() {
		Assert.assertFalse(local.estaCercaDe(puntoA600mtsDelOrigen));
	}
	
	@Test
	public void unCgpEstaCercaSiElPuntoEstaDentroDeSuComuna() {
		Assert.assertTrue(cgp.estaCercaDe(puntoA50mtsDelOrigen));
	}
	
	@Test
	public void unCgpEstaCercaSiElPuntoEstaEnElBordeDeLaComuna() {
		Assert.assertTrue(cgp.estaCercaDe(puntoEnELBordeDeLaComuna));
	}
	
	@Test
	public void unCgpEstaLejosSIElPuntoEstaFueraDeSuComuna() {
		Assert.assertFalse(cgp.estaCercaDe(puntoFueraDeLaComuna));
	}
	
	@Test
	public void unaParadaEstaCercaSiElPuntoEstaDentroDeSuRango() {
		Assert.assertTrue(parada.estaCercaDe(puntoA50mtsDelOrigen));
	}
	
	@Test
	public void unaParadaNOEstaCercaSiElPuntoEstaFueraDeSuRango() {
		Assert.assertFalse(parada.estaCercaDe(puntoA250mtsDelOrigen));
	}
	
}
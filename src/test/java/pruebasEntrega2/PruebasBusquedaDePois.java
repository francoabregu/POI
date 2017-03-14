package pruebasEntrega2;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import tpAnual_dominio.Banco;
import tpAnual_dominio.CentroGestionParticipacion;
import tpAnual_dominio.Rubro;
import tpAnual_dominio.Servicio;
import tpAnual_dominio.LocalComercial;
import tpAnual_dominio.ParadaDeColectivo;

import tpAnual_dominio.MapaReal;

public class PruebasBusquedaDePois {

	MapaReal mapa = new MapaReal();
	
	@Before
	public void setupMapa(){
		Banco bancoDeComercio = new Banco("bancom","banco de comercio",null,0,0);
		Banco bancoPatagonia = new Banco("patagonia","banco patagonia",null,0,0);

		Servicio habilitaciones = new Servicio("habilitacion comercio");
		CentroGestionParticipacion cgpComuna7 = 
				new CentroGestionParticipacion("cgp7","cgp comuna 7",null,0,0, null);
		cgpComuna7.agregarServicio(habilitaciones);
		
		Rubro comercio = new Rubro("comercio",0.4);
		Rubro kiosco = new Rubro("kiosco",0.3);
		LocalComercial tiendaMozart = new LocalComercial("mozart","venta de discos",null,0,0,comercio);
		LocalComercial kioscoCobo = new LocalComercial("Cobo","Maxi Kiosco Cobo",null,0,0,kiosco); 
		
		ParadaDeColectivo parada101Cobo = new ParadaDeColectivo("cobo",
				"parada de la linea 101 en la av. cobo",null,0,0);
		ParadaDeColectivo parada101Mozart = new ParadaDeColectivo("mozart",
				"parada de la linea 101 en la calle mozart",null,0,0);
		ParadaDeColectivo parada7Mozart = new ParadaDeColectivo("mozart",
				"parada de la linea 7 en la calle mozart",null,0,0);
		ParadaDeColectivo parada26DelComercio = new ParadaDeColectivo("del comercio",
				"parada de la linea 26 en el pasaje el comercio",null,0,0);
		
		mapa.agregarPoi(bancoDeComercio);
		mapa.agregarPoi(bancoPatagonia);
		mapa.agregarPoi(cgpComuna7);
		mapa.agregarPoi(tiendaMozart);
		mapa.agregarPoi(kioscoCobo);
		mapa.agregarPoi(parada101Cobo);
		mapa.agregarPoi(parada101Mozart);
		mapa.agregarPoi(parada7Mozart);
		mapa.agregarPoi(parada26DelComercio);
	}
	
	@Test
	public void testBusquedaRetornaListaVacia(){
		Assert.assertTrue(mapa.buscarPois("farmacia").isEmpty());
	}
	
	@Test
	public void testBusquedaRetornaUnLocalComercial(){
		Assert.assertTrue(mapa.buscarPois("kiosco").size() == 1);
	}
	
	@Test
	public void testBusquedaRetornaUnaParada(){
		Assert.assertTrue(mapa.buscarPois("cobo").size() == 1);
	}
	
	@Test
	public void testBusquedaRetornaUnBanco(){
		Assert.assertTrue(mapa.buscarPois("patagonia").size() == 1);
	}
	
	@Test
	public void testBusquedaRetornaDosParadas(){
		Assert.assertTrue(mapa.buscarPois("mozart").size() == 2);
	}
		
	@Test
	public void testBusquedaRetornaUnoDeCadaTipo(){
		Assert.assertTrue(mapa.buscarPois("comercio").size() == 4);
	}

}
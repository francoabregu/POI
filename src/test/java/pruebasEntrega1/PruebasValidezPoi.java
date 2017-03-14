package pruebasEntrega1;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import tpAnual_dominio.Banco;

public class PruebasValidezPoi {
	
	Banco banco, bancoSinNombre, bancoFueraDeLatitud, bancoFueraDeLongitud;
	
	@Before
	public void setup() {		
		banco = new Banco("banco",null,null,-179,89);
		bancoSinNombre = new Banco(null,null,null,0,0);
		bancoFueraDeLatitud = new Banco("banco",null,null,-180,0);
		bancoFueraDeLongitud = new Banco("banco",null,null,0,90);
		
	}

	@Test
	public void unPoiNoEsValidoSiNoTieneNombre() {
		Assert.assertFalse(bancoSinNombre.esValido());
	}
	
	@Test
	public void unPoiNoEsValidoSiNoTieneLatitudValida() {
		Assert.assertFalse(bancoFueraDeLatitud.esValido());
	}
	
	@Test
	public void unPoiNoEsValidoSiNoTieneLongitudValida() {
		Assert.assertFalse(bancoFueraDeLongitud.esValido());
	}
	
	@Test
	public void unPoiEsValidoSiTieneNombreLatitudLongitudValidas() {
		Assert.assertTrue(banco.esValido());
	}

}
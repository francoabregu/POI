package pruebasEntrega1;

import org.junit.Assert;
import org.junit.Test;

import tpAnual_dominio.Banco;

public class PruebasValidesPoi { //FIXME validez va con 'z' (?) xD
	

	//Un Poi no es valido si no tiene nombre
	@Test
	public void unPoiNoEsValidoSiNoTieneNombre() {
		//Precondiciones
		//Uso un banco porque la clase Poi es abstracta y todos los tipos de 
		//pois se validan de la misma manera
		Banco banco = new Banco(null, "banco nacional", null, 0, 0);
		
		//Operaciones
		
		//Post condiciones
		Assert.assertFalse(banco.esValido());
	}
	
	//Un Poi no es valido si no tiene latitud entre -180 y 180
	@Test
	public void unPoiNoEsValidoSiNoTieneLatitudValida() {
		//Precondiciones
		//Uso un banco porque la clase Poi es abstracta y todos los tipos de 
		//pois se validan de la misma manera
		Banco banco = new Banco("banco", "banco nacional", null, -500.57, 0);
		
		//Operaciones
		
		//Post condiciones
		Assert.assertFalse(banco.esValido());
	}
	
	//Un Poi no es valido si no tiene longitud entre -90 y 90
	@Test
	public void unPoiNoEsValidoSiNoTieneLongitudValida() {
		//Precondiciones
		//Uso un banco porque la clase Poi es abstracta y todos los tipos de 
		//pois se validan de la misma manera
		Banco banco = new Banco("banco", "banco nacional", null, 0, 725.64);
		
		//Operaciones
		
		//Post condiciones
		Assert.assertFalse(banco.esValido());
	}
	
	//Un Poi es valido si tiene nombre y latitud entre -180 y 180 y longitud entre -90 y 90
	@Test
	public void unPoiEsValidoSiTieneNombreLatitudLongitudValidas() {
		//Precondiciones
		//Uso un banco porque la clase Poi es abstracta y todos los tipos de 
		//pois se validan de la misma manera
		Banco banco = new Banco("banco", "banco nacional", null, 0, 0);
		
		//Operaciones
		
		//Post condiciones
		Assert.assertTrue(banco.esValido());
	}
}

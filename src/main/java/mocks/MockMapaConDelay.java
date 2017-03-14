package mocks;

import java.util.ArrayList;
import java.util.List;

import tpAnual_dominio.Mapa;
import tpAnual_dominio.Poi;

public class MockMapaConDelay extends Mapa {
	
	int segundosDeDelay;

	public MockMapaConDelay(int segundosDeDelay) {
		this.segundosDeDelay = segundosDeDelay;
	}

	@Override
	public List<Poi> buscarPois(String clave) {
		try {
			Thread.sleep(segundosDeDelay*1000);
		} 
		catch (InterruptedException e) {
			segundosDeDelay = Math.abs(segundosDeDelay);
			buscarPois(clave);
		}
		return new ArrayList<Poi>();
	}

}
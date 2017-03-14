package mocks;

import tpAnual_dominio.EnviadorDeMails;
import tpAnual_dominio.ProcesoAsincronico;

public class MockEnviadorEmails extends EnviadorDeMails {

	private boolean fueLlamado = false;

	@Override
	public void informarBusquedaLenta(String nombreTerminal, String criterioDeBusqueda,
			long tiempoMaximoBusquedaPermitido, long tiempoBusqueda) {
		this.fueLlamado  = true;

	}
	
	@Override
	public void informarFallaEnProcesoAsincronico(ProcesoAsincronico procesoFallido) {
		this.fueLlamado = true;
		
	}



	public boolean seEnvioEmail() {
		return fueLlamado;
	}

}
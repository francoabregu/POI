package mocks;

import java.time.LocalDateTime;

import tpAnual_dominio.AccionDeProceso;
import tpAnual_dominio.ResultadoDeProcesoAsincronico;

public class FallarUnaCantidadDeVeces implements AccionDeProceso {
	
	private int cantidadDeFallas;

	public FallarUnaCantidadDeVeces(int cantidadDeFallas) {
		this.cantidadDeFallas = cantidadDeFallas;
	}

	@Override
	public ResultadoDeProcesoAsincronico ejecutarAccion() throws Exception {
		if(cantidadDeFallas > 0){
			cantidadDeFallas--;
			throw new Exception("Produje una falla");
		}
		
		return new ResultadoDeProcesoAsincronico(0,LocalDateTime.now(), "exito");

	}

}

package mocks;

import java.time.LocalDateTime;

import tpAnual_dominio.AccionDeProceso;
import tpAnual_dominio.ResultadoDeProcesoAsincronico;

public class NuncaFallar implements AccionDeProceso {

	@Override
	public ResultadoDeProcesoAsincronico ejecutarAccion() {
		return new ResultadoDeProcesoAsincronico(0, LocalDateTime.now(), "exito");
	}


}

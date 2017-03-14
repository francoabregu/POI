package mocks;

import tpAnual_dominio.AccionDeProceso;
import tpAnual_dominio.ResultadoDeProcesoAsincronico;

public class SiempreFallar implements AccionDeProceso {


	@Override
	public ResultadoDeProcesoAsincronico ejecutarAccion() throws Exception {
		throw new Exception("Produje una falla");
	}

}

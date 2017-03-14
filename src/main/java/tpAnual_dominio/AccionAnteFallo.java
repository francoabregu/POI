package tpAnual_dominio;

import java.time.LocalDateTime;

public interface AccionAnteFallo {
	
	abstract public ResultadoDeProcesoAsincronico ejecutarAccion(ProcesoAsincronico procesoFallido);
	
	public default ResultadoDeProcesoAsincronico resultadoFallido(){
		return new ResultadoDeProcesoAsincronico(0, LocalDateTime.now(), "fallido");
	}

}

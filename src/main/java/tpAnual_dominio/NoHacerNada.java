package tpAnual_dominio;


public class NoHacerNada implements AccionAnteFallo {

	@Override
	public ResultadoDeProcesoAsincronico ejecutarAccion(ProcesoAsincronico procesoFallido) {
		return resultadoFallido();
	}

}

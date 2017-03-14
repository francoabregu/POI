package tpAnual_dominio;

public class NotificarAdmin implements AccionAnteFallo {
	
	EnviadorDeMails enviadorDeEmails;
	
	public NotificarAdmin(EnviadorDeMails enviadorDeEmails) {
		super();
		this.enviadorDeEmails = enviadorDeEmails;
	}


	@Override
	public ResultadoDeProcesoAsincronico ejecutarAccion(ProcesoAsincronico procesoFallido) {
		enviadorDeEmails.informarFallaEnProcesoAsincronico(procesoFallido);

		return resultadoFallido();
	}

}

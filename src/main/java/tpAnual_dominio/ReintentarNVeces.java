package tpAnual_dominio;

public class ReintentarNVeces implements AccionAnteFallo {

	private int CantidadDeReintentosRestantes;
	
	public ReintentarNVeces(int cantidadDeReintentos) {
		this.CantidadDeReintentosRestantes = cantidadDeReintentos;
	}
	
	@Override
	public ResultadoDeProcesoAsincronico ejecutarAccion(ProcesoAsincronico procesoFallido) {
		if(CantidadDeReintentosRestantes > 0){
			CantidadDeReintentosRestantes--;
			procesoFallido.ejecutar();
		}
		
		return procesoFallido.resultado().equals(null)? resultadoFallido() : procesoFallido.resultado();
	}

}

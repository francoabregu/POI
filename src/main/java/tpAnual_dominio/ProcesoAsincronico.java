package tpAnual_dominio;


public class ProcesoAsincronico {//Sus subclases deben redefinir toString
	
	 private AccionAnteFallo accioneAnteFalloPrincipal;
	 private AccionAnteFallo accionAnteFalloSecundaria;
	 private AccionDeProceso accionDeProceso;
	 private ResultadoDeProcesoAsincronico resultado;
	 
	
	public ProcesoAsincronico(AccionDeProceso accionDeProceso,AccionAnteFallo... accionesAnteFallo) {
		super();
		this.accionDeProceso = accionDeProceso;
	}
	
	public void ejecutar(){
		ResultadoDeProcesoAsincronico resultadoEjecucion = null;
		try{
			resultadoEjecucion = realizarAccion();			
		}catch(Exception e){
			resultadoEjecucion = fallar();
		}finally{
			almacenarResultado(resultadoEjecucion);
		}
	}
	
	ResultadoDeProcesoAsincronico realizarAccion() throws Exception{
		return accionDeProceso.ejecutarAccion();
	}
	
	protected void almacenarResultado(ResultadoDeProcesoAsincronico unResultado){
		resultado = unResultado;
	}

	
	
	protected ResultadoDeProcesoAsincronico fallar(){
		ResultadoDeProcesoAsincronico resultadoEjecucion = 	accioneAnteFalloPrincipal.ejecutarAccion(this);
		if(resultadoEjecucion.fallo())
			resultadoEjecucion = accionAnteFalloSecundaria.ejecutarAccion(this);
		
		return resultadoEjecucion;
	}
	

	
	public String toString(){
		return accionDeProceso.toString();
	}

	public ResultadoDeProcesoAsincronico resultado() {
		return resultado;
	}
	
	
}

package tpAnual_dominio;



public class AgregarAccionesAUsuario extends ModificarAccionesAUsuario {
	

	public AgregarAccionesAUsuario(Accion... acciones) {
		super(acciones);
	}

	@Override
	public ResultadoDeProcesoAsincronico ejecutarAccion() {
		accionesAAgregar.stream()
		.forEach(accion -> terminalesAModificar.stream()
									.forEach(terminal -> terminal.habilitarAccion(accion)));
		
		return super.estadoExitoso();

	}
	
	public String toString(){
		String texto = new StringBuilder().append("Agregar Acciones a Usuarios: ")
										.append(super.toString())
										.toString();
		
		return texto;	
	}

}

package tpAnual_dominio;

public class QuitarAccionesAUsuario extends ModificarAccionesAUsuario {

	public QuitarAccionesAUsuario(Accion... acciones) {
		super(acciones);
	}

	@Override
	public ResultadoDeProcesoAsincronico ejecutarAccion() {
		accionesAAgregar.stream()
		.forEach(accion -> terminalesAModificar.stream()
									.forEach(terminal -> terminal.deshabilitarAccion(accion)));
		
		return super.estadoExitoso();
	}
	
	public String toString(){
		String texto = new StringBuilder().append("Quitar Acciones a Usuarios: ")
										.append(super.toString())
										.toString();
		
		return texto;	
	}

}

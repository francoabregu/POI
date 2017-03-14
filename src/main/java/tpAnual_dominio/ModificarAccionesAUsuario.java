package tpAnual_dominio;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.uqbar.geodds.Polygon;

abstract class ModificarAccionesAUsuario implements AccionDeProceso {
	
	List <Terminal> terminalesAModificar;
	Set <Accion> accionesAAgregar;
	
	public ModificarAccionesAUsuario(Accion... acciones) {;
		accionesAAgregar = new HashSet<>(Arrays.asList(acciones));
		
	}

	
	public void agregarTerminales(Polygon comuna, RepositorioDeTerminales repo){
		terminalesAModificar = repo.obtenerTerminalesDentroDe(comuna);
	}
	
	public void agregarTerminales(RepositorioDeTerminales repo){
		terminalesAModificar = repo.obtenerTerminales();
	}
	
	public void agregarTerminales(List<Terminal> terminales){
		terminalesAModificar = new ArrayList<>(terminales);
	}
	
	public String toString(){
		String texto = new StringBuilder().append("Terminales: ")
										.append(nombresTerminales())
										.append("Acciones: ")
										.append(nombresAcciones())
										.toString();
		
		return texto;	
	}


	private String nombresTerminales() {
		StringBuilder builder = new StringBuilder();
		terminalesAModificar.stream().forEach(terminal -> builder.append(terminal.getNombre()+ ","));
		return builder.toString();
	}
	
	private String nombresAcciones() {
		StringBuilder builder = new StringBuilder();
		accionesAAgregar.stream().forEach(accion -> builder.append(accion.toString()+ ","));
		return builder.toString();
	}


	public ResultadoDeProcesoAsincronico estadoExitoso() {
		return new ResultadoDeProcesoAsincronico(terminalesAModificar.size(), LocalDateTime.now(), "exitoso");
	}


}

package tpAnual_dominio;


import java.time.LocalDateTime;
import java.util.List;

import componentesExternos.Archivo;
import componentesExternos.Linea;

public class ActualizarLocalesComerciales implements AccionDeProceso {

	private Mapa mapa;
	private Archivo archivo;
	private int cantidadPoisActualizados = 0;
	
	public ActualizarLocalesComerciales(Mapa unMapa,String unaRuta) {
		archivo = new Archivo(unaRuta);
		mapa = unMapa;
	}
	
	public ResultadoDeProcesoAsincronico ejecutarAccion(){
		archivo.getLineas().forEach(linea -> actualizarLocalesComerciales(linea));
		return new ResultadoDeProcesoAsincronico(cantidadPoisActualizados, LocalDateTime.now(), "exito");
	}
	
	private void actualizarLocalesComerciales(Linea lineaArchivo){
		List<Poi> localesAModificar =  mapa.buscarPoisPorNombre(lineaArchivo.getNombreFantasia());
		localesAModificar.forEach(poi -> actualizarLocalComercial(poi,lineaArchivo.getPalabrasClave()));
					
	}
	
	private void actualizarLocalComercial(Poi local,List<String> palabrasClaveNuevas){
		if(local instanceof LocalComercial){
			((LocalComercial)local).setPalabrasClave(palabrasClaveNuevas);	
			cantidadPoisActualizados++;
		}
	}
	
	
	public String toString(){
		String texto = new StringBuilder().append("Actualizar locales comerciales desde archivo: ")
										.append(archivo.getRuta())
										.toString();
		
		return texto;	
	}
	
	
	
	
}

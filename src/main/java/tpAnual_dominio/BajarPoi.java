package tpAnual_dominio;

import java.time.LocalDateTime;
import java.util.List;

import componentesExternos.AdaptadorSistemaBajaPois;
import tpAnual_dominio.Mapa;

public class BajarPoi implements AccionDeProceso {

	private AdaptadorSistemaBajaPois adaptadorBajaPois;
	private Mapa mapa;

	public BajarPoi(AdaptadorSistemaBajaPois adaptadorBajaPoi, Mapa mapa) {

		this.adaptadorBajaPois = adaptadorBajaPoi;
		this.mapa = mapa;
	}

	public ResultadoDeProcesoAsincronico ejecutarAccion(){
		List<Poi> pois = adaptadorBajaPois.obtenerPois();
		pois.stream().forEach(p-> mapa.eliminarPoiPorClave(p.getNombre(),p.getPosicion()));
			//FIXME no manejen los estados como strings, o al menos, tengan constantes
		return new ResultadoDeProcesoAsincronico(pois.size(), LocalDateTime.now(), "exitoso");
	}
	
	public String toString(){
		return "Accion.BajarPoi";	
	}

}

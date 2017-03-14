package tpAnual_dominio;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;

@Entity
public class MapaReal extends Mapa {
	
	public MapaReal() {
	
	}
	
	public List<Poi> buscarPois(String clave) {	
		List<Poi> resultadoBusqueda =
			pois.stream().filter(unPoi -> unPoi.coincideBusqueda(clave)).collect(Collectors.toList());
		return resultadoBusqueda;
	}
		
}
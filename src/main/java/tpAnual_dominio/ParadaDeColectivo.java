package tpAnual_dominio;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


import org.uqbar.geodds.Point;


@Entity
@DiscriminatorValue(value = "PARADACOLECTIVO")

public class ParadaDeColectivo extends Poi {

	public ParadaDeColectivo(String nombre, String descripcion, Direccion direccion, double latitud, double longitud) {
		super(nombre, descripcion, direccion, latitud, longitud);
	}
	
	public ParadaDeColectivo() {
		
	}
	
	
	
	@Override
	public boolean estaDisponible(LocalDateTime momento, String valorBuscado) {return true;};

	
	@Override
	public boolean estaCercaDe(Point unPunto) {
		return super.seEncuentraAMenosDeXMetrosDe(unPunto, 0.1);
	}


	@Override
	public boolean coincideBusqueda(String criterioBusqueda) {
		return nombre.toLowerCase().contains(criterioBusqueda) || descripcion.toLowerCase().contains(criterioBusqueda)
				|| super.coincideBusqueda(criterioBusqueda);
	}

	@Override
	protected boolean atiendeEn(LocalDateTime momento, String valorBuscado) {
		return true;
	}

	@Override
	public String obtenerNombreTipo() {
		return "Parada de Colectivo";
	}


}
package tpAnual_dominio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


import org.uqbar.geodds.Point;

@Entity
@DiscriminatorValue(value = "LOCALCOMERCIAL")

public class LocalComercial extends PoiConFranjaHoraria {
	
	
	@ManyToOne
	@org.mongodb.morphia.annotations.Transient //No se persiste en mongo
	private Rubro rubro;
	
	public LocalComercial(String nombre, String descripcion, Direccion direccion, double latitud, double longitud, Rubro rubro){
		super(nombre, descripcion, direccion, latitud, longitud);
		this.rubro = rubro;
	}
	
	public LocalComercial(){
		
	}
	
	
	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	@Override
	public boolean estaCercaDe(Point unPunto) {
		return this.posicion.distance(unPunto) <= rubro.getRadioCercania();
	}
	
	@Override
	public boolean coincideBusqueda(String criterioBusqueda) {
		return elRubroContiene(criterioBusqueda) || super.coincideBusqueda(criterioBusqueda);
	}
	
	
	private boolean elRubroContiene(String unNombre) {
		return rubro.contiene(unNombre);
	}
	
	public void agregar(FranjaDisponibilidadHoraria franjaHoraria) {
		horarioAtencion.add(franjaHoraria);
	}
	
	public void borrar(FranjaDisponibilidadHoraria franjaHoraria) {
		horarioAtencion.removeIf(franja -> franja.equals(franjaHoraria));
	}

	@Override
	public String obtenerNombreTipo() {
		return rubro.getNombre();
	}
		
	
}
package tpAnual_dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.uqbar.geodds.Point;
import org.uqbar.geodds.Polygon;

@Entity
@DiscriminatorValue(value = "CGP")

public class CentroGestionParticipacion extends Poi {

	@OneToMany
	@JoinColumn(name = "poi_id")
	@org.mongodb.morphia.annotations.Transient //No se persiste en mongo
	private List<Servicio> servicios;
	
	@Transient
	@org.mongodb.morphia.annotations.Transient //No se persiste en mongo
	private Polygon comuna;
	
	public CentroGestionParticipacion(String nombre, String descripcion, Direccion direccion, double latitud,
			double longitud, Polygon comuna) {
		super(nombre, descripcion, direccion, latitud, longitud);
		this.servicios = new ArrayList<Servicio>();
		this.comuna = comuna;
	}
	
	public CentroGestionParticipacion() {
		
	}
	
		
	public Polygon getComuna() {
		return comuna;
	}

	public void setComuna(Polygon comuna) {
		this.comuna = comuna;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	@Override
	public boolean estaCercaDe(Point unPunto) {
		//return comuna.isInside(unPunto);
		return true; //FIXME al no persistir la comuna en la db la logica anterior rompe por null pointer exception
	}
	
	@Override
	public boolean coincideBusqueda(String criterioBusqueda) {
		return servicios.stream().anyMatch(servicio -> servicio.elNombreContiene(criterioBusqueda)) 
				|| super.coincideBusqueda(criterioBusqueda);
	}
	
	public void agregarServicio(Servicio nuevoServicio) {
		servicios.add(nuevoServicio);
	}


	@Override
	protected boolean atiendeEn(LocalDateTime momento, String valorBuscado) {
		return servicios.stream().anyMatch(servicio -> servicio.atiendeEn(momento) && servicio.elNombreContiene(valorBuscado));
	}
	

	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}

	@Override
	public String obtenerNombreTipo() {
		return "CGP";
	}

}
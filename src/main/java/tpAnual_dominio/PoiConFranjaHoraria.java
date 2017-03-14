package tpAnual_dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@MappedSuperclass
public abstract class PoiConFranjaHoraria extends Poi {
	
	@ManyToMany(fetch= FetchType.EAGER )
	@JoinTable(name="Poi_FranjaHoraria", joinColumns=@JoinColumn(name="poi_id"), inverseJoinColumns=@JoinColumn(name="franja_id")) 
	@org.mongodb.morphia.annotations.Transient //No se persiste en mongo
	protected List<FranjaDisponibilidadHoraria> horarioAtencion;

	public List<FranjaDisponibilidadHoraria> getHorarioAtencion() {
		return horarioAtencion;
	}
	public void setHorarioAtencion(List<FranjaDisponibilidadHoraria> horarioAtencion) {
		this.horarioAtencion = horarioAtencion;
	}
	public PoiConFranjaHoraria(){
		horarioAtencion = new ArrayList<FranjaDisponibilidadHoraria>();
	}
	public PoiConFranjaHoraria(String nombre, String descripcion, Direccion direccion, double latitud,
			double longitud) {
		super(nombre, descripcion, direccion, latitud, longitud);
		this.horarioAtencion = new ArrayList<FranjaDisponibilidadHoraria>();
	}

	@Override
	protected boolean atiendeEn(LocalDateTime momento, String valorBuscado) {
		return horarioAtencion.stream().anyMatch(franjaHoraria -> franjaHoraria.estaDisponible(momento));
	}

}

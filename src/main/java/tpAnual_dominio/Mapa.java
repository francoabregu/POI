package tpAnual_dominio;

import org.uqbar.geodds.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public abstract class Mapa {
	
	@Id
	@GeneratedValue
	private int mapa_id;
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	@JoinColumn(name="mapa_id")
	protected List<Poi> pois;
			
	public Mapa() {
		super();
		this.pois = new ArrayList<Poi>();
	}
	
	

	public int getMapa_id() {
		return mapa_id;
	}



	public void setMapa_id(int mapa_id) {
		this.mapa_id = mapa_id;
	}



	public List<Poi> getPois() {
		return pois;
	}



	public void setPois(List<Poi> pois) {
		this.pois = pois;
	}



	public void agregarPoi(Poi unPoi) {
		pois.add(unPoi);
	}
	
	public void eliminarPoi(Poi unPoi) {
		pois.remove(unPoi);
	}
	
	abstract public List<Poi> buscarPois(String clave);
	
	public List<Poi> buscarPoisPorNombre(String nombre){
		return pois.stream().filter(unPoi -> unPoi.nombre == nombre).collect(Collectors.toList());
	}
	
	public void eliminarPoiPorClave(String nombrePoi,Point posicion){
		Poi poi = (pois.stream().filter(unPoi -> unPoi.coincideDatosUnivocos(nombrePoi,posicion)).findFirst().get());
		eliminarPoi(poi);
	}
	
}
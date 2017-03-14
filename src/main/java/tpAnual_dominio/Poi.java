package tpAnual_dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;

import org.mongodb.morphia.annotations.Transient;
import org.uqbar.geodds.Point;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name = "tipo_poi")

public abstract class Poi {

	@Id
	@GeneratedValue
	private int poi_id;
	@Column
	protected String nombre;
	@Column
	protected String descripcion;
	@Embedded
	@Transient //No se persiste en mongo
	protected Direccion direccion;
	@Column
	@Convert(converter = PointConverter.class)
	@Transient //No se persiste en mongo
	protected Point posicion;
	@ElementCollection(targetClass= String.class)
	@CollectionTable(name="palabras_clave", 
	joinColumns= @JoinColumn(name="poi_id")) 
	@Column(name="palabra_clave")
	@Transient //No se persiste en mongo
	protected List<String> palabrasClave;

	public Poi(String nombre, String descripcion, Direccion direccion, double latitud, double longitud) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.direccion = direccion;
		this.posicion = new Point(latitud, longitud);
		this.palabrasClave = new ArrayList<String>();
	}
	
	public Poi(){
	
	}
	
	public int getPoi_id() {
		return poi_id;
	}

	public void setPoi_id(int poi_id) {
		this.poi_id = poi_id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Point getPosicion() {
		return posicion;
	}

	public void setPosicion(Point posicion) {
		this.posicion = posicion;
	}

	public List<String> getPalabrasClave() {
		return palabrasClave;
	}

	public boolean esValido() {
		return (nombre != null) && esLatitudValida(posicion.latitude()) && esLongitudValida(posicion.longitude());
	}
	private boolean esLatitudValida(double latitud) {
		return (latitud > -180) && (latitud < 180);
	}
	private boolean esLongitudValida(double longitud) {
		return (longitud > -90) && (longitud < 90);
	}

	
	public boolean seEncuentraAMenosDeXMetrosDe(Point otroPoi, double xMetros){
		return (posicion.distance(otroPoi) < xMetros);
	}

	public boolean estaCercaDe(Point unPunto) {
		return this.seEncuentraAMenosDeXMetrosDe(unPunto, 0.5);
	}
	

	public boolean estaDisponible(LocalDateTime momento, String valorBuscado){
		if(valorBuscado != null)
			return coincideBusqueda(valorBuscado) && atiendeEn(momento, valorBuscado);
		
		return atiendeEn(momento, valorBuscado);
	}
	

	protected abstract boolean atiendeEn(LocalDateTime momento, String valorBuscado);
	
	
	public  boolean coincideBusqueda(String criterioBusqueda){
		return this.palabrasClave.contains(criterioBusqueda);
	}
	
	public boolean coincideDatosUnivocos(String nombre,Point posicion){
		return (coincideBusqueda(nombre) && esLatitudValida(posicion.latitude()) && esLongitudValida(posicion.longitude()));
	}
	
	public void agregarPalabraClave(String palabra){
		palabrasClave.add(palabra);
	}
	
	public void eliminarPalabraClave(String palabra){
		palabrasClave.remove(palabra);
	}

	public void setPalabrasClave(List<String> palabrasClave) {
		this.palabrasClave = palabrasClave;
	}

	public abstract String obtenerNombreTipo();
	
}
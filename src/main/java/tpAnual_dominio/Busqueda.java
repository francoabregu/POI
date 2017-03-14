package tpAnual_dominio;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;*/


//@Entity
//@Table(name = "Busqueda")
@Entity("busquedas")
@Indexes(
	    @Index(value = "terminal", fields = @Field("terminal"))
	    )
public class Busqueda {
	
	//@Id
	//@GeneratedValue
	@Id
	private ObjectId busqueda_id;
	//@Column
	LocalDate fecha;
	//@Column
	private int cantidadResultadosDevueltos;
	//@Column
	private int tiempoDemorado;
	//@Column
	private String criterioDeBusqueda;
	//@ManyToMany
	@Embedded
	private Set<Poi> pois;
	


	private String nombreTerminal;
	
	public Busqueda(){
		
	}
	
	public Busqueda(LocalDate fecha, int cantidadResultadosDevueltos, int tiempoDemorado,
			String criterioDeBusqueda, Set<Poi> poisDevueltos, String terminal) {
		super();
		this.fecha = fecha;
		this.cantidadResultadosDevueltos = cantidadResultadosDevueltos;
		this.tiempoDemorado = tiempoDemorado;
		this.criterioDeBusqueda = criterioDeBusqueda;
		this.pois = poisDevueltos;
		this.nombreTerminal = terminal;
		this.busqueda_id = new ObjectId();
	}

		public Set<Poi> getPois() {
		return pois;
	}

	public void setPois(Set<Poi> pois) {
		this.pois = pois;
	}
	
	public ObjectId getBusqueda_id() {
		return busqueda_id;
	}

	public void setBusqueda_id(ObjectId busqueda_id) {
		this.busqueda_id = busqueda_id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getCantidadResultadosDevueltos() {
		return cantidadResultadosDevueltos;
	}

	public void setCantidadResultadosDevueltos(int cantidadResultadosDevueltos) {
		this.cantidadResultadosDevueltos = cantidadResultadosDevueltos;
	}

	public long getTiempoDemorado() {
		return tiempoDemorado;
	}

	public void setTiempoDemorado(int tiempoDemorado) {
		this.tiempoDemorado = tiempoDemorado;
	}

	public String getCriterioDeBusqueda() {
		return criterioDeBusqueda;
	}

	public void setCriterioDeBusqueda(String criterioDeBusqueda) {
		this.criterioDeBusqueda = criterioDeBusqueda;
	}

	public void registrarseEnRegistroPorFecha(Map<LocalDate, Long> registro) {
		if(registro.containsKey(fecha)) {
			registro.put(fecha, registro.get(fecha) + 1);
		}
		else {
			registro.put(fecha, (long) 1);
		}
	}

	public long cantidadDeRespuestas() {
		return this.cantidadResultadosDevueltos;
	}
	
	public void setNombreTerminal(String terminal){
		this.nombreTerminal = terminal;
	}
	
	public String getNombreTerminal(){
		return this.nombreTerminal;
	}
	
}
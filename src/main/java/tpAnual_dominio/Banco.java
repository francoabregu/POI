package tpAnual_dominio;



import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;



@Entity
@DiscriminatorValue(value = "BANCO")

public class Banco extends Poi{
	
	@Transient
	@org.mongodb.morphia.annotations.Transient //No se persiste en mongo
	double viernes = 6, horaInicio = 10, horaFin = 15;
	@Transient
	@org.mongodb.morphia.annotations.Transient //No se persiste en mongo
	private List<FranjaDisponibilidadHoraria> horarioAtencion = new ArrayList<FranjaDisponibilidadHoraria>();
	
	
	
	public List<FranjaDisponibilidadHoraria> getHorarioAtencion() {
		return horarioAtencion;
	}

	public Banco(String nombre, String descripcion, Direccion direccion, double latitud, double longitud) {
		super(nombre, descripcion, direccion, latitud, longitud);
		this.setHorario();
	}

	public Banco(){
		super();
		setHorario();
	}
	
	
	private void setHorario() {
		LocalTime inicio = LocalTime.of(10, 0);
		LocalTime fin = LocalTime.of(15, 0);
		for(int i = 1; i < 6 ; i++){ 
			this.horarioAtencion.add(new FranjaDisponibilidadHoraria(inicio, fin, DayOfWeek.of(i)));
		}
	}

	@Override
	public boolean coincideBusqueda(String criterioBusqueda) {		
		return this.nombre.toLowerCase().contains(criterioBusqueda) || this.descripcion.toLowerCase().contains(criterioBusqueda)
				|| super.coincideBusqueda(criterioBusqueda);
	}

	@Override
	public boolean atiendeEn(LocalDateTime momento, String valorBuscado) {
		return horarioAtencion.stream().anyMatch(franjaHoraria -> franjaHoraria.estaDisponible(momento));
	}

	@Override
	public String obtenerNombreTipo() {
		return "Banco";
	}
}
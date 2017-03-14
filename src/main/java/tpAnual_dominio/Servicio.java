package tpAnual_dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Servicio {
	
	@Id
	@GeneratedValue
	private int servicio_id;
	@Column
	private String nombreServicio;
	@ManyToMany(fetch= FetchType.EAGER)
	private List<FranjaDisponibilidadHoraria> listaFranjaDisponibilidadHoraria;

		
	public Servicio(String nombreServicio) {
		super();
		this.nombreServicio = nombreServicio;
		this.listaFranjaDisponibilidadHoraria = new ArrayList<FranjaDisponibilidadHoraria>();
	}
	
	public Servicio(){
		
	}
	
	public boolean estaDisponible(LocalDateTime unMomento, String valorBusqueda) {
		return nombreServicio.equals(valorBusqueda) && atiendeEn(unMomento);		
	}
	

	public boolean atiendeEn(LocalDateTime unMomento) {
		return listaFranjaDisponibilidadHoraria.stream().anyMatch(franjaHoraria -> 
					franjaHoraria.estaDisponible(unMomento));
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public List<FranjaDisponibilidadHoraria> getListaFranjaDisponibilidadHoraria() {
		return listaFranjaDisponibilidadHoraria;
	}

	public void setListaFranjaDisponibilidadHoraria(List<FranjaDisponibilidadHoraria> listaFranjaDisponibilidadHoraria) {
		this.listaFranjaDisponibilidadHoraria = listaFranjaDisponibilidadHoraria;
	}
	
	public void agregar(FranjaDisponibilidadHoraria franjaHoraria) {
		listaFranjaDisponibilidadHoraria.add(franjaHoraria);
	}
	
	public void borrar(FranjaDisponibilidadHoraria franjaHoraria) {
		listaFranjaDisponibilidadHoraria.removeIf(franja -> franja.equals(franjaHoraria));
	}
	

	public boolean elNombreContiene(String unNombre){
		if (unNombre == null)
			return true;
		return nombreServicio.toLowerCase().contains(unNombre);
	}
	
}
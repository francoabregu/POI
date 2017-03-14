package tpAnual_dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Rubro")

public class Rubro {
	
	@Id
	@GeneratedValue
	private int rubro_id;
	@Column
	private String nombre;
	@Column
	private double radioCercania;

	public Rubro(String nombre, double radioCercania) {
		this.nombre = nombre;
		this.radioCercania = radioCercania;
	}
	
	public Rubro(){
		
	}
	
	
	
	public int getRubro_id() {
		return rubro_id;
	}

	public void setRubro_id(int rubro_id) {
		this.rubro_id = rubro_id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setRadioCercania(double radioCercania) {
		this.radioCercania = radioCercania;
	}

	public double getRadioCercania() {
		return radioCercania;
	}
	
	public boolean contiene(String nombre) {
		if(nombre == null)
			return true;
		return this.nombre.toLowerCase().contains(nombre);
	}
}
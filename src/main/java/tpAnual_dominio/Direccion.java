package tpAnual_dominio;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Direccion { // sin comportamiento

	@Column
	private String callePrincipal;
	@Column
	private String entrecalle1;
	@Column
	private String entrecalle2;
	@Column
	private int numero;
	@Column
	private Integer piso;
	@Column
	private String departamento;
	@Column
	private String unidad;
	@Column
	private Integer codigoPostal;
	@Column
	private String localidad;
	@Column
	private String barrio;
	@Column
	private String provincia;
	@Column
	private String pais;
	
	//lo necesita hibernate
	public Direccion(){
		
	}
	
	public String getCallePrincipal() {
		return callePrincipal;
	}

	public void setCallePrincipal(String callePrincipal) {
		this.callePrincipal = callePrincipal;
	}

	public String getEntrecalle1() {
		return entrecalle1;
	}

	public void setEntrecalle1(String entrecalle1) {
		this.entrecalle1 = entrecalle1;
	}

	public String getEntrecalle2() {
		return entrecalle2;
	}

	public void setEntrecalle2(String entrecalle2) {
		this.entrecalle2 = entrecalle2;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Integer getPiso() {
		return piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}


	public Direccion(String callePrincipal, String entrecalle1, String entrecalle2, Integer numero, Integer piso,
			String departamento, String unidad, Integer codigoPostal, String localidad, String barrio, 
			String provincia, String pais) {
		super();
		this.callePrincipal = callePrincipal;
		this.entrecalle1 = entrecalle1;
		this.entrecalle2 = entrecalle2;
		this.numero = numero;
		this.piso = piso;
		this.departamento = departamento;
		this.unidad = unidad;
		this.codigoPostal = codigoPostal;
		this.localidad = localidad;
		this.barrio = barrio;
		this.provincia = provincia;
		this.pais = pais;
	}

}
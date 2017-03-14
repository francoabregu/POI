package tpAnual_dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.uqbar.geodds.Point;
import org.uqbar.geodds.Polygon;

import componentesExternos.AdaptadorSistemaExterno;
import dbConectors.MongoConector;

@Entity
@Table(name = "Terminal")

public class Terminal {
	
	@Id
	@GeneratedValue
	private int id;
	@Column
	private String nombre;
	@Column
	private long tiempoMaximoBusqueda;
	//@OneToMany
	//@JoinColumn(name  = "usuario_id")
	@Transient
	private List<Busqueda> busquedas;
	@OneToOne
	private EnviadorDeMails enviadorDeMails;
	@OneToOne
	private Mapa mapa;
	@Column
	private int comuna;
	@Transient 
	private List<AdaptadorSistemaExterno> adaptadoresSistemasExternos;
	@ElementCollection(targetClass=Accion.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name="Accion")
	@Column(name="accion")
	private Set<Accion> accionesHabilitadas;
	@Column
	@Convert(converter = PointConverter.class)
	private Point posicion;
							
		public int getComuna() {
		return comuna;
	}



	public void setComuna(int comuna) {
		this.comuna = comuna;
	}		
	
	public int getId() {
		return id;
	}



	public void setId(int usuario_id) {
		this.id = usuario_id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public long getTiempoMaximoBusqueda() {
		return tiempoMaximoBusqueda;
	}



	public void setTiempoMaximoBusqueda(long tiempoMaximoBusqueda) {
		this.tiempoMaximoBusqueda = tiempoMaximoBusqueda;
	}



	public List<Busqueda> getBusquedas() {
		return busquedas;
	}



	public void setBusquedas(List<Busqueda> busquedas) {
		this.busquedas = busquedas;
	}



	public EnviadorDeMails getEnviadorDeMails() {
		return enviadorDeMails;
	}



	public void setEnviadorDeMails(EnviadorDeMails enviadorDeMails) {
		this.enviadorDeMails = enviadorDeMails;
	}



	public Mapa getMapa() {
		return mapa;
	}



	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}



	public List<AdaptadorSistemaExterno> getAdaptadoresSistemasExternos() {
		return adaptadoresSistemasExternos;
	}



	public void setAdaptadoresSistemasExternos(List<AdaptadorSistemaExterno> adaptadoresSistemasExternos) {
		this.adaptadoresSistemasExternos = adaptadoresSistemasExternos;
	}



	public Set<Accion> getAccionesHabilitadas() {
		return accionesHabilitadas;
	}



	public void setAccionesHabilitadas(Set<Accion> accionesHabilitadas) {
		this.accionesHabilitadas = accionesHabilitadas;
	}



	public Point getPosicion() {
		return posicion;
	}



	public void setPosicion(Point posicion) {
		this.posicion = posicion;
	}



	public Terminal(){
		adaptadoresSistemasExternos = new ArrayList<AdaptadorSistemaExterno>();
		busquedas = new ArrayList<Busqueda>();
	}
	
	

	public Terminal(String nombre, Mapa mapa, EnviadorDeMails enviadorDeMails, long tiempoMaximoBusqueda,
					double latitud, double longitud) {
		this.nombre = nombre;
		this.mapa = mapa;
		this.enviadorDeMails = enviadorDeMails;
		this.tiempoMaximoBusqueda = tiempoMaximoBusqueda;
		busquedas = new ArrayList<Busqueda>();
		adaptadoresSistemasExternos = new ArrayList<AdaptadorSistemaExterno>();
		this.accionesHabilitadas = new HashSet<Accion>();
		posicion = new Point(latitud, longitud);
	}
	
	public void habilitarAccion(Accion accion){
		accionesHabilitadas.add(accion);
	}
	
	public void deshabilitarAccion(Accion accion){
		accionesHabilitadas.remove(accion);
	}
	
	public Set<Poi> realizarBusqueda(String criterioDeBusqueda){
		Cronometro cronometro = new Cronometro();
		cronometro.iniciarCronometro();
		
		Set<Poi> pois = obtenerPois(criterioDeBusqueda);
		
		int tiempoBusqueda = cronometro.finalizarCronometro();
		
		if(accionesHabilitadas.contains(Accion.notificar_por_mail))
		{
			if(tiempoBusqueda > tiempoMaximoBusqueda)
				enviadorDeMails.informarBusquedaLenta(nombre, criterioDeBusqueda, 
													tiempoMaximoBusqueda, tiempoBusqueda);
		}
		
		if(accionesHabilitadas.contains(Accion.almacenar_busquedas_realizadas))
			registrarBusqueda(pois.size(), tiempoBusqueda, criterioDeBusqueda, pois);
		
		return pois;
	}
	
	
	private void registrarBusqueda(int size, int tiempoBusqueda,String criterioDeBusqueda, Set<Poi> poisObtenidos) {	
		Busqueda nuevaBusqueda = new Busqueda(LocalDate.now(), size, tiempoBusqueda, 
												criterioDeBusqueda, poisObtenidos, nombre);
		busquedas.add(nuevaBusqueda);
		new MongoConector().persistirBusqueda(nuevaBusqueda);
	}

	private Set<Poi> obtenerPois(String criterioDeBusqueda) {
		Set<Poi> resultado = new HashSet<Poi>();
		resultado.addAll(mapa.buscarPois(criterioDeBusqueda));
		for (AdaptadorSistemaExterno adaptador: adaptadoresSistemasExternos) {
			resultado.addAll(adaptador.obtenerBancos(criterioDeBusqueda, ""));
			resultado.addAll(adaptador.obtenerBancos("", criterioDeBusqueda));
			resultado.addAll(adaptador.obtenerCGPs(criterioDeBusqueda));
		}
		return resultado.stream().filter(poi -> poi.estaCercaDe(getPosicion())).collect(Collectors.toSet());
	}

	private Map<LocalDate, Long> obtenerBusquedasPorFechas() {
		Map<LocalDate, Long> registro =  new HashMap<LocalDate, Long>();
		busquedas.stream().forEach(busqueda -> busqueda.registrarseEnRegistroPorFecha(registro));
		return registro;
	}
	
	private List<Long> obtenerCantidadResultadosPorConsultas() {
		List<Long> registro =  new ArrayList<Long>();
		busquedas.stream()
		.forEach(busqueda -> registro.add(busqueda.cantidadDeRespuestas()));
		return registro;
	}
	
	public void agregarSistemaExterno (AdaptadorSistemaExterno unAdapter) {
		adaptadoresSistemasExternos.add(unAdapter);
	}
	
	public void eliminarSistemaExterno (AdaptadorSistemaExterno unAdapter) {
		adaptadoresSistemasExternos.remove(unAdapter);
	}

	public void registrarCantidadResultados(Map<String, Long> registro) {
		if(accionesHabilitadas.contains(Accion.reporte_resultados_por_busqueda_y_terminal_total))
		registro.put(this.nombre, busquedas.stream()
									.mapToLong(busqueda -> busqueda.cantidadDeRespuestas()).sum());
		return;
	}

	public void registrarCantidadBusquedasPorFecha(Map<LocalDate, Long> registro) {
		if(accionesHabilitadas.contains(Accion.reporte_cantidad_de_busquedas_por_fecha))
			obtenerBusquedasPorFechas().forEach((key, value) -> registro.merge(key, value, Long :: sum));
		
		return;
	}

	public void registrarResultadosParciales(Map<String, List<Long>> registro) {
		if(accionesHabilitadas.contains(Accion.reporte_resultados_por_busqueda_y_terminal_parcial))
			registro.put(this.nombre, obtenerCantidadResultadosPorConsultas());
		return;
	}

	public boolean estaDentroDe(Polygon comuna) {
		return comuna.isInside(posicion);
	}
	
	public int calcularDistanciaAPoi(Poi poi){
		return Double.valueOf(poi.getPosicion().distance(this.getPosicion())*1000).intValue();
	}
		
}
package dbConectors;

import java.time.LocalDate;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;

import componentesExternos.CentroDTO;
import tpAnual_dominio.Busqueda;
import tpAnual_dominio.Terminal;
import tpAnual_dominio.Banco;

public class MongoConector {
	
	final Morphia morphia = new Morphia();
	// create the Datastore connecting to the default port on the local host
	final Datastore datastore = morphia.createDatastore(new MongoClient(), "SistemaPois");
	
	
	public MongoConector() {
		super();
		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("tpAnual_dominio.Busqueda");
		morphia.mapPackage("tpAnual_dominio.Poi");
		morphia.getMapper().getConverters().addConverter(new LocalDateConverter());
		datastore.ensureIndexes();	
	}
	
	public void persistirBusqueda(Busqueda busqueda){
		datastore.save(busqueda);
	}
	
	
	public Datastore getDatastore() {
		return datastore;
	}

	public List<Busqueda> busquedasPorTerminal (Terminal terminal) {
		String nombre = terminal.getNombre();
		Query<Busqueda> query = datastore.createQuery(Busqueda.class);
		return query.filter("nombreTerminal",nombre).asList();
	}
	
	public Busqueda obtenerBusqueda(String id){
		Query<Busqueda> query = datastore.createQuery(Busqueda.class);
		return query.filter("_id", new ObjectId(id)).get();
	}
	
	public List<Busqueda> filtrarBusquedas(String nombreTerminal, Integer cantidadResultadosMin, Integer CantidadResultadosMax,
											LocalDate fechaInicio, LocalDate fechaFin){
		Query<Busqueda> query = datastore.createQuery(Busqueda.class).field("nombreTerminal").contains(nombreTerminal);
		
		if(cantidadResultadosMin != null)
			query = query.field("cantidadResultadosDevueltos").greaterThanOrEq(cantidadResultadosMin);
		
		if(CantidadResultadosMax != null)
			query = query.field("cantidadResultadosDevueltos").lessThanOrEq(CantidadResultadosMax);
		
		if(fechaInicio != null)
			query = query.field("fecha").greaterThanOrEq(fechaInicio);
		
		if(fechaFin != null)
			query = query.field("fecha").lessThanOrEq(fechaFin);
														
		return query.asList();
	}
	
	public void persistirCentroDTO(CentroDTO centro){
		datastore.save(centro);
	}
	
	public List<CentroDTO> buscarCentroDTO (String ubicacion) {
		Query<CentroDTO> query = datastore.createQuery(CentroDTO.class);	
		List<CentroDTO> centros_en_esa_calle = query.field("domicilio").contains(ubicacion).asList();
		List<CentroDTO> centros_contenedores_de_esa_zona = query.field("zonasIncluidas").contains(ubicacion).asList();
		centros_en_esa_calle.addAll(centros_contenedores_de_esa_zona);
		return centros_en_esa_calle;
		
	}
	
	public List<Banco> buscarBanco (String nombreBanco) {
		Query<Banco> query = datastore.createQuery(Banco.class);	
		List<Banco> bancos_con_ese_nombre = query.field("domicilio").contains(nombreBanco).asList();
		List<Banco> bancos_contenedores_de_esa_zona = query.field("nombresIncluidos").contains(nombreBanco).asList();
		bancos_con_ese_nombre.addAll(bancos_contenedores_de_esa_zona);
		return bancos_con_ese_nombre;
		
	}
	
	public void persistirBanco(Banco banco){
		datastore.save(banco);
	}

}

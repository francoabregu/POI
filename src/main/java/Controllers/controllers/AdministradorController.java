package Controllers.controllers;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dbConectors.MongoConector;
import dbConectors.RepositorioPois;
import dbConectors.RepositorioTerminales;
import dbConectors.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tpAnual_dominio.Accion;
import tpAnual_dominio.Busqueda;
import tpAnual_dominio.Poi;
import tpAnual_dominio.Terminal;
import tpAnual_dominio.Usuario;

public class AdministradorController {
	
	public static ModelAndView getPerfilAdministrador(Request req, Response res){
		return new ModelAndView(null, "html/funcionalidadesAdmin.hbs");
	}
	
	public static ModelAndView vistaPoisAdministrador(Request req, Response res){
		return new ModelAndView(null, "html/busquedaPoisAdmin.hbs");
	}
	
	public static ModelAndView vistaBusquedasAdministrador(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String,Object>();
		viewModel.put("terminales", new RepositorioTerminales().obtenerTerminalesDb());
		return new ModelAndView(viewModel, "html/busquedasConsultas.hbs");
	}
	
		public static ModelAndView vistaModificarPoi(Request req, Response res){
		Map<String,Object> viewModel = new HashMap<String, Object>();
		int id = Integer.parseInt(req.params(":id"));
		Poi poi = new RepositorioPois().obtenerPoi(id);
		
		viewModel.put("poi", poi);
		
		return new ModelAndView(viewModel, "html/edicionPoi.hbs");
	}
		
	public static ModelAndView buscarPois(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String, Object>();
		List<Poi> listaPois;
		List<Map<String,Object>> pois= new ArrayList<Map<String, Object>>();
		String filtroNombre = null, filtroTipo;
		String selectorFiltro = req.queryParams("selector");
		RepositorioPois repositorio = new RepositorioPois();
		int cantidadResultados = Integer.parseInt(req.queryParams("cantidadResultados"));
		
		if(req.queryParams("botonFiltrar") != null)
			cantidadResultados = 5;
		
		if(selectorFiltro.equals("nombre")){
			filtroNombre = req.queryParams("filtroNombre");
			listaPois = repositorio.obtenerPoisConNombre(filtroNombre);
			viewModel.put("nombreSeleccionado", true);
		}else{
			filtroTipo = req.queryParams("tipos");
			listaPois = repositorio.obtenerPoisDelTipo(filtroTipo);
			viewModel.put("tipoSeleccionado", true);
			viewModel.put("bancoSeleccionado", filtroTipo.equals("BANCO"));
			viewModel.put("cgpSeleccionado", filtroTipo.equals("CGP"));
			viewModel.put("localSeleccionado", filtroTipo.equals("LOCALCOMERCIAL"));
			viewModel.put("paradaSeleccionado", filtroTipo.equals("PARADACOLECTIVO"));
		}
		
		
		listaPois.forEach(poi -> {
				Map<String,Object> datosPoi = new HashMap<String, Object>();
				datosPoi.put("poi", poi);
				datosPoi.put("tipoPoi", poi.obtenerNombreTipo());
				pois.add(datosPoi);});
		
		
		 Collections.sort(pois, new Comparator<Map<String, Object>>() {
									@Override
									public int compare(Map<String, Object> poi1, Map<String, Object> poi2)
									{ return  ((Integer)((Poi) poi1.get("poi")).getPoi_id()).compareTo((Integer)((Poi) poi2.get("poi")).getPoi_id());}
									});
		
		viewModel.put("pois", pois.subList(0, Integer.min(cantidadResultados,pois.size())));
		viewModel.put("selectorFiltro", selectorFiltro);
		viewModel.put("nombre", filtroNombre);
		viewModel.put("cantidadResultados", cantidadResultados+5);
		viewModel.put("masResultados", pois.size() > cantidadResultados);
		viewModel.put("mostrarBotonFiltrar", true);
		
		return new ModelAndView(viewModel, "html/busquedaPoisAdmin.hbs");
	}
	
	public static ModelAndView filtrarBusquedas(Request req, Response res){
		Map<String,Object> viewModel = new HashMap<String, Object>();
		String nombreTerminal = req.queryParams("nombreTerminal");
		LocalDate fechaInicio = obtenerLocalDateParam(req, "fechaInicio");
		LocalDate fechaFin = obtenerLocalDateParam(req, "fechaFin");
		Integer cantidadMinimaBusquedas = obtenerIntegerParam(req, "min");
		Integer cantidadMaximaBusquedas = obtenerIntegerParam(req, "max");
		
		List<Busqueda> busquedasFiltradas = new MongoConector().filtrarBusquedas(nombreTerminal, cantidadMinimaBusquedas, cantidadMaximaBusquedas, fechaInicio, fechaFin);
		
		
		viewModel.put("terminales", new RepositorioTerminales().obtenerTerminalesDb());
		viewModel.put("fechaInicio", fechaInicio);
		viewModel.put("fechaFin", fechaFin);
		viewModel.put("min", cantidadMinimaBusquedas);
		viewModel.put("max", cantidadMaximaBusquedas);
		viewModel.put("busquedas", busquedasFiltradas);
		
		return new ModelAndView(viewModel, "html/busquedasConsultas.hbs");
		
	}
	
	public static ModelAndView vistaTerminalesAdministrador(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String, Object>();
		
		List<Terminal> terminales = new RepositorioTerminales().obtenerTerminalesDb();
		
		 Collections.sort(terminales, new Comparator<Terminal>() {
				@Override
				public int compare(Terminal terminal1, Terminal terminal2)
				{ return  ((Integer)(terminal1.getId())).compareTo((Integer)(terminal2.getId()));}
				});
		
		viewModel.put("terminales", terminales);
		 
		return new ModelAndView(viewModel, "html/busquedaTerminales.hbs");
	}
	
	public static ModelAndView vistaConfigurarAcciones(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String, Object>();
		int id = Integer.parseInt(req.params(":id"));
		
		Terminal terminal = new RepositorioTerminales().obtenerTerminal(id);
		
		List<String> stringsAccionesHabilitadas = mapearAccionesAStrings(terminal.getAccionesHabilitadas());
		List<String> stringsAccionesDeshabilitadas = mapearAccionesAStrings(Arrays.asList(Accion.values()));
		stringsAccionesDeshabilitadas.removeAll(stringsAccionesHabilitadas);
		
		
		req.session().attribute("terminal", terminal);
		
		viewModel.put("accionesHabilitadas", stringsAccionesHabilitadas);
		
		viewModel.put("terminal", terminal);
		if(stringsAccionesDeshabilitadas.isEmpty())
			viewModel.put("accionesDeshabilitadas", null);
		else
			viewModel.put("accionesDeshabilitadas", stringsAccionesDeshabilitadas);
		
		return new ModelAndView(viewModel, "html/configurarAccionesTerminal.hbs");
	}
	
	public static ModelAndView filtrarTerminales(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String, Object>();
		Integer comuna =obtenerIntegerParam(req, "comuna");
		
		
		List<Terminal> terminales = new RepositorioTerminales().obtenerTerminalesDb();
		
		if(comuna != null)
			terminales = terminales.stream().filter(terminal -> terminal.getComuna() == comuna).collect(Collectors.toList());
		
		 Collections.sort(terminales, new Comparator<Terminal>() {
				@Override
				public int compare(Terminal terminal1, Terminal terminal2)
				{ return  ((Integer)(terminal1.getId())).compareTo((Integer)(terminal2.getId()));}
				});
		
		viewModel.put("terminales", terminales);
		 
		return new ModelAndView(viewModel, "html/busquedaTerminales.hbs");
	}
	
	public static ModelAndView vistaCrearTerminal(Request req, Response res){
		return new ModelAndView(null, "html/crearTerminal.hbs");
	}
	
	public static ModelAndView vistaModificarTerminal(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String, Object>();
		int id = Integer.parseInt(req.params(":id"));
		
		Terminal terminal = new RepositorioTerminales().obtenerTerminal(id);
		Usuario user = new RepositorioUsuarios().obtenerUsuarioConTerminal(terminal);
		
		viewModel.put("terminal", terminal);
		viewModel.put("usuario",user);
		viewModel.put("latitud", terminal.getPosicion().latitude());
		viewModel.put("longitud",terminal.getPosicion().longitude());
		
		return new ModelAndView(viewModel, "html/modificarTerminal.hbs");
	}
	

	private static Integer obtenerIntegerParam(Request req, String nombreCampo){
		String valor = req.queryParams(nombreCampo);
		
		if(valor.equals(""))
			return null;
		else
			return Integer.parseInt(valor);
				
	}
	
	private static LocalDate obtenerLocalDateParam(Request req, String nombreCampo){
		String valor = req.queryParams(nombreCampo);
		
		if(valor.equals(""))
			return null;
		else
			return LocalDate.parse(valor);
				
	}
	
	
	private static List<String> mapearAccionesAStrings(Collection<Accion> acciones){
		return  acciones.stream().map(accion -> accion.toString()).collect(Collectors.toList());
	}

}


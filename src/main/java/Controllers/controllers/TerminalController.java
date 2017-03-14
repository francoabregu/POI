package Controllers.controllers;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.uqbar.geodds.Point;

import dbConectors.RepositorioTerminales;
import dbConectors.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tpAnual_dominio.Accion;
import tpAnual_dominio.Banco;
import tpAnual_dominio.CentroGestionParticipacion;
import tpAnual_dominio.EnviadorDeMails;
import tpAnual_dominio.LocalComercial;
import tpAnual_dominio.Mapa;
import tpAnual_dominio.ParadaDeColectivo;
import tpAnual_dominio.Poi;
import tpAnual_dominio.Terminal;
import tpAnual_dominio.Usuario;


public class TerminalController {
		
	public static ModelAndView getPerfilTerminal(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String, Object>();
		int id = Integer.parseInt(req.params(":id"));
			
		Terminal terminal = obtenerTerminal(id);
		
		req.session().attribute("terminal", terminal);
		
		viewModel.put("terminal", terminal);
		
		return new ModelAndView(viewModel, "html/busquedaPoisUsuario.hbs");
	}
	
	public static ModelAndView buscarPois(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String, Object>();
		int cantidadResultados = Integer.parseInt(req.queryParams("cantidadResultados"));
		String criterioBusqueda = req.queryParams("criterioBusqueda");
		String[] checkbox = req.queryParamsValues("checkbox");
		List <String> listaCheckbox;
		
		if(checkbox != null)
			listaCheckbox = Arrays.asList(checkbox);
		else
			listaCheckbox = new ArrayList<String>();
			
		boolean cgp = listaCheckbox.contains("CGP");
		boolean banco = listaCheckbox.contains("banco");
		boolean paradaColectivo = listaCheckbox.contains("paradaColectivo");
		boolean local = listaCheckbox.contains("localComercial");
		
		if(req.queryParams("botonBuscar") != null)
			cantidadResultados = 5;
		
		Terminal terminal = req.session().attribute("terminal");
		
		Set<Poi> pois = terminal.realizarBusqueda(criterioBusqueda.toLowerCase());
		Set<Poi> poisFiltrados = new HashSet<Poi>();
		
		Set<Poi> bancos;
		Set<Poi> cgps;
		Set<Poi> locales;
		Set<Poi> paradas;
		
		if(cgp || banco || paradaColectivo || local){
			bancos = filtrarBancos(pois, banco);
			paradas = filtrarParadas(pois, paradaColectivo);
			cgps = filtrarCgps(pois, cgp);
			locales = filtrarLocales(pois, local);	
			
			poisFiltrados.addAll(bancos);
			poisFiltrados.addAll(locales);
			poisFiltrados.addAll(cgps);
			poisFiltrados.addAll(paradas);
		}else
			poisFiltrados = pois;
		
		List<Map<String, Object>> datosPois = new ArrayList<Map<String,Object>>();
		
		
		poisFiltrados.forEach(poi -> {
				Map<String,Object> datosPoi = new HashMap<String, Object>();
				datosPoi.put("poi", poi);
				datosPoi.put("distancia", terminal.calcularDistanciaAPoi(poi));
				datosPoi.put("tipoPoi", poi.obtenerNombreTipo());
				datosPois.add(datosPoi);});
		
		
		 Collections.sort(datosPois, new Comparator<Map<String, Object>>() {
									@Override
									public int compare(Map<String, Object> poi1, Map<String, Object> poi2)
									{ return  ((Integer) poi1.get("distancia")).compareTo((Integer)poi2.get("distancia"));}
									});
		
		viewModel.put("pois", datosPois.subList(0, Integer.min(cantidadResultados,datosPois.size())));
		viewModel.put("terminal", terminal);
		viewModel.put("masResultados", datosPois.size() > cantidadResultados);
		viewModel.put("cantidadResultados", cantidadResultados+5);
		viewModel.put("criterioBusqueda", criterioBusqueda);
		viewModel.put("bancoCheckbox", banco);
		viewModel.put("cgpCheckbox", cgp);
		viewModel.put("localCheckbox", local);
		viewModel.put("paradaCheckbox", paradaColectivo);
		
		return new ModelAndView(viewModel, "html/busquedaPoisUsuario.hbs");
	}
	
	public static ModelAndView borrarTerminal(Request req, Response res){
		int id = Integer.parseInt(req.params(":id"));
		
		Terminal terminal = obtenerTerminal(id);
		
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		
		repoUsuarios.delete(repoUsuarios.obtenerUsuarioConTerminal(terminal));
		
		new RepositorioTerminales().delete(terminal);
		
		res.redirect("/PerfilAdministrador/AdministrarTerminales");
		
		return null;
	}
	
	public static ModelAndView crearTerminal(Request req, Response res){
		String nombre = req.queryParams("nombre");
		int tiempoBusqueda = Integer.parseInt(req.queryParams("tiempoBusqueda"));
		int comuna = Integer.parseInt(req.queryParams("comuna"));
		Double latitud = Double.parseDouble(req.queryParams("latitud"));
		Double longitud = Double.parseDouble(req.queryParams("longitud"));
		String username = req.queryParams("username");
		String password = req.queryParams("contrasenia");
		
		Terminal nuevaTerminal = new Terminal(nombre, obtenerMapa(), obtenerEnviadorMails(), tiempoBusqueda, latitud, longitud);
		nuevaTerminal.setComuna(comuna);
		
		new RepositorioTerminales().persist(nuevaTerminal);
		
		Usuario nuevoUsuario = new Usuario(username, password, nuevaTerminal);
		
		new RepositorioUsuarios().persist(nuevoUsuario);
		
		res.redirect("/PerfilAdministrador/AdministrarTerminales");
		
		return null;
	}
	
	public static ModelAndView modificarTerminal(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String, Object>();
		int id = Integer.parseInt(req.params(":id"));
		String nombre = req.queryParams("nombre");
		int tiempoBusqueda = Integer.parseInt(req.queryParams("tiempoBusqueda"));
		int comuna = Integer.parseInt(req.queryParams("comuna"));
		Double latitud = Double.parseDouble(req.queryParams("latitud"));
		Double longitud = Double.parseDouble(req.queryParams("longitud"));
		String username = req.queryParams("username");
		String password = req.queryParams("contrasenia");
		
		Terminal terminal = new RepositorioTerminales().obtenerTerminal(id);
		Usuario user = new RepositorioUsuarios().obtenerUsuarioConTerminal(terminal);
		
		terminal.setNombre(nombre);
		terminal.setTiempoMaximoBusqueda(tiempoBusqueda);
		terminal.setComuna(comuna);
		terminal.setPosicion(new Point(latitud, longitud));
		
		user.setNombreUsuario(username);
		user.setContrasenia(password);
		user.setTerminal(terminal);
		
		new RepositorioTerminales().update(terminal);
		new RepositorioUsuarios().update(user);
		
		viewModel.put("terminal", terminal);
		viewModel.put("usuario",user);
		viewModel.put("latitud", terminal.getPosicion().latitude());
		viewModel.put("longitud",terminal.getPosicion().longitude());
		
		res.redirect("/PerfilAdministrador/AdministrarTerminales");
		
		return null;
	}
	
	public static ModelAndView habilitarAccion(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String,Object>();
		String accion = req.queryParams("accion");
		
		Terminal terminal = req.session().attribute("terminal");
		terminal.habilitarAccion(Accion.valueOf(accion));
		
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
	
	public static ModelAndView deshabilitarAccion(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String,Object>();
		String accion = req.queryParams("accion");
		
		Terminal terminal = req.session().attribute("terminal");
		terminal.deshabilitarAccion(Accion.valueOf(accion));
		
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
	
	public static ModelAndView guardarConfiguracionAcciones(Request req, Response res){
		Terminal terminal = req.session().attribute("terminal");
		
		new RepositorioTerminales().update(terminal);

		res.redirect("/PerfilAdministrador/AdministrarTerminales");
		
		return null;
	}
	
	public static ModelAndView verTerminal(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String,Object>();
		int id = Integer.parseInt(req.params(":id"));
		
		Terminal terminal = obtenerTerminal(id);
		Usuario user = new RepositorioUsuarios().obtenerUsuarioConTerminal(terminal);
		
		List<String> accionesHabilitadas = mapearAccionesAStrings(terminal.getAccionesHabilitadas());
		
		viewModel.put("accionesHabilitadas", accionesHabilitadas);
		viewModel.put("terminal", terminal);
		viewModel.put("usuario",user);
		viewModel.put("latitud", terminal.getPosicion().latitude());
		viewModel.put("longitud",terminal.getPosicion().longitude());
		
		return new ModelAndView(viewModel, "html/verTerminal.hbs");
	}
	
	private static Terminal obtenerTerminal(int id){
		RepositorioTerminales dao = new RepositorioTerminales();
		return dao.obtenerTerminal(id);
	}
	
	private static EnviadorDeMails obtenerEnviadorMails(){
		return new RepositorioTerminales().obtenerEnviadorMails();
	}
	
	private static Mapa obtenerMapa(){
		return  new RepositorioTerminales().obtenerMapa();
	}
		
	private static Set<Poi> filtrarBancos(Set<Poi> pois, boolean filtrar){
		if(filtrar)
			return pois.stream().filter(poi -> poi.getClass().equals(Banco.class)).collect(Collectors.toSet());
		else
			return new HashSet<Poi>();
	}
	
	private static Set<Poi> filtrarParadas(Set<Poi> pois, boolean filtrar){
		if(filtrar)
			return pois.stream().filter(poi -> poi.getClass().equals(ParadaDeColectivo.class)).collect(Collectors.toSet());
		else
			return new HashSet<Poi>();
	}
	
	private static Set<Poi> filtrarLocales(Set<Poi> pois, boolean filtrar){
		if(filtrar)
			return pois.stream().filter(poi -> poi.getClass().equals(LocalComercial.class)).collect(Collectors.toSet());
		else
			return new HashSet<Poi>();
	}
	
	private static Set<Poi> filtrarCgps(Set<Poi> pois, boolean filtrar){
		if(filtrar)
			return pois.stream().filter(poi -> poi.getClass().equals(CentroGestionParticipacion.class)).collect(Collectors.toSet());
		else
			return new HashSet<Poi>();
	}
	
	private static List<String> mapearAccionesAStrings(Collection<Accion> acciones){
		return  acciones.stream().map(accion -> accion.toString()).collect(Collectors.toList());
	}
		
}

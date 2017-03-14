package Controllers.controllers;

import java.util.HashMap;
import java.util.Map;


import dbConectors.RepositorioPois;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tpAnual_dominio.Poi;
import tpAnual_dominio.Terminal;

public class PoiController {
	
	public static ModelAndView verPoi(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String, Object>();
		int id = Integer.parseInt(req.params(":id"));
		
		Poi poi = new RepositorioPois().obtenerPoi(id);
		Terminal terminal = req.session().attribute("terminal");
		
		viewModel.put("poi", poi);
		viewModel.put("tipoPoi", poi.obtenerNombreTipo());
		if(terminal != null)
			viewModel.put("distancia", terminal.calcularDistanciaAPoi(poi));
		viewModel.put("terminal", terminal);
		
		return new ModelAndView(viewModel, "html/verPoi.hbs");
	}
	
	public static ModelAndView eliminarPoi(Request req, Response res){
		int id = Integer.parseInt(req.params(":id"));
		RepositorioPois repo = new RepositorioPois();
		
		repo.delete(repo.obtenerPoi(id));
		res.redirect("/PerfilAdministrador/AdministrarPois");
		
		return null;
	}
	
	public static ModelAndView modificarPoi(Request req, Response res){
		int id = Integer.parseInt(req.params(":id"));
		String nombre = req.queryParams("nombrePoi");
		String descripcion = req.queryParams("descripcion");
		String callePpal = req.queryParams("callePpal");
		Integer numero = obtenerIntegerParam(req, "numero");
		String entrecalle1 = req.queryParams("entrecalle1");
		String entrecalle2 = req.queryParams("entrecalle2");
		Integer piso = obtenerIntegerParam(req, "piso");
		String depto = req.queryParams("depto");
		String unidad = req.queryParams("unidad");
		Integer codigoPostal = obtenerIntegerParam(req, "codPostal");
		String localidad = req.queryParams("localidad");
		String barrio = req.queryParams("barrio");
		String provincia = req.queryParams("provincia");
		String pais = req.queryParams("pais");
		
		Poi poi = new RepositorioPois().obtenerPoi(id);
		
		poi.setNombre(nombre);
		poi.setDescripcion(descripcion);
		poi.getDireccion().setCallePrincipal(callePpal);
		poi.getDireccion().setNumero(numero);
		poi.getDireccion().setEntrecalle1(entrecalle1);
		poi.getDireccion().setEntrecalle2(entrecalle2);
		poi.getDireccion().setPiso(piso);
		poi.getDireccion().setDepartamento(depto);
		poi.getDireccion().setUnidad(unidad);
		poi.getDireccion().setCodigoPostal(codigoPostal);
		poi.getDireccion().setLocalidad(localidad);
		poi.getDireccion().setBarrio(barrio);
		poi.getDireccion().setProvincia(provincia);
		poi.getDireccion().setPais(pais);
		
		new RepositorioPois().update(poi);
		
		//Lo duermo por 1 segundo para que actualize la db
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		res.redirect("/PerfilAdministrador/AdministrarPois");
		
		return null;
	}
	
	private static Integer obtenerIntegerParam(Request req, String nombreCampo){
		String valor = req.queryParams(nombreCampo);
		
		if(valor.equals(""))
			return null;
		else
			return Integer.parseInt(valor);
				
	}
	
}

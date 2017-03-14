package Controllers.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbConectors.MongoConector;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tpAnual_dominio.Busqueda;
import tpAnual_dominio.Poi;

public class BusquedaController {

	public static ModelAndView getVistaBusqueda(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String, Object>();
		String id = req.params(":id");
		
		Busqueda busqueda = new MongoConector().obtenerBusqueda(id);
		List<Map<String, Object>> pois = new ArrayList<Map<String,Object>>();
				
		busqueda.getPois().stream().forEach(poi -> {
			Map<String,Object> datosPoi = new HashMap<String, Object>();
			datosPoi.put("poi", poi);
			datosPoi.put("tipoPoi", poi.obtenerNombreTipo());
			pois.add(datosPoi);});
		
		 Collections.sort(pois, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> poi1, Map<String, Object> poi2)
				{ return  ((Integer)((Poi) poi1.get("poi")).getPoi_id()).compareTo((Integer)((Poi) poi2.get("poi")).getPoi_id());}
				});
		
		viewModel.put("pois", pois);
		viewModel.put("busqueda", busqueda);
		
		return new ModelAndView(viewModel, "html/vistaBusqueda.hbs");
		
	}
}

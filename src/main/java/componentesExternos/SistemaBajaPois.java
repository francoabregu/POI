package componentesExternos;

import com.google.gson.JsonArray;


import java.util.ArrayList;
import java.util.List;

import tpAnual_dominio.Poi;
import com.google.gson.Gson;

public class SistemaBajaPois implements AdaptadorSistemaBajaPois {

	public SistemaBajaPois(InterfazBajaPoi interfazPoi) {
		this.interfazPoi = interfazPoi;
	}

	private InterfazBajaPoi interfazPoi;
	
	public List<Poi> obtenerPois(){
		JsonArray jsonArrayPoi = this.interfazPoi.findPois();
		Gson gson = new Gson();
	   	List<Poi> pois = new ArrayList<Poi>();
	   	
		if(jsonArrayPoi != null){
	   	for(int i = 0; i< jsonArrayPoi.size();i++ ){
	   			
	   			Poi poi =gson.fromJson(jsonArrayPoi.get(i),Poi.class);
	   			pois.add(poi);
	   	  			
	   		}
		}
		return pois;
	}
}

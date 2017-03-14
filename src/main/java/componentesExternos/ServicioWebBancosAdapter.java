package componentesExternos;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dbConectors.MongoConector;
import tpAnual_dominio.Banco;
import tpAnual_dominio.CentroGestionParticipacion;

public class ServicioWebBancosAdapter implements AdaptadorSistemaExterno{
	
	public ServicioWebBancosAdapter(InterfazServicioWebBanco interfazDeBanco) {
		this.interfazDeBanco = interfazDeBanco;
		}

	private InterfazServicioWebBanco interfazDeBanco;
	
	
	
	public List<CentroGestionParticipacion> obtenerCGPs(String lugar) {
		List<CentroGestionParticipacion> cgps = new ArrayList<CentroGestionParticipacion>();
		return cgps;
	}

	
	public List<Banco> obtenerBancos(String nombreBanco, String servicio) {
		

	   	Gson gson = new Gson();
	   	String nBanco,descripcion;
	   	double x,y;
	   	ArrayList<Banco> bancos = new ArrayList<Banco>();
	   	MongoConector conectorDB = new MongoConector();
	   	List<Banco> bancosEnMongo = conectorDB.buscarBanco(nombreBanco);
	   	
	   	if(bancosEnMongo.isEmpty()){
	   	JsonArray bancosArray = interfazDeBanco.findBanco(nombreBanco, servicio);
	   	if(bancosArray != null){
	   		for(int i = 0; i< bancosArray.size();i++){
	   			JsonObject resultado = gson.fromJson(bancosArray.get(i),JsonElement.class).getAsJsonObject();
	   			nBanco = resultado.get("banco").getAsString();
	   			x = resultado.get("x").getAsDouble();
	   			y = resultado.get("y").getAsDouble();
	   			descripcion = resultado.get("sucursal").getAsString();
	   			  			
	   			Banco banco = new Banco(nBanco,descripcion,null,x,y);
	   			bancos.add(banco);
	   		}
	   	}	
	   	bancos.forEach(banco ->conectorDB.persistirBanco(banco));
	   	return bancos;	
	   	}else{
	   	
	   		return bancosEnMongo;
	   	
	   	}
		 	
	}
	

}
	
	
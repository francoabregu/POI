package componentesExternos;

import com.google.gson.JsonArray;

public interface InterfazServicioWebBanco {

	JsonArray findBanco(String nombreBanco, String servicio);

}

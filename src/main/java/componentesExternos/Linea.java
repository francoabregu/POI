package componentesExternos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Linea {
	private String nombreFantasia;
	private List<String> palabrasClave;

	public Linea(String contenido) {
		palabrasClave = new ArrayList<String>();
		obtenerDatos(contenido);
	}
	
	public void obtenerDatos(String contenidoLinea){
		String[] partes = contenidoLinea.split(";"); // parte la linea en 2 quedando en el primer elemento el nombre y en el otro las funcionalidades
		nombreFantasia = partes[0];
		palabrasClave = Arrays.asList(partes[1]);
	}

	public String getNombreFantasia() {
		return nombreFantasia;
	}

	public List<String> getPalabrasClave() {
		return palabrasClave;
	}
	
	
}

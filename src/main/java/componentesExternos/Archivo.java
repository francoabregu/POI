package componentesExternos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Archivo {
	private List<Linea> lineas;
	private String ruta;
	
	public Archivo(String unaRuta){
		lineas = new ArrayList<Linea>();
		ruta = unaRuta;
		obtenerDatos();
	}
	
	public void obtenerDatos(){
		File archivoDeTexto = null;
		BufferedReader lector = null;
		try (FileReader lectorDeArchivo = new FileReader(archivoDeTexto)){
			archivoDeTexto = new File(ruta);
			lector = new BufferedReader(lectorDeArchivo);
			String contenidoLinea = lector.readLine();
			while( contenidoLinea != null){
				lineas.add(new Linea(contenidoLinea));
			}
	            
		}
		catch(Exception excepcion){//FIXME 
	         excepcion.printStackTrace();
	      }
	}

	public List<Linea> getLineas() {
		return lineas;
	}
	
	public String getRuta(){
		return ruta;
	}
	
	
	
}

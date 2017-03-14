package componentesExternos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

import dbConectors.MongoConector;
import tpAnual_dominio.Banco;
import tpAnual_dominio.CentroGestionParticipacion;
import tpAnual_dominio.Direccion;
import tpAnual_dominio.Servicio;

public class CentroDTOACGPAdapter implements AdaptadorSistemaExterno{
	
	private BibliotecaCGP bibliotecaExternaCGP;
	
	
	
	
	public CentroDTOACGPAdapter(BibliotecaCGP bibliotecaExternaCGP) {
		this.bibliotecaExternaCGP = bibliotecaExternaCGP;
	}


	// Recibe un texto del estilo "Junin 521" y devuelve un objeto de tipo Direccion
	public Direccion obtenerDireccion(String direccion){
		String [] textoConDireccion = direccion.split(" ");
		String callePrincipal = textoConDireccion[0];
		int numero = Integer.parseInt(textoConDireccion[1]);
		Direccion direccionLocal = new Direccion(callePrincipal,"","",numero,0,"","",0,"","","","");
		return direccionLocal;
				
	}
	
	
	private List<Servicio> generarServicios(ServiciosDTO[] vectorServicios) {
		List<ServiciosDTO> serviciosDTO = Arrays.asList(vectorServicios);
		return serviciosDTO.stream().map(servicioDTO -> servicioDTO.aServicio()).collect(Collectors.toList());
	}
	
	public CentroGestionParticipacion transformarEnCGP(CentroDTO unCentroDTO){
		Direccion direccion = obtenerDireccion(unCentroDTO.getDomicilio());
		CentroGestionParticipacion cgp  = new CentroGestionParticipacion("", "", direccion, 0, 0, null);
		cgp.setServicios(generarServicios(unCentroDTO.getServicios()));
		return cgp;
	}
	
	public List<CentroGestionParticipacion> obtenerCGPS(List<CentroDTO> centros){
		return centros.stream().map(centroDTO -> transformarEnCGP(centroDTO)).collect(Collectors.toList());
	}
	

	public List<CentroGestionParticipacion> obtenerCGPs(String lugar){
		MongoConector conectorDb = new MongoConector();
		List<CentroDTO> centrosEnMongo = conectorDb.buscarCentroDTO(lugar);
		if(centrosEnMongo.isEmpty()){
			List<CentroDTO> centrosDTO = bibliotecaExternaCGP.findCentros(lugar);
			centrosDTO.forEach(centro ->conectorDb.persistirCentroDTO(centro));
			return obtenerCGPS(centrosDTO);
		}
		else{
			return obtenerCGPS(centrosEnMongo);
		}

	}

	public List<Banco> obtenerBancos(String nombreBanco,String servicio){
		return new ArrayList<Banco>();
	}
}

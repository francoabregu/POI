package componentesExternos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.bson.types.ObjectId;

import tpAnual_dominio.FranjaDisponibilidadHoraria;
import tpAnual_dominio.Servicio;

@Entity("ServiciosDTO")
public class ServiciosDTO {
	
	@Id
	private ObjectId  servicioDTO_id;
	private String nombre;
	@Embedded
	private RangosServicioDTO[] rangosServicio;

	

	public ServiciosDTO(String nombre, RangosServicioDTO[] rangosServicio) {
		this.nombre = nombre;
		this.rangosServicio = rangosServicio;
	}


	
	
	public String getNombre() {
		return nombre;
	}




	public RangosServicioDTO[] getRangosServicio() {
		return rangosServicio;
	}  
	
	
	public Servicio aServicio(){
		List <RangosServicioDTO> listaServicios = Arrays.asList(rangosServicio);
		List <FranjaDisponibilidadHoraria> franjas = listaServicios.stream().map(servicioDTO -> servicioDTO.aFranjaDisponibilidadHoraria()).collect(Collectors.toList());
		Servicio servicio = new Servicio(nombre);
		servicio.setListaFranjaDisponibilidadHoraria(franjas);
		return servicio;
	}
	
	

}

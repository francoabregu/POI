package componentesExternos;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

@Entity("CentrosDTO")
public class CentroDTO {

	@Id
	private ObjectId centroDTO_id;
	@Transient
	private int numeroDeComuna;
	private String zonasIncluidas;
	@Transient
	private String nombreDirector;
	private String domicilio;
	@Transient
	private String telefonoCGP;
	@Embedded
	private ServiciosDTO[] servicios;
	
	public CentroDTO(int numeroDeComuna, String zonasIncluidas, String nombreDirector, String domicilio,
			String telefonoCGP, ServiciosDTO[] servicios) {
		this.numeroDeComuna = numeroDeComuna;
		this.zonasIncluidas = zonasIncluidas;
		this.nombreDirector = nombreDirector;
		this.domicilio = domicilio;
		this.telefonoCGP = telefonoCGP;
		this.servicios = servicios;
	}

	public int getNumeroDeComuna() {
		return numeroDeComuna;
	}

	public String getZonasIncluidas() {
		return zonasIncluidas;
	}

	public String getNombreDirector() {
		return nombreDirector;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public String getTelefonoCGP() {
		return telefonoCGP;
	}

	public ServiciosDTO[] getServicios() {
		return servicios;
	}
	
	
	
	
}

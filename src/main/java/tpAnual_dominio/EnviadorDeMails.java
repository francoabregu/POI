package tpAnual_dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public abstract class EnviadorDeMails {
	
	@GeneratedValue
	@Id
	private int enviador_id;
	
	public abstract void informarBusquedaLenta(String nombreTerminal, String criterioDeBusqueda,
										long tiempoMaximoBusquedaPermitido, long tiempoBusqueda);
	

	public abstract void informarFallaEnProcesoAsincronico(ProcesoAsincronico procesoFallido);
	
	public int getEnviador_id() {
		return enviador_id;
	}

	public void setEnviador_id(int enviador_id) {
		this.enviador_id = enviador_id;
	}

}
package tpAnual_dominio;

import java.time.LocalDateTime;

public class ResultadoDeProcesoAsincronico {
	
	public ResultadoDeProcesoAsincronico(int elementosAfectados, LocalDateTime fecha, String estado) {
		cantidadElementosAfectados = elementosAfectados;
		fechaEjecucion = fecha;
		estadoFinal = estado;
	}
	@SuppressWarnings("unused")
	private int cantidadElementosAfectados;
	
	@SuppressWarnings("unused")
	private LocalDateTime fechaEjecucion;
	private String estadoFinal;
	
	public boolean fallo() {
		return estadoFinal.equals("fallido");
	}
}

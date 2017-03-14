package mocks;

import tpAnual_dominio.AccionAnteFallo;
import tpAnual_dominio.AccionDeProceso;
import tpAnual_dominio.ProcesoAsincronico;
import tpAnual_dominio.ResultadoDeProcesoAsincronico;

public class MockProcesoAsincronico extends ProcesoAsincronico {

	private boolean seRegistroElResultado = false;
	private int cantidadDeFallos = 0;

	public MockProcesoAsincronico(AccionDeProceso accionDeProceso,AccionAnteFallo... accionesAnteFallo) {
		super(accionDeProceso, accionesAnteFallo);
	}

	@Override
	protected void almacenarResultado(ResultadoDeProcesoAsincronico unResultado) {
		seRegistroElResultado = true;
		super.almacenarResultado(unResultado);
	}
	
	@Override
	protected ResultadoDeProcesoAsincronico fallar(){
		cantidadDeFallos++;
		return super.fallar();
	}
	
	public int cantidadDeFallos(){
		return cantidadDeFallos;
	}
	
	public boolean seRegistroResultado(){
		return seRegistroElResultado;
	}
	
	

}

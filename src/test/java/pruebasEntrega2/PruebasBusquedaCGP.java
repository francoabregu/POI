package pruebasEntrega2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import componentesExternos.BibliotecaCGP;
import componentesExternos.CentroDTOACGPAdapter;
import tpAnual_dominio.CentroGestionParticipacion;


@SuppressWarnings("unused")
public class PruebasBusquedaCGP {
	
	public BibliotecaCGP impostorBibliotecaExterna;
	public CentroDTOACGPAdapter adaptadorCentroDTOACG;
	public String lugar;

	@Before
	public void iniciar(){
		impostorBibliotecaExterna = Mockito.mock(BibliotecaCGP.class);
		adaptadorCentroDTOACG = new CentroDTOACGPAdapter(impostorBibliotecaExterna);	
	}
	
	@Test
	public void siBuscoCGPsDeUnLugarHayUnaInteraccionConLaBibliotecaExterna() {
		lugar = "Junin 521";
		List<CentroGestionParticipacion> cgpsEncontrados = adaptadorCentroDTOACG.obtenerCGPs(lugar);
		Mockito.verify(impostorBibliotecaExterna).findCentros(lugar);
	}

}

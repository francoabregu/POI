package pruebasEntrega2;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import componentesExternos.ServicioWebBancosAdapter;
import componentesExternos.InterfazServicioWebBanco;
import tpAnual_dominio.Banco;

@SuppressWarnings("unused")
public class PruebasBusquedaBanco {
	
	public InterfazServicioWebBanco impostorBancoExterno;
	public ServicioWebBancosAdapter adaptadorBanco;
	public String nombreBanco, servicioBanco;

	@Before
	public void setUp(){
		impostorBancoExterno = Mockito.mock(InterfazServicioWebBanco.class);
		adaptadorBanco = new ServicioWebBancosAdapter(impostorBancoExterno);	
	}

	
	@Test
	public void siBuscoBancosDeUnLugarHayInteraccionConBibliotecaExterna() {
		nombreBanco = "Banco de la plaza";
		servicioBanco = "cobro cheques";
		List<Banco> bancosEncontrados = adaptadorBanco.obtenerBancos(nombreBanco, servicioBanco);
		Mockito.verify(impostorBancoExterno).findBanco(nombreBanco, servicioBanco);
	}

}

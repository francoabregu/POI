package db;

import static org.junit.Assert.*;

import java.util.List;


import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import dbConectors.RepositorioPois;
import dbConectors.RepositorioTerminales;
import dbConectors.RepositorioUsuarios;
import junit.framework.Assert;
import tpAnual_dominio.Banco;
import tpAnual_dominio.Direccion;
import tpAnual_dominio.EnviadorDeMailsConcreto;
import tpAnual_dominio.MapaReal;
import tpAnual_dominio.ParadaDeColectivo;
import tpAnual_dominio.Poi;
import tpAnual_dominio.Terminal;
import tpAnual_dominio.Usuario;

@SuppressWarnings("deprecation")
public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}
		
	@Test
	public void unBancoSeInsertoCorrectamente() throws Exception {
		RepositorioPois poiDao = new RepositorioPois();
		Direccion direccionProvincia = new Direccion("Avenida Velez Sarsfield", "Armenia", "Bernardino Rivadavia", 4448, 0,
										   "", "", 1605, "Vicente Lopez", "Munro", "Buenos Aires", "Argentina");
		Banco bcoProvincia = new Banco("Provincia", "el preferido de los pibes", direccionProvincia, 1.25, 11.00);
		poiDao.beginTransaction();
		persist(bcoProvincia);
		assertEquals(poiDao.obtenerPoi(bcoProvincia.getPoi_id()), bcoProvincia);
		poiDao.rollback();
	}
	
	
	@Test
	public void seTraenLasTerminalesDeLaDBCorrectamente() throws Exception {
		RepositorioTerminales terminalDao = new RepositorioTerminales();
		MapaReal mapaBsAs = new MapaReal();
		EnviadorDeMailsConcreto enviadorDeMails = new EnviadorDeMailsConcreto();
		Terminal terminalBanfield = new Terminal("Terminal campestre", mapaBsAs,enviadorDeMails,100,10.0,1.8);
		Terminal terminalCaballito = new Terminal("Terminal oligarca", mapaBsAs,enviadorDeMails,50,50.0,31.8);
		terminalDao.beginTransaction();
		persist(mapaBsAs);
		persist(enviadorDeMails);
		persist(terminalBanfield);
		persist(terminalCaballito);
		List<Terminal> terminalesAlmacenadas = terminalDao.obtenerTerminalesDb();
		terminalDao.rollback();
		Assert.assertTrue(terminalesAlmacenadas.contains(terminalBanfield) && terminalesAlmacenadas.contains(terminalCaballito));
	}
	
	@Test
	public void elUsuarioYContraseniaIngresadosExisten() throws Exception {
		RepositorioUsuarios usuarioDao = new RepositorioUsuarios();
		String usuarioIngresado = "Admin21";
		String contraseniaIngresada = "WSUJH23RY4374236";
		Usuario usuarioAdmin = new Usuario(usuarioIngresado, contraseniaIngresada);
		usuarioDao.beginTransaction();
		persist(usuarioAdmin);
		Assert.assertTrue(usuarioDao.existeUsuario(usuarioIngresado, contraseniaIngresada));
		usuarioDao.rollback();
		
	}
	@Test
	public void laDBdevuelveCorrectamentLosPoisDeTipoParada() throws Exception {
		RepositorioPois poiDao = new RepositorioPois();
		Direccion direccionProvincia = new Direccion("Avenida Velez Sarsfield", "Armenia", "Bernardino Rivadavia", 4448, 0,
				   					   "", "", 1605, "Vicente Lopez", "Munro", "Buenos Aires", "Argentina");
		ParadaDeColectivo paradaDel41 = new ParadaDeColectivo("Parada Linea 41", "Parada linea 41, Munro", direccionProvincia, 4.0, 11.0);
		poiDao.beginTransaction();
		persist(paradaDel41);
		List<Poi> poisEncontrados = poiDao.obtenerPoisDelTipo("PARADACOLECTIVO");
		poiDao.rollback();	
		Assert.assertTrue(poisEncontrados.contains(paradaDel41));
	}
	
	@Test
	public void seDevuelveCorrectamenteUnPoiAlBuscarloPorNombre() throws Exception {
		RepositorioPois poiDao = new RepositorioPois();
		Direccion direccionProvincia = new Direccion("Avenida Velez Sarsfield", "Armenia", "Bernardino Rivadavia", 4448, 0,
				   					   "", "", 1605, "Vicente Lopez", "Munro", "Buenos Aires", "Argentina");
		//Banco bcoProvincia = new Banco("Provincia", "el preferido de los pibes", direccionProvincia, 1.25, 11.00);
		ParadaDeColectivo paradaDel41 = new ParadaDeColectivo("Parada Linea 41", "Parada linea 41, Munro", direccionProvincia, 4.0, 11.0); 
		poiDao.beginTransaction();
		persist(paradaDel41);
		List<Poi> poisEncontrados = poiDao.obtenerPoisConNombre("Parada Linea 41");
		poiDao.rollback();
		Assert.assertTrue(poisEncontrados.contains(paradaDel41));
	}
	@Test
	public void seActualizaElNombreDeUnPoi() throws Exception {
		RepositorioPois poiDao = new RepositorioPois();
		Direccion direccionProvincia = new Direccion("Avenida Velez Sarsfield", "Armenia", "Bernardino Rivadavia", 4448, 0,
				   					   "", "", 1605, "Vicente Lopez", "Munro", "Buenos Aires", "Argentina");
		//Banco bcoProvincia = new Banco("Provincia", "el preferido de los pibes", direccionProvincia, 1.25, 11.00);
		ParadaDeColectivo paradaDel41 = new ParadaDeColectivo("Parada Linea 41", "Parada linea 41, Munro", direccionProvincia, 4.0, 11.0); 
		poiDao.beginTransaction();
		persist(paradaDel41);
		String nombreNuevo = "Parada picardia";
		paradaDel41.setNombre(nombreNuevo);
		poiDao.update(paradaDel41);
		Assert.assertTrue(paradaDel41.getNombre() == nombreNuevo);
	}
}

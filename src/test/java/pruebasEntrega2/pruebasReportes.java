package pruebasEntrega2;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import tpAnual_dominio.Accion;
import tpAnual_dominio.Banco;
import tpAnual_dominio.LocalComercial;
import tpAnual_dominio.Mapa;
import tpAnual_dominio.MapaReal;
import tpAnual_dominio.ParadaDeColectivo;
import tpAnual_dominio.RepositorioDeTerminales;
import tpAnual_dominio.Rubro;
import tpAnual_dominio.Terminal;

public class pruebasReportes {
	
	private Mapa mapa;
	private Banco bancoDeDolares;
	private Banco bancoDeSangre;
	private Rubro kiosco;
	private LocalComercial elKioscoDePedro;
	private LocalComercial libreriaEl100;
	private Rubro libreria;
	private ParadaDeColectivo paradaDel100;
	private Terminal terminal9DeJulio;
	private Terminal terminalCorrientes;
	private RepositorioDeTerminales centralOperativa;

	@Before
	public void setUp(){
		mapa = new MapaReal();
		bancoDeDolares = new Banco("banco de dolares", 
									"un banco donde se venden dolares",
									null, 5.0, 5.0);
		
		bancoDeSangre = new Banco("banco de sangre",
									"un banco donde se consigue sangre para donaciones",
									null, 5.0, 4.0);
		
		kiosco = new Rubro("kiosco", 2.0);
		
		elKioscoDePedro = new LocalComercial("el kioso de pedro", 
										"un kiosco lleno de chocolate y golosinas",
										null, 5.0, 3.0, kiosco);
		
		libreria = new Rubro("libreria", 5.0);
		
		libreriaEl100 = new LocalComercial("libreria El 100",
								"una libreria escolar", null, 4.0, 4.0, libreria);
		
		paradaDel100 = new ParadaDeColectivo("parada del 100",
									"parada de colectivo de la linea 100", null, 6.0, 5.0);
		
		mapa.agregarPoi(bancoDeDolares);
		mapa.agregarPoi(bancoDeSangre);
		mapa.agregarPoi(elKioscoDePedro);
		mapa.agregarPoi(libreriaEl100);
		mapa.agregarPoi(paradaDel100);
		
		terminal9DeJulio = new Terminal("terminal 9 de julio", mapa, null, 999999, 5.0, 5.0);
		terminal9DeJulio.habilitarAccion(Accion.almacenar_busquedas_realizadas);
		terminalCorrientes = new Terminal("terminal corrientes", mapa, null, 99999, 5.0, 9.0);
		terminalCorrientes.habilitarAccion(Accion.almacenar_busquedas_realizadas);
		
		centralOperativa = new RepositorioDeTerminales();
		centralOperativa.agregarTerminal(terminal9DeJulio);
		centralOperativa.agregarTerminal(terminalCorrientes);
		
		terminal9DeJulio.realizarBusqueda("banco");
		terminal9DeJulio.realizarBusqueda("100");
		terminalCorrientes.realizarBusqueda("banco");
		terminalCorrientes.realizarBusqueda("kiosco");
		terminalCorrientes.realizarBusqueda("libreria");
	
	}
	
	//Tests analizando La cantidad de registros devueltos en cada reporte sin considerar sus valores

	@Test
	public void siEstanDeshabilitadosLosReportesSeGeneranVacios() {
		
		assertEquals(centralOperativa.obtenerCantidadResultadosPorTerminal().size(), 0, 0);
		assertEquals(centralOperativa.obtenerCantidadBusquedasPorFecha().size(), 0, 0);
		assertEquals(centralOperativa.obtenerCantidadResultadosParcialesPorTerminal().size(), 0, 0);
	}
	
	@Test 
	public void laCantidadDeRegistrosDevueltosEnReporteResultadosPorTerminalEstaBien(){
		terminal9DeJulio.habilitarAccion(Accion.reporte_resultados_por_busqueda_y_terminal_total);
		terminalCorrientes.habilitarAccion(Accion.reporte_resultados_por_busqueda_y_terminal_total);
		
		assertEquals(centralOperativa.obtenerCantidadResultadosPorTerminal().size(), 2 , 0);
	}
	
	@Test
	public void laCantidadDeRegistrosDevueltosEnReporteResultadosPorTerminalParcialesEstaBien(){
		terminal9DeJulio.habilitarAccion(Accion.reporte_resultados_por_busqueda_y_terminal_parcial);
		terminalCorrientes.habilitarAccion(Accion.reporte_resultados_por_busqueda_y_terminal_parcial);
		
		assertEquals(centralOperativa.obtenerCantidadResultadosParcialesPorTerminal().size(), 2 , 0);
	}
	
	@Test
	public void laCantidadDeRegistrosDevueltosEnReporteBusquedaPorFechaEstaBien(){
		terminal9DeJulio.habilitarAccion(Accion.reporte_cantidad_de_busquedas_por_fecha);
		terminalCorrientes.habilitarAccion(Accion.reporte_cantidad_de_busquedas_por_fecha);
		
		assertEquals(centralOperativa.obtenerCantidadBusquedasPorFecha().size(), 1 , 0);
	}
	
	//Tests analizando los valores devueltos para cada reporte
	
	@Test 
	public void losResultadosDevueltosPorElReporteCantidadResultadosPorTerminalEstaBien(){
		terminal9DeJulio.habilitarAccion(Accion.reporte_resultados_por_busqueda_y_terminal_total);
		terminalCorrientes.habilitarAccion(Accion.reporte_resultados_por_busqueda_y_terminal_total);
		
		Map<String,Long> reporte = centralOperativa.obtenerCantidadResultadosPorTerminal();
		
		assertEquals(reporte.get("terminal 9 de julio"), 3, 0);
		assertEquals(reporte.get("terminal corrientes"), 4, 0);
	}
	
	@Test
	public void losResultadosDevueltosPorElReporteCantidadResultadosParcialesPorTerminalEstaBien(){
		terminal9DeJulio.habilitarAccion(Accion.reporte_resultados_por_busqueda_y_terminal_parcial);
		terminalCorrientes.habilitarAccion(Accion.reporte_resultados_por_busqueda_y_terminal_parcial);
		
		Map<String, List<Long>> reporte = centralOperativa.obtenerCantidadResultadosParcialesPorTerminal();
		
		List<Long> resultadosPara9DeJulio = new ArrayList<Long>();
		resultadosPara9DeJulio.add((long)2);
		resultadosPara9DeJulio.add((long)1);
		
		List<Long> resultadosParaCorrientes = new ArrayList<Long>();
		resultadosParaCorrientes.add((long)2);
		resultadosParaCorrientes.add((long)1);
		resultadosParaCorrientes.add((long)1);
		
		assertTrue(reporte.get("terminal 9 de julio").equals(resultadosPara9DeJulio));
		assertTrue(reporte.get("terminal corrientes").equals(resultadosParaCorrientes));
		
	}
	
	@Test
	public void losResultadosDevueltosPorElReporteBusquedasPorFechaEstaBien(){
		terminal9DeJulio.habilitarAccion(Accion.reporte_cantidad_de_busquedas_por_fecha);
		terminalCorrientes.habilitarAccion(Accion.reporte_cantidad_de_busquedas_por_fecha);
		
		Map<LocalDate, Long> reporte = centralOperativa.obtenerCantidadBusquedasPorFecha();
		
		assertEquals(reporte.get(LocalDate.now()), 5, 0);
		
	}
	

}

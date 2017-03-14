package pruebasEntrega1;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tpAnual_dominio.Banco;
import tpAnual_dominio.CentroGestionParticipacion;
import tpAnual_dominio.FranjaDisponibilidadHoraria;
import tpAnual_dominio.LocalComercial;
import tpAnual_dominio.ParadaDeColectivo;
import tpAnual_dominio.Rubro;
import tpAnual_dominio.Servicio;

public class PruebasDisponibilidad {
	
	LocalDateTime domingoMediodia;
	LocalDateTime lunesSeisDeLaManana;
	LocalDateTime lunesDiezDeLaManana;
	LocalDateTime lunesOnceDeLaNoche;
	LocalDateTime martesOchoTreintaDeLaManana;
	LocalDateTime martesDiezDeLaManana;
	LocalDateTime martesOnceTreintaDeLaManana;
	LocalDateTime miercolesMediodia;
	LocalDateTime juevesTresDeLaManana;
	LocalDateTime viernesTresDeLaTarde;
	
	Banco banco;
	LocalComercial kioscoCobo; 
	CentroGestionParticipacion cgp, cgpSinServicios;
	ParadaDeColectivo parada;
	
	private LocalDateTime generarDia(int hora, int minutos, String diaDeLaSemana) throws Exception{
		int numeroDeDia = 0;
		
		switch (diaDeLaSemana.toLowerCase()){
		case "lunes": numeroDeDia = DayOfWeek.MONDAY.getValue();
						break;
						
		case "martes": numeroDeDia = DayOfWeek.TUESDAY.getValue();
						break;
						
		case "miercoles": numeroDeDia = DayOfWeek.WEDNESDAY.getValue();
						break;
						
		case "jueves": numeroDeDia = DayOfWeek.THURSDAY.getValue();
		break;

		case "viernes": numeroDeDia = DayOfWeek.FRIDAY.getValue();
		break;
		
		case "sabado": numeroDeDia = DayOfWeek.SATURDAY.getValue();
		break;
		
		case "domingo": numeroDeDia = DayOfWeek.SUNDAY.getValue();
		break;
		
		default: throw new Exception("Dia de la semana invalido");
		}
		
		return LocalDateTime.now().withHour(hora).withMinute(minutos)
				.with(ChronoField.DAY_OF_WEEK,numeroDeDia);
	}
	
	@Before
	public void setup() throws Exception {

		domingoMediodia = generarDia(12,0, "domingo");
		lunesSeisDeLaManana = generarDia(6,0,"lunes");
		lunesDiezDeLaManana = generarDia(10, 0, "lunes");
		lunesOnceDeLaNoche =generarDia(23, 0, "lunes");
		martesOchoTreintaDeLaManana = generarDia(8, 30, "martes");
		martesDiezDeLaManana = generarDia(10, 0, "martes");
		martesOnceTreintaDeLaManana = generarDia(11, 30, "martes");
		miercolesMediodia = generarDia(12, 0, "miercoles");
		juevesTresDeLaManana = generarDia(3, 0 , "jueves");
		viernesTresDeLaTarde = generarDia(15, 0, "viernes");
		
		banco = new Banco("banco","banco",null,0,0);
				
		Rubro kiosco = new Rubro("kiosco",0.3);
		FranjaDisponibilidadHoraria franjaKiosco = new FranjaDisponibilidadHoraria(
										LocalTime.of(6,00),LocalTime.of(23,00),DayOfWeek.MONDAY);
		kioscoCobo = new LocalComercial(null,null,null,0,0,kiosco);
		kioscoCobo.agregar(franjaKiosco);
		
		Servicio asesoramientoLegal = new Servicio("asesoramiento legal");
		FranjaDisponibilidadHoraria franjaAsesoramiento = new FranjaDisponibilidadHoraria(
										LocalTime.of(8,30),LocalTime.of(11,30),DayOfWeek.TUESDAY);
		asesoramientoLegal.agregar(franjaAsesoramiento);
		cgp = new CentroGestionParticipacion(null,null,null,0,0,null);
		cgp.agregarServicio(asesoramientoLegal);
		cgpSinServicios = new CentroGestionParticipacion(null,null,null,0,0,null);

		parada = new ParadaDeColectivo(null,null,null,0,0);
	}

	@Test
	public void unBancoEstaDisponibleDiaDeSemanaDuranteSuHorario() {		
		Assert.assertTrue(banco.estaDisponible(miercolesMediodia,null));
	}
	
	@Test
	public void unBancoNoEstaDisponiBleFinDeSemana() {
		Assert.assertFalse(banco.estaDisponible(domingoMediodia,null));
	}
	
	@Test
	public void unBancoEstaDisponiBleDiaDeSemanaApenasAbre() {
		Assert.assertTrue(banco.estaDisponible(lunesDiezDeLaManana,null));
	}
	
	@Test
	public void unBancoNoEstaDisponiBleDiaDeSemanaApenasCierra() {
		Assert.assertFalse(banco.estaDisponible(viernesTresDeLaTarde,null));
	}
	
	@Test
	public void unaParadaEstaDisponibleDuranteElDia() {
		Assert.assertTrue(parada.estaDisponible(lunesDiezDeLaManana,null));
	}
	
	@Test
	public void unaParadaEstaDisponibleDuranteLaMadrugada() {
		Assert.assertTrue(parada.estaDisponible(juevesTresDeLaManana,null));
	}
	
	@Test
	public void unCgpNoEstaDisponibleSiNoTieneServicios() {
		Assert.assertFalse(cgpSinServicios.estaDisponible(lunesDiezDeLaManana,null));
	}
	
	@Test
	public void unCgpNoEstaDisponibleSiNoTieneServiciosDisponibles() {
		Assert.assertFalse(cgp.estaDisponible(viernesTresDeLaTarde,"asesoramiento legal"));
	}
	
	@Test
	public void unCgpEstaDisponibleSiTieneServiciosDisponibles() {
		Assert.assertTrue(cgp.estaDisponible(martesDiezDeLaManana,"asesoramiento legal"));
	}

	@Test
	public void unCgpEstaDisponibleApenasAbreSuServicio() {
		Assert.assertTrue(cgp.estaDisponible(martesOchoTreintaDeLaManana,"asesoramiento legal"));
	}
		
	@Test
	public void unCgpNoEstaDisponibleApenasCierraSusServicios() {
		Assert.assertFalse(cgp.estaDisponible(martesOnceTreintaDeLaManana,"asesoramiento legal"));
	}
	
	@Test
	public void unLocalEstaDisponibleDuranteSuHorario() {
		Assert.assertTrue(kioscoCobo.estaDisponible(lunesDiezDeLaManana,null));
	}
	
	@Test
	public void unLocalNoEstaDisponibleFueraDeSuHorario() {
		Assert.assertFalse(kioscoCobo.estaDisponible(juevesTresDeLaManana,null));
	}
	
	@Test
	public void unLocalEstaDisponibleApenasAbre() {
		Assert.assertTrue(kioscoCobo.estaDisponible(lunesSeisDeLaManana,null));
	}
	
	@Test
	public void unLocalNoEstaDisponibleApenasCierra() {
		Assert.assertFalse(kioscoCobo.estaDisponible(lunesOnceDeLaNoche,null));
	}
	
}
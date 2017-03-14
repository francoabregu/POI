package componentesExternos;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import tpAnual_dominio.FranjaDisponibilidadHoraria;

@Entity("RangosServiciosDTO")
public class RangosServicioDTO {
	@Id
	private ObjectId rengosServicios_id;
	private int diaDeLaSemana; 
	private int horarioDesde; 
	private int minutosDesde; 
	private int horarioHasta; 
	private int minutosHasta;
	
	public RangosServicioDTO(int diaDeLaSemana, int horarioDesde, int minutosDesde, int horarioHasta,
			int minutosHasta) {
		this.diaDeLaSemana = diaDeLaSemana;
		this.horarioDesde = horarioDesde;
		this.minutosDesde = minutosDesde;
		this.horarioHasta = horarioHasta;
		this.minutosHasta = minutosHasta;
	}

	public int getDiaDeLaSemana() {
		return diaDeLaSemana;
	}

	public int getHorarioDesde() {
		return horarioDesde;
	}

	public int getMinutosDesde() {
		return minutosDesde;
	}

	public int getHorarioHasta() {
		return horarioHasta;
	}

	public int getMinutosHasta() {
		return minutosHasta;
	}
	
	public FranjaDisponibilidadHoraria aFranjaDisponibilidadHoraria(){
		DayOfWeek dia = DayOfWeek.of(diaDeLaSemana);
		LocalTime horaInicio = LocalTime.of(horarioDesde, minutosDesde);
		LocalTime horaFin = LocalTime.of(horarioHasta,minutosHasta);
		FranjaDisponibilidadHoraria franja = new FranjaDisponibilidadHoraria(horaInicio, horaFin, dia);		
		return franja;
	}
	
}

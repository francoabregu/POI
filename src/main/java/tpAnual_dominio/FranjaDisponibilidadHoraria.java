package tpAnual_dominio;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

@Entity
public class FranjaDisponibilidadHoraria {
	
	@Id
	@GeneratedValue
	private int franja_id;
	@Column
	private LocalTime horaInicio;
	@Column
	private LocalTime horaFin;
	@Enumerated(EnumType.STRING)
	@Column(name="dia")
	private DayOfWeek dia;
	
	@Transient
	private String nombreDia;
	
	public FranjaDisponibilidadHoraria(LocalTime horaInicio, LocalTime horaFin, DayOfWeek dia) {
		super();
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.dia = dia;
		this.setNombreDia();
	}

	public FranjaDisponibilidadHoraria(){
	}
	
	public int getFranja_id() {
		return franja_id;
	}

	public void setFranja_id(int franja_id) {
		this.franja_id = franja_id;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public DayOfWeek getDia() {
		return dia;
	}

	public void setDia(DayOfWeek dia) {
		this.dia = dia;
	}

	public boolean estaDisponible(LocalDateTime momento) { 
		LocalTime horarioDelMomento = LocalTime.from(momento);
		return momento.getDayOfWeek().equals(dia) && 
				horarioDelMomento.isAfter(horaInicio) && 
				horarioDelMomento.isBefore(horaFin);
	}
	
	public String getNombreDia() {
		return nombreDia;
	}

	@PostLoad
	private void setNombreDia() {
		switch(dia){
			case MONDAY: nombreDia = "Lunes";
							break;
			case TUESDAY: nombreDia = "Martes";
							break;
			case WEDNESDAY: nombreDia = "Miercoles";
							break;
			case THURSDAY: nombreDia = "Jueves";
							break;
			case FRIDAY: nombreDia = "Viernes";
							break;
			case SATURDAY: nombreDia = "Sabado";
							break;
			case SUNDAY: nombreDia = "Domingo";
							break;
			default: throw new RuntimeException("Dia invalido");
		}
	}
		
}
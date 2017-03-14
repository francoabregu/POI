package tpAnual_dominio;

import java.time.Duration;
import java.time.LocalDateTime;

public class Cronometro {
	LocalDateTime tiempoDeInicio;
	
	public void iniciarCronometro(){
		tiempoDeInicio = LocalDateTime.now();
	}
	
	public int finalizarCronometro(){
		return (int) Duration.between(tiempoDeInicio, LocalDateTime.now()).getSeconds();
	}

}

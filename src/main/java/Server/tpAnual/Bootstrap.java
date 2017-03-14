package Server.tpAnual;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;


import tpAnual_dominio.*;
import dbConectors.MongoConector;



public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps{
	
	public void init(){
		withTransaction(() ->{
			//CREAR DIRECCIONES
			Direccion direccionSantanderCorrientes = new Direccion("Av. corrientes", "Suipacha", "Carlos Pelegrini", 
					957, null, null, null, 1043, "CABA", "Centro" ,"Buenos Aires", "Argentina");
			
			Direccion direccionGaliciaLavalle = new Direccion("Lavalle", "Cerrito", "Av. Presidente Roque Saenz Peña", 
					1102, null, null, null, 1043, "CABA", "Centro" ,"Buenos Aires", "Argentina");
			
			Direccion direccionItau = new Direccion("Cerrito", "Viamonte", "Av. Cordoba", 
					748, null, null, null, 1043, "CABA", "Centro" ,"Buenos Aires", "Argentina");
			
			Direccion metrobusTeatroColon = new Direccion("Av. 9 de julio", "Viamonte", "Tucuman", 
					628, null, null, null, null, "CABA", "Centro" ,"Buenos Aires", "Argentina");
			
			Direccion metrobusObeliscoSur = new Direccion("Av. 9 de julio", "Sarmiento", "Tte. gral. Juan Domingo Peron", 
					800, null, null, null, null, "CABA", "Centro" ,"Buenos Aires", "Argentina");
			
			Direccion direccionIlGato = new Direccion("Av. corrientes", "Carlos Pelegrini", "Suipacha", 
					959, null, null, null, 1043, "CABA", "Centro" ,"Buenos Aires", "Argentina");
			
			Direccion direccionLasCuartetas = new Direccion("Av. corrientes", "Esmeralda", "Suipacha", 
					838, null, null, null, 1043, "CABA", "Centro" ,"Buenos Aires", "Argentina");
			
			Direccion direccionLoveChuka = new Direccion("Carlos Pelegrini", "Lavalle", "Av. corrientes", 
					425, null, null, null, 1043, "CABA", "Centro" ,"Buenos Aires", "Argentina");
			
			Direccion direccionTeatroColon = new Direccion("Cerrito", "Tucuman", "Viamonte", 
					628, null, null, null, 1043, "CABA", "Centro" ,"Buenos Aires", "Argentina");
			
			Direccion direccionCgp1 = new Direccion("Uruguay", "Av. Cordoba", "Viamonte", 
					740, null, null, null, 1015, "CABA", "Centro" ,"Buenos Aires", "Argentina");
			
			//CREAR BANCOS
			Banco santanderCorrientes = new Banco("Banco Santander", "Banco Santander Rio S.A.",
					direccionSantanderCorrientes, -34.603490, -58.379965);
			
			Banco galiciaLavalle = new Banco("Banco Galicia", "Banco Galicia S.A.", direccionGaliciaLavalle, 
					-34.6034636,-58.3822526);
			
			Banco itauBuenAyre = new Banco("Banco Itau Buen Ayre", "Banco Itau S.A.", direccionItau, 
					-34.6007172,-58.382553);
			
			//CREAR PARADAS DE BONDIS
			ParadaDeColectivo metrobus10 = new ParadaDeColectivo("Parada linea 10", "Parada linea 10 metrobus teatro colon",
												metrobusTeatroColon, -34.6006921,-58.3824484);
			
			ParadaDeColectivo metrobus45 = new ParadaDeColectivo("Parada linea 45", "Parada linea 45 metrobus teatro colon",
					metrobusTeatroColon, -34.6006921,-58.3824484);
			
			ParadaDeColectivo metrobus70 = new ParadaDeColectivo("Parada linea 70", "Parada linea 70 metrobus teatro colon",
					metrobusTeatroColon, -34.6006921,-58.3824484);
			
			ParadaDeColectivo metrobus67 = new ParadaDeColectivo("Parada linea 67", "Parada linea 67 metrobus obelisco sur",
					metrobusObeliscoSur, -34.6057205,-58.3821554);
			
			ParadaDeColectivo metrobus129 = new ParadaDeColectivo("Parada linea 129", "Parada linea 129 metrobus obelisco sur",
					metrobusObeliscoSur, -34.6057205,-58.3821554);
			
			//CREAR RUBROS DE LOCALES
			Rubro localDeRopa = new Rubro("Local de ropa", 0.5);
			Rubro restaurant = new Rubro("Restaurant", 0.8);
			Rubro teatro = new Rubro("Teatro", 0.2);
			
			//CREAR FRANJAS DE ATENCION DE LOCALES COMERCIALES
			FranjaDisponibilidadHoraria lunesmaniana = new FranjaDisponibilidadHoraria(LocalTime.of(9, 00), LocalTime.of(15, 00), DayOfWeek.MONDAY);
			FranjaDisponibilidadHoraria martesmaniana = new FranjaDisponibilidadHoraria(LocalTime.of(9, 00), LocalTime.of(15, 00), DayOfWeek.TUESDAY);
			FranjaDisponibilidadHoraria miercolesmaniana = new FranjaDisponibilidadHoraria(LocalTime.of(9, 00), LocalTime.of(15, 00), DayOfWeek.WEDNESDAY);
			FranjaDisponibilidadHoraria juevesmaniana = new FranjaDisponibilidadHoraria(LocalTime.of(9, 00), LocalTime.of(15, 00), DayOfWeek.THURSDAY);
			FranjaDisponibilidadHoraria viernesmaniana = new FranjaDisponibilidadHoraria(LocalTime.of(9, 00), LocalTime.of(15, 00), DayOfWeek.FRIDAY);
			
			FranjaDisponibilidadHoraria sabado = new FranjaDisponibilidadHoraria(LocalTime.of(9, 00), LocalTime.of(17, 00), DayOfWeek.SATURDAY);
			FranjaDisponibilidadHoraria domingo = new FranjaDisponibilidadHoraria(LocalTime.of(9, 00), LocalTime.of(17, 00), DayOfWeek.SUNDAY);
			
			FranjaDisponibilidadHoraria viernesNoche= new FranjaDisponibilidadHoraria(LocalTime.of(19, 00), LocalTime.of(23, 00), DayOfWeek.FRIDAY);
			FranjaDisponibilidadHoraria sabadoNoche = new FranjaDisponibilidadHoraria(LocalTime.of(19, 00), LocalTime.of(23, 00), DayOfWeek.SATURDAY);
			FranjaDisponibilidadHoraria domingoNoche = new FranjaDisponibilidadHoraria(LocalTime.of(19, 00), LocalTime.of(23, 00), DayOfWeek.SUNDAY);
			
			FranjaDisponibilidadHoraria horarioTramiteDni = new FranjaDisponibilidadHoraria(LocalTime.of(9, 00), LocalTime.of(16, 00), DayOfWeek.WEDNESDAY);
			FranjaDisponibilidadHoraria horarioTramiteDomicilio = new FranjaDisponibilidadHoraria(LocalTime.of(9, 00), LocalTime.of(16, 00), DayOfWeek.MONDAY);
			
			//CREAR LOCALES COMERCIALES
			LocalComercial ilGatoObelisco = new LocalComercial("Il gato Tratoria", "Restaurant il Gato tratoria sucursal obelisco",
											direccionIlGato, -34.6023423,-58.3821668, restaurant);
			
			ilGatoObelisco.agregar(viernesNoche);
			ilGatoObelisco.agregar(sabadoNoche);
			ilGatoObelisco.agregar(domingoNoche);
			
			LocalComercial lasCuartetas = new LocalComercial("Pizzeria Las Cuartetas", "Pizzeria clasica de Buenos Aires",
											direccionLasCuartetas, -34.6031055,-58.3803161, restaurant);

			lasCuartetas.agregar(viernesNoche);
			lasCuartetas.agregar(sabadoNoche);
			lasCuartetas.agregar(domingoNoche);
			
			LocalComercial loveChuka = new LocalComercial("Love Chuka", "Indumentaria femenina",
					direccionLoveChuka, -34.6029533,-58.38194, localDeRopa);
			
			loveChuka.agregar(lunesmaniana);
			loveChuka.agregar(martesmaniana);
			loveChuka.agregar(miercolesmaniana);
			loveChuka.agregar(juevesmaniana);
			loveChuka.agregar(viernesmaniana);
			
			LocalComercial teatroColon = new LocalComercial("Teatro Colon", "Teatro iconico de la ciudad de Buenos Aires",
					direccionTeatroColon, -34.6015052,-58.3825761, teatro);
			
			teatroColon.agregar(sabado);
			teatroColon.agregar(domingo);
			
			//CREAR SERVICIOS CGP
			Servicio tramiteDocumento = new Servicio("Tramite de documento de identidad");
			
			tramiteDocumento.agregar(horarioTramiteDni);

			
			Servicio cambioDomicilio = new Servicio("Tramite de cambio de domicilio");
			
			cambioDomicilio.agregar(horarioTramiteDomicilio);
						
			
			//CREAR CGPS
			CentroGestionParticipacion cgpComuna1 = new CentroGestionParticipacion("CGP comuna 1", "CGP comuna 1", 
					direccionCgp1, -34.6002749,-58.3856967, null);
			
			cgpComuna1.agregarServicio(cambioDomicilio);
			cgpComuna1.agregarServicio(tramiteDocumento);
			
			//CREAR MAPA
			Mapa mapa = new MapaReal();
			
			mapa.agregarPoi(cgpComuna1);
			mapa.agregarPoi(santanderCorrientes);
			mapa.agregarPoi(itauBuenAyre);
			mapa.agregarPoi(galiciaLavalle);
			mapa.agregarPoi(metrobus10);
			mapa.agregarPoi(metrobus129);
			mapa.agregarPoi(metrobus45);
			mapa.agregarPoi(metrobus67);
			mapa.agregarPoi(metrobus70);
			mapa.agregarPoi(teatroColon);
			mapa.agregarPoi(loveChuka);
			mapa.agregarPoi(ilGatoObelisco);
			mapa.agregarPoi(lasCuartetas);
			
			//CREAR ENVIADOR DE MAILS
			EnviadorDeMails envMails = new EnviadorDeMailsConcreto("admin@gmail.com");
			
			//CREAR TERMINALES
			Terminal terminalMetrobusTeatroColon = new Terminal("Terminal metrobus Teatro Colon", mapa, envMails,
					2000, -34.6006921,-58.3824484 );
			
			terminalMetrobusTeatroColon.setComuna(1);
			
			Terminal terminalMetrobusObeliscoSur = new Terminal("Terminal metrobus Obelisco Sur", mapa, envMails,
					2000, -34.6057205,-58.3821554 );
			
			terminalMetrobusObeliscoSur.setComuna(1);
			
			//RESULTADOS DE BUSQUEDAS
			Set<Poi> resultadoBusqueda1 = new HashSet<Poi>();
			resultadoBusqueda1.add(itauBuenAyre);
			resultadoBusqueda1.add(galiciaLavalle);
			resultadoBusqueda1.add(santanderCorrientes);
			
			Set<Poi> resultadoBusqueda2 = new HashSet<Poi>();
			resultadoBusqueda2.add(cgpComuna1);
			
			//CREAR BUSQUEDAS
			Busqueda busqueda1 = new Busqueda(LocalDate.now(), 3, 25,"Banco" , resultadoBusqueda1, 
									terminalMetrobusTeatroColon.getNombre());
			
			Busqueda busqueda2 = new Busqueda(LocalDate.now(), 1, 25,"Tramite" , resultadoBusqueda2, 
					terminalMetrobusObeliscoSur.getNombre());
			
			//CREAR USUARIOS
			
			Usuario usuarioTerminalMetrobusTeatroColon = new Usuario("terminalMetrobusTeatroColon", "teatroColon", terminalMetrobusTeatroColon);
			Usuario usuarioTerminalMetrobusObeliscoSur = new Usuario("terminalMetrobusObeliscoSur", "obeliscoSur", terminalMetrobusObeliscoSur);
			Usuario admin = new Usuario("enCasaAndaba", "enCasaAndaba");
			
			//PERSISTO LOS OBJETOS			
			persist(santanderCorrientes);
			persist(galiciaLavalle);
			persist(itauBuenAyre);
			
			persist(metrobus10);
			persist(metrobus45);
			persist(metrobus70);
			persist(metrobus129);
			persist(metrobus67);
			
			persist(teatro);
			persist(localDeRopa);
			persist(restaurant);
			
			persist(teatroColon);
			persist(loveChuka);
			persist(ilGatoObelisco);
			persist(lasCuartetas);
			
			persist(lunesmaniana);
			persist(martesmaniana);
			persist(miercolesmaniana);
			persist(juevesmaniana);
			persist(viernesmaniana);
			persist(viernesNoche);
			persist(sabado);
			persist(sabadoNoche);
			persist(domingo);
			persist(domingoNoche);
			
			persist(tramiteDocumento);
			persist(cambioDomicilio);
			
			persist(horarioTramiteDni);
			persist(horarioTramiteDomicilio);
			
			persist(cgpComuna1);
			
			persist(mapa);
			
			persist(envMails);
			
			persist(terminalMetrobusObeliscoSur);
			persist(terminalMetrobusTeatroColon);
			
			MongoConector mongo =new MongoConector();
			
			mongo.persistirBusqueda(busqueda1);
			mongo.persistirBusqueda(busqueda2);
			
			persist(usuarioTerminalMetrobusObeliscoSur);
			persist(usuarioTerminalMetrobusTeatroColon);
			persist(admin);
		});
	}
}
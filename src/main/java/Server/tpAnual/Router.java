package Server.tpAnual;

import Controllers.controllers.AdministradorController;
import Controllers.controllers.BusquedaController;
import Controllers.controllers.IndexController;
import Controllers.controllers.PoiController;
import Controllers.controllers.TerminalController;
import Spark.Utils.tpAnual.HandlebarsTemplateEngineBuilder;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;



public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.build();

		Spark.staticFiles.location("/public");

		//######## LOGIN #######
		Spark.get("/", IndexController::get,engine);
		Spark.post("/", IndexController::post, engine);
		
		//######## Perfil Terminal #########
		Spark.get("/PerfilTerminal/:id", TerminalController::getPerfilTerminal, engine);
		Spark.post("/PerfilTerminal", TerminalController::buscarPois, engine);
		
		//######## Perfil Administrador #########
		Spark.get("/PerfilAdministrador", AdministradorController::getPerfilAdministrador, engine);
		Spark.get("/PerfilAdministrador/AdministrarPois", AdministradorController::vistaPoisAdministrador, engine);
		Spark.post("/PerfilAdministrador/AdministrarPois", AdministradorController::buscarPois, engine);
		Spark.get("/PerfilAdministrador/AdministrarPois/ModificarPoi/:id", AdministradorController::vistaModificarPoi, engine);
		Spark.get("/PerfilAdministrador/AdministrarBusquedas", AdministradorController::vistaBusquedasAdministrador, engine);
		Spark.post("/PerfilAdministrador/AdministrarBusquedas", AdministradorController::filtrarBusquedas, engine);
		Spark.get("/PerfilAdministrador/AdministrarTerminales", AdministradorController::vistaTerminalesAdministrador, engine);
		Spark.post("/PerfilAdministrador/AdministrarTerminales", AdministradorController::filtrarTerminales, engine);
		Spark.get("/PerfilAdministrador/AdministrarTerminales/CrearTerminal", AdministradorController::vistaCrearTerminal, engine);
		Spark.get("/PerfilAdministrador/AdministrarTerminales/ModificarTerminal/:id", AdministradorController::vistaModificarTerminal, engine);
		Spark.get("/PerfilAdministrador/AdministrarTerminales/ModificarTerminal/:id/ConfigurarAcciones", AdministradorController::vistaConfigurarAcciones, engine);
		
		//######## Pois #########
		Spark.get("/VerPoi/:id", PoiController::verPoi, engine);
		Spark.post("/BorrarPoi/:id", PoiController::eliminarPoi, engine);
		Spark.post("/ModificarPoi/:id", PoiController::modificarPoi, engine);
		
		//######## Busquedas #########
		Spark.get("/VerBusqueda/:id", BusquedaController::getVistaBusqueda, engine);
		
		//######## Terminales #########
		Spark.get("/VerTerminal/:id", TerminalController::verTerminal, engine);
		Spark.get("/BorrarTerminal/:id", TerminalController::borrarTerminal, engine);
		Spark.post("/CrearTerminal", TerminalController::crearTerminal, engine);
		Spark.post("/ModificarTerminal/:id", TerminalController::modificarTerminal, engine);
		Spark.post("/HabilitarAccion", TerminalController::habilitarAccion, engine);
		Spark.post("/DeshabilitarAccion", TerminalController::deshabilitarAccion, engine);
		Spark.post("/GuardarConfiguracionAcciones", TerminalController::guardarConfiguracionAcciones, engine);
		
	}

}
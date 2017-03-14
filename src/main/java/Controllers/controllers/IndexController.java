package Controllers.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbConectors.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tpAnual_dominio.Accion;
import tpAnual_dominio.Terminal;
import tpAnual_dominio.TipoUsuario;
import tpAnual_dominio.Usuario;

public class IndexController {
	
	public static ModelAndView get(Request req, Response res) {
		Map<String, Object> viewModel = new HashMap<String, Object>();	
		return new ModelAndView(viewModel, "html/login.hbs");
	}
	
	public static ModelAndView post(Request req, Response res){
		Map<String, Object> viewModel = new HashMap<String, Object>();
		
		String username = req.queryParams("nombreUsuario");
		String password = req.queryParams("contrasenia");
		
		RepositorioUsuarios usuarioDao = new RepositorioUsuarios();
		Usuario usuario;
		if(usuarioDao.existeUsuario(username, password))
			usuario = usuarioDao.obtenerUsuario(username);
		else{	
			viewModel.put("mensajeError", "Nombre de usuario o contraseña incorrecta");
			return new ModelAndView(viewModel, "html/login.hbs");
		}
		if(usuario.getTipo().equals(TipoUsuario.terminal))
			res.redirect("/PerfilTerminal/" + usuario.getTerminal().getId());
			
		if(usuario.getTipo().equals(TipoUsuario.admin))
			res.redirect("/PerfilAdministrador");
		
		return null;
	}

}

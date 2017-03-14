package dbConectors;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import tpAnual_dominio.Terminal;
import tpAnual_dominio.Usuario;

public class RepositorioUsuarios extends SqlConector implements WithGlobalEntityManager{
	
	public RepositorioUsuarios(){
		
	}
	
	public boolean existeUsuario(String nombreUsuario, String contrasenia){
		try{	
			entityManager().createQuery("FROM Usuario u WHERE u.nombreUsuario LIKE :usuario AND u.contrasenia LIKE :contrasenia")
			.setParameter("usuario", nombreUsuario)
			.setParameter("contrasenia", contrasenia)
			.getSingleResult();
			return true;
		}
		catch(Exception e)
		{
			if(e instanceof NoResultException){
				e.printStackTrace();
			}
			return false;
		}
}
	
	
	public Usuario obtenerUsuario(String nombreUsuario){
		try{
		Query consulta = entityManager().createQuery("FROM Usuario u WHERE u.nombreUsuario LIKE :usuario")
						 .setParameter("usuario", nombreUsuario);
		return (Usuario)consulta.getSingleResult();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	public Usuario obtenerUsuarioConTerminal(Terminal terminal) {
		try{
		Query consulta = entityManager().createQuery("FROM Usuario u WHERE u.terminal LIKE :terminal")
						 .setParameter("terminal", terminal);
		return (Usuario)consulta.getSingleResult();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

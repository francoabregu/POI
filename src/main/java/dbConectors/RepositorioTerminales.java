package dbConectors;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import tpAnual_dominio.EnviadorDeMails;
import tpAnual_dominio.Mapa;
import tpAnual_dominio.Terminal;

public class RepositorioTerminales extends SqlConector implements WithGlobalEntityManager{
	
	@SuppressWarnings("unchecked")
	public List<Terminal> obtenerTerminalesDb(){
		return entityManager().createQuery("FROM Terminal").getResultList();
	}
	
	public Mapa obtenerMapa(){
		return (Mapa)entityManager().createQuery("From Mapa").getSingleResult();
	}
	
	public EnviadorDeMails obtenerEnviadorMails(){
		return (EnviadorDeMails)entityManager().createQuery("From EnviadorDeMails").getSingleResult();
	}
	
	public Terminal obtenerTerminal(int id){
		return entityManager().find(Terminal.class, id);
	}
}

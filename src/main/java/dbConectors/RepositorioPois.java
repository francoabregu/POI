package dbConectors;


import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import tpAnual_dominio.Poi;

public class RepositorioPois extends SqlConector implements WithGlobalEntityManager{		
	

	
	public RepositorioPois() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Poi obtenerPoi(int poi_id){
		return entityManager().find(Poi.class, poi_id);
	}
	

	
	@SuppressWarnings("unchecked")
	public List<Poi> obtenerPoisDelTipo(String unTipo){
		return entityManager().createQuery("FROM Poi p WHERE p.class LIKE :tipo").setParameter("tipo", unTipo).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Poi> obtenerPoisConNombre(String unNombre){
		return entityManager().createQuery("FROM Poi p WHERE p.nombre LIKE :nombre").setParameter("nombre", "%"+unNombre+"%").getResultList();
	}
	
}

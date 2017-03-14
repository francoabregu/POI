package dbConectors;



import java.util.function.*;


import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.hibernate.Session;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;



abstract class SqlConector implements WithGlobalEntityManager{
	
			public void beginTransaction() {
			EntityTransaction tx = 	entityManager().getTransaction();
			if(!tx.isActive()){
				tx.begin();
			}
		}

		public void commitTransaction() {
		   	EntityTransaction tx = entityManager().getTransaction();
			if(tx.isActive()){
				tx.commit();
			}
		}

		public void rollback(){
			EntityTransaction tx = entityManager().getTransaction();
			if(tx.isActive()){
				tx.rollback();
			}
		}
		    
		public Query createQuery(String query) {
			return entityManager().createQuery(query);
		}
		    
		public void persist(Object o){
			beginTransaction();
				entityManager().persist(o);
				entityManager().flush();
			commitTransaction();
		}
		
		public void update(Object objetoModificado){
			beginTransaction();
				entityManager().merge(objetoModificado);
				entityManager().flush();
			commitTransaction();
		}
		
		public void delete(Object unObjeto){
			beginTransaction();
			entityManager().remove(unObjeto);
		    entityManager().flush();
			commitTransaction();
		}
		
		public <A> A withTransaction(Supplier<A> action) {
			beginTransaction();
			try{
				A result = action.get();
				commitTransaction();
				return result;
		    }
			catch(Throwable e){
				rollback();
		    	throw e;
		    }
		}
		
}
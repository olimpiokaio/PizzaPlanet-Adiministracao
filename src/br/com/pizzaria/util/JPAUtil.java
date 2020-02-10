package br.com.pizzaria.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory em = Persistence.createEntityManagerFactory("jsf-pizzaria");
	
	public EntityManager getEntityManager() {
		return em.createEntityManager();
	}
	
	public void close(EntityManager em) {
		em.close();
	}
}

package br.com.pizzaria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.pizzaria.modelo.Pizza;
import br.com.pizzaria.modelo.Tipo;
import br.com.pizzaria.util.JPAUtil;

public class PizzaDao implements Serializable {
	private static final long serialVersionUID = 7148828511705112325L;
	/**
	 * 
	 */
	public List<Pizza> listarPorTipo(Tipo tipo) {
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			em.getTransaction().begin();
			
			System.out.println(tipo.getCategoria());
			
			String jpql = "select p from Pizza p where p.tipo = :pTipo";
			TypedQuery<Pizza> query = em.createQuery(jpql, Pizza.class);
			query.setParameter("pTipo", tipo);
			
			List<Pizza> resultado = query.getResultList();
			return resultado;
		} catch(NoResultException e) {
			System.err.println("Não foi possivel buscar um lista de pizzas");
			return null;
		} finally {
			em.getTransaction().commit();
			em.close();
		}
	}

}

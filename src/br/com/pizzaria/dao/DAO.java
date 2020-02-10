package br.com.pizzaria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.pizzaria.util.JPAUtil;


public class DAO<T> implements Serializable {
	private static final long serialVersionUID = 2208624262421606790L;

	private final Class<T> classe;
	
	public DAO(Class<T> classe) {
		this.classe = classe;
	}
	
	public void gravar(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		// persistir emtidade
		em.persist(t);
		
		em.getTransaction().commit();
		em.close();
	}
	
	public void remover(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		// remover entidade
		em.remove(em.merge(t));
		
		em.getTransaction().commit();
		em.close();
	}
	
	public void atualizar(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		em.merge(t);
		
		em.getTransaction().commit();
		em.close();
	}
	
	public List<T> listarTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		// cria uma query select da classe
		query.select(query.from(classe));
		
		try {
			// retorna lista da tabela da classe
			List<T> lista = em.createQuery(query).getResultList();
			return lista;
		} 
		catch(Exception e) {
			System.err.println(e);
			return null;
		} 
		finally {
			em.getTransaction().commit();
			em.close();
		}
	}

	public T buscarPorId(Integer id) {		
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			em.getTransaction().begin();
			T resultado = em.find(classe, id);
			return resultado;			
		}
		catch (Exception e) {
			System.err.println("Erro trado " + e);
			return null;
		}
		finally {
			em.getTransaction().commit();
			em.close();			
		}
	}
	
	
}












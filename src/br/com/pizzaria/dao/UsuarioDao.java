package br.com.pizzaria.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.pizzaria.modelo.Usuario;
import br.com.pizzaria.util.JPAUtil;

public class UsuarioDao implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 */

	public boolean existe(Usuario usuario) {
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			em.getTransaction().begin();
			
			String jpql = "select u from Usuario u where u.email = :pEmail and"
					+ " u.senha = :pSenha";
			
			TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
			query.setParameter("pEmail", usuario.getEmail());
			query.setParameter("pSenha", usuario.getSenha());
			
			Usuario user;
			user = query.getSingleResult();
			
			
			return user != null;
		} catch (NoResultException e) {
			System.err.println("Não tem resultado para esse email e senha");
			return false;
			
		} finally {
			em.getTransaction().commit();
			em.close();
		}
	}
	
}
















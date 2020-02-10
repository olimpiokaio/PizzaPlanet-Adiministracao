package br.com.pizzaria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.pizzaria.modelo.Tipo;
import br.com.pizzaria.util.JPAUtil;

public class TipoDao implements Serializable {
	private static final long serialVersionUID = 1L;

	
	EntityManager manager = new JPAUtil().getEntityManager();	
	
	public List<Tipo> buscarPorCategoria(int idCategoria) {
		manager.getTransaction().begin();
		
		Query query = manager.createQuery
				("select t from Tipo t where t.categoria.id = :pIdCategoria");
		
		query.setParameter("pIdCategoria", idCategoria);
		
		List<Tipo> listaTipo = query.getResultList();
		
		manager.getTransaction().commit();
		manager.close();
		
		return listaTipo;
	}
	
}



















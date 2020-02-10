package br.com.pizzaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.pizzaria.dao.DAO;
import br.com.pizzaria.modelo.Categoria;

@ViewScoped
@ManagedBean
public class CategoriaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private Categoria categoria = new Categoria();
	
	private List<Categoria> categoriaLista = new ArrayList<Categoria>();
	private DAO<Categoria> dao = new DAO<Categoria>(Categoria.class);
	
	
	/*----------------------------------*/
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public List<Categoria> getCategoriaLista() {
		return categoriaLista;
	}
	
	public void setCategoriaLista(List<Categoria> categoriaLista) {
		this.categoriaLista = categoriaLista;
	}

	/*------------------------------------*/
	
	@PostConstruct
	public void init() {
		this.categoriaLista = dao.listarTodos();
	}
	
	public String gravar() {
		System.out.println("salvando categoria");
		DAO<Categoria> dao = new DAO<Categoria>(Categoria.class);
		
		if (this.categoria.getId() != null) {
			dao.atualizar(this.categoria);
		} else {
			dao.gravar(this.categoria);			
		}
		return "cadCategoria";
	}
	
	public void editar(int id) {
		this.categoria = dao.buscarPorId(id);
	}
	
	public String remover(int id) {
		Categoria cat = dao.buscarPorId(id);
		dao.remover(cat);
		return "cadCategoria?faces-redirect=true";
	}

}















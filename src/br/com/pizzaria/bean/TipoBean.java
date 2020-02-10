package br.com.pizzaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.pizzaria.dao.DAO;
import br.com.pizzaria.modelo.Categoria;
import br.com.pizzaria.modelo.Tipo;


@ViewScoped
@ManagedBean
public class TipoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Tipo tipo = new Tipo();
	private Integer idCategoria;
	
	private List<Categoria> listaCategoria = new ArrayList<Categoria>();
	private List<Tipo> listaTipo = new ArrayList<Tipo>();

	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}
	
	public Integer getIdCategoria() {
		this.idCategoria = this.tipo.getCategoria().getId();
		return this.idCategoria;
	}
	
	public void setIdCategoria(Integer idCat) {
		this.idCategoria = idCat;
	}
	
	public List<Tipo> getListaTipo() {
		return listaTipo;
	}
	
	/*--------------------------------------------*/
	
	@PostConstruct
	private void init() {
		this.listaCategoria = new DAO<Categoria>(Categoria.class).listarTodos();
		this.listaTipo = new DAO<Tipo>(Tipo.class).listarTodos();
	}
	
	public String gravar() {
		DAO<Categoria> daoCategoria = new DAO<Categoria>(Categoria.class);
		Categoria cat = daoCategoria.buscarPorId(this.idCategoria);
		this.tipo.setCategoria(cat);
		
		DAO<Tipo> daoTipo = new DAO<Tipo>(Tipo.class);
		if (this.tipo.getId() != null) {
			daoTipo.atualizar(this.tipo);
		} else {
			daoTipo.gravar(this.tipo);			
		}
		return "cadTipo";
	}
	
	public void remover(int id) {
		System.out.println("removendo...");
		Tipo tipo = new DAO<Tipo>(Tipo.class).buscarPorId(id);
		new DAO<Tipo>(Tipo.class).remover(tipo);
		this.init();
	}
	
	public void editar(int id) {
		Tipo tipo = new DAO<Tipo>(Tipo.class).buscarPorId(id);
		this.tipo = tipo;
	}
	
}











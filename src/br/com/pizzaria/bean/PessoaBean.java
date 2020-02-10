package br.com.pizzaria.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.pizzaria.dao.DAO;
import br.com.pizzaria.modelo.Pessoa;

@ManagedBean
@SessionScoped
public class PessoaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 */

	private Pessoa pessoa = new Pessoa();

	/* ------------------------------- */
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	/* ------------------------------- */
	
	public void gravar() {
		new DAO<Pessoa>(Pessoa.class).gravar(this.pessoa);
	}
	
}












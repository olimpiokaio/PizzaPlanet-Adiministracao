package br.com.pizzaria.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Carrinho implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private long precoTotal;
	
	@ManyToOne
	private Pessoa pessoa;

	@ManyToMany
	private List<Produto> produtos = new ArrayList<Produto>();

	/*--------------------------*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public long getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(long precoTotal) {
		this.precoTotal = precoTotal;
	}

	public void adicionaProduto(Produto produto) {
		this.produtos.add(produto);
	}
	
	public void removeProduto(int index) {
		this.produtos.remove(index);
	}

}

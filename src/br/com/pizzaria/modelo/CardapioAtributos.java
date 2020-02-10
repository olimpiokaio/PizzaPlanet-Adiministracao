package br.com.pizzaria.modelo;

import java.io.Serializable;


public class CardapioAtributos implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int quantidade;
	private double subtotal;
	private int avaliacao;

	public CardapioAtributos() {
	}

	public int getQuantidade() {
		if (this.quantidade <= 0) {
			setQuantidade(1);
		} else if (this.quantidade > 10) {
			setQuantidade(10);
		}
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int avaliacao) {
		this.avaliacao = avaliacao;
	}
}
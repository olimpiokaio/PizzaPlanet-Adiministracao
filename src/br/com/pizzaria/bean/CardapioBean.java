package br.com.pizzaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.pizzaria.dao.DAO;
import br.com.pizzaria.dao.PizzaDao;
import br.com.pizzaria.modelo.Bebida;
import br.com.pizzaria.modelo.CardapioAtributos;
import br.com.pizzaria.modelo.Pizza;
import br.com.pizzaria.modelo.Tipo;

@ViewScoped
@ManagedBean
public class CardapioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private DAO<Pizza> pizzaDao = new DAO<Pizza>(Pizza.class);
	private DAO<Bebida> bebidaDao = new DAO<Bebida>(Bebida.class);
	
	/* lista de pizzas separadas por tipos */
	private List<Pizza> listaPizzaSalgado = new ArrayList<Pizza>();
	private List<Pizza> listaPizzaDoce = new ArrayList<Pizza>();
	
	/* listas de bebidas */
	private List<Bebida> listaBebida = new ArrayList<Bebida>();

	// manipula dados do cardapio de pizzas do tipo sagado
	private Pizza pizzaEscolhaSalgado = new Pizza();
	private CardapioAtributos atributosSalgado = new CardapioAtributos();
	
	// manipula dados do cardapio de pizzas doce
	private Pizza pizzaEscolhaDoce = new Pizza();
	private CardapioAtributos atributosDoce = new CardapioAtributos();
	
	// manipula dados do cardapio de bebidas
	private Bebida bebidaEscolha = new Bebida();
	private CardapioAtributos atributosBebida = new CardapioAtributos();

	/*------------------------------------------------*/
	
	public List<Bebida> getListaBebida() {
		return listaBebida;
	}
	
	public Bebida getBebidaEscolha() {
		return bebidaEscolha;
	}

	public void setBebidaEscolha(Bebida bebidaEscolha) {
		this.bebidaEscolha = bebidaEscolha;
	}

	public CardapioAtributos getAtributosBebida() {
		return atributosBebida;
	}

	public void setAtributosBebida(CardapioAtributos atributosBebida) {
		this.atributosBebida = atributosBebida;
	}

	public void setListaBebida(List<Bebida> listaBebida) {
		this.listaBebida = listaBebida;
	}

	public Pizza getpizzaEscolhaSalgado() {
		return pizzaEscolhaSalgado;
	}

	public void setpizzaEscolhaSalgado(Pizza pizzaEscolhaSalgado) {
		this.pizzaEscolhaSalgado = pizzaEscolhaSalgado;
	}

	public Pizza getPizzaEscolhaDoce() {
		return pizzaEscolhaDoce;
	}
	
	public void setPizzaEscolhaDoce(Pizza pizzaEscolhaDoce) {
		this.pizzaEscolhaDoce = pizzaEscolhaDoce;
	}
	
	public CardapioAtributos getAtributosDoce() {
		return atributosDoce;
	}
	
	public void setAtributosDoce(CardapioAtributos atributosDoce) {
		this.atributosDoce = atributosDoce;
	}
	
	public List<Pizza> getListaPizzaSalgado() {
		return listaPizzaSalgado;
	}
	
	public CardapioAtributos getAtributosSalgado() {
		return atributosSalgado;
	}
	
	public void setAtributosSalgado(CardapioAtributos atributosSalgado) {
		this.atributosSalgado = atributosSalgado;
	}
	
	public List<Pizza> getListaPizzaDoce() {
		return listaPizzaDoce;
	}
	
	public void setListaPizzaDoce(List<Pizza> listaPizzaDoce) {
		this.listaPizzaDoce = listaPizzaDoce;
	}
	
	/*------------------------------------------------*/
	
	@PostConstruct
	public void init() {
		Tipo tipo = new Tipo();
		
		// busca o tipo de id = 4(salgado)
		tipo = new DAO<Tipo>(Tipo.class).buscarPorId(4);
		this.listaPizzaSalgado = new PizzaDao().listarPorTipo(tipo);
		
		// busca o tipo de id = 12(doce)
		tipo = new DAO<Tipo>(Tipo.class).buscarPorId(12);
		this.listaPizzaDoce = new PizzaDao().listarPorTipo(tipo);
		
		// busca lista de bebidas
		this.listaBebida = new DAO<Bebida>(Bebida.class).listarTodos();
	}
	
	/* -------------- metodos bebida ----------- */
	
	public int getQuantidadePizza(Pizza pizza) {
		if (pizza.getTipo().getCaracteristica().equalsIgnoreCase("salgado")) {
			
			if (atributosSalgado.getQuantidade() <= 0) {
				atributosSalgado.setQuantidade(1);
			} else if (atributosSalgado.getQuantidade() > 10) {
				atributosSalgado.setQuantidade(10);
			}
			somaSubtotalPizza(pizza);
			return atributosSalgado.getQuantidade();
			
		} else if (pizza.getTipo().getCaracteristica().equalsIgnoreCase("doce")) {
			if (atributosDoce.getQuantidade() <= 0) {
				atributosDoce.setQuantidade(1);
			} else if (atributosDoce.getQuantidade() > 10) {
				atributosDoce.setQuantidade(10);
			}
			somaSubtotalPizza(pizza);
			return atributosDoce.getQuantidade();
		}
		return 1;
	}
	
	public double getSubtotal(Pizza pizza) {
		if (pizza.getTipo().getCaracteristica().equalsIgnoreCase("salgado")) {
			somaSubtotalPizza(pizza);			
			return atributosSalgado.getSubtotal();
		} else if (pizza.getTipo().getCaracteristica().equalsIgnoreCase("doce")) {
			somaSubtotalPizza(pizza);
			return atributosDoce.getSubtotal();
		}
		return 0;
	}
	
	
	/* define uma escolha de pizza para uma representação temporaria sendo ele salgado ou doce */
	public void selecionaPizza(int idPizza) {
		Pizza pizzaSelecionada = pizzaDao.buscarPorId(idPizza);
		if (pizzaSelecionada.getTipo().getCaracteristica().equalsIgnoreCase("salgado")) {
			this.pizzaEscolhaSalgado = pizzaSelecionada;
			this.atributosSalgado.setQuantidade(1);
			this.atributosSalgado.setAvaliacao(0);
			somaSubtotalPizza(this.pizzaEscolhaSalgado);
		} else if (pizzaSelecionada.getTipo().getCaracteristica().equalsIgnoreCase("doce")) {
			this.pizzaEscolhaDoce = pizzaSelecionada;
			this.atributosDoce.setQuantidade(1);
			this.atributosDoce.setAvaliacao(0);
			somaSubtotalPizza(this.pizzaEscolhaDoce);
		}
 	}
	
	public void somaSubtotalPizza(Pizza pizza) {
		if (pizza.getTipo().getCaracteristica().equalsIgnoreCase("salgado")) {
			double valorPizza = (double) pizza.getPreco();
			this.atributosSalgado.setSubtotal(this.atributosSalgado.getQuantidade() * valorPizza);			
		} 
		else if (pizza.getTipo().getCaracteristica().equalsIgnoreCase("doce")) { 
			double valorPizza = (double) pizza.getPreco();
			this.atributosDoce.setSubtotal(this.atributosDoce.getQuantidade() * valorPizza);			
		}
	}
	
	public void removePizzaSelecionada() {
		this.pizzaEscolhaSalgado = null;
	}
	
	/* -------------- metodos bebida ----------- */
	
	/* define uma escolha de bebida para uma representação temporaria */
	public void selecionaBebida(int idBebida) {
		Bebida bebidaSelecionada = bebidaDao.buscarPorId(idBebida);
		this.bebidaEscolha = bebidaSelecionada;
		this.atributosBebida.setQuantidade(1);
		this.atributosBebida.setAvaliacao(0);
		somaSubtotalBebida(this.bebidaEscolha);
 	}
	
	public void somaSubtotalBebida(Bebida bebida) {
		double valorBebida = (double) bebida.getPreco();
		this.atributosBebida.setSubtotal(this.atributosBebida.getQuantidade() * valorBebida);			
	}
	
	public int getQuantidadeBebida(Bebida bebida) {
		if (atributosBebida.getQuantidade() <= 0) {
			atributosBebida.setQuantidade(1);
		} else if (atributosBebida.getQuantidade() > 10) {
			atributosBebida.setQuantidade(10);
		}
		somaSubtotalBebida(bebida);
		return atributosBebida.getQuantidade();
	}
	
	public double getSubtotalBebida(Bebida bebida) {
		somaSubtotalBebida(bebida);			
		return atributosBebida.getSubtotal();
	}
	
}

package br.com.pizzaria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.pizzaria.dao.DAO;
import br.com.pizzaria.modelo.Bebida;
import br.com.pizzaria.modelo.Carrinho;
import br.com.pizzaria.modelo.Pizza;
import br.com.pizzaria.modelo.Produto;
import br.com.pizzaria.modelo.Usuario;

@ManagedBean
@SessionScoped
public class CarrinhoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 */
	private Carrinho carrinho = new Carrinho();
	private int itens;
	private double valorTotal;

	/*----------------------*/

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}
	
	public int getItens() {
		return itens;
	}
	
	public void setItens(int itens) {
		this.itens = itens;
	}
	
	public double getValorTotal() {
		calculaValorTotal();
		return valorTotal;
	}
	
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	/*----------------------*/
	
	public void adicionaPizza(int idPizza, int quantidade) {
		Pizza pizza = new DAO<Pizza>(Pizza.class).buscarPorId(idPizza);
		
		System.out.println("nome:" + pizza.getNome() + " / quantidade: " + quantidade + 
				" / preco: " + pizza.getPreco());	
		
		boolean existe = produtoJaExiste(pizza.getNome());
		
		if (existe) {
			alteraQuatidade(pizza.getNome(), quantidade);
		} else {
			Produto produto = new Produto(pizza.getNome(), quantidade, pizza.getPreco(), pizza.getImagem());
			this.carrinho.adicionaProduto(produto);
		}
		
		for (Produto item : this.carrinho.getProdutos()) {
			System.out.println(item.getNome() + " | " + item.getPreco() + " | " + item.getQuantidade());
		}
		
		this.itens = this.carrinho.getProdutos().size();
		System.out.println("itens: " + this.itens);
		calculaValorTotal();
	}
	
	public void adicionaBebida(int idbebida, int quantidade) {
		Bebida bebida = new DAO<Bebida>(Bebida.class).buscarPorId(idbebida);
		
		System.out.println("nome:" + bebida.getNome() + " / quantidade: " + quantidade + 
				" / preco: " + bebida.getPreco());	
		
		boolean existe = produtoJaExiste(bebida.getNome());
		
		if (existe) {
			alteraQuatidade(bebida.getNome(), quantidade);
		} else {
			Produto produto = new Produto(bebida.getNome(), quantidade, bebida.getPreco(), bebida.getImagem());
			this.carrinho.adicionaProduto(produto);
		}
		
		for (Produto item : this.carrinho.getProdutos()) {
			System.out.println(item.getNome() + " | " + item.getPreco() + " | " + item.getQuantidade());
		}
		
		this.itens = this.carrinho.getProdutos().size();
		System.out.println("itens: " + this.itens);
		calculaValorTotal();
	}
	
	/* verifica se o produto já existe dentro do carrinho retornando true or false */
	private boolean produtoJaExiste(String nome) {
		boolean contem = false;
		for (Produto p : this.carrinho.getProdutos()) {
			if (p.getNome().equals(nome)) {
				contem = true;
				break;
			}
		}
		calculaValorTotal();
		return contem;
	}
	
	/* objetivo: alterar a quantidade de um produto dentro do objeto carrinho
	 * regra de negocio: a quantidade do produto não pode utrapassar o valor de 10 */
	public void alteraQuatidade(String nome, int quantidade) {
		for (Produto produto : this.getCarrinho().getProdutos()) {
			if (produto.getNome().equals(nome)) {
				produto.setQuantidade(quantidade);
			}
		}
		calculaValorTotal();
	}
	
	/* objetivo: retornar a index do produto contida na lista do objeto carrinho*/
	private int indexDoProduto(String nome) {
		int index = -1;
		System.out.println("Remover item: " + nome);
		for (Produto produto : this.getCarrinho().getProdutos()) {
			if (produto.getNome().equals(nome)) {
				index = this.getCarrinho().getProdutos().lastIndexOf(produto);
				System.out.println(index);
				break;
			}
		}
		return index;
	}
	
	/* objetivo: remover o produto da lista de produtos contida no objeto carrinho */
	public String removeProduto(String nome) {
		int index = indexDoProduto(nome);
		if(index != -1) {
			this.carrinho.getProdutos().remove(index);			
		} else {
			System.err.println("Erro: Não foi possivel localizar a index do produto na lista do carrinho!");
		}
		this.itens = this.carrinho.getProdutos().size();
		calculaValorTotal();
		return "carrinho?faces-redirect=true";
	}
	
	/* objetivo: calcular o valor total de todos os produtos vezes sua quantidade, 
	 * produtos esses contidos no objeto carrinho */
	private void calculaValorTotal() {
		double valorTotal = 0;
		for (Produto produto : this.carrinho.getProdutos()) {
			valorTotal += produto.getPreco() * produto.getQuantidade();
		}
		setValorTotal(valorTotal);
		System.out.println(valorTotal);
	}
	
	/* calcula o subtotal do produto */
	public double calculaSubtotal(double preco, int quantidade) {
		return preco * quantidade;
	}
	
	
	/* fechar pedido caso o usuario estaja logado */
	public String fecharPedido() {
		System.out.println("chamou fecharPedido");
		boolean logado = estaLogado();
		if (logado) {
			System.out.println("Usuario logado");
			this.carrinho = null;
			return "telaPrincipal?faces-redirect=true";
		} else {
			System.out.println("Usuario Não está logado");
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Cadastre-se para finalizar sua compra", "Ou faça seu login" ));
			return "cadastro?faces-redirect=true";
		}
	}
	
	/* verifica se há usuario de sessão */
	private boolean estaLogado() {
		System.out.println("chamou estaLogado()");
		try {
			Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().get("usuarioPizzaPlanet");
			
			if (usuarioLogado != null) {
				return true;
			} else {
				return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println(e);
			return false;
		}
	}
	
}




















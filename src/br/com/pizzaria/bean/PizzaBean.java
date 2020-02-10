package br.com.pizzaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.xml.bind.DatatypeConverter;

import org.primefaces.event.FileUploadEvent;

import br.com.pizzaria.dao.DAO;
import br.com.pizzaria.dao.TipoDao;
import br.com.pizzaria.modelo.Categoria;
import br.com.pizzaria.modelo.Pizza;
import br.com.pizzaria.modelo.Tipo;

@ViewScoped
@ManagedBean
public class PizzaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Pizza pizza = new Pizza();
	private Integer idTipo;
	
	private List<Pizza> listaPizza = new ArrayList<Pizza>();
	private List<Tipo> listaTipo = new ArrayList<Tipo>();
	
	private DAO<Categoria> categoriaDao = new DAO<Categoria>(Categoria.class);
	private DAO<Pizza> pizzaDao = new DAO<Pizza>(Pizza.class);
	
	private TipoDao tipoDao = new TipoDao();
	
	/*------------------------------------------------*/
	
	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}
	
	public Integer getIdTipo() {
		return idTipo;
	}
	
	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
		DAO<Tipo> dao = new DAO<Tipo>(Tipo.class);
		Tipo t = dao.buscarPorId(this.idTipo);
		this.pizza.setTipo(t);
	}
	
	public void setIdCategoria(Integer idCategoria) {
		Categoria categoria = categoriaDao.buscarPorId(idCategoria);
		this.pizza.setCategoria(categoria);
	}
	
	public List<Pizza> getListaPizza() {
		return listaPizza;
	}
	
	public void setListaPizza(List<Pizza> listaPizza) {
		this.listaPizza = listaPizza;
	}
	
	public List<Tipo> getListaTipo() {
		return listaTipo;
	}
	
	public void setListaTipo(List<Tipo> listaTipo) {
		this.listaTipo = listaTipo;
	}
	
	/*------------------------------------------------*/
	@PostConstruct
	public void init() {
		this.setIdCategoria(1);
		this.listaPizza = pizzaDao.listarTodos();
		this.listaTipo = tipoDao.buscarPorCategoria(1);
	}
	
	public void removerPizza(int idPizza) {
		Pizza pizza = pizzaDao.buscarPorId(idPizza);
		try {
			pizzaDao.remover(pizza);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null , new FacesMessage("Pizza " + pizza.getNome() + " foi removido com sucesso!"));
			this.listaPizza = pizzaDao.listarTodos();
		} catch(Exception e) {
			System.err.println("Erro tratado: " + e);
		}
	}
	
	public void editarPizza(int idPizza) {
		this.pizza = pizzaDao.buscarPorId(idPizza);
		this.setIdTipo(this.pizza.getTipo().getId());
	}
	
	public String gravar() {
		if (this.pizza.getCategoria() != null) {
			FacesContext context = FacesContext.getCurrentInstance();
			// para a messagem se manter em memoria por 2 requisições
			context.getExternalContext().getFlash().setKeepMessages(true);

			// verifica se grava ou autualiza a pizza
			if (this.pizza.getId() == null) {
				pizzaDao.gravar(this.pizza);			
				context.addMessage(null , new FacesMessage("Pizza cadastrada com sucesso!"));
			} else {
				pizzaDao.atualizar(this.pizza);
				context.addMessage(null , new FacesMessage("Pizza atualizada com sucesso!"));
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("msgCategoria messages", new FacesMessage("Escolha uma categoria"));
		}
		return "cadPizza?faces-redirect=true";
	}
	
    public void upload(FileUploadEvent image) {
    	System.out.println(image.getFile().getContentType());
    	String imagemBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContents());
    	this.pizza.setImagem(imagemBase64);
    }
    
}




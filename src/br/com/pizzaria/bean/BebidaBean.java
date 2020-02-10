package br.com.pizzaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.DatatypeConverter;

import org.primefaces.event.FileUploadEvent;

import br.com.pizzaria.dao.DAO;
import br.com.pizzaria.dao.TipoDao;
import br.com.pizzaria.modelo.Bebida;
import br.com.pizzaria.modelo.Categoria;
import br.com.pizzaria.modelo.Marca;
import br.com.pizzaria.modelo.Tipo;

@ViewScoped
@ManagedBean
public class BebidaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Bebida bebida = new Bebida();
	
	private int tipoId;
	private int marcaId;
	
	private List<Tipo> tipoLista = new ArrayList<Tipo>();
	private List<Bebida> bebidaLista = new ArrayList<Bebida>();
	private List<Marca> marcaLista = new ArrayList<Marca>();
	
	/*----------------------------------*/

	public Bebida getBebida() {
		return bebida;
	}
	
	public void setBebida(Bebida bebida) {
		this.bebida = bebida;
	}

	public int getTipoId() {
		return tipoId;
	}

	public void setTipoId(int tipoId) {
		this.tipoId = tipoId;
		Tipo t = new DAO<Tipo>(Tipo.class).buscarPorId(this.tipoId);
		this.bebida.setTipo(t);
	}

	public int getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(int marcaId) {
		this.marcaId = marcaId;
		Marca m = new DAO<Marca>(Marca.class).buscarPorId(this.marcaId);
		this.bebida.setMarca(m);
	}
	
	public List<Tipo> getTipoLista() {
		return tipoLista;
	}
	
	public void setTipoLista(List<Tipo> tipoLista) {
		this.tipoLista = tipoLista;
	}
	
	public List<Bebida> getBebidaLista() {
		return bebidaLista;
	}
	
	public void setBebidaLista(List<Bebida> bebidaLista) {
		this.bebidaLista = bebidaLista;
	}
	
	public List<Marca> getMarcaLista() {
		return marcaLista;
	}
	
	public void setMarcaLista(List<Marca> marcaLista) {
		this.marcaLista = marcaLista;
	}

	/*----------------------------------*/
	
	@PostConstruct
	public void init() {
		this.tipoLista = new TipoDao().buscarPorCategoria(2);
		
		this.bebidaLista = new DAO<Bebida>(Bebida.class).listarTodos();
		
		this.marcaLista = new DAO<Marca>(Marca.class).listarTodos();
		
		Categoria c = new DAO<Categoria>(Categoria.class).buscarPorId(2);
		this.bebida.setCategoria(c);
	}
	
	public String gravar() {
		if (this.bebida.getCategoria() != null && this.bebida.getTipo() != null) {
			DAO<Bebida> dao = new DAO<Bebida>(Bebida.class);
			
			if (this.bebida.getId() != null) {
				dao.atualizar(this.bebida);
			} else {
				dao.gravar(this.bebida);				
			}
		}
		return "cadBebida";
	}
	
	public void remover(int idBebida) {
		DAO<Bebida> dao = new DAO<Bebida>(Bebida.class);
		Bebida bebida = dao.buscarPorId(idBebida);
		dao.remover(bebida);
		
		this.bebidaLista = new DAO<Bebida>(Bebida.class).listarTodos();
	}
	
	public void editar(int idBebida) {
		DAO<Bebida> dao = new DAO<Bebida>(Bebida.class);
		Bebida bebidaEdita = dao.buscarPorId(idBebida);
		
		this.bebida = bebidaEdita;
		try {
			this.tipoId = this.bebida.getTipo().getId();			
		} catch(NullPointerException e) {
			System.err.println("Não pode ser referenciado o tipo a bebida");
		}
		
		try {
			this.marcaId = this.bebida.getMarca().getId();			
		} catch(NullPointerException e) {
			System.err.println("Não pode ser referenciado o tipo a bebida");
		}
	}
	
	public void upload(FileUploadEvent image) {
    	System.out.println(image.getFile().getContentType());
    	String imagemBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContents());
    	this.bebida.setImagem(imagemBase64);
    }
	
}


















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
import br.com.pizzaria.modelo.Marca;

@ViewScoped
@ManagedBean
public class MarcaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Marca marca = new Marca();
	
	private List<Marca> listaMarca = new ArrayList<Marca>();
		
	/*------------------------------*/
	public Marca getMarca() {
		return marca;
	}
	
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	public List<Marca> getListaMarca() {
		return listaMarca;
	}
	
	public void setListaMarca(List<Marca> listaMarca) {
		this.listaMarca = listaMarca;
	}
	
	/*----------------------------------*/
	
	@PostConstruct
	private void init() {
		this.listaMarca = new DAO<Marca>(Marca.class).listarTodos();
	}
	
	public String gravar() {
		DAO<Marca> dao = new DAO<Marca>(Marca.class);
		
		if (this.marca.getId() != null) {
			dao.atualizar(this.marca);
		} else {
			dao.gravar(this.marca);
		}
		
		return "cadMarca?faces-redirect=true";
	}
	
	public void editar(int idMarca) {
		DAO<Marca> dao = new DAO<Marca>(Marca.class);
		this.marca = dao.buscarPorId(idMarca);
	}
	
	public void remover (int idMarca) {
		DAO<Marca> dao = new DAO<Marca>(Marca.class);
		Marca m = dao.buscarPorId(idMarca);
		dao.remover(m);
		
		this.init();
	}
	
	public void upload(FileUploadEvent image) {
    	System.out.println(image.getFile().getContentType());
    	String imagemBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContents());
    	this.marca.setImagem(imagemBase64);
    }
	
}

















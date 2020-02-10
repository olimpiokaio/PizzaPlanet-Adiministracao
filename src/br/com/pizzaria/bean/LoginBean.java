package br.com.pizzaria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.pizzaria.dao.UsuarioDao;
import br.com.pizzaria.modelo.Usuario;

@ViewScoped
@ManagedBean
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Usuario usuairo = new Usuario();
	
	private UsuarioDao dao = new UsuarioDao();

	/* ------------------- */
	
	public Usuario getUsuairo() {
		return usuairo;
	}

	public void setUsuairo(Usuario usuairo) {
		this.usuairo = usuairo;
	}
	
	/* ------------------- */

	public String efetuaLogin() {
		boolean existe = dao.existe(this.usuairo);
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (existe) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuairo);
			return "home?faces-redirect-true";
		}
		
		// para a messagem ficar em memoria por duas requisições e ser ixibida na tela
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado!"));
		
		return "login?faces-redirect=true";
	}
	
	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "login?faces-redirect=true";
	}
	
}

















package tpAnual_dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;




@Entity
@Table(name = "Usuario")


public class Usuario {
	
	@Id
	@GeneratedValue
	private long usuario_id;
	@Column
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipo;
	@Column
	private String nombreUsuario;

	@Column
	private String contrasenia;
	@OneToOne
	private Terminal terminal;
	
	// Si es una terminal
	public Usuario(String nombreUsuario, String contrasenia, Terminal terminal) {
		this.tipo = TipoUsuario.terminal;
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
		this.terminal = terminal;
	}

	// Si es un admin
	public Usuario(String nombreUsuario, String contrasenia) {
		this.tipo = TipoUsuario.admin;
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
	}
	
	public Usuario(){
		
	}


	public long getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(long usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
	
}

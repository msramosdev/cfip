package open.digytal.model.acesso;

public class Usuario {
	protected String login;
	protected String nome;
	protected String email;
	protected String senha;
	public Usuario() {
		
	}
	public Usuario(String login, String nome, String email,String senha) {
		super();
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Usuario [login=" + login + ", nome=" + nome + ", email=" + email + ", senha=" + senha + "]";
	}
	
}
package calendall.com.br.calendallpro.dtoOUT;

public class LoginOUT {

	private String login;
	private String senha;

	public String getLogin() {
		return login;
	}
	
	public String getSenha() {
		return senha;
	}

	public LoginOUT(String login, String senha) {
		super();
		this.login = login;
		this.senha = senha;
	}
}

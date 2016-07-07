package calendall.com.br.calendallpro.dtoOUT;

public class LoginOUT {

	private String email;
	private String senha;

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public LoginOUT(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}
}

package calendall.com.br.calendallpro.dtoOUT;

public class AlterarSenhaOUT {

	private Long id;
	private String senha;

	public Long getId() {
		return id;
	}
	
	public String getSenha() {
		return senha;
	}

	public AlterarSenhaOUT(Long id, String senha) {
		super();
		this.id = id;
		this.senha = senha;
	}
}

package calendall.com.br.calendallpro.dtoIN;

public class ErroIN {

	private String campo;
	private String mensagem;

	public String getCampo() {
		return campo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public ErroIN() {
		super();
	}

	public ErroIN(String campo, String mensagem) {
		super();
		this.campo = campo;
		this.mensagem = mensagem;
	}

}

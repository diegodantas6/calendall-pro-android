package calendall.com.br.calendallpro.dtoOUT;

public class CadastroUsuarioEnderecoOUT {

	private Long id;
	private String cpf;
	private String celular;
	private String cep;
	private Integer numero;
	private String complemento;
	private String tipoAtendimento;

	public Long getId() {
		return id;
	}

	public String getCpf() {
		return cpf;
	}

	public String getCelular() {
		return celular;
	}

	public String getCep() {
		return cep;
	}

	public Integer getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getTipoAtendimento() {
		return tipoAtendimento;
	}

    public CadastroUsuarioEnderecoOUT(Long id, String cpf, String celular, String cep, Integer numero, String complemento, String tipoAtendimento) {
        this.id = id;
        this.cpf = cpf;
        this.celular = celular;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
        this.tipoAtendimento = tipoAtendimento;
    }
}

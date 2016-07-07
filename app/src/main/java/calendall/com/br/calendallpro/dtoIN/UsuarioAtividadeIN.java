package calendall.com.br.calendallpro.dtoIN;

public class UsuarioAtividadeIN {

	private Long id;
	private String nome;
	private Double preco;
	private Integer tempo;
	
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}
}

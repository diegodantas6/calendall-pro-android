package calendall.com.br.calendallpro.dtoOUT;

public class DadoAtividadeOUT {

	private Long id;
	private Integer tempo;
	private Double preco;

	public Long getId() {
		return id;
	}

	public Integer getTempo() {
		return tempo;
	}

	public Double getPreco() {
		return preco;
	}

	public DadoAtividadeOUT(Long id, Integer tempo, Double preco) {
		this.id = id;
		this.tempo = tempo;
		this.preco = preco;
	}
}

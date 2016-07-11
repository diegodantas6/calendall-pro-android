package calendall.com.br.calendallpro.dtoOUT;

public class CadastroFormaPagamentoOUT {

	private Long id;
	private String tipoPagamento;

	public Long getId() {
		return id;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public CadastroFormaPagamentoOUT(Long id, String tipoPagamento) {
		this.id = id;
		this.tipoPagamento = tipoPagamento;
	}
}

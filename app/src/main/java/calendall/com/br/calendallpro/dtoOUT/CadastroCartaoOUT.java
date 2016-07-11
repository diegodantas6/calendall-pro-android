package calendall.com.br.calendallpro.dtoOUT;

public class CadastroCartaoOUT {

	private Long id;
	private String numero;
	private String nome;
	private String cvv;
	private int mes;
	private int ano;

    public CadastroCartaoOUT(Long id, String numero, String nome, String cvv, int mes, int ano) {
        this.id = id;
        this.numero = numero;
        this.nome = nome;
        this.cvv = cvv;
        this.mes = mes;
        this.ano = ano;
    }
}

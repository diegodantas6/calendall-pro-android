package calendall.com.br.calendallpro.dtoOUT;

public class CadastroUsuarioOUT {

    private String nome;
    private String email;
    private String senha;
    private String tipo;

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipo() {
        return tipo;
    }

    public CadastroUsuarioOUT(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = "P";
    }
}

package calendall.com.br.calendallpro.dtoOUT;

public class AtividadeOUT {

    private Long id;
    private String nome;
    private boolean checked;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

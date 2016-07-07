package calendall.com.br.calendallpro.dtoOUT;

import java.util.List;

public class UsuarioAtividadeOUT {

    private Long usuario;
    private List<Long> atividades;

    public UsuarioAtividadeOUT(Long usuario, List<Long> atividades) {
        super();
        this.usuario = usuario;
        this.atividades = atividades;
    }

}

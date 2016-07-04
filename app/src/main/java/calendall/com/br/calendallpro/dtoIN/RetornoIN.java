package calendall.com.br.calendallpro.dtoIN;

import java.util.List;

public class RetornoIN {

	private boolean ok;
	private List<ErroIN> erros;

	public boolean isOk() {
		return ok;
	}

	public List<ErroIN> getErros() {
		return erros;
	}


}

package calendall.com.br.calendallpro.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import calendall.com.br.calendallpro.BuildConfig;
import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.dtoIN.RetornoIN;
import calendall.com.br.calendallpro.dtoOUT.CadastroFormaPagamentoOUT;
import calendall.com.br.calendallpro.dtoOUT.CadastroUsuarioEnderecoOUT;
import calendall.com.br.calendallpro.service.CallService;
import calendall.com.br.calendallpro.service.CallServiceInterface;
import calendall.com.br.calendallpro.service.ServiceName;
import calendall.com.br.calendallpro.util.SharedUtil;

public class RegisterBoletoCartaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_boleto_cartao);
    }

    public void onBoleto(View v) {
        regitrarTipoPagamento("B");
    }

    public void onCartao(View v) {
        regitrarTipoPagamento("C");
    }

    private void regitrarTipoPagamento(final String tipoPagamento) {
        final Gson gson = new Gson();
        CallService callService = new CallService(this, new CallServiceInterface() {
            @Override
            public void postCallService(String string) {

                if (BuildConfig.DEBUG)
                    Log.i("RET", string);

                RetornoIN in = gson.fromJson(string, RetornoIN.class);

                if (in.isOk()) {

                    Intent intent;
                    if (tipoPagamento.equals("C")) {
                        intent = new Intent(RegisterBoletoCartaoActivity.this, RegisterCartaoActivity.class);
                    } else {
                        intent = new Intent(RegisterBoletoCartaoActivity.this, AtividadeListActivity.class);
                    }
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterBoletoCartaoActivity.this, "Não foi possivel cadastrar!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SharedUtil sharedUtil = new SharedUtil(this);
        Long id = Long.valueOf(sharedUtil.getPreferences(SharedUtil.KEY_ID));

        CadastroFormaPagamentoOUT out = new CadastroFormaPagamentoOUT(id, tipoPagamento);
        callService.execute(ServiceName.CADASTRO_FORMA_PAGAMENTO, gson.toJson(out));
    }
}

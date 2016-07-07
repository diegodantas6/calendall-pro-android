package calendall.com.br.calendallpro.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.dtoOUT.AtividadeOUT;

public class AtividadeAdapter extends BaseAdapter {

    Context context;
    AtividadeOUT[] atividades;

    public AtividadeAdapter(Context context, AtividadeOUT[] atividades) {
        this.context = context;
        this.atividades = atividades;
    }

    @Override
    public int getCount() {
        return atividades.length;
    }

    @Override
    public AtividadeOUT getItem(int position) {
        return atividades[position];
    }

    @Override
    public long getItemId(int position) {
        return atividades[position].getId();
    }

    private class ViewHolder {
        CheckBox atividade;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_atividade_list_item, null);

            holder.atividade = (CheckBox) convertView.findViewById(R.id.atividade);

            holder.atividade.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AtividadeOUT atividadeOUT = getItem(position);
                    boolean checked = !atividadeOUT.isChecked();
                    atividadeOUT.setChecked(checked);
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AtividadeOUT atividade = atividades[position];

        holder.atividade.setText(atividade.getNome());

        return convertView;
    }

}

package calendall.com.br.calendallpro.list;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import calendall.com.br.calendallpro.R;
import calendall.com.br.calendallpro.dtoIN.UsuarioAtividadeIN;
import calendall.com.br.calendallpro.dtoOUT.AtividadeOUT;

public class AtividadeUsuarioAdapter extends BaseAdapter {

    Context context;
    UsuarioAtividadeIN[] atividades;

    /* ensure that this constant is greater than the maximum list size */
    private static final int DEFAULT_ID_VALUE = -1;
    /* used to keep the note edit text row id within the list */
    private int mNoteId = DEFAULT_ID_VALUE;

    public AtividadeUsuarioAdapter(Context context, UsuarioAtividadeIN[] atividades) {
        this.context = context;
        this.atividades = atividades;
    }

    @Override
    public int getCount() {
        return atividades.length;
    }

    @Override
    public UsuarioAtividadeIN getItem(int position) {
        return atividades[position];
    }

    @Override
    public long getItemId(int position) {
        return atividades[position].getId();
    }

    private class ViewHolder {
        TextView nome;
        TextView valor;
        SeekBar tempo;
        TextView txtTempo;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.activity_atividade_usuario_list_item, null);

            holder.nome = (TextView) convertView.findViewById(R.id.nome);
            holder.valor = (TextView) convertView.findViewById(R.id.valor);
            holder.txtTempo = (TextView) convertView.findViewById(R.id.txtTempo);
            holder.tempo = (SeekBar) convertView.findViewById(R.id.tempo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UsuarioAtividadeIN atividade = atividades[position];

        holder.nome.setText(atividade.getNome());
        holder.valor.setText( String.valueOf( atividade.getPreco() ));
        holder.txtTempo.setText(String.valueOf(atividade.getTempo()));
        holder.tempo.setProgress(atividade.getTempo());
        holder.tempo.setMax(120);
        holder.tempo.incrementProgressBy(15);


        /* if the last id is set, the edit text from this list item was pressed */
        if (mNoteId == position) {

            /* make the edit text recive focus */
            holder.valor.requestFocusFromTouch();
            /* make the edit text's cursor to appear at the end of the text */
            //holder.valor.setSelection(holder.valor.getText().length());

            /* reset the last id to default value */
            mNoteId = DEFAULT_ID_VALUE;
        }

        holder.valor.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    /* get the index of the touched list item */
                    mNoteId = position;
                }
                return false;
            }
        });

        holder.valor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    EditText editText = (EditText) v;
                    String valor = editText.getText().toString();

                    UsuarioAtividadeIN atividadeIN = getItem(position);
                    atividadeIN.setPreco(Double.valueOf(valor));
                }
            }
        });

        holder.tempo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                progress = progress / 15;
                progress = progress * 15;

                UsuarioAtividadeIN atividadeIN = getItem(position);
                atividadeIN.setTempo(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                progress = progress / 15;
                progress = progress * 15;

                holder.txtTempo.setText(String.valueOf(progress));
            }
        });

        return convertView;
    }

}

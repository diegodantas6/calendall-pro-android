package calendall.com.br.calendallpro.mask;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextCARTAO extends EditText {

    //1234 1234 1234 1234
    private boolean isUpdating;
    private int positioning[] = {0,1,2,3,4,6,7,8,9,11,12,13,14,16,17,18,19};

    public EditTextCARTAO(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public EditTextCARTAO(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public EditTextCARTAO(Context context) {
        super(context);
    }

    public String getCleanText() {
        String text = EditTextCARTAO.this.getText().toString();
        text.replaceAll("[^0-9]*", "");
        return text;
    }

    private void initialize() {

        final int maxNumberLength = 16;
        this.setKeyListener(keylistenerNumber);

        this.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String current = s.toString();

                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String number = current.replaceAll("[^0-9]*", "");
                if (number.length() > maxNumberLength)
                    number = number.substring(0, maxNumberLength);
                int length = number.length();

                if (length > 0) {
                    String paddedNumber = padNumber(number, maxNumberLength);

                    String part1 = paddedNumber.substring(0, 4);
                    String part2 = paddedNumber.substring(4, 8);
                    String part3 = paddedNumber.substring(8, 12);
                    String part4 = paddedNumber.substring(12, 16);

                    String cep = String.format("%s %s %s %s", part1, part2, part3, part4);

                    isUpdating = true;
                    EditTextCARTAO.this.setText(cep);

                    EditTextCARTAO.this.setSelection(positioning[length]);
                } else {
                    isUpdating = true;
                    EditTextCARTAO.this.setText("");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    protected String padNumber(String number, int maxLength) {
        String padded = new String(number);
        for (int i = 0; i < maxLength - number.length(); i++)
            padded += " ";
        return padded;
    }

    private final KeylistenerNumber keylistenerNumber = new KeylistenerNumber();

    private class KeylistenerNumber extends NumberKeyListener {

        public int getInputType() {
            return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
        }

        @Override
        protected char[] getAcceptedChars() {
            return new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        }
    }
}

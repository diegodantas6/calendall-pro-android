package calendall.com.br.calendallpro.mask;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextCEP extends EditText {

    //29120-010
    private boolean isUpdating;
    private int positioning[] = {0, 1, 2, 3, 4, 5, 7, 8, 9};

    public EditTextCEP(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();

    }

    public EditTextCEP(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();

    }

    public EditTextCEP(Context context) {
        super(context);
    }

    public String getCleanText() {
        String text = EditTextCEP.this.getText().toString();
        text.replaceAll("[^0-9]*", "");
        return text;

    }

    private void initialize() {

        final int maxNumberLength = 8;
        this.setKeyListener(keylistenerNumber);

        this.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String current = s.toString();

/*
* Ok, here is the trick... calling setText below will recurse
* to this function, so we set a flag that we are actually
* updating the text, so we don't need to reprocess it...
*/
                if (isUpdating) {
                    isUpdating = false;
                    return;

                }

/* Strip any non numeric digit from the String... */
                String number = current.replaceAll("[^0-9]*", "");
                if (number.length() > maxNumberLength)
                    number = number.substring(0, maxNumberLength);
                int length = number.length();

/* Pad the number to 10 characters... */
                String paddedNumber = padNumber(number, maxNumberLength);

/* Split phone number into parts... */
                String part1 = paddedNumber.substring(0, 5);
                String part2 = paddedNumber.substring(5, 8);

/* build the masked phone number... */
                String cpf = part1 + "-" + part2;

/*
* Set the update flag, so the recurring call to
* afterTextChanged won't do nothing...
*/
                isUpdating = true;
                EditTextCEP.this.setText(cpf);

                EditTextCEP.this.setSelection(positioning[length]);

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

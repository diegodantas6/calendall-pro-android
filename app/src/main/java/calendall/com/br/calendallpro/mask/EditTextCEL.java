package calendall.com.br.calendallpro.mask;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextCEL extends EditText {

    // (27)99728-1174
    private boolean isUpdating;
    private int positioning[] = {0,2,3,5,6,7,8,9,11,12,13,14};

    public EditTextCEL(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public EditTextCEL(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public EditTextCEL(Context context) {
        super(context);
    }

    public String getCleanText() {
        String text = EditTextCEL.this.getText().toString();
        text.replaceAll("[^0-9]*", "");
        return text;
    }

    private void initialize() {

        final int maxNumberLength = 11;
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

                    String part1 = paddedNumber.substring(0, 2);
                    String part2 = paddedNumber.substring(2, 7);
                    String part3 = paddedNumber.substring(7, 11);

                    String cel = String.format("(%s)%s-%s", part1, part2, part3);

                    isUpdating = true;
                    EditTextCEL.this.setText(cel);

                    EditTextCEL.this.setSelection(positioning[length]);
                } else {
                    isUpdating = true;
                    EditTextCEL.this.setText("");
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

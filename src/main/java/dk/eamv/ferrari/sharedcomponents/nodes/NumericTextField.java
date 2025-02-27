package dk.eamv.ferrari.sharedcomponents.nodes;

import javafx.scene.control.TextField;

// Made by Benjamin and Christian
public class NumericTextField extends TextField {

    private boolean allowDecimals;
    private int maxLength;

    /**
     * Creates a new NumericTextField
     */
    public NumericTextField() {
        maxLength = -1;
        allowDecimals = true;
    }

    /**
     * Creates a new NumericTextField
     * @param allowDecimals whether to allow decimals
     * @param maxLength the max amount of digits in the textfield
     */
    public NumericTextField(boolean allowDecimals, int maxLength) {
        this.allowDecimals = allowDecimals;
        this.maxLength = maxLength;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        // Save the previous text for restoration if invalid input
        var prev = getText();
        super.replaceText(start, end, text.trim());

        // Validate the text, reset the text and caret if not numeric
        if (!validate(getText())) {
            var position = getCaretPosition();
            setText(prev);
            positionCaret(position);
        }
    }

    // Validate if the text is numeric
    private boolean validate(String text) {
        if (text.isEmpty()) {
            return true;
        }

        if (!text.matches("[0-9]+(\\.[0-9]*)?")) {
            return false;
        }

        return (maxLength == -1) || (maxLength > 0 && text.length() < maxLength);
    }
}

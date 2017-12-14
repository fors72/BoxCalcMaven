package ua.com.eliteupakovka;

import javafx.scene.control.TextField;

public class TextValidation {
    public static void validateDouble(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("")) {
                if (!newValue.equals("-")) {
                    try {
                        Double.valueOf(newValue.replace(",","."));
                        if (newValue.endsWith("f") || newValue.endsWith("d") || newValue.endsWith(" ")) {
                            textField.setText(newValue.substring(0, newValue.length() - 1));
                        }
                    } catch (Exception e) {
                        textField.setText(oldValue);
                    }
                }
            }
        });
    }

    public static void validateInteger(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("")) {
                try {
                    Integer.valueOf(newValue);
                }catch (Exception e){
                    textField.setText(oldValue);
                }
            }
        });
    }
}

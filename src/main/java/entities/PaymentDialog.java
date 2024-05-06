package entities;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PaymentDialog extends Dialog<String> {

    private TextField cardNumberField;
    private TextField expiryDateField;
    private TextField cvvField;

    public PaymentDialog() {
        this.setTitle("Enter Payment Information");

        // Create the layout for the dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create text fields for card number, expiry date, and CVV
        cardNumberField = new TextField();
        cardNumberField.setPromptText("Card Number");

        expiryDateField = new TextField();
        expiryDateField.setPromptText("Expiry Date (MM/YY)");

        cvvField = new TextField();
        cvvField.setPromptText("CVV");

        // Add text fields to the layout
        grid.add(cardNumberField, 0, 0);
        grid.add(expiryDateField, 0, 1);
        grid.add(cvvField, 0, 2);

        // Set the dialog content to the layout
        this.getDialogPane().setContent(grid);

        // Add buttons for OK and Cancel
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Convert the result to a string when OK is clicked
        this.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return String.format("Card Number: %s, Expiry Date: %s, CVV: %s",
                        cardNumberField.getText(), expiryDateField.getText(), cvvField.getText());
            }
            return null;
        });
    }
}

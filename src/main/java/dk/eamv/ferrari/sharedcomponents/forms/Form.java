package dk.eamv.ferrari.sharedcomponents.forms;

import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.scenes.employee.EmployeeController;
import dk.eamv.ferrari.sharedcomponents.email.EmailService;
import dk.eamv.ferrari.sharedcomponents.nodes.AutoCompleteComboBox;
import dk.eamv.ferrari.sharedcomponents.nodes.NumericTextField;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

// Made by: Christian
// Modified by: Benjamin (simplifications, fieldMap, misc...)

/**
 * A Form is a wrapperclass for a GridPane. It allows the user to enter input into various Control objects.
 */
public class Form {

    private Button forwardBoss;
    private GridPane gridPane;
    private HashMap<String, Control> fieldMap;
    private int column;
    private int row;

    private Form() {
        gridPane = createGridPane();
        fieldMap = new HashMap<String, Control>();
        forwardBoss = new Button("Videresend til chefen");
        setForwardToBossListener();
        column = 0;
        row = 0;
    }

    private static GridPane createGridPane() {
        var gridPane = new GridPane();
        gridPane.setVgap(25);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    protected Button getForwardBoss() {
        return forwardBoss;
    }

    protected GridPane getGridPane() {
        return gridPane;
    }

    protected HashMap<String, Control> getFieldMap() {
        return fieldMap;
    }

    /**
     * Sets a mouselistener that on click sends a confirmation email to the boss.
     * The button is displayed if the sellers loan limits are reached.
     */
    private void setForwardToBossListener() {
        forwardBoss.setOnMouseClicked(e -> EmailService.sendEmail());
        forwardBoss.setVisible(false);
    }

    /**
     * Loops over the hashmap and casts each element into their concrete Nodes. It then checks if there is input in the node.
     * If there is no input in the node, the nodes style is so to red, so that the end user knows that theres missing input in that specific Control.
     * @return - true if all fields have input inside, else false.
     */
    protected boolean verifyHasFilledFields() {
        var redStyle =
            """
                -fx-prompt-text-fill: F50000;
                -fx-background-color: #f7adb1;
                -fx-border-color: F50000;
            """;

        var allFieldsFilled = true;
        for (var widget : fieldMap.values()) {
            var filledField = true;
            if (widget instanceof TextField) {
                filledField = !((TextField) widget).getText().isEmpty();
            } else if (widget instanceof ComboBox) {
                filledField = !((AutoCompleteComboBox<?>) widget).isEmpty();
            } else if (widget instanceof DatePicker) {
                filledField = !(((DatePicker) widget).getValue() == null);
            }

            if (!filledField) {
                widget.setStyle(redStyle);
                allFieldsFilled = false;
            } else {
                widget.setStyle(null);
            }
        }
        return allFieldsFilled;
    }

    private void setColumn(int value) {
        column = value;
    }

    private void setRow(int value) {
        row = value;
    }

    private int getColumn() {
        return column;
    }

    private int getRow() {
        return row;
    }

    /**
     * Utilizes the builder pattern, allowing creation of custom forms.
     * It is a nested/subclass of Form. It is static, so that it cant be instantiated on its own.
     */
    protected static class Builder {

        private Form form;

        protected Builder() {
            form = new Form();
        }

        /**
         * Adds a control to the Hashmap & gridpane. Keeps track of which column & row to insert into, with the Forms instance variables.
         * @param labelText - the String/Header to be added above the field, for the end user to see in the GUI.
         * @param control - the Control to be added.
         * @return - itself allowing method chaining.
         */
        private Builder addFieldToForm(String labelText, Control control) {
            var row = form.getRow();
            var column = form.getColumn();

            var vBox = new VBox();
            var heading = new Label(labelText);
            vBox.getChildren().addAll(heading, control);
            if (column > 2) { //enforce maximum 3 fields in each row.
                column = 0;
                row++;
            }

            form.getGridPane().add(vBox, column, row);
            form.getFieldMap().put(labelText, control);
            column++;

            form.setColumn(column);
            form.setRow(row);
            return this;
        }

        /**
         * Inserts a plain TextField into the GridPane & HashMap, allowing for String input from the end user.
         * @param input - The Header(s) of the TextField(s). Made with varargs, allowing for 0..M Strings.
         * @return - itself allowing for method chaining.
         */
        private Builder withFieldsString(String... input) {
            for (var i : input) {
                var textField = new TextField();
                textField.setPromptText(i);
                addFieldToForm(i, textField);
            }

            return this;
        }

        /**
         * Inserts a NumericTextField into the, allowing the end user to only input ints, or also floats, if decimal argument is set to true.
         * @param maxLength - The maximum length of the input allowed in the NumericTextField.
         * @param decimals - Decides whether the end user is allowed to add a single "." in the NumericTextField.
         * @param input - The Header(s) of the NumericTextField(s).
         * @return - itself allowing for method chaining.
         * @see dk.eamv.ferrari.sharedcomponents.nodes.NumericTextField
         */
        private Builder withFieldNumbers(int maxLength, boolean decimals, String input) {
            var numberField = new NumericTextField(decimals, maxLength);
            numberField.setPromptText(input);
            addFieldToForm(input, numberField);
            return this;
        }

        /**
         * Inserts a plain TextField into the GridPane & HashMap, which is then disabled, allowing no inputs.
         * Intended to be combined with binding of other Control.
         * @param input - The Header(s) of the TextField(s). Made with varargs, allowing for 0..M Strings.
         * @return - itself allowing for method chaining.
         */
        private Builder withFieldsUneditable(String... input) {
            for (var i : input) {
                var textField = new TextField();
                textField.setDisable(true);
                addFieldToForm(i, textField);
            }

            return this;
        }

        /**
         * Inserts a "searchable dropdown menu" in the GridPane & HashMap.
         * The method is generically typed with <E>, allowing a list of entities to be added to the ComboBox,
         * which then will then display their properties using E.toString().
         * @param <E> - generic typed Entity, intended to be used for Car, Customer, Employee.
         * @param content - the observable list of the Entity.
         * @param input - the String/Header above the dropdown.
         * @return - itself allowing for method chaining.
         * @see dk.eamv.ferrari.sharedcomponents.nodes.AutoCompleteComboBox
         */
        private <E> Builder withDropDownBox(ObservableList<E> content, String input) {
            var dropDown = new AutoCompleteComboBox<E>(content);
            addFieldToForm(input, dropDown);
            return this;
        }

        /**
         * Adds 2 DatePickers to the Form, with the labels "Start dato & Slut dato"
         * @return itself allowing for method chaining.
         */
        private Builder withFieldsDatePicker() {
            var startDatePicker = new DatePicker();
            var endDatePicker = new DatePicker();

            addFieldToForm("Start dato DD/MM/ÅÅÅÅ", startDatePicker);
            addFieldToForm("Slut dato DD/MM/ÅÅÅÅ", endDatePicker);

            return this;
        }

        /**
         * Called when all the fields has been added. Returns the form, breaking the method chain.
         * @return - the built form.
         */
        private Form build() {
            return form;
        }

        /**
         * Builds the customerForm.
         * @return - the customer form.
         */
        protected Form buildCustomerForm() {
            form = new Form.Builder()
                .withFieldsString("Fornavn", "Efternavn")
                .withFieldNumbers(8, false, "Telefonnummer")
                .withFieldsString("Email", "Adresse")
                .withFieldNumbers(10, false, "CPR")
                .build();
            return form;
        }

        /**
         * Builds the car form.
         * @return - the car form.
         */
        protected Form buildCarForm() {
            form = new Form.Builder()
                .withFieldNumbers(4, false, "Årgang")
                .withFieldNumbers(-1, true, "Pris") //-1 = no maxlength constraint
                .withFieldsString("Model")
                .build();
            return form;
        }

        /**
         * Builds the employee form.
         * @return - the employee form.
         */
        protected Form buildEmployeeForm() {
            form = new Form.Builder()
                .withFieldsString("Fornavn", "Efternavn")
                .withFieldNumbers(8, false, "Telefon nr.")
                .withFieldsString("Email", "Kodeord")
                .withFieldNumbers(-1, false, "Udlånsgrænse")
                .build();
            return form;
        }

        /**
         * Builds the loan form.
         * @return - the loan form.
         */
        protected Form buildLoanForm() {
            form = new Form.Builder()
                .withDropDownBox(CarController.getCars(), "Bil")
                .withDropDownBox(CustomerController.getCustomers(), "CPR & Kunde")
                .withDropDownBox(EmployeeController.getEmployees(), "Medarbejder")
                .withFieldsUneditable("Model", "Kundens Fornavn", "Medarbejderens Fornavn")
                .withFieldsUneditable("Årgang", "Kundens Efternavn", "Medarbejderens Efternavn")
                .withFieldsUneditable("Pris", "Kundens CPR", "Medarbejderens ID")
                .withFieldsUneditable("Stelnummer", "Kundens Telefon nr.", "Medarbejderens Telefon nr.")
                .withFieldsUneditable("Kundens Adresse", "Kundens Email", "Medarbejderens Email", "Lånets størrelse")
                .withFieldNumbers(-1, true, "Udbetaling")
                .withFieldsUneditable("Rente")
                .withFieldsDatePicker()
                .build();
            return form;
        }
    }
}

package dk.eamv.ferrari.sharedcomponents.filter.forms;

import java.sql.Date;
import java.util.ArrayList;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarModel;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.customer.CustomerModel;
import dk.eamv.ferrari.scenes.loan.Loan;
import dk.eamv.ferrari.scenes.loan.LoanModel;
import dk.eamv.ferrari.scenes.loan.LoanStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FormFactory {
    /*
     * Lavet af: Christian & Stefan
     */

    private enum Type {
        CAR,
        LOAN,
        CUSTOMER
    }

    private static int[] createFieldsString(GridPane gridPane, ArrayList<TextField> fieldsList, int column, int row, String... input) {
        for (String i : input) {
            VBox vBox = new VBox();
            Label heading = new Label(i);
            TextField textField = new TextField();
            textField.setPromptText(i);
            vBox.getChildren().addAll(heading, textField);
            if (column > 2) {
                column = 0;
                row++;
            }
            gridPane.add(vBox, column, row);
            fieldsList.add(textField);
            column++;
        }
        return new int[] { column, row };
    }

    private static int[] createFieldsInt(GridPane gridPane, ArrayList<TextField> fieldsList, int column, int row, String... input) {
        for (String i : input) {
            VBox vBox = new VBox();
            Label heading = new Label(i);
            NumericTextField textField = new NumericTextField();
            textField.setPromptText(i);
            vBox.getChildren().addAll(heading, textField);
            if (column > 2) {
                column = 0;
                row++;
            }
            gridPane.add(vBox, column, row);
            fieldsList.add(textField);
            column++;
        }

        return new int[] { column, row };
    }
    
    private static GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(25);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);

        return gridPane;
    }

    private static Form createCustomerForm() {
        Form customerForm = new Form(createGridPane(), new ArrayList<TextField>());
        int[] fields = createFieldsString(customerForm.getGridPane(), customerForm.getFieldsList(), 0, 0, "Fornavn", "Efternavn", "Email", "Adresse");
        createFieldsInt(customerForm.getGridPane(), customerForm.getFieldsList(), fields[0], fields[1], "Telefonnummer", "CPR");
        return customerForm;
    }

    private static Form createCarForm() {
        Form carForm = new Form(createGridPane(), new ArrayList<TextField>());
        int[] fields = createFieldsInt(carForm.getGridPane(), carForm.getFieldsList(), 0, 0, "Årgang", "Pris", "Stelnummer");
        createFieldsString(carForm.getGridPane(), carForm.getFieldsList(), fields[0], fields[1], "Model");
        return carForm;
    }
    
    private static Form createLoanForm() {
        Form loanForm = new Form(createGridPane(), new ArrayList<TextField>());
        createFieldsInt(loanForm.getGridPane(), loanForm.getFieldsList(), 0, 0, "Stelnummer", "Kunde CPR",
                "Lånets størrelse", "Udbetaling", "Rente",
                "Start dato", "Forfaldsdag");
        return loanForm;
    }

    private static Dialog wrap(Form form, Type table) {
        Dialog dialog = new Dialog<>();
        
        Label missingInput = new Label("Fejl: manglede input");
        missingInput.setVisible(false);
        missingInput.setPadding(new Insets(0, 0, 0, 100));
        Button buttonOK = new Button("OK");
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnMouseClicked(e -> {
            dialog.setResult(true);
            dialog.close();
        });

        switch (table) {
            //TODO: Loan should be considered a placeholder until MVP is done, then think of a better implementation.
            case LOAN:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.hasFilledFields()) {
                        dialog.setResult(true);
                        //TODO: The ID's should be gotten from a dropdown menu that queries the relative table, instead of random.
                        //TODO: Consider if this should be autoincremented in DB instead, else add a field for manual ID input.
                        //EDIT: THEY NEED TO. Due to foreignkey constraints the INSERT statement will not execute. This is on hold until MVP is done.
                        int carID = (int) Math.random() * 100000;
                        int customerID = (int) Math.random() * 100000;
                        int employeeID = (int) Math.random() * 100000;
                        int loanSize = getInt(form, 2);
                        double downPayment = getDouble(form, 3);
                        double interestRate = getDouble(form, 4);
                        //TODO: Figure out how to select a data in the dialog. Placeholders for now.
                        Date startDate = new Date(2025, 1, 1);
                        Date endDate = new Date(2025, 1, 1);
                        LoanStatus loanStatus = new LoanStatus(3);
                        //TODO: Consider if the above should be put directly into the constructor below
                        Loan loan = new Loan(carID, customerID, employeeID, loanSize, downPayment, interestRate, startDate, endDate, loanStatus);
                        LoanModel.create(loan);
                        dialog.close();
                    } else {
                        missingInput.setVisible(true);
                    }
                });
                break;

            case CUSTOMER:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.hasFilledFields()) {
                        dialog.setResult(true);
                        //TODO: Consider if this should be autoincremented in DB instead, else add a field for manual ID input.
                        String firstName = getString(form, 0);
                        String lastName = getString(form, 1);
                        //TODO: Consider if phonenumber should be an int
                        String phoneNumber = getString(form, 4);
                        String email = getString(form, 2);
                        String address = getString(form, 3);
                        //TODO: Consider if CPR should be an int instead
                        String cpr = getString(form, 5);
                        //TODO: Consider if the above should be put directly into the constructor below
                        Customer customer = new Customer(firstName, lastName, phoneNumber, email, address, cpr);
                        CustomerModel.create(customer);
                        dialog.close();
                    } else {
                        missingInput.setVisible(true);
                    }
                });
                break;

            case CAR:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.hasFilledFields()) {
                        dialog.setResult(true);
                        //TODO: Consider if this should be autoincremented in DB instead, else add a field for manual ID input.
                        int frameNumber = getInt(form, 2);
                        String model = getString(form, 3);
                        int year = getInt(form, 0);
                        double price = getDouble(form, 1);
                        //TODO: Consider if the above should be put directly into the constructor below
                        Car car = new Car(frameNumber, model, year, price);
                        CarModel.create(car);
                        dialog.close();
                    } else {
                        missingInput.setVisible(true);
                    }
                });
                break;

            default:
                break;
        }
        
        HBox buttons = new HBox(buttonCancel, buttonOK, missingInput);
        buttons.setSpacing(25);
        VBox vBox = new VBox(form.getGridPane(), buttons);
        vBox.setSpacing(50);
        dialog.getDialogPane().setContent(vBox);
        dialog.setResizable(true);
    
        return dialog;
    }

    
    private static String getString(Form form, int index) {
        return form.getFieldsList().get(index).getText();
    }

    private static int getInt(Form form, int index) {
        return Integer.valueOf(form.getFieldsList().get(index).getText());
    }

    private static double getDouble(Form form, int index) {
        return Double.valueOf(form.getFieldsList().get(index).getText());
    }

    public static void createCustomerFormDialogBox() {
        Dialog<Void> dialog = wrap(createCustomerForm(), Type.CUSTOMER);
        dialog.setTitle("Opret kunde");
        dialog.show();
    }

    public static void createCarFormDialogBox() {
        Dialog<Void> dialog = wrap(createCarForm(), Type.CAR);
        dialog.setTitle("Opret bil");
        dialog.show();
    }

    public static void createLoanFormDialogBox() {
        Dialog<Void> dialog = wrap(createLoanForm(), Type.LOAN);
        dialog.setTitle("Opret lån");
        dialog.show();
    }

    private static void setFieldsRed(Form form) {
        
    }
}

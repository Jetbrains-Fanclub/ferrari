package dk.eamv.ferrari.scenes.loan;

import dk.eamv.ferrari.resources.SVGResources;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import dk.eamv.ferrari.sharedcomponents.forms.FormFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LoanController {

    protected static FilteredTableBuilder<Loan> filteredTableBuilder;
    private static final ObservableList<Loan> loans = FXCollections.observableArrayList(LoanModel.readAll());

    protected static void initFilterBuilder() {
        filteredTableBuilder = new FilteredTableBuilder<Loan>()
                .withData(loans)
                .withColumn("id", Loan::getId)
                .withColumn("Bil", Loan::getCarLabel)
                .withColumn("Kunde", Loan::getCustomerLabel)
                .withColumn("Sælger", Loan::getEmployeeLabel)
                .withColumn("Lån (DKK)", Loan::getLoanSize)
                .withColumn("Udbetaling (DKK)", Loan::getDownPayment)
                .withColumn("Rente (%)", Loan::getInterestRate)
                .withColumn("Start", Loan::getStartDate)
                .withColumn("Slut", Loan::getEndDate)
                .withColumn("Status", loan -> loan.getStatus().getDisplayName())
                .withIconButtonColumn(SVGResources.getChangeStatusIcon(), LoanController::updateLoanStatus)
                .withIconButtonColumn(SVGResources.getEditIcon(), LoanView::showEditLoanDialog)
                .withIconButtonColumn(SVGResources.getDeleteIcon(), LoanController::deleteLoan)
                .withIconButtonColumn(SVGResources.getExportCSVIcon(), LoanController::exportLoan);
    }

    protected static void createLoan() {
        FormFactory.createLoanFormDialogBox();
    }

    protected static void updateLoan(Loan loan) {
        System.out.println("Call method in LoanModel update loan with id: " + loan.getId());

        LoanView.refreshTableView();
    }

    protected static void deleteLoan(Loan loan) {
        LoanModel.delete(loan.getId());

        // When removing the Loan from the ObservableList, the TableView updates automatically
        loans.remove(loan);
    }

    protected static void updateLoanStatus(Loan loan) {
        // Create a ChoiceBox to enable the user to set the status of this loan
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        for (LoanState state : LoanState.values()) {
            choiceBox.getItems().add(new LoanStatus(state).getDisplayName());
        }

        // Initial value of the ChoiceBox is set to the current status of the loan
        choiceBox.setValue(loan.getStatus().getDisplayName());

        // If a different status is selected in the ChoiceBox, it is observed and the Loan is updated
        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldStatus, newStatus) -> {
            if (newStatus != null) {
                for (LoanState state : LoanState.values()) {
                    if (new LoanStatus(state).getDisplayName().equals(newStatus)) {
                        loan.setStatus(new LoanStatus(state));
                        LoanController.updateLoan(loan);
                        LoanView.refreshTableView(); // TableView is refreshed so the new status is shown
                        break;
                    }
                }
            }
        });

        // Use a dialog for the ChoiceBox
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Opdater status");
        dialog.setHeaderText("Vælg ny status for dette lån");
        dialog.getDialogPane().setContent(choiceBox);
        dialog.showAndWait();
    }

    private static void exportLoan(Loan loan) {
        //TODO: Implement export to CSV here to export the selected loan which is passed as the parameter
    }

    public static ObservableList<Loan> getLoans() {
        return loans;
    }
}

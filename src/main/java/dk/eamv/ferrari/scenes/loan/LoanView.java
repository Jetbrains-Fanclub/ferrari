package dk.eamv.ferrari.scenes.loan;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import dk.eamv.ferrari.sharedcomponents.filter.FilterTextField;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTable;
import dk.eamv.ferrari.sharedcomponents.filter.SearchContainer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoanView {

    private static FilteredTable<Loan> tableView;
    private static SearchContainer searchContainer;
    private static Button buttonCreate;

    public static BorderPane getScene() {
        var scene = new BorderPane();

        scene.setLeft(SidebarView.getSidebarView());
        scene.setCenter(getLoanView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.LOANS);

        return scene;
    }

    private static StackPane getLoanView() {
        LoanController.initFilterBuilder();

        initTableView();
        initSearchContainer();
        initButtonCreate();

        var containerAboveTable = new HBox();
        containerAboveTable.setAlignment(Pos.CENTER_LEFT);
        containerAboveTable.setPadding(new Insets(0, 10, 0, 0));
        containerAboveTable.setSpacing(10);
        containerAboveTable.getChildren().addAll(searchContainer, buttonCreate); // Put search box top right of table

        var tableContainer = new VBox();
        tableContainer.setAlignment(Pos.BOTTOM_CENTER);
        tableContainer.setMaxWidth(Double.MAX_VALUE);
        tableContainer.setPadding(new Insets(25));
        tableContainer.setSpacing(25);
        tableContainer.getStyleClass().add("table-view-container");
        tableContainer.getChildren().addAll(containerAboveTable, tableView);

        // Apply drop shadow to parentContainer to avoid applying it to VBox children
        var parentContainer = new StackPane(tableContainer);
        parentContainer.getStyleClass().add("drop-shadow-effect");

        var window = new StackPane(parentContainer);
        window.setPadding(new Insets(50));
        window.setStyle("-fx-background-color: lightgrey");
        return window;
    }

    private static void initTableView() {
        tableView = LoanController.filteredTableBuilder.build();
        tableView.setPrefHeight(1200);
        tableView.setRowFactory(tableview -> {
            var row = new TableRow<Loan>();
            return row;
        });
    }

    private static void initSearchContainer() {
        searchContainer = new SearchContainer(new FilterTextField<>(LoanController.filteredTableBuilder));
    }

    private static void initButtonCreate() {
        buttonCreate = new Button("Opret ny låneaftale");
        buttonCreate.getStyleClass().add("create-button");
        buttonCreate.setOnAction(e -> LoanController.showCreateLoan());
    }

    protected static void showEditLoanDialog(Loan selectedLoan) {
        LoanController.updateLoan(selectedLoan);
    }

    public static void refreshTableView() {
        tableView.refresh();
        tableView.sort();
    }
}

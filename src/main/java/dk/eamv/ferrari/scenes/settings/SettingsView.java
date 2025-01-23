package dk.eamv.ferrari.scenes.settings;

import dk.eamv.ferrari.scenes.employee.EmployeeModel;
import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import dk.eamv.ferrari.sessionmanager.SessionManager;
import dk.eamv.ferrari.sharedcomponents.nodes.NumericTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

// Made by: Benjamin and Stefan
// Checked by:
// Modified by: Mikkel and Stefan
public class SettingsView {

    public static BorderPane getScene() {
        var scene = new BorderPane();
        scene.setLeft(SidebarView.getSidebarView());
        scene.setCenter(getSettingsView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.SETTINGS);
        return scene;
    }

    private static StackPane getSettingsView() {
        var statusLabel = new Label();
        statusLabel.setVisible(false);

        var settingsLabel = new Label("Indstillinger");
        settingsLabel.getStyleClass().add("settings-title");

        var editLabel = new Label("Rediger oplysninger");
        editLabel.getStyleClass().add("settings-overskrift");

        var emailLabel = new Label("email");
        var emailInput = new TextField(SessionManager.getUser().getEmail());

        var telephoneLabel = new Label("Telefonnummer");
        var telephoneInput = new NumericTextField(false, 8);
        telephoneInput.setText(SessionManager.getUser().getPhoneNumber());

        var telephoneContainer = new VBox(-2);
        telephoneContainer.setAlignment(Pos.CENTER);
        telephoneContainer.getChildren().addAll(emailLabel, emailInput, telephoneLabel, telephoneInput);

        var currentPasswordLabel = new Label("Nuværende adgangskode");
        var currentPasswordInput = new PasswordField();

        var newPasswordLabel = new Label("Ny adgangskode");
        var newPasswordInput = new PasswordField();

        var confirmPasswordLabel = new Label("Gentag kode");
        var confirmPasswordInput = new PasswordField();

        var passwordContainer = new VBox(-2);
        passwordContainer.setAlignment(Pos.CENTER);
        passwordContainer
            .getChildren()
            .addAll(
                currentPasswordLabel,
                currentPasswordInput,
                newPasswordLabel,
                newPasswordInput,
                confirmPasswordLabel,
                confirmPasswordInput
            );

        var updateButton = new Button("Opdater oplysninger");
        updateButton.setOnAction(event -> {
            if (!emailInput.getText().equals(SessionManager.getUser().getEmail())) {
                SessionManager.getUser().setEmail(emailInput.getText());
                EmployeeModel.update(SessionManager.getUser());

                statusLabel.setText("Email er blevet ændret");
                statusLabel.setVisible(true);
            }

            if (!telephoneInput.getText().equals(SessionManager.getUser().getPhoneNumber())) {
                SessionManager.getUser().setPhoneNumber(telephoneInput.getText());
                EmployeeModel.update(SessionManager.getUser());

                statusLabel.setText("Telefonnummeret er ændret");
                statusLabel.setVisible(true);
            }

            if (!currentPasswordInput.getText().equals(SessionManager.getUser().getPassword())) {
                statusLabel.setText("Koden er IKKE ok");
                statusLabel.setVisible(true);
                return;
            }

            if (newPasswordInput.getText().equals(confirmPasswordInput.getText())) {
                statusLabel.setText("Koden er nu ændret");
                SessionManager.getUser().setPassword(confirmPasswordInput.getText());
                EmployeeModel.update(SessionManager.getUser());
                statusLabel.setVisible(true);
            } else {
                statusLabel.setText("Skriv den samme kode i 'ny kode' og 'gentag kode'");
                statusLabel.setVisible(true);
            }
        });

        var vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox
            .getChildren()
            .addAll(settingsLabel, editLabel, telephoneContainer, statusLabel, passwordContainer, updateButton);

        vbox.setMaxWidth(Double.MAX_VALUE);
        vbox.setPadding(new Insets(25));
        vbox.setSpacing(15);
        vbox.getStyleClass().addAll("table-view-container", "settings");

        // Apply drop shadow to parentContainer to avoid applying it to VBox children
        var parentContainer = new StackPane(vbox);
        parentContainer.getStyleClass().add("drop-shadow-effect");

        var container = new StackPane(parentContainer);
        container.setPadding(new Insets(50));
        container.setStyle("-fx-background-color: lightgrey");
        return container;
    }
}

package dk.eamv.ferrari.scenes.login;

import dk.eamv.ferrari.resources.SVGResources;
import dk.eamv.ferrari.utils.Align;
import dk.eamv.ferrari.utils.RoundCorners;
import dk.eamv.ferrari.utils.ScreenBounds;
import dk.eamv.ferrari.utils.ToggleVisible;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

// Made by: Christian
// Modified by: Mikkel (CSS og små visuelle ændringer)
public class LoginView implements ToggleVisible {

    private static TextField usernameTextField;
    private static PasswordField passwordPasswordField;
    private static final Button loginButton = new Button("LOGIN");
    private static Label wrongLogin;

    public static AnchorPane getScene() {
        var scene = new AnchorPane(makeBackground());

        var loginBox = makeLoginBox();
        var loginBoxContent = makeLoginBoxContent(loginBox);
        setLoginButton(loginBoxContent);
        var loginHeader = makeLoginHeader();
        wrongLogin = makeWrongLoginLabel();
        showErrorMessage(false);
        var loginMessages = makeLoginMessages(loginHeader, wrongLogin);
        var usernameField = makeUsernameField();
        var passwordField = makePasswordField();
        var fields = new VBox(usernameField, passwordField);
        fields.setPadding(new Insets(0, 0, 0, 50));
        setFields(fields);
        loginBoxContent.getChildren().addAll(loginButton, loginMessages, fields);

        scene.getChildren().addAll(makeLogos(), loginBox, loginBoxContent);
        scene.getStyleClass().add("login");

        return scene;
    }

    private static Rectangle makeBackground() {
        var stops = new Stop[] { new Stop(0, Color.web("5A060D")), new Stop(1, Color.web("#EF1A2D")) };
        var linearGradient = new LinearGradient(
            200,
            200,
            ScreenBounds.getWidth() - 200,
            ScreenBounds.getHeight() - 200,
            false,
            CycleMethod.REFLECT,
            stops
        );
        var background = new Rectangle(ScreenBounds.getWidth(), ScreenBounds.getHeight());
        background.setFill(linearGradient);
        return background;
    }

    private static Rectangle makeLoginBox() {
        var loginBox = new Rectangle(500, ScreenBounds.getHeight() - 350, Color.web("#D9D9D9"));
        Align.screenCenter(loginBox);
        loginBox.setLayoutY(150);
        RoundCorners.round(loginBox, loginBox.getHeight() * 0.05);
        var dropShadow = new DropShadow(25, -1, 1, Color.web("#555555"));
        loginBox.setEffect(dropShadow);
        return loginBox;
    }

    private static AnchorPane makeLoginBoxContent(Rectangle loginBox) {
        var loginBoxContent = new AnchorPane();
        loginBoxContent.setMinWidth(loginBox.getWidth());
        loginBoxContent.setMinHeight(loginBox.getHeight());
        loginBoxContent.setLayoutX(loginBox.getLayoutX());
        loginBoxContent.setLayoutY(loginBoxContent.getLayoutY());
        return loginBoxContent;
    }

    private static void setLoginButton(AnchorPane parent) {
        AnchorPane.setBottomAnchor(loginButton, 10.0);
        AnchorPane.setLeftAnchor(loginButton, 50.0);
        loginButton.setOnAction(e -> LoginController.authenticate());
    }

    private static Label makeLoginHeader() {
        var loginHeader = new Label("LOGIN");
        loginHeader.getStyleClass().add("login-header");
        return loginHeader;
    }

    private static VBox makeLoginMessages(Label login, Label error) {
        var loginMessages = new VBox(login, error);
        loginMessages.setSpacing(25);
        loginMessages.setLayoutY(175);
        return loginMessages;
    }

    private static VBox makeUsernameField() {
        var username = new VBox(10);
        username.setPadding(new Insets(225, 0, 0, 0));
        var usernameLabel = new Label("Brugernavn");
        var usernameField = new HBox();
        var usernameIcon = new SVGPath();
        usernameIcon.setContent(SVGResources.getUsernameIcon());
        usernameTextField = new TextField();
        usernameTextField.setFocusTraversable(false);
        usernameTextField.setPromptText("Indtast brugernavn");
        usernameField.getChildren().addAll(usernameIcon, usernameTextField);
        var usernameLine = new Line();
        usernameLine.setEndX(400);
        username.getChildren().addAll(usernameLabel, usernameField, usernameLine);
        return username;
    }

    private static VBox makePasswordField() {
        var password = new VBox(10);
        var passwordLabel = new Label("Password");
        var passwordField = new HBox();
        var passwordIcon = new SVGPath();
        passwordIcon.setContent(SVGResources.getPasswordIcon());
        passwordPasswordField = new PasswordField();
        passwordPasswordField.setFocusTraversable(false);
        passwordPasswordField.setPromptText("Indtast password");
        passwordField.getChildren().addAll(passwordIcon, passwordPasswordField);
        var passwordLine = new Line();
        passwordLine.setEndX(400);
        var forgottenPassword = new Label("Glemt kodeord?");
        forgottenPassword.getStyleClass().add("login-forgotten-password");
        password.getChildren().addAll(passwordLabel, passwordField, passwordLine, forgottenPassword);
        return password;
    }

    private static void setFields(VBox fields) {
        fields.setSpacing(50);
        fields.setLayoutY(150);
    }

    private static VBox makeLogos() {
        var logos = new VBox(40);
        logos.getChildren().addAll(getFullLine(), getNotFullLine(), getFullLine(), getNotFullLine());
        return logos;
    }

    private static HBox getFullLine() {
        var fullLine = new HBox(190);
        for (var i = 0; i < 6; i += 1) {
            fullLine.getChildren().add(getLogo());
        }

        return fullLine;
    }

    private static HBox getNotFullLine() {
        var notFullLine = new HBox(190);
        notFullLine.setPadding(new Insets(0, 190, 0, 190));
        for (var i = 0; i < 5; i += 1) {
            notFullLine.getChildren().add(getLogo());
        }

        return notFullLine;
    }

    private static SVGPath getLogo() {
        var logo = new SVGPath();
        logo.setContent(SVGResources.getLogoHorse());
        logo.getStyleClass().add("login-logo-horse");
        return logo;
    }

    private static Label makeWrongLoginLabel() {
        var wrongLogin = new Label("Login ikke fundet");
        wrongLogin.getStyleClass().add("login-error");
        return wrongLogin;
    }

    public static void showErrorMessage(boolean makeVisible) {
        wrongLogin.setVisible(makeVisible);
    }

    public static String getUsernameInput() {
        return usernameTextField.getText();
    }

    public static String getPasswordInput() {
        return passwordPasswordField.getText();
    }

    public static Button getLoginButton() {
        return loginButton;
    }
}

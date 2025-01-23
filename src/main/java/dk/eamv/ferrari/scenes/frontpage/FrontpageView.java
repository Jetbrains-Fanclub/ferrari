package dk.eamv.ferrari.scenes.frontpage;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

// Made by: Benjamin and Stefan
public class FrontpageView {

    public static BorderPane getScene() {
        var scene = new BorderPane();
        scene.setLeft(SidebarView.getSidebarView());
        scene.setCenter(getFrontPageView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.DASHBOARD);
        return scene;
    }

    private static BorderPane getFrontPageView() {
        var bPane = new BorderPane();
        var imageView = new ImageView(new Image("file:src/main/resources/media/ferrari-logo.png"));

        imageView.setFitHeight(500);
        imageView.setFitWidth(300);
        bPane.setCenter(imageView);

        var wvbox = new VBox();
        var welcome = new Label("Velkommen til Ferrari Herning");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 34));

        wvbox.getChildren().add(welcome);
        wvbox.setAlignment(Pos.CENTER);
        bPane.setTop(wvbox);

        return bPane;
    }
}

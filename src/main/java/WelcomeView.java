import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class WelcomeView {
    private Controller controller;
    private BorderPane borderPane;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Gets the display of the current view
     */
    public VBox doWelcomeView() {
        return this.initView();
    }

    private VBox initView() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(1200);
        vBox.setPrefHeight(600);

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-padding: 0 0 0 0");
        borderPane.setPrefWidth(1200);
        borderPane.setPrefHeight(600);
        
        ImageView icon_design = new ImageView("file:src/main/resources/sakura/design.png");
        icon_design.setFitHeight(32);
        icon_design.setFitWidth(32);

        Button startButton = new Button();
        startButton.setGraphic(icon_design);
        startButton.setText("start design");
        startButton.setOnMouseClicked((event -> {
            controller.showStartDesignView();
        }));
        Label label = new Label("Welcome");
        label.setWrapText(true);
        label.setStyle("-fx-padding: 0 0 30 0; -fx-font-size: 30");

        VBox centerVBox = new VBox();
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(label, startButton);
        borderPane.setCenter(centerVBox);
        
        
        ImageView icon_rating = new ImageView("file:src/main/resources/sakura/rating.png");
        icon_rating.setFitHeight(32);
        icon_rating.setFitWidth(32);

        Button rateUsButton = new Button();
        rateUsButton.setGraphic(icon_rating);
        rateUsButton.setText("rate us");
        rateUsButton.setOnMouseClicked((event -> {
            controller.showRateUsView();
        }));
        VBox rightVBox = new VBox();
        rightVBox.setStyle("-fx-padding: 0 100 100 0");
        rightVBox.setAlignment(Pos.BOTTOM_RIGHT);
        rightVBox.getChildren().addAll(rateUsButton);
        borderPane.setRight(rightVBox);
        
        ImageView icon_library = new ImageView("file:src/main/resources/sakura/library.png");
        icon_library.setFitHeight(32);
        icon_library.setFitWidth(32);

        Button libraryButton = new Button();
        libraryButton.setGraphic(icon_library);
        libraryButton.setText("library");
        libraryButton.setOnMouseClicked((event -> {
            controller.showNativeView();
        }));
        VBox leftVBox = new VBox();
        leftVBox.setStyle("-fx-padding: 0 0 100 100");
        leftVBox.setAlignment(Pos.BOTTOM_LEFT);
        leftVBox.getChildren().addAll(libraryButton);
        borderPane.setLeft(leftVBox);

        vBox.getChildren().add(borderPane);
        return vBox;
    }
}

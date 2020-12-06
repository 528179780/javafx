import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;

public class RateUsView {
    private Controller controller;
    private BorderPane borderPane;
    private Label message_text;

    ImageView icon_star_0;
    ImageView icon_star_1;
    ImageView icon_star_2;

    ImageView icon_star_false_0;
    ImageView icon_star_false_1;
    ImageView icon_star_false_2;

    boolean icon_star_0_status = false;
    boolean icon_star_1_status = false;
    boolean icon_star_2_status = false;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Gets the display of the current view
     */
    public VBox doRateUsView() {
        return this.initView();
    }

    public void setup_text(){

    }

    private VBox initView() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(1200);
        vBox.setPrefHeight(600);

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-padding: 0 0 0 0");
        borderPane.setPrefWidth(1200);
        borderPane.setPrefHeight(600);

        Label label = new Label("Rate US");
        label.setWrapText(true);
        label.setStyle("-fx-padding: 0 0 30 0; -fx-font-size: 30");

        message_text = new Label("Bad");
        message_text.setWrapText(true);
        message_text.setStyle("-fx-padding: 0 0 30 0; -fx-font-size: 30");

        HBox star_box = new HBox();
        star_box.setAlignment(Pos.CENTER);


        VBox centerVBox = new VBox();
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(label);
        centerVBox.getChildren().addAll(star_box);
        borderPane.setCenter(centerVBox);
        vBox.getChildren().add(borderPane);
        centerVBox.getChildren().add(message_text);

        icon_star_0 = new ImageView("file:src/main/resources/sakura/star.png");
        icon_star_1 = new ImageView("file:src/main/resources/sakura/star.png");
        icon_star_2 = new ImageView("file:src/main/resources/sakura/star.png");

        icon_star_false_0 = new ImageView("file:src/main/resources/sakura/star_empty.png");
        icon_star_false_1 = new ImageView("file:src/main/resources/sakura/star_empty.png");
        icon_star_false_2 = new ImageView("file:src/main/resources/sakura/star_empty.png");


        icon_star_0.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            icon_star_0_status = !icon_star_0_status;
            if(!icon_star_0_status){
                handle_rating(star_box, 0);
            }
            else{
                handle_rating(star_box, 1);
            }
            setup_text();
        });

        icon_star_1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            icon_star_0_status = !icon_star_1_status;
            if(!icon_star_0_status){
                handle_rating(star_box, 0);
            }
            else{
                handle_rating(star_box, 2);
            }
            setup_text();
        });

        icon_star_2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            icon_star_0_status = !icon_star_2_status;
            if(!icon_star_0_status){
                handle_rating(star_box, 0);
            }
            else{
                handle_rating(star_box, 3);
            }
            setup_text();
        });

        icon_star_false_0.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            icon_star_0_status = !icon_star_0_status;
            if(!icon_star_0_status){
                handle_rating(star_box, 0);
            }
            else{
                handle_rating(star_box, 1);
            }
            setup_text();
        });

        icon_star_false_1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            icon_star_0_status = !icon_star_1_status;
            if(!icon_star_0_status){
                handle_rating(star_box, 0);
            }
            else{
                handle_rating(star_box, 2);
            }
            setup_text();

        });

        icon_star_false_2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            icon_star_0_status = !icon_star_2_status;
            if(!icon_star_0_status){
                handle_rating(star_box, 0);
            }
            else{
                handle_rating(star_box, 3);
            }
            setup_text();
        });

        handle_rating(star_box, 0);

        return vBox;
    }

    public void handle_rating(HBox star_box, int rating){

        star_box.getChildren().clear();

        switch (rating){
            case 0: {
                star_box.getChildren().addAll(icon_star_false_0, icon_star_false_1, icon_star_false_2);
                message_text.setText("Bad");
                break;
            }
            case 1: {
                star_box.getChildren().addAll(icon_star_0, icon_star_false_1, icon_star_false_2);
                message_text.setText("Normal");
                break;
            }
            case 2: {
                star_box.getChildren().addAll(icon_star_0, icon_star_1, icon_star_false_2);
                message_text.setText("Good");
                break;
            }
            case 3: {
                star_box.getChildren().addAll(icon_star_0, icon_star_1, icon_star_2);
                message_text.setText("Perfect");
                break;
            }
        }







    }


    public MenuBar doRateUSMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu tableMenu8 = new Menu();
        // Create a new label
        Label tableLabel8 = new Label("Back");
        // Bind the mouse click event to the label
        tableLabel8.setOnMouseClicked(event -> {
            controller.showWelcomeView();
        });
        // Set the label to the graphic attribute of the menu
        tableMenu8.setGraphic(tableLabel8);
        menuBar.getMenus().add(tableMenu8);

        return menuBar;
    }
}


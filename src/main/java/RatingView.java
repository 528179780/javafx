import java.util.HashMap;
import java.util.LinkedList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RatingView {
	private GridPane gridPane;

    /**
     * Gets the display of the current view
     */
    public VBox doRatingView() {
        return this.initView();
    }
	
	private VBox initView() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(1200);
        vBox.setPrefHeight(600);

        HBox hBox = new HBox();
        hBox.setPrefWidth(0);
        hBox.setPrefHeight(0);
        hBox.setStyle("-fx-padding: 70 0 0 0");

        Button button = new Button();
        button.setText("Confirm");
        Label l1 =new Label();
        Label l2 =new Label();
        TextField response  = new TextField("Your suggestion is ....");
        button.setOnMouseClicked(event -> {
        	if(response.getText() == null||response.getText().equals("Your suggestion is ....")) {
        		l1.setText("please give us some suggestions");
        		l2.setText("");
        	}
        	else {
        		l1.setText("Thank you for your suggestion!");
                l2.setText("If you want to go to try other features, then click the button on menu!");
        	}
            
            response.setText(null);
        });
        Image i = new Image("file:src/main/resources/image/Boxelder.png");
        ImageView imageView = new ImageView();
		imageView.setImage(i);
        gridPane = new GridPane();
        gridPane.setStyle("-fx-padding: 70 0 0 0");
        
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(new Label("please provide us with your suggestion, Thank you! "));
        vBox.getChildren().add(response);
        vBox.getChildren().add(button);
        vBox.getChildren().add(l1);
        vBox.getChildren().add(l2);
        
        return vBox;
    } 
}


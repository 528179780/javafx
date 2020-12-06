import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartDesignView {
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Gets the display of the current view
     */
    public VBox doStartDesignView() {
        return this.initView();
    }

    private VBox initView() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(1200);
        vBox.setPrefHeight(600);

        /*
         * Build master GridPane
         */
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Start Design");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label heightLabel = new Label("Height:");
        grid.add(heightLabel, 0, 1);
        TextField heightTextField = new TextField();
        heightTextField.setText("500");
        grid.add(heightTextField, 1, 1);

        Label widthLabel = new Label("Width:");
        grid.add(widthLabel, 0, 2);
        TextField widthTextField = new TextField();
        widthTextField.setText("1000");
        grid.add(widthTextField, 1, 2);

        Button designButton = new Button();
        designButton.setText("Design");
        designButton.setOnMouseClicked((event -> {
            if (!isNumeric(heightTextField.getText()) || !isNumeric(widthTextField.getText())) {
                System.out.println("Please key in numbers.");
                return;
            }
            controller.showGardenView(Integer.parseInt(heightTextField.getText()), Integer.parseInt(widthTextField.getText()));
        }));
        grid.add(designButton, 8, 9);

        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setOnMouseClicked((event -> {
            controller.showWelcomeView();
        }));
        grid.add(backButton, 9, 9);

        vBox.getChildren().add(grid);
        return vBox;
    }

    /**
     * Whether the match is a number
     */
    private static boolean isNumeric(String str) {
        // The regular expression can match all numbers, including negative numbers
        Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;
        }
        // matcher是全匹配
        Matcher isNum = pattern.matcher(bigStr);
        return isNum.matches();
    }
}


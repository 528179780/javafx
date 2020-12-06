import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * View: GrowthTable
 */
public class GrowthTableView {

    /**
     * Canvas, dynamically update data
     */
    private GridPane gridPane;

    /**
     * Gets the display of the current view
     */
    public VBox doGrowthTableView() {
        PlantModel.initData();
        return this.initView();
    }

    /**
     * init the display of the current view
     */
    private VBox initView() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(1200);
        vBox.setPrefHeight(600);

        HBox hBox = new HBox();
        hBox.setPrefWidth(250);
        hBox.setPrefHeight(150);
        hBox.setStyle("-fx-padding: 70 0 0 0");

        MenuButton button = new MenuButton();
        button.setText("Your Choice");

        HashMap<String, LinkedList<PlantModel.PlantInfo>> growthMap = PlantModel.growthMap;
        for (String key : growthMap.keySet()) {
            MenuItem menuItem = new MenuItem();
            menuItem.setOnAction((event -> {
                updateGridPane(key);
                button.setText(key);
            }));
            menuItem.setText(key);
            button.getItems().add(menuItem);
        }

        gridPane = new GridPane();
        gridPane.setStyle("-fx-padding: 0 0 0 40");

        updateGridPane("Boxelder");
        hBox.getChildren().addAll(button, gridPane);
        vBox.getChildren().add(hBox);
        return vBox;
    }

    /**
     * Click the menu to switch the picture on the right dynamically
     */
    private void updateGridPane(String menuName) {
        LinkedList<PlantModel.PlantInfo> list = PlantModel.growthMap.get(menuName);
        for (int i = 0; i < 3; i++) {
            PlantModel.PlantInfo info = list.get(i);
            VBox paneVBox = new VBox();
            paneVBox.setPrefWidth(350);
            paneVBox.setPrefHeight(350);

            ImageView imageView = new ImageView(new Image(info.getImage()));
            imageView.setFitHeight(350);
            imageView.setFitWidth(350);
            Label imageLabel = new Label(info.getName());
            imageLabel.setStyle("-fx-padding: 0 0 0 30");
            paneVBox.getChildren().addAll(imageView, imageLabel);
            gridPane.add(paneVBox, i, 0);
        }
    }
}

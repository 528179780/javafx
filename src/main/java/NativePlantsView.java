import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

/**
 * View: NativePlants
 */
public class NativePlantsView {
    private Tooltip tooltip = new Tooltip();

    /**
     * Gets the display of the current view
     */
    public VBox doNativePlantsView() {
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

        GridPane gridPane = new GridPane();

        HashMap<String, PlantModel.PlantInfo> nativeMap = PlantModel.nativeMap;
        int columnIndex = 0;
        int rowIndex = 0;
        int count = 0;
        for (String key : nativeMap.keySet()) {
            if (count > 9) {
                break;
            }
            PlantModel.PlantInfo info = nativeMap.get(key);

            VBox paneVBox = new VBox();
            paneVBox.setPrefWidth(200);
            paneVBox.setPrefHeight(200);
            if (columnIndex > 5) {
                columnIndex = 0;
                rowIndex++;
            }
            ImageView imageView = new ImageView(new Image(info.getImage()));
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imageView.setId(info.getName());
            Label imageLabel = new Label(info.getName());
            imageLabel.setStyle("-fx-padding: 0 0 0 50");

            imageView.setOnMouseClicked(this::onclick);
            imageView.setOnMouseExited(this::OnMouseExited);

            paneVBox.getChildren().addAll(imageView, imageLabel);
            gridPane.add(paneVBox, columnIndex, rowIndex);
            columnIndex++;
            count++;
        }

        vBox.getChildren().add(gridPane);
        return vBox;
    }

    /**
     * Processing click picture, display prompt information
     */
    private void onclick(MouseEvent event) {
        String eventId = ((ImageView) event.getSource()).getId();
        tooltip.setText(PlantModel.nativeMap.get(eventId).getDescription());
        Node node = (Node) event.getSource();
        tooltip.show(node, event.getScreenX() - event.getX(), event.getScreenY());
    }

    /**
     * Move the mouse out of the picture to hide the prompt information
     */
    private void OnMouseExited(MouseEvent event) {
        tooltip.hide();
    }
}

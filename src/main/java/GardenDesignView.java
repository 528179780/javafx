import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author sufu
 */
public class GardenDesignView {
	private GridPane gridPane;

    /**
     * 初始化页面
     * Gets the display of the current view
     */
    public VBox dogardendesignView() {
        return this.initView();
    }
    private VBox initView() {
    	VBox vBox = new VBox();
        vBox.setPrefWidth(1200);
        vBox.setPrefHeight(600);

        HBox hBox = new HBox();
        hBox.setPrefWidth(250);
        hBox.setPrefHeight(150);
        hBox.setStyle("-fx-padding: 0 0 0 0");
        gridPane = new GridPane();
        gridPane.setStyle("-fx-padding: 0 0 0 0");
        updateGridPane("Boxelder");
        hBox.getChildren().addAll(gridPane);
        vBox.getChildren().add(hBox);
        MenuButton button = new MenuButton();
        button.setText("Your Choice");
		return vBox;
    }
    private void updateGridPane(String key) {
    	VBox paneVBox = new VBox();
        paneVBox.setPrefWidth(1200);
        paneVBox.setPrefHeight(600);
        ImageView imageView = new ImageView("file:src/main/resources/gardenback/garden.png");
        imageView.setFitHeight(600);
        imageView.setFitWidth(1200);
        paneVBox.getChildren().addAll(imageView);
        gridPane.add(paneVBox, 1, 0);
        gridPane.add(new Button("save"), 0, 0);
        
    }
}

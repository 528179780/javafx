import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author sufu
 */
public class Controller extends Application {

    private Stage stage;
    private GrowthTableView growthTableView = new GrowthTableView();
    private NativePlantsView nativePlantsView = new NativePlantsView();
    private PlantstatusView plantstatusView = new PlantstatusView();
    private InsectAttractView insectattractView = new InsectAttractView();
    private RatingView ratingView = new RatingView();
    private GardenDesignView gardendesignView = new GardenDesignView();
    private WelcomeView welcomeView = new WelcomeView();
    private RateUsView rateUsView = new RateUsView();
    private StartDesignView startDesignView = new StartDesignView();  
    private GardenView gardenView = new GardenView();

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        showWelcomeView();
        stage.show();
    }

    /**
     * Show Native main page
     */
    public void showNativeView() {
        VBox vBox = nativePlantsView.doNativePlantsView();
        replaceSceneContent("Native Plants List for Delaware", vBox, this.doMenuBar());
    }

    private void showGardenDesignView() {
        VBox vBox = gardendesignView.dogardendesignView();
        replaceSceneContent("Garden design", vBox, this.doMenuBar());
    }

    public void showWelcomeView() {
        welcomeView.setController(this);
        VBox vBox = welcomeView.doWelcomeView();
        replaceSceneContent("Garden design software", vBox, null);
    }

    public void showStartDesignView() {
        startDesignView.setController(this);
        VBox vBox = startDesignView.doStartDesignView();
        replaceSceneContent("Start Design", vBox, null);
    }

    public void showRateUsView() {
        rateUsView.setController(this);
        VBox vBox = rateUsView.doRateUsView();
        MenuBar menuBar = rateUsView.doRateUSMenuBar();
        replaceSceneContent("Rate US", vBox, menuBar);
    }

    public void showGardenView(int height, int width) {
        gardenView.setController(this);
        VBox vBox = gardenView.doGardenView(height, width);
        MenuBar menuBar = gardenView.doGardenMenuBar();
        replaceSceneContent("Garden design", vBox, menuBar);
    }

    private void showRating() {
        VBox vBox = ratingView.doRatingView();
        replaceSceneContent("rating", vBox, this.doMenuBar());
    }

    private void showinsectattractView() {
        VBox vBox = insectattractView.doInsectView();
        replaceSceneContent("The plants which attract insect", vBox, this.doMenuBar());
    }

    /**
     * Show Growth main page
     */
    private void showGrowthTableView() {
        VBox vBox = growthTableView.doGrowthTableView();
        replaceSceneContent("Growth Table for Native Plants", vBox, this.doMenuBar());
    }

    private void showPlantstatusView() {
        VBox vBox = plantstatusView.doPlantstatusView();
        replaceSceneContent("For Each Season", vBox, this.doMenuBar());
    }

    /**
     * Handling common menu logic
     */
    private MenuBar doMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu plantsMenu = new Menu();
        // Create a new label
        Label plantsLabel = new Label("Native Plants");
        // Bind the mouse click event to the label
        plantsLabel.setOnMouseClicked(event -> {
            showNativeView();
        });
        // Set the label to the graphic attribute of the menu
        plantsMenu.setGraphic(plantsLabel);
        menuBar.getMenus().add(plantsMenu);

        Menu tableMenu = new Menu();
        // Create a new label
        Label tableLabel = new Label("Growth Table");
        // Bind the mouse click event to the label
        tableLabel.setOnMouseClicked(event -> {
            showGrowthTableView();
        });
        // Set the label to the graphic attribute of the menu
        tableMenu.setGraphic(tableLabel);
        menuBar.getMenus().add(tableMenu);
        Menu tableMenu2 = new Menu();
        // Create a new label
        Label tableLabel2 = new Label("Plant's status in different climates");
        // Bind the mouse click event to the label
        tableLabel2.setOnMouseClicked(event -> {
            showPlantstatusView();
        });
        // Set the label to the graphic attribute of the menu
        tableMenu2.setGraphic(tableLabel2);
        menuBar.getMenus().add(tableMenu2);

        Menu tableMenu3 = new Menu();
        // Create a new label
        Label tableLabel3 = new Label("Which attract insects");
        // Bind the mouse click event to the label
        tableLabel3.setOnMouseClicked(event -> {
            showinsectattractView();
        });
        // Set the label to the graphic attribute of the menu
        tableMenu3.setGraphic(tableLabel3);
        menuBar.getMenus().add(tableMenu3);

        Menu tableMenu7 = new Menu();
        // Create a new label
        Label tableLabel7 = new Label("Feedback");
        // Bind the mouse click event to the label
        tableLabel7.setOnMouseClicked(event -> {
            showRating();
        });
        // Set the label to the graphic attribute of the menu
        tableMenu7.setGraphic(tableLabel7);
        menuBar.getMenus().add(tableMenu7);

        Menu tableMenu8 = new Menu();
        // Create a new label
        Label tableLabel8 = new Label("Back");
        // Bind the mouse click event to the label
        tableLabel8.setOnMouseClicked(event -> {
            showWelcomeView();
        });
        // Set the label to the graphic attribute of the menu
        tableMenu8.setGraphic(tableLabel8);
        menuBar.getMenus().add(tableMenu8);

        return menuBar;
    }

    /**
     * Switch to the specified view
     * 选择具体的view展示
     */
    private void replaceSceneContent(String title, VBox vBox, MenuBar menuBar) {
        Node node = vBox.getChildren().remove(0);
        if (menuBar != null) {
            vBox.getChildren().add(menuBar);
        }
        vBox.getChildren().add(node);
        Scene scene = new Scene(vBox);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
    }

    public static void main(String[] arg0) {
        launch(arg0);
    }
}


	


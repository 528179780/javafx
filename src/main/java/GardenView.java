import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class GardenView {
    /**
     * version util
     * i am not sure this way could keep gardenFileModel
     */
    private static GardenFileModel gardenFileModel = new GardenFileModel();
    private String nowVersion = "";
    private Controller controller;
    private BorderPane mainBorderPane;
    /**
     * main canvas
     */
    private static Pane canvas;
    private Menu menuVersion;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Gets the display of the current view
     */
    public VBox doGardenView(int height, int width) {
        return this.initView(height, width);
    }

    /**
     * 根据给定的大小初始化view
     * @author sufu
     * @date 2020/12/5 13:45
     * @param height height
     * @param width width
     * @return javafx.scene.layout.VBox
     **/
    private VBox initView(int height, int width) {
        VBox vBox = new VBox();
        vBox.setPrefWidth(1200);
        vBox.setPrefHeight(600);

        mainBorderPane = new BorderPane();
        mainBorderPane.setStyle("-fx-padding: 0 0 0 0");
        mainBorderPane.setPrefWidth(1200);
        mainBorderPane.setPrefHeight(600);
        // 渲染左边的list项 也就是选择的贴图元素
        renderLeftItems(0);

        canvas = getNewCanvas(height, width);
        mainBorderPane.setCenter(canvas);
        vBox.getChildren().add(mainBorderPane);
        return vBox;
    }

    /**
     * get a new canvas with given height and width,nowVersion will be set ""
     * @author sufu
     * @date 2020/12/5 17:25
     * @param height height
     * @param width width
     * @return new canvas
     **/
    private Pane getNewCanvas(int height, int width) {
        Pane newCanvas = new Pane();
        newCanvas.setMaxHeight(height);
        newCanvas.setMaxWidth(width);
        //yournode.setBackground(new Background(new BackgroundFill(Color.web("#" + enteredByUser), CornerRadii.EMPTY, Insets.EMPTY)));
        //rightPane.setStyle("-fx-background-color: '7EC7FF';");
        newCanvas.setStyle("-fx-background-image: url(\"file:src/main/resources/sakura/background.jpg\"); -fx-background-repeat: no-repeat; -fx-background-size: contain;");
        nowVersion = "";
        return newCanvas;
    }


    /**
     * init a new version
     * @author sufu
     * @date 2020/12/6 9:21
     **/
    private void initVersion() {
        nowVersion = gardenFileModel.getNewVersion();
    }

    public void resizable(Node node){

        node.addEventFilter(ScrollEvent.SCROLL, event -> {

            ImageView iv = (ImageView)((Pane)node).getChildren().get(0);

            double o_w = iv.getFitWidth();
            double o_h = iv.getFitHeight();

            if (event.getDeltaY() > 0) {
                iv.setFitWidth(o_w+10);
                iv.setFitHeight(o_h+10);

            } else {
                iv.setFitWidth(o_w-10);
                iv.setFitHeight(o_h-10);

            }


        });

    }
    /**
     * add image to canvas by given image url
     * @author sufu
     * @date 2020/12/5 17:17
     * @param imageUrl The url of the picture to be added
     **/
    private void onClick(String imageUrl) {
        // TODO 这里实现逻辑只是向canvas中添加一个image，需要实现从左侧选项中直接拖动过去。
        Pane node = getCircleNode(imageUrl);
        setDraggable(node);
        resizable(node);
        canvas.getChildren().add(node);
    }
    /**
     * get a new image to be added into canvas with given Url
     * @author sufu
     * @date 2020/12/5 17:20
     * @param ImageUrl The url of the picture to be added
     * @return javafx.scene.layout.Pane
     **/
    private Pane getCircleNode(String ImageUrl) {
        StackPane node = new StackPane();
        ImageView imageView = new ImageView(new Image(ImageUrl));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        node.getChildren().add(imageView);
        return setNodeDeletable(node);
    }

    private Pane getCircleNode(String ImageUrl, double w, double h) {
        Pane node = new StackPane();
        ImageView imageView = new ImageView();
        Image image = new Image(ImageUrl);
        imageView.setFitWidth(w);
        imageView.setFitHeight(h);
        imageView.setImage(image);
        node.getChildren().addAll(imageView);
        return node;
    }
    /**
     * set node Draggable
     * @author sufu
     * @date 2020/12/5 17:23
     * @param node The node that needs to be set
     **/
    private void setDraggable(Node node) {
        final Position pos = new Position();
        // Prompt the user that the node is clickable
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> node.setCursor(Cursor.HAND));
        node.addEventHandler(MouseEvent.MOUSE_EXITED, event -> node.setCursor(Cursor.DEFAULT));
        // Prompt the user that the node can be dragged
        // 实现可拖动
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            node.setCursor(Cursor.MOVE);

            // When a pressing event occurs, cache the location coordinates where the event occurred
            pos.x = event.getX();
            pos.y = event.getY();
        });
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> node.setCursor(Cursor.DEFAULT));

        // Implement drag and drop function
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            double distanceX = event.getX() - pos.x;
            double distanceY = event.getY() - pos.y;
            double x = node.getLayoutX() + distanceX;
            double y = node.getLayoutY() + distanceY;
            if(((node.getLayoutX() + distanceX)<450)&&((node.getLayoutX() + distanceX)>=0)){
                x = node.getLayoutX() + distanceX;
                }
            else if((node.getLayoutX() + distanceX)<0) {
            	x=0;
            	}
            else {
            	x=450;
                }
            if(((node.getLayoutY() + distanceY)<400)&&((node.getLayoutY() + distanceY)>=0)){
              	y = node.getLayoutY() + distanceY;
                }
            else if((node.getLayoutY() + distanceY)<0) {
               	y=0;
                }
            else {
               	y=400;
               	}
            // After calculating x and y, relocate the node to the specified coordinate point (x, y)
            node.relocate(x, y);
        });
    }
    /**
     * change version with the given key
     * @author sufu
     * @date 2020/12/5 15:05
     * @param key version
     **/
    private void doVersion(String key) {
        nowVersion = key;
        canvas.getChildren().removeAll(canvas.getChildren());

        List<ImageModel> list = gardenFileModel.versionMap.get(key);
        for (ImageModel imageModel : list) {
        	StackPane pane = (StackPane) getCircleNode("file:" + imageModel.imageUrl, imageModel.w, imageModel.h);
        	pane = setNodeDeletable(pane);
            setDraggable(pane);
            resizable(pane);
            pane.relocate(imageModel.x, imageModel.y);
            canvas.getChildren().add(pane);
            }
    }
    /**
     * render version menu items
     * @author sufu
     * @date 2020/12/5 15:10
     **/
    private void doVersionMenu() {
        if (menuVersion.getItems().size() > 0) {
            ObservableList<MenuItem> list = menuVersion.getItems();
            menuVersion.getItems().removeAll(list);
        }

        for (String key : gardenFileModel.getVersions()) {
            MenuItem menu = new MenuItem(key);
            menu.setOnAction(event -> {
                this.doVersion(((MenuItem) event.getTarget()).getText());
            });
            menuVersion.getItems().add(menu);
        }
    }

    /**
     * Switch scene picture, change picture content
     * 0: House
     * 1: Rock
     * 2: Swiming pool
     */
    private void renderLeftItems(int type) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(125);
        scrollPane.setPrefHeight(600);
        /*
         * Load the image content on the left
         */
        VBox vBoxImage = new VBox();
        switch (type) {
            case 0: {
                for (int i = 1; i <= 13; i++) {
                    ImageView imageView = new ImageView(new Image("file:src/main/resources/garden/image" + i + ".png"));
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    imageView.setOnMouseClicked(event -> {
                        onClick(((ImageView) event.getPickResult().getIntersectedNode()).getImage().getUrl());
                    });
                    vBoxImage.getChildren().addAll(imageView);
                }
                break;
            }
            case 1: {
                for (int i = 1; i <= 4; i++) {
                    ImageView imageView = new ImageView(new Image("file:src/main/resources/rock/rock" + i + ".png"));
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    imageView.setOnMouseClicked(event -> {
                        onClick(((ImageView) event.getPickResult().getIntersectedNode()).getImage().getUrl());
                    });
                    vBoxImage.getChildren().addAll(imageView);
                }
                break;
            }
            case 2: {
                for (int i = 1; i <= 3; i++) {
                    ImageView imageView = new ImageView(new Image("file:src/main/resources/swimming_pool/swimming_pool" + i + ".png"));
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    imageView.setOnMouseClicked(event -> {
                        onClick(((ImageView) event.getPickResult().getIntersectedNode()).getImage().getUrl());
                    });
                    vBoxImage.getChildren().addAll(imageView);
                }
                break;
            }
            case 3: {
                for (int i = 1; i <= 4; i++) {
                    ImageView imageView = new ImageView(new Image("file:src/main/resources/house/house" + i + ".png"));
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    imageView.setOnMouseClicked(event -> {
                        onClick(((ImageView) event.getPickResult().getIntersectedNode()).getImage().getUrl());
                    });
                    vBoxImage.getChildren().addAll(imageView);
                }
                break;
            }
        }
        scrollPane.setContent(vBoxImage);
        mainBorderPane.setLeft(scrollPane);
    }

    /**
     * init top menu
     * @author sufu
     * @date 2020/12/5 16:21
     * @return javafx.scene.control.MenuBar
     **/
    public MenuBar doGardenMenuBar() {
        MenuBar menuBar = new MenuBar();


        Menu fileMenu = new Menu("File");
        MenuItem newVersionItem = new MenuItem("new version");
        newVersionItem.setOnAction(e->{
            nowVersion = "";
        });
        MenuItem saveItem = new MenuItem("save");
        MenuItem saveNewVersionItem = new MenuItem("save as new version");
        saveNewVersionItem.setOnAction(event -> saveVersion());
        fileMenu.getItems().addAll(newVersionItem,saveItem,saveNewVersionItem);

        //---------------------------------------------------------------------
        Menu menuSave = new Menu("save");
        MenuItem saveToThisVersion = new MenuItem("save");
        saveToThisVersion.setOnAction(e->saveVersion());
        menuSave.getItems().add(saveToThisVersion);
        if(nowVersion.length()>0){
            // save to new version
            MenuItem saveAsANewVersion = new MenuItem("保存到新版本");
            saveAsANewVersion.setOnAction(e->saveVersion());
            menuSave.getItems().add(saveAsANewVersion);
        }
        //---------------------------------------------------------------------



        Menu menu1 = new Menu("List");
        MenuItem plantMenu = new MenuItem("Plant List");
        plantMenu.setOnAction(event -> {
            this.renderLeftItems(0);
        });

        MenuItem houseMenu = new MenuItem("House");
        houseMenu.setOnAction(event -> {
            this.renderLeftItems(3);
        });

        MenuItem rockMenu = new MenuItem("Rock");
        rockMenu.setOnAction(event -> {
            this.renderLeftItems(1);
        });

        MenuItem SwimingMenu = new MenuItem("Swiming pool");
        SwimingMenu.setOnAction(event -> {
            this.renderLeftItems(2);
        });
        menu1.getItems().addAll(plantMenu, houseMenu, rockMenu, SwimingMenu);
        menuVersion = new Menu("Version");
        this.doVersionMenu();


        Menu deleteMenu = new Menu();
        Label deleteLabel = new Label("Delete");
        deleteLabel.setOnMouseClicked(event -> {
            gardenFileModel.doDelete(nowVersion);
            this.doVersionMenu();
            initVersion();
            canvas.getChildren().removeAll(canvas.getChildren());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Delete Success");
            alert.show();
        });
        deleteMenu.setGraphic(deleteLabel);


        Menu backMenu = new Menu();
        Label backLabel = new Label("back");
        backLabel.setOnMouseClicked(event -> {
            controller.showWelcomeView();
        });
        backMenu.setGraphic(backLabel);

        menuBar.getMenus().addAll(fileMenu, menu1, menuVersion, menuSave, deleteMenu, backMenu);
        return menuBar;
    }
    /**
     * get List<ImageModel> from canvas, this will be saved as a version
     * @author sufu
     * @date 2020/12/6 13:42
     * @return java.util.List<ImageModel>
     **/
    private List<ImageModel> getImageModelListFromPane(){
        List<ImageModel> list = new ArrayList<>();
        for (Node node : canvas.getChildren()) {
            StackPane stackPane = (StackPane) node;
            ImageView imageView = (ImageView) stackPane.getChildren().get(0);
            list.add(
                    new ImageModel(
                            imageView.getImage().getUrl().replace("file:", ""),
                            stackPane.getLayoutX(), stackPane.getLayoutY(),
                            ((ImageView) stackPane.getChildren().get(0)).getFitWidth(), ((ImageView) stackPane.getChildren().get(0)).getFitHeight()));
        }
        return list;
    }
    /**
     * save version and update versionMenu
     * @author sufu
     * @date 2020/12/5 15:46
     **/
    private void saveVersion() {
        // TODO version save with current version
        gardenFileModel.doSave(nowVersion,getImageModelListFromPane());
        // 修改version 菜单项
        doVersionMenu();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String s="";
        alert.setHeaderText("Save Success"+" "+s);
        alert.show();
    }
    // TODO 保存到版本，在这里写两个方法 复用doSave方法
    /**
     * save to new version
     * @author sufu
     * @date 2020/12/6 11:57
     * @param
     * @return
     **/
    private void saveNewVersion(){
        String newVersion = gardenFileModel.getNewVersion();
    }

    /**
     * set image Deletable
     * @author sufu
     * @date 2020/12/5 14:42
     * @param stackPane image
     * @return javafx.scene.layout.StackPane
     **/
    public StackPane setNodeDeletable(StackPane stackPane){
        // 设置右键点击弹出删除菜单项
        MenuItem menuItem = new MenuItem("删除");
        menuItem.setOnAction(e->{
            MenuItem deleteMenuItem = (MenuItem) e.getSource();
            ContextMenu contextMenu = deleteMenuItem.getParentPopup();
            StackPane source = (StackPane) contextMenu.getOwnerNode();
            // 从画布中删除 stackPane
            // TODO 修改切换版本之后不能右键删除的bug
            Pane pane = (Pane) source.getParent();
            pane.getChildren().remove(source);
        });
        ContextMenu contextMenu = new ContextMenu(menuItem);
        stackPane.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.SECONDARY){
                contextMenu.show(stackPane,event.getScreenX(),event.getScreenY());
            }
        });
        return stackPane;
    }
}

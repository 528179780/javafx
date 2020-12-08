import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import resources.Resource;
import resources.ResourcesManagement;

import java.util.*;
import java.util.stream.Collectors;

public class GardenView {
    /**
     * version util
     * i am not sure this way could keep gardenFileModel
     */
    private final GardenFileModel gardenFileModel = new GardenFileModel();
    private String nowVersion = "";
    private Controller controller;
    private BorderPane mainBorderPane;
    /**
     * main canvas
     */
    private static Pane canvas;
    private Menu menuVersion;
    /**
     * init evaluationMap by using Anonymous inner class
     * @date 2020/12/8 9:36
     **/
    private final HashMap<Integer,String> evaluationMap = new HashMap<>();
    /**
     * load resources, key is kind, value resources is arraylist
     * @date 2020/12/8 11:49
     **/
    HashMap<String, List<Resource>> resources = Resource.getResources("src/main/resources/file/plantinfo.csv");

    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Gets the display of the current view and init evaluationMap
     */
    public VBox doGardenView(int height, int width) {
        evaluationMap.put(1, "bad");
        evaluationMap.put(2, "normal");
        evaluationMap.put(3, "good");
        evaluationMap.put(4, "perfect");
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
//        renderLeftItems(0);
        renderLeftItemsWithResource(resources.keySet().iterator().next());
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
     * @param imageUrl The url of the picture to be added into canvas
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
    private void updateVersionMenu() {
        if (menuVersion.getItems().size() > 0) {
            ObservableList<MenuItem> list = menuVersion.getItems();
            menuVersion.getItems().removeAll(list);
        }

        for (String key : gardenFileModel.getVersions()) {
            MenuItem menu = new MenuItem(key);
            menu.setOnAction(event -> doVersion(((MenuItem) event.getTarget()).getText()));
            menuVersion.getItems().add(menu);
        }
    }
    /**
     * render left items in the csv
     * @author sufu
     * @date 2020/12/8 8:46
     **/
    private void renderLeftItemsWithResource(String key){
        ScrollPane leftScrollPane = new ScrollPane();
        leftScrollPane.setPrefWidth(125);
        leftScrollPane.setPrefHeight(600);
        VBox vBoxImage = new VBox();
        for (Resource resource : resources.get(key)) {
            ImageView imageView = new ImageView(new Image("file:"+resource.getImageAddress()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setOnMouseClicked(e-> onClick("file:"+resource.getImageAddress()));
            vBoxImage.getChildren().add(imageView);
        }
        leftScrollPane.setContent(vBoxImage);
        mainBorderPane.setLeft(leftScrollPane);
    }

    /**
     * init top menu
     * @author sufu
     * @date 2020/12/5 16:21
     * @return javafx.scene.control.MenuBar
     **/
    public MenuBar doGardenMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu saveMenu = new Menu();
        Label saveLabel = new Label("Save");
        saveMenu.setGraphic(saveLabel);
        saveLabel.setOnMouseClicked(event -> saveVersion());

        Menu resourcesListMenu = new Menu("Resources List");
        for (String key : resources.keySet()) {
            MenuItem menuItem = new MenuItem(key);
            menuItem.setOnAction(event -> renderLeftItemsWithResource(key));
            resourcesListMenu.getItems().add(menuItem);
        }
        menuVersion = new Menu("Version");
        updateVersionMenu();

        Menu newVersionMenu = new Menu();
        Label newVersionLabel = new Label("New Version");
        newVersionMenu.setGraphic(newVersionLabel);
        newVersionLabel.setOnMouseClicked(e->{
            if(nowVersion == null){
                return;
            }
            nowVersion = "";
            canvas = getNewCanvas((int)canvas.getHeight(), (int)canvas.getWidth());
            mainBorderPane.setCenter(canvas);
        });

        Menu deleteMenu = new Menu();
        Label deleteLabel = new Label("Delete");
        deleteLabel.setOnMouseClicked(event -> {
            if(Objects.equals(nowVersion, "")){
                return;
            }
            System.out.println("do delete");
            gardenFileModel.doDelete(nowVersion);
            updateVersionMenu();
            // check if version is null
//            if(gardenFileModel.getVersions().size() == 0){
//                // all versions is deleted, new pane will be add to canvas
//            }
            nowVersion ="";
            canvas.getChildren().removeAll(canvas.getChildren());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Delete Success");
            alert.show();
        });
        deleteMenu.setGraphic(deleteLabel);


        Menu backMenu = new Menu();
        Label backLabel = new Label("Back");
        backLabel.setOnMouseClicked(event -> controller.showWelcomeView());
        backMenu.setGraphic(backLabel);

        menuBar.getMenus().addAll(resourcesListMenu, menuVersion, newVersionMenu, saveMenu, deleteMenu, backMenu);
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
     * save version and update versionMenu. if nowVersion is null,
     * show a dialog to get version name,
     * if user didn't input version,
     * auto get new version from gardenFileModel
     * @author sufu
     * @date 2020/12/5 15:46
     **/
    private void saveVersion() {
        List<ImageModel> imageModelListFromPane = getImageModelListFromPane();
        if(imageModelListFromPane.size() == 0){
            return;
        }
        if(nowVersion == null || nowVersion.length() == 0){
            TextInputDialog textInputDialog = getTextInputDialog();
            Optional<String> result  = textInputDialog.showAndWait();
            if(result.isEmpty()){
                // 如果点击取消，则返回 不执行
                return;
            }
            nowVersion = result.orElse("");
            if(nowVersion.length() == 0){
                nowVersion = gardenFileModel.getNewVersion();
            }
        }else {
            // check if nothing happened since last click save
            if (gardenFileModel.versionMap.get(nowVersion).equals(imageModelListFromPane)) {
                return;
            }
        }
        gardenFileModel.doSave(nowVersion, imageModelListFromPane);
        // 修改version 菜单项
        updateVersionMenu();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Your garden is "+getEvaluation(imageModelListFromPane));
        alert.setContentText("Saved as version "+nowVersion);
        alert.show();
    }

    /**
     * get Evaluation before save version according to the number of image types,
     * one type for bad, two for normal,three for good, four or more for perfect
     * @author sufu
     * @date 2020/12/8 9:39
     * @param imageModelListFromPane imageModels from the canvas
     * @return java.lang.String Evaluation
     **/
    private String getEvaluation(List<ImageModel> imageModelListFromPane) {
        Set<String> typeSet = new HashSet<>();
        // 从url中提取类型信息
        for (ImageModel imageModel : imageModelListFromPane) {
            // 两次截取获得分类
            String imageUrl = imageModel.imageUrl;
            String substring = imageUrl.substring(0,imageUrl.lastIndexOf("/"));
            String type = substring.substring(substring.lastIndexOf("/")+1);
            typeSet.add(type);
        }
        return evaluationMap.getOrDefault(typeSet.size(), "perfect");
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
    private TextInputDialog getTextInputDialog(){
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setGraphic(new Label());
        textInputDialog.setHeaderText("Please input version");
        textInputDialog.setTitle("Input version");
        textInputDialog.setContentText("Version:");
        return textInputDialog;
    }
}

package comp1140.ass2.gui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

//Whole class written by Jinming Dong
public class Instructions extends Application implements Runnable {
    private final Group root = new Group();
    @Override
    public void run() {
        try {
            new Instructions().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Instructions");
        Scene scene = new Scene(root, 933, 700);

        //set instruction image
        final ImageView imv2 = new ImageView();
        final Image image2 = new Image(Instructions.class.getResourceAsStream("assets/instructions.jpg"));
        imv2.setImage(image2);
        imv2.setFitWidth(233);
        imv2.setFitHeight(175);
        imv2.setX(350);
        imv2.setY(0);
        imv2.toFront();
        root.getChildren().add(imv2);

        //set background image
        final ImageView imv1 = new ImageView();
        final Image image3 = new Image(Instructions.class.getResourceAsStream("assets/instruction.jpg"));
        imv1.setImage(image3);
        imv1.setFitWidth(933);
        imv1.setFitHeight(700);
        imv1.setOpacity(0.6);
        root.getChildren().add(imv1);

        //quote website,tutoiral given by oracle javafx
        Pane pane = new Pane();
        pane.setMinSize(600, 480);
        pane.setMaxSize(600, 480);
        pane.setLayoutX(170);
        pane.setLayoutY(160);
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff;" +
                "-fx-background-insets: 0,30;");

        String url = Instructions.class.getResource("assets/instructions.html").toExternalForm();
        WebView web = new WebView();
        web.setMaxSize(540, 420);
        web.setLayoutX(30);
        web.setLayoutY(30);
        web.getEngine().load(url);

        pane.getChildren().add(web);
        root.getChildren().add(pane);
        Image icon = new Image(getClass().getResource("assets/icon2.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

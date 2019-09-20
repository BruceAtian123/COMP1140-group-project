package comp1140.ass2.gui;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;
//Whole class written by Jinming Dong
//Tutorial given by Youtube:https:www.youtube.com/watch?v=N2EmtYGLh4U&index=1&list=PL4h6ypqTi3RQWPZfR6t73rxZK_TFkyURe
public class Board extends Application {

    private static final int WIDTH = 933;
    private static final int HEIGHT = 700;
    private static final String URI_BASE = "assets/";

    // how to play sound loop learnt from oracle javafx tutorial
    // https://stackoverflow.com/questions/23202272/how-to-play-sounds-with-javafx
    private static final String LOOP_URI = Board.class.getResource(URI_BASE + "RiverFlowsInYou.mp3").toString();
    private static final String LOOP1_URI = Board.class.getResource(URI_BASE + "epitone project.mp3").toString();
    private AudioClip loop;
    private AudioClip loop1;
    private boolean loopPlaying = false;

    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<>("Single Player", new SinglePlayer()),
            new Pair<>("Multiplayer", new MultiPlayer()),
            new Pair<>("Instructions", new Instructions()),
            new Pair<>("Turn on/off the music", new Runnable() {
                @Override
                public void run() {
                    toggleSoundLoop();
                }
            }),
            new Pair<String, Runnable>("Exit to Desktop", Platform::exit)
    );

    private Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;

    private Parent createContent() {
        addBackground();
        addTitle();

        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;

        addLine(lineX, lineY);
        addMenu(lineX + 5, lineY + 5);

        startAnimation();

        return root;
    }

    private void addBackground() {
        ImageView imageView = new ImageView(new Image(getClass().getResource("assets/background.jpg").toExternalForm()));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        root.getChildren().add(imageView);
    }

    private void addTitle() {
        MenuTitle title = new MenuTitle("Blooms");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(HEIGHT / 3);

        root.getChildren().add(title);
    }

    private void addLine(double x, double y) {
        line = new Line(x, y, x, y + 200);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);
    }

    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {

            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

    private void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            MenuItem item = new MenuItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });

        root.getChildren().add(menuBox);
    }

    private void setUpSoundLoop() {
        try {
            loop1 = new AudioClip(LOOP1_URI);
            loop1.setCycleCount(AudioClip.INDEFINITE);
        } catch (Exception e) {
            System.err.println(":-( something bad happened (" + LOOP_URI + "): " + e);
        }
    }

    /**
     * Turn the sound loop on or off
     */
    private void toggleSoundLoop() {
        if (loopPlaying)
            loop1.stop();
        else
            loop1.play();
        loopPlaying = !loopPlaying;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Blooms");
        primaryStage.setScene(scene);
        primaryStage.show();
        setUpSoundLoop();
        toggleSoundLoop();
        Image icon = new Image(getClass().getResource("assets/icon2.png").toExternalForm());
        primaryStage.getIcons().add(icon);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

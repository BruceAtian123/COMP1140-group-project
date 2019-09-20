package comp1140.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

//Written by Jinming Dong
/**
 * A very simple viewer for placements in the Blooms game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just allows you to visualize
 * placements.
 */
public class Viewer extends Application {
    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;
    private static final int BOARD_X = 150;
    private static final int BOARD_Y = 100;
    private static final int BOARD_HEIGHT = 500;
    private static final int BOARD_WIDTH = 637;

    private static final String URI_BASE = "assets/";
    private static final String BOARD_URI = Viewer.class.getResource(URI_BASE + "board.png").toString();

    private final Group root = new Group();
    private final Group background = new Group();
    private final Group controls = new Group();
    private final Group stones = new Group();

    TextField textField;

    Stone[] Stones = new Stone[148];

    //make the size 4 board for the game by using the board image provided
    class Board extends ImageView {
        Image board;

        Board() {
            board = new Image(BOARD_URI);
            setImage(board);

            setFitHeight(BOARD_HEIGHT);
            setFitWidth(BOARD_WIDTH);

            setLayoutX(BOARD_X);
            setLayoutY(BOARD_Y);
        }
    }

    //Stone has four colour,each signified by letters a,b,c,d
    class Stone extends ImageView {
        Image a;
        Image b;
        Image c;
        Image d;
        Stone(char index) {
            a = new Image(Viewer.class.getResource(URI_BASE + "a.png").toString());
            b = new Image(Viewer.class.getResource(URI_BASE + "b.png").toString());
            c = new Image(Viewer.class.getResource(URI_BASE + "c.png").toString());
            d = new Image(Viewer.class.getResource(URI_BASE + "d.png").toString());
            if(index=='a'){
                setImage(a);
            }else if(index=='b'){
                setImage(b);
            }else if(index=='c'){
                setImage(c);
            }else if(index=='d'){
                setImage(d);
            }
            setFitHeight(500);
            setFitWidth(633);
            setOpacity(0);
        }
    }

    private void makeBoard() {
        background.getChildren().add(new Viewer.Board());
    }

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
         //FIXME Task 5: implement the simple placement viewer
        //only allowed to have 37 stones on the board
        assert (placement.length() == 37);

        //split placement into numbers and letters
        String[] numbers = placement.split("[a-d]");
        String[] letters = placement.split("\\d+");

        //to reset stones
        for (int i = 0; i < 148; i++)
            if (Stones[i] != null)
                Stones[i].setOpacity(0);
        stones.getChildren().removeAll();


        //put letters into an arraylist
        ArrayList<String> cs = new ArrayList<>();
        for(int j = 0;j<letters.length;j++){
            cs.add(letters[j]);
        }

        //to avoid errors so I use letters' length + numbers' length, actually either one is fine, but I just can't get why there's an error
        //if the letter of this index is a then the index is 0, b->1, c->2, d->3
        for (int i = 0; i < (letters.length+numbers.length)/2; i++) {
            int index = 0;
            if(cs.get(i).contains("a")){
                index = 0;
            }else if(cs.get(i).contains("b")){
                index = 1;
            }else if(cs.get(i).contains("c")){
                index = 2;
            }else if(cs.get(i).contains("d")){
                index = 3;
            }
            int x = Integer.parseInt(numbers[i+1]);

            //This idea is taken from assignment 1's tilecode, the actual index(not the int index that I used before) is the combination of
            //numbers and letters similar to assignment1's tile position and direction
            Stone stone = Stones[4*x+index];

            if(x==0) {
                stone.setLayoutX(18);
                stone.setLayoutY(-100);
            }else if(x==1) {
                stone.setLayoutX(108);
                stone.setLayoutY(-100);
            }else if(x==2) {
                stone.setLayoutX(198);
                stone.setLayoutY(-100);
            }else if(x==3) {
                stone.setLayoutX(288);
                stone.setLayoutY(-100);
            }else if(x==4){
                stone.setLayoutX(-27);
                stone.setLayoutY(-31.5);
            }else if(x==5){
                stone.setLayoutX(63);
                stone.setLayoutY(-31.5);
            }else if(x==6){
                stone.setLayoutX(153);
                stone.setLayoutY(-31.5);
            }else if(x==7){
                stone.setLayoutX(243);
                stone.setLayoutY(-31.5);
            }else if(x==8){
                stone.setLayoutX(333);
                stone.setLayoutY(-31.5);
            }else if(x==9){
                stone.setLayoutX(-72);
                stone.setLayoutY(35);
            }else if(x==10){
                stone.setLayoutX(18);
                stone.setLayoutY(35);
            }else if(x==11){
                stone.setLayoutX(108);
                stone.setLayoutY(35);
            }else if(x==12){
                stone.setLayoutX(198);
                stone.setLayoutY(35);
            }else if(x==13){
                stone.setLayoutX(288);
                stone.setLayoutY(35);
            }else if(x==14){
                stone.setLayoutX(378);
                stone.setLayoutY(35);
            }else if(x==15){
                stone.setLayoutX(-117);
                stone.setLayoutY(102.5);
            }else if(x==16){
                stone.setLayoutX(-27);
                stone.setLayoutY(102.5);
            }else if(x==17){
                stone.setLayoutX(63);
                stone.setLayoutY(102.5);
            }else if(x==18){
                stone.setLayoutX(153);
                stone.setLayoutY(102.5);
            }else if(x==19){
                stone.setLayoutX(243);
                stone.setLayoutY(102.5);
            }else if(x==20){
                stone.setLayoutX(333);
                stone.setLayoutY(102.5);
            }else if(x==21){
                stone.setLayoutX(423);
                stone.setLayoutY(102.5);
            }else if(x==22){
                stone.setLayoutX(-72);
                stone.setLayoutY(170);
            }else if(x==23){
                stone.setLayoutX(18);
                stone.setLayoutY(170);
            }else if(x==24){
                stone.setLayoutX(108);
                stone.setLayoutY(170);
            }else if(x==25){
                stone.setLayoutX(198);
                stone.setLayoutY(170);
            }else if(x==26){
                stone.setLayoutX(288);
                stone.setLayoutY(170);
            }else if(x==27){
                stone.setLayoutX(378);
                stone.setLayoutY(170);
            }else if(x==28){
                stone.setLayoutX(-27);
                stone.setLayoutY(237.5);
            }else if(x==29){
                stone.setLayoutX(63);
                stone.setLayoutY(237.5);
            }else if(x==30){
                stone.setLayoutX(153);
                stone.setLayoutY(237.5);
            }else if(x==31){
                stone.setLayoutX(243);
                stone.setLayoutY(237.5);
            }else if(x==32){
                stone.setLayoutX(333);
                stone.setLayoutY(237.5);
            }else if(x==33) {
                stone.setLayoutX(18);
                stone.setLayoutY(305.5);
            }else if(x==34) {
                stone.setLayoutX(108);
                stone.setLayoutY(305.5);
            }else if(x==35) {
                stone.setLayoutX(198);
                stone.setLayoutY(305.5);
            }else if(x==36) {
                stone.setLayoutX(288);
                stone.setLayoutY(305.5);
            }

            stone.setOpacity(1);
        }
    }


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Display");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(250);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
        root.getChildren().add(controls);
    }

    //make stones for the game
    private void makeStones() {
        for (int i = 0; i < 148; i++) {
            if(i>=0&&i<=3) {
                Stones[i] = new Stone((char) ('a' + i));
                stones.getChildren().add(Stones[i]);
            }else{
                Stones[i] = new Stone((char) ('a' + (i%4)));
                stones.getChildren().add(Stones[i]);
            }
        }
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Blooms Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(background);
        root.getChildren().add(stones);

        makeBoard();
        makeStones();
        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

package comp1140.ass2.gui;

import comp1140.ass2.BloomsGame;
import comp1140.ass2.ComputerPlayer;
import comp1140.ass2.Placement;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

//Whole class written by Jinming Dong
public class SinglePlayer extends Application implements Runnable {
    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;
    private static final int BOARD_X = 150;
    private static final int BOARD_Y = 100;
    private static final int BOARD_HEIGHT = 500;
    private static final int BOARD_WIDTH = 637;
    private static final String URI_BASE = "assets/";
    private static final String BOARD_URI = SinglePlayer.class.getResource(URI_BASE + "board.png").toString();

    private final Slider difficulty = new Slider();
    int turn = 0;

    private final Group root = new Group();
    private final Group background = new Group();
    private final Group controls = new Group();
    private final Group stones = new Group();
    private final Group scores = new Group();

    // how to set text learnt from oracle javafx tutorial
    // http://docs.oracle.com/javafx/2/text/jfxpub-text.htm
    Text t1 = new Text();
    Text t2 = new Text();
    Text t3 = new Text();
    Text t4 = new Text();
    Text completionText1 = new Text();
    Text completionText2 = new Text();

    private String pla="";
    private String move="";
    private String pastplacement="";
    public static String place2="";
    public static String place1="";

    Stone[] Stones = new Stone[152];

    @Override
    public void run() {
        try {
            new SinglePlayer().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //make board for the game
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

    //make stones for the game
    class Stone extends ImageView {
        Image a;
        Image b;
        Image c;
        Image d;

        Stone(char index) {
            a = new Image(SinglePlayer.class.getResource(URI_BASE + "a.png").toString());
            b = new Image(SinglePlayer.class.getResource(URI_BASE + "b.png").toString());
            c = new Image(SinglePlayer.class.getResource(URI_BASE + "c.png").toString());
            d = new Image(SinglePlayer.class.getResource(URI_BASE + "d.png").toString());
            if (index == 'a') {
                setImage(a);
            } else if (index == 'b') {
                setImage(b);
            } else if (index == 'c') {
                setImage(c);
            } else if (index == 'd') {
                setImage(d);
            }
            setFitHeight(500);
            setFitWidth(633);
            setOpacity(0);
        }
    }

    //take in a string of placement and automatically place stones onto the board
    void makePlacement(String placement) {
        //only allowed to have 37 stones on the board
        assert (placement.length() <= 37);

        System.out.println(place1);
        System.out.println(place2);

        placement = placement.replaceAll("[.]","");

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
        Collections.addAll(cs, letters);

        //to avoid errors so I use letters' length + numbers' length, actually either one is fine, but I just can't get why there's an error
        //if the letter of this index is a then the index is 0, b->1, c->2, d->3
        for (int i = 0; i < (numbers.length+letters.length)/2; i++) {
            int index = 0;
            if (cs.get(i).contains("a")) {
                index = 0;
            } else if (cs.get(i).contains("b")) {
                index = 1;
            } else if (cs.get(i).contains("c")) {
                index = 2;
            } else if (cs.get(i).contains("d")) {
                index = 3;
            }
            int x;
            x = Integer.parseInt(numbers[i + 1]);


            //This idea is taken from assignment 1's tilecode, the actual index(not the int index that I used before) is the combination of
            //numbers and letters similar to assignment1's tile position and direction
            SinglePlayer.Stone stone = Stones[4 * x + index];

            if (x == 0) {
                stone.setLayoutX(18);
                stone.setLayoutY(-100);
            } else if (x == 1) {
                stone.setLayoutX(108);
                stone.setLayoutY(-100);
            } else if (x == 2) {
                stone.setLayoutX(198);
                stone.setLayoutY(-100);
            } else if (x == 3) {
                stone.setLayoutX(288);
                stone.setLayoutY(-100);
            } else if (x == 4) {
                stone.setLayoutX(-27);
                stone.setLayoutY(-31.5);
            } else if (x == 5) {
                stone.setLayoutX(63);
                stone.setLayoutY(-31.5);
            } else if (x == 6) {
                stone.setLayoutX(153);
                stone.setLayoutY(-31.5);
            } else if (x == 7) {
                stone.setLayoutX(243);
                stone.setLayoutY(-31.5);
            } else if (x == 8) {
                stone.setLayoutX(333);
                stone.setLayoutY(-31.5);
            } else if (x == 9) {
                stone.setLayoutX(-72);
                stone.setLayoutY(35);
            } else if (x == 10) {
                stone.setLayoutX(18);
                stone.setLayoutY(35);
            } else if (x == 11) {
                stone.setLayoutX(108);
                stone.setLayoutY(35);
            } else if (x == 12) {
                stone.setLayoutX(198);
                stone.setLayoutY(35);
            } else if (x == 13) {
                stone.setLayoutX(288);
                stone.setLayoutY(35);
            } else if (x == 14) {
                stone.setLayoutX(378);
                stone.setLayoutY(35);
            } else if (x == 15) {
                stone.setLayoutX(-117);
                stone.setLayoutY(102.5);
            } else if (x == 16) {
                stone.setLayoutX(-27);
                stone.setLayoutY(102.5);
            } else if (x == 17) {
                stone.setLayoutX(63);
                stone.setLayoutY(102.5);
            } else if (x == 18) {
                stone.setLayoutX(153);
                stone.setLayoutY(102.5);
            } else if (x == 19) {
                stone.setLayoutX(243);
                stone.setLayoutY(102.5);
            } else if (x == 20) {
                stone.setLayoutX(333);
                stone.setLayoutY(102.5);
            } else if (x == 21) {
                stone.setLayoutX(423);
                stone.setLayoutY(102.5);
            } else if (x == 22) {
                stone.setLayoutX(-72);
                stone.setLayoutY(170);
            } else if (x == 23) {
                stone.setLayoutX(18);
                stone.setLayoutY(170);
            } else if (x == 24) {
                stone.setLayoutX(108);
                stone.setLayoutY(170);
            } else if (x == 25) {
                stone.setLayoutX(198);
                stone.setLayoutY(170);
            } else if (x == 26) {
                stone.setLayoutX(288);
                stone.setLayoutY(170);
            } else if (x == 27) {
                stone.setLayoutX(378);
                stone.setLayoutY(170);
            } else if (x == 28) {
                stone.setLayoutX(-27);
                stone.setLayoutY(237.5);
            } else if (x == 29) {
                stone.setLayoutX(63);
                stone.setLayoutY(237.5);
            } else if (x == 30) {
                stone.setLayoutX(153);
                stone.setLayoutY(237.5);
            } else if (x == 31) {
                stone.setLayoutX(243);
                stone.setLayoutY(237.5);
            } else if (x == 32) {
                stone.setLayoutX(333);
                stone.setLayoutY(237.5);
            } else if (x == 33) {
                stone.setLayoutX(18);
                stone.setLayoutY(305.5);
            } else if (x == 34) {
                stone.setLayoutX(108);
                stone.setLayoutY(305.5);
            } else if (x == 35) {
                stone.setLayoutX(198);
                stone.setLayoutY(305.5);
            } else if (x == 36) {
                stone.setLayoutX(288);
                stone.setLayoutY(305.5);
            }

            stone.setOpacity(1);
        }
    }

    //make scores for both user and AI player
    private void makeScore() {
        scores.getChildren().removeAll();
        int[] score = comp1140.ass2.Board.getScore(4,pla);
        int s1 = score[0];
        int s2 = score[1];
        t1.setText("Player: "+s1);
        t2.setText("Computer Player: "+s2);
        t1.setX(50);
        t1.setY(133);
        t2.setX(50);
        t2.setY(183);
        t1.setOpacity(1);
        t2.setOpacity(1);
        t1.setStyle("-fx-font: 20 arial;");
        t2.setStyle("-fx-font: 20 arial;");
        try {
            scores.getChildren().addAll(t1, t2);
        }catch(IllegalArgumentException e){
            return;
        }
    }

    //This is where AI player make moves, if difficulty is 1 then the AI player will be a random move AI player, if it is 2 then
    //the AI player will be a greedy bot, if difficulty is 3 then the AI player will be using Alpha-beta pruning
    //Also after 5 turns, we need to decide is the game is over or not
    private void makeMove(){
        String move = "";
        if((int)difficulty.getValue()==1) {
            try {
                ArrayList<String> list = BloomsGame.allValidMoves(4, pla, false);
                Collections.shuffle(list);
                move = list.get(0);
            }catch(NumberFormatException e1){
                return;
            }
        }else if((int)difficulty.getValue()==2) {
            try {
            move = ComputerPlayer.makeMove1(4, pla);
        }catch(NumberFormatException e1){
            return;
        }
        }else if((int)difficulty.getValue()==3) {
            try {
                move = ComputerPlayer.makeMove2(4, pla);
            }catch(NumberFormatException e1){
                return;
            }
        }
        turn++;
        pla+=move;
        place2+=move;
        pla = comp1140.ass2.Board.updateBoard(4, pla, ".");
        if(turn>=5) {
            try {
                if (comp1140.ass2.Board.isFinished(4, pla, true).contains("Player1")) {
                    completionText1.setText("You win!");
                    completionText1.setX(VIEWER_WIDTH / 3 + 50);
                    completionText1.setY(VIEWER_HEIGHT / 2);
                    completionText1.setOpacity(1);
                    completionText1.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                    completionText1.setFill(Color.SKYBLUE);
                    completionText1.setEffect(new DropShadow(30, Color.GREY));
                    completionText1.toFront();
                    root.getChildren().add(completionText1);
                } else if (comp1140.ass2.Board.isFinished(4, pla, true).contains("Player2")) {
                    completionText2.setText("You lose");
                    completionText2.setX(VIEWER_WIDTH / 3 + 50);
                    completionText2.setY(VIEWER_HEIGHT / 2);
                    completionText2.setOpacity(1);
                    completionText2.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                    completionText2.setFill(Color.WHITE);
                    completionText2.setEffect(new DropShadow(30, Color.BLACK));
                    completionText2.toFront();
                    root.getChildren().add(completionText2);
                } else if (comp1140.ass2.Board.isFinished(4, pla, true).contains("None")) {
                    completionText1.toBack();
                    completionText2.toBack();
                    completionText1.setOpacity(0);
                    completionText2.setOpacity(0);
                }
            } catch (IllegalArgumentException e1) {
                return;
            }
        }
        makePlacement(pla);
    }

    private void makeBoard() {
        background.getChildren().add(new SinglePlayer.Board());
    }

    //make stones for the game
    private void makeStones() {
        for (int i = 0; i < 148; i++) {
            if (i >= 0 && i <= 3) {
                Stones[i] = new SinglePlayer.Stone((char) ('a' + i));
                stones.getChildren().add(Stones[i]);
            } else {
                Stones[i] = new SinglePlayer.Stone((char) ('a' + (i % 4)));
                stones.getChildren().add(Stones[i]);
            }
        }
    }

    //make instructions for the game
    private void makeInstructions() {
        Text t5 = new Text();
        t5.setText("How to play:\n" +
                "To play this game,first select the type of the move that you want to take(single move, normal move, skip).\n" +
                "Then select color that you want to place(a-red,b-yellow,c-green,d-blue).\n" +
                "Each time remember to click the type of the move first and then the color, \n" +
                "finally click the position on the board which is the place that you want to place this stone on.\n" +
                "(if you are not doing this by order then you have to close this window and enter into it again)"
        );
        t5.setX(50);
        t5.setY(20);
        root.getChildren().add(t5);
    }

    //record the board and placement of player, determine if the move is valid or not, if not then clear it and enable the
    //player to reselect the types of move and make move again, also for turn>5, we need to determine the game is finished or
    //not
    private void makeControls() {
        Button button1 = new Button("a");
        Button button2 = new Button("b");
        Button button3 = new Button("resign");
        Button button5 = new Button("single move");
        Button button6 = new Button("normal move");
        Button button7 = new Button("skip");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                pla+="a";
                move+="a";
                place1+="a";
                e.consume();
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                pla+="b";
                move+="b";
                place1+="b";
                e.consume();
            }
        });
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    t4.setText("You lose");
                    t4.setX(VIEWER_WIDTH / 3 + 50);
                    t4.setY(VIEWER_HEIGHT / 2);
                    t4.setOpacity(1);
                    t4.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                    t4.setFill(Color.WHITE);
                    t4.setEffect(new DropShadow(30, Color.BLACK));
                    t4.toFront();
                    root.getChildren().add(t4);
                }catch(IllegalArgumentException e1){
                    return;
                }
                e.consume();
            }
        });
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.setOnMouseClicked((new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        if (event.getSceneX() > 285 && event.getSceneX() < 375 && event.getSceneY() > 104 && event.getSceneY() < 192) {
                            pla += "0.";
                            move += "0.";
                            place1+="0.";
                        } else if (event.getSceneX() > 375 && event.getSceneX() < 465 && event.getSceneY() > 104 && event.getSceneY() < 192) {
                            pla += "1.";
                            move += "1.";
                            place1+="1.";
                        } else if (event.getSceneX() > 465 && event.getSceneX() < 555 && event.getSceneY() > 104 && event.getSceneY() < 192) {
                            pla += "2.";
                            move += "2.";
                            place1+="2.";
                        } else if (event.getSceneX() > 555 && event.getSceneX() < 645 && event.getSceneY() > 104 && event.getSceneY() < 192) {
                            pla += "3.";
                            move += "3.";
                            place1+="3.";
                        } else if (event.getSceneX() > 243 && event.getSceneX() < 333 && event.getSceneY() > 171 && event.getSceneY() < 260) {
                            pla += "4.";
                            move += "4.";
                            place1+="4.";
                        } else if (event.getSceneX() > 333 && event.getSceneX() < 423 && event.getSceneY() > 171 && event.getSceneY() < 260) {
                            pla += "5.";
                            move += "5.";
                            place1+="5.";
                        } else if (event.getSceneX() > 423 && event.getSceneX() < 513 && event.getSceneY() > 171 && event.getSceneY() < 260) {
                            pla += "6.";
                            move += "6.";
                            place1+="6.";
                        } else if (event.getSceneX() > 513 && event.getSceneX() < 603 && event.getSceneY() > 171 && event.getSceneY() < 260) {
                            pla += "7.";
                            move += "7.";
                            place1+="7.";
                        } else if (event.getSceneX() > 603 && event.getSceneX() < 693 && event.getSceneY() > 171 && event.getSceneY() < 260) {
                            pla += "8.";
                            move += "8.";
                            place1+="8.";
                        } else if (event.getSceneX() > 195 && event.getSceneX() < 285 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "9.";
                            move += "9.";
                            place1+="9.";
                        } else if (event.getSceneX() > 285 && event.getSceneX() < 375 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "10.";
                            move += "10.";
                            place1+="10.";
                        } else if (event.getSceneX() > 375 && event.getSceneX() < 465 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "11.";
                            move += "11.";
                            place1+="11.";
                        } else if (event.getSceneX() > 465 && event.getSceneX() < 555 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "12.";
                            move += "12.";
                            place1+="12.";
                        } else if (event.getSceneX() > 555 && event.getSceneX() < 645 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "13.";
                            move += "13.";
                            place1+="13.";
                        } else if (event.getSceneX() > 645 && event.getSceneX() < 735 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "14.";
                            move += "14.";
                            place1+="14.";
                        } else if (event.getSceneX() > 153 && event.getSceneX() < 243 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "15.";
                            move += "15.";
                            place1+="15.";
                        } else if (event.getSceneX() > 243 && event.getSceneX() < 333 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "16.";
                            move += "16.";
                            place1+="16.";
                        } else if (event.getSceneX() > 333 && event.getSceneX() < 423 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "17.";
                            move += "17.";
                            place1+="17.";
                        } else if (event.getSceneX() > 423 && event.getSceneX() < 513 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "18.";
                            move += "18.";
                            place1+="18.";
                        } else if (event.getSceneX() > 513 && event.getSceneX() < 603 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "19.";
                            move += "19.";
                            place1+="19.";
                        } else if (event.getSceneX() > 603 && event.getSceneX() < 693 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "20.";
                            move += "20.";
                            place1+="20.";
                        } else if (event.getSceneX() > 693 && event.getSceneX() < 783 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "21.";
                            move += "21.";
                            place1+="21.";
                        } else if (event.getSceneX() > 195 && event.getSceneX() < 285 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "22.";
                            move += "22.";
                            place1+="22.";
                        } else if (event.getSceneX() > 285 && event.getSceneX() < 375 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "23.";
                            move += "23.";
                            place1+="23.";
                        } else if (event.getSceneX() > 375 && event.getSceneX() < 465 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "24.";
                            move += "24.";
                            place1+="24.";
                        } else if (event.getSceneX() > 465 && event.getSceneX() < 555 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "25.";
                            move += "25.";
                            place1+="25.";
                        } else if (event.getSceneX() > 555 && event.getSceneX() < 645 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "26.";
                            move += "26.";
                            place1+="26.";
                        } else if (event.getSceneX() > 645 && event.getSceneX() < 735 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "27.";
                            move += "27.";
                            place1+="27";
                        } else if (event.getSceneX() > 243 && event.getSceneX() < 333 && event.getSceneY() > 445 && event.getSceneY() < 535) {
                            pla += "28.";
                            move += "28.";
                            place1+="28.";
                        } else if (event.getSceneX() > 333 && event.getSceneX() < 423 && event.getSceneY() > 445 && event.getSceneY() < 535) {
                            pla += "29.";
                            move += "29.";
                            place1+="29.";
                        } else if (event.getSceneX() > 423 && event.getSceneX() < 513 && event.getSceneY() > 445 && event.getSceneY() < 535) {
                            pla += "30.";
                            move += "30.";
                            place1+="30.";
                        } else if (event.getSceneX() > 513 && event.getSceneX() < 603 && event.getSceneY() > 445 && event.getSceneY() < 535) {
                            pla += "31.";
                            move += "31.";
                            place1+="31.";
                        } else if (event.getSceneX() > 603 && event.getSceneX() < 693 && event.getSceneY() > 445 && event.getSceneY() < 535) {
                            pla += "32.";
                            move += "32.";
                            place1+="32.";
                        } else if (event.getSceneX() > 285 && event.getSceneX() < 375 && event.getSceneY() > 515 && event.getSceneY() < 605) {
                            pla += "33.";
                            move += "33.";
                            place1+="33.";
                        } else if (event.getSceneX() > 375 && event.getSceneX() < 465 && event.getSceneY() > 515 && event.getSceneY() < 605) {
                            pla += "34.";
                            move += "34.";
                            place1+="34.";
                        } else if (event.getSceneX() > 465 && event.getSceneX() < 555 && event.getSceneY() > 515 && event.getSceneY() < 605) {
                            pla += "35.";
                            move += "35.";
                            place1+="35.";
                        } else if (event.getSceneX() > 555 && event.getSceneX() < 645 && event.getSceneY() > 515 && event.getSceneY() < 605) {
                            pla += "36.";
                            move += "36.";
                            place1+="36.";
                        }
                        pastplacement = pla.substring(0,pla.length()-move.length());
                        if(Placement.isPlacementValid(4,pastplacement,move)) {
                            turn++;
                            t3.setOpacity(0);
                            pla = pla.replaceAll("[.]","");
                            pla = comp1140.ass2.Board.updateBoard(4, pla, ".");
                            makePlacement(pla);
                            makeMove();
                            makeScore();
                            move="";
                            pastplacement="";
                        }else {
                            pla = pla.substring(0,pla.length()-move.length());
                            place1 = place1.substring(0,place1.length()-move.length());
                            pla = pla.replaceAll("[.]","");
                            makePlacement(pla);
                            makeScore();
                            move="";
                            pastplacement="";
                            try {
                                t3.setText("Invalid move");
                                t3.setX(VIEWER_WIDTH / 3 + 20);
                                t3.setY(VIEWER_HEIGHT / 2);
                                t3.setOpacity(1);
                                t3.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                                t3.setFill(Color.RED);
                                t3.setEffect(new DropShadow(30, Color.BLACK));
                                root.getChildren().add(t3);
                            }catch(IllegalArgumentException e1){
                                return;
                            }
                        }
                        if(turn>=5) {
                            try {
                                if (comp1140.ass2.Board.isFinished(4, pla, true).contains("Player1")) {
                                    t3.setOpacity(0);
                                    completionText1.setText("You win!");
                                    completionText1.setX(VIEWER_WIDTH / 3 + 50);
                                    completionText1.setY(VIEWER_HEIGHT / 2);
                                    completionText1.setOpacity(1);
                                    completionText1.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                                    completionText1.setFill(Color.SKYBLUE);
                                    completionText1.setEffect(new DropShadow(30, Color.BLACK));
                                    completionText1.toFront();
                                    root.getChildren().add(completionText1);
                                } else if (comp1140.ass2.Board.isFinished(4, pla, true).contains("Player2")) {
                                    t3.setOpacity(0);
                                    completionText2.setText("You lose");
                                    completionText2.setX(VIEWER_WIDTH / 3 + 50);
                                    completionText2.setY(VIEWER_HEIGHT / 2);
                                    completionText2.setOpacity(1);
                                    completionText2.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                                    completionText2.setFill(Color.WHITE);
                                    completionText2.setEffect(new DropShadow(30, Color.BLACK));
                                    completionText2.toFront();
                                    root.getChildren().add(completionText2);
                                } else if (comp1140.ass2.Board.isFinished(4, pla, true).contains("None")) {
                                    completionText1.toBack();
                                    completionText2.toBack();
                                    completionText1.setOpacity(0);
                                    completionText2.setOpacity(0);
                                }
                            } catch (IllegalArgumentException e1) {
                                return;
                            }
                        }
                    }
                }));

                e.consume();
            }
        });
        //Similar to single move, but just add a counter as c to count if the player has make 2 moves or not
        button6.setOnAction(new EventHandler<ActionEvent>() {
            int c = 0;
            @Override
            public void handle(ActionEvent e) {
                root.setOnMouseClicked((new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        if (event.getSceneX() > 285 && event.getSceneX() < 375 && event.getSceneY() > 104 && event.getSceneY() < 192) {
                            pla += "0";
                            move += "0";
                            place1 += "0";
                            c++;
                        } else if (event.getSceneX() > 375 && event.getSceneX() < 465 && event.getSceneY() > 104 && event.getSceneY() < 192) {
                            pla += "1";
                            move += "1";
                            place1 += "1";
                            c++;
                        } else if (event.getSceneX() > 465 && event.getSceneX() < 555 && event.getSceneY() > 104 && event.getSceneY() < 192) {
                            pla += "2";
                            move += "2";
                            place1 += "2";
                            c++;
                        } else if (event.getSceneX() > 555 && event.getSceneX() < 645 && event.getSceneY() > 104 && event.getSceneY() < 192) {
                            pla += "3";
                            move += "3";
                            place1 += "3";
                            c++;
                        } else if (event.getSceneX() > 243 && event.getSceneX() < 333 && event.getSceneY() > 171 && event.getSceneY() < 260) {
                            pla += "4";
                            move += "4";
                            place1 += "4";
                            c++;
                        } else if (event.getSceneX() > 333 && event.getSceneX() < 423 && event.getSceneY() > 171 && event.getSceneY() < 260) {
                            pla += "5";
                            move += "5";
                            place1 += "5";
                            c++;
                        } else if (event.getSceneX() > 423 && event.getSceneX() < 513 && event.getSceneY() > 171 && event.getSceneY() < 260) {
                            pla += "6";
                            move += "6";
                            place1 += "6";
                            c++;
                        } else if (event.getSceneX() > 513 && event.getSceneX() < 603 && event.getSceneY() > 171 && event.getSceneY() < 260) {
                            pla += "7";
                            move += "7";
                            place1 += "7";
                            c++;
                        } else if (event.getSceneX() > 603 && event.getSceneX() < 693 && event.getSceneY() > 171 && event.getSceneY() < 260) {
                            pla += "8";
                            move += "8";
                            place1 += "8";
                            c++;
                        } else if (event.getSceneX() > 195 && event.getSceneX() < 285 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "9";
                            move += "9";
                            place1 += "9";
                            c++;
                        } else if (event.getSceneX() > 285 && event.getSceneX() < 375 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "10";
                            move += "10";
                            place1 += "10";
                            c++;
                        } else if (event.getSceneX() > 375 && event.getSceneX() < 465 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "11";
                            move += "11";
                            place1 += "11";
                            c++;
                        } else if (event.getSceneX() > 465 && event.getSceneX() < 555 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "12";
                            move += "12";
                            place1 += "12";
                            c++;
                        } else if (event.getSceneX() > 555 && event.getSceneX() < 645 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "13";
                            move += "13";
                            place1 += "13";
                            c++;
                        } else if (event.getSceneX() > 645 && event.getSceneX() < 735 && event.getSceneY() > 238 && event.getSceneY() < 328) {
                            pla += "14";
                            move += "14";
                            place1 += "14";
                            c++;
                        } else if (event.getSceneX() > 153 && event.getSceneX() < 243 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "15";
                            move += "15";
                            place1 += "15";
                            c++;
                        } else if (event.getSceneX() > 243 && event.getSceneX() < 333 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "16";
                            move += "16";
                            place1 += "16";
                            c++;
                        } else if (event.getSceneX() > 333 && event.getSceneX() < 423 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "17";
                            move += "17";
                            place1 += "17";
                            c++;
                        } else if (event.getSceneX() > 423 && event.getSceneX() < 513 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "18";
                            move += "18";
                            place1 += "18";
                            c++;
                        } else if (event.getSceneX() > 513 && event.getSceneX() < 603 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "19";
                            move += "19";
                            place1 += "19";
                            c++;
                        } else if (event.getSceneX() > 603 && event.getSceneX() < 693 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "20";
                            move += "20";
                            place1 += "20";
                            c++;
                        } else if (event.getSceneX() > 693 && event.getSceneX() < 783 && event.getSceneY() > 305 && event.getSceneY() < 395) {
                            pla += "21";
                            move += "21";
                            place1 += "21";
                            c++;
                        } else if (event.getSceneX() > 195 && event.getSceneX() < 285 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "22";
                            move += "22";
                            place1 += "22";
                            c++;
                        } else if (event.getSceneX() > 285 && event.getSceneX() < 375 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "23";
                            move += "23";
                            place1 += "23";
                            c++;
                        } else if (event.getSceneX() > 375 && event.getSceneX() < 465 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "24";
                            move += "24";
                            place1 += "24";
                            c++;
                        } else if (event.getSceneX() > 465 && event.getSceneX() < 555 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "25";
                            move += "25";
                            place1 += "25";
                            c++;
                        } else if (event.getSceneX() > 555 && event.getSceneX() < 645 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "26";
                            move += "26";
                            place1 += "26";
                            c++;
                        } else if (event.getSceneX() > 645 && event.getSceneX() < 735 && event.getSceneY() > 375 && event.getSceneY() < 465) {
                            pla += "27";
                            move += "27";
                            place1 += "27";
                            c++;
                        } else if (event.getSceneX() > 243 && event.getSceneX() < 333 && event.getSceneY() > 445 && event.getSceneY() < 535) {
                            pla += "28";
                            move += "28";
                            place1 += "28";
                            c++;
                        } else if (event.getSceneX() > 333 && event.getSceneX() < 423 && event.getSceneY() > 445 && event.getSceneY() < 535) {
                            pla += "29";
                            move += "29";
                            place1 += "29";
                            c++;
                        } else if (event.getSceneX() > 423 && event.getSceneX() < 513 && event.getSceneY() > 445 && event.getSceneY() < 535) {
                            pla += "30";
                            move += "30";
                            place1 += "30";
                            c++;
                        } else if (event.getSceneX() > 513 && event.getSceneX() < 603 && event.getSceneY() > 445 && event.getSceneY() < 535) {
                            pla += "31";
                            move += "31";
                            place1 += "31";
                            c++;
                        } else if (event.getSceneX() > 603 && event.getSceneX() < 693 && event.getSceneY() > 445 && event.getSceneY() < 535) {
                            pla += "32";
                            move += "32";
                            place1 += "32";
                            c++;
                        } else if (event.getSceneX() > 285 && event.getSceneX() < 375 && event.getSceneY() > 515 && event.getSceneY() < 605) {
                            pla += "33";
                            move += "33";
                            place1 += "33";
                            c++;
                        } else if (event.getSceneX() > 375 && event.getSceneX() < 465 && event.getSceneY() > 515 && event.getSceneY() < 605) {
                            pla += "34";
                            move += "34";
                            place1 += "34";
                            c++;
                        } else if (event.getSceneX() > 465 && event.getSceneX() < 555 && event.getSceneY() > 515 && event.getSceneY() < 605) {
                            pla += "35";
                            move += "35";
                            place1 += "35";
                            c++;
                        } else if (event.getSceneX() > 555 && event.getSceneX() < 645 && event.getSceneY() > 515 && event.getSceneY() < 605) {
                            pla += "36";
                            move += "36";
                            place1 += "36";
                            c++;
                        }
                        if(c == 1){
                            pastplacement = pla.substring(0,pla.length()-move.length());
                            if(Placement.isPlacementValid(4,pastplacement,move+".")) {
                                t3.setOpacity(0);
                                pla = pla.replaceAll("[.]","");
                                pla = comp1140.ass2.Board.updateBoard(4, pla, ".");
                                makePlacement(pla);
                                makeScore();
                            }else {
                                pla = pla.substring(0,pla.length()-move.length());
                                place1 = place1.substring(0,place1.length()-move.length());
                                c = 0;
                                pla = pla.replaceAll("[.]","");
                                makePlacement(pla);
                                makeScore();
                                pastplacement = "";
                                move = "";
                                try {
                                    t3.setText("Invalid move");
                                    t3.setX(VIEWER_WIDTH / 3 + 20);
                                    t3.setY(VIEWER_HEIGHT / 2);
                                    t3.setOpacity(1);
                                    t3.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                                    t3.setFill(Color.RED);
                                    t3.setEffect(new DropShadow(30, Color.BLACK));
                                    root.getChildren().add(t3);
                                }catch(IllegalArgumentException e1){
                                    return;
                                }
                            }
                            if(turn>=5) {
                                try {
                                    if (comp1140.ass2.Board.isFinished(4, pla, true).contains("Player1")) {
                                        t3.setOpacity(0);
                                        completionText1.setText("You win!");
                                        completionText1.setX(VIEWER_WIDTH / 3 + 50);
                                        completionText1.setY(VIEWER_HEIGHT / 2);
                                        completionText1.setOpacity(1);
                                        completionText1.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                                        completionText1.setFill(Color.ORANGE);
                                        completionText1.setEffect(new DropShadow(30, Color.BLACK));
                                        completionText1.toFront();
                                        root.getChildren().add(completionText1);
                                    } else if (comp1140.ass2.Board.isFinished(4, pla, true).contains("Player2")) {
                                        t3.setOpacity(0);
                                        completionText2.setText("You lose");
                                        completionText2.setX(VIEWER_WIDTH / 3 + 50);
                                        completionText2.setY(VIEWER_HEIGHT / 2);
                                        completionText2.setOpacity(1);
                                        completionText2.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                                        completionText2.setFill(Color.RED);
                                        completionText2.setEffect(new DropShadow(30, Color.BLACK));
                                        completionText2.toFront();
                                        root.getChildren().add(completionText2);
                                    } else if (comp1140.ass2.Board.isFinished(4, pla, true).contains("None")) {
                                        completionText1.toBack();
                                        completionText2.toBack();
                                        completionText1.setOpacity(0);
                                        completionText2.setOpacity(0);
                                    }
                                } catch (IllegalArgumentException e1) {
                                    return;
                                }
                            }
                        }
                        if (c == 2){
                            pastplacement = pla.substring(0, pla.length() - move.length());
                        if (Placement.isPlacementValid(4, pastplacement, move)) {
                            turn++;
                            t3.setOpacity(0);
                            pla = pla.replaceAll("[.]", "");
                            pla = comp1140.ass2.Board.updateBoard(4, pla, ".");
                            makePlacement(pla);
                                makeMove();
                                makeScore();
                                c = 0;
                            pastplacement = "";
                            move = "";
                        } else {
                            pla = pla.substring(0,pla.length()-move.length());
                            place1 = place1.substring(0, place1.length() - move.length());
                            pla = pla.replaceAll("[.]", "");
                            makePlacement(pla);
                                c = 0;
                                makeScore();
                            try {
                                t3.setText("Invalid move");
                                t3.setX(VIEWER_WIDTH / 3 + 20);
                                t3.setY(VIEWER_HEIGHT / 2);
                                t3.setOpacity(1);
                                t3.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                                t3.setFill(Color.RED);
                                t3.setEffect(new DropShadow(30, Color.GREY));
                                root.getChildren().add(t3);
                            } catch (IllegalArgumentException e1) {
                                return;
                            }
                            pastplacement = "";
                            move = "";
                        }
                        if(turn>=5) {
                            try {
                                if (comp1140.ass2.Board.isFinished(4, pla, true).contains("Player1")) {
                                    t3.setOpacity(0);
                                    completionText1.setText("You win!");
                                    completionText1.setX(VIEWER_WIDTH / 3 + 50);
                                    completionText1.setY(VIEWER_HEIGHT / 2);
                                    completionText1.setOpacity(1);
                                    completionText1.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                                    completionText1.setFill(Color.ORANGE);
                                    completionText1.setEffect(new DropShadow(30, Color.BLACK));
                                    completionText1.toFront();
                                    root.getChildren().add(completionText1);
                                } else if (comp1140.ass2.Board.isFinished(4, pla, true).contains("Player2")) {
                                    t3.setOpacity(0);
                                    completionText2.setText("You lose");
                                    completionText2.setX(VIEWER_WIDTH / 3 + 50);
                                    completionText2.setY(VIEWER_HEIGHT / 2);
                                    completionText2.setOpacity(1);
                                    completionText2.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                                    completionText2.setFill(Color.WHITE);
                                    completionText2.setEffect(new DropShadow(30, Color.BLACK));
                                    completionText2.toFront();
                                    root.getChildren().add(completionText2);
                                } else if (comp1140.ass2.Board.isFinished(4, pla, true).contains("None")) {
                                    completionText1.toBack();
                                    completionText2.toBack();
                                    completionText1.setOpacity(0);
                                    completionText2.setOpacity(0);
                                }
                            } catch (IllegalArgumentException e1) {
                                return;
                            }
                        }
                    }
                    }
                }));
                e.consume();
            }
        });
        //skip the turn
        button7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                place1+=".";
                makeMove();
                pla = pla.replaceAll("[.]","");
                makeScore();
                try {
                    if (comp1140.ass2.Board.isFinished(4, pla,true).contains("Player1")) {
                        t3.setOpacity(0);
                        completionText1.setText("You win!");
                        completionText1.setX(VIEWER_WIDTH/3+50);
                        completionText1.setY(VIEWER_HEIGHT/2);
                        completionText1.setOpacity(1);
                        completionText1.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                        completionText1.setFill(Color.ORANGE);
                        completionText1.setEffect(new DropShadow(30, Color.BLACK));
                        completionText1.toFront();
                        root.getChildren().add(completionText1);
                    } else if (comp1140.ass2.Board.isFinished(4, pla,true).contains("Player2")) {
                        t3.setOpacity(0);
                        completionText2.setText("You lose");
                        completionText2.setX(VIEWER_WIDTH/3+50);
                        completionText2.setY(VIEWER_HEIGHT/2);
                        completionText2.setOpacity(1);
                        completionText2.setFont(Font.loadFont(comp1140.ass2.gui.Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
                        completionText2.setFill(Color.WHITE);
                        completionText2.setEffect(new DropShadow(30, Color.BLACK));
                        completionText2.toFront();
                        root.getChildren().add(completionText2);
                    } else if (comp1140.ass2.Board.isFinished(4, pla,true).contains("None")) {
                        completionText1.toBack();
                        completionText2.toBack();
                        completionText1.setOpacity(0);
                        completionText2.setOpacity(0);
                    }
                }catch(IllegalArgumentException e1){
                    return;
                }
                e.consume();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(button1,button2,button5,button6,button7);
        hb.setSpacing(10);
        hb.setLayoutX(450);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        //How to set slider bar learnt from oracle javafx tutorial, also you can find similar difficulty setting in comp1110 assignment1
        //https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/slider.htm#CCHDJDFE
        difficulty.setMin(1);
        difficulty.setMax(3);
        difficulty.setValue(0);
        difficulty.setShowTickLabels(true);
        difficulty.setShowTickMarks(true);
        difficulty.setMajorTickUnit(1);
        difficulty.setMinorTickCount(1);
        difficulty.setSnapToTicks(true);

        difficulty.setLayoutX(VIEWER_WIDTH / 4 - 140);
        difficulty.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(difficulty);

        final Label difficultyCaption = new Label("Difficulty:");
        difficultyCaption.setTextFill(Color.GREY);
        difficultyCaption.setLayoutX(VIEWER_WIDTH / 4 - 210);
        difficultyCaption.setLayoutY(VIEWER_HEIGHT - 50);
        Button button = new Button("restart");
        button.setLayoutX(VIEWER_WIDTH / 4 + 30);
        button.setLayoutY(VIEWER_HEIGHT - 50);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                newGame();
            }
        });
        button3.setLayoutX(820);
        button3.setLayoutY(25);
        controls.getChildren().add(button);
        controls.getChildren().add(button3);
        controls.getChildren().add(difficultyCaption);
        controls.getChildren().add(hb);
        root.getChildren().add(controls);
    }

    private void addBackground() {
        ImageView imageView = new ImageView(new Image(getClass().getResource("assets/gamebackground.jpg").toExternalForm()));
        imageView.setFitWidth(VIEWER_WIDTH);
        imageView.setFitHeight(VIEWER_HEIGHT);
        imageView.setOpacity(0.4);

        root.getChildren().add(imageView);
    }

    /**
     * Start a new game, resetting everything as necessary
     */
    private void newGame() {
        try {
            //to reset stones
            for (int i = 0; i < 148; i++)
                if (Stones[i] != null)
                    Stones[i].setOpacity(0);
            stones.getChildren().removeAll();
            scores.getChildren().removeAll();
            t1.setOpacity(0);
            t2.setOpacity(0);
            t3.setOpacity(0);
            t4.setOpacity(0);
            completionText1.setOpacity(0);
            completionText2.setOpacity(0);
            makeStones();
            pla = "";
            place1 = "";
            place2 = "";
            pastplacement = "";
            move = "";
        } catch (IllegalArgumentException e) {
            System.err.println("Uh oh. " + e);
            e.printStackTrace();
            Platform.exit();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SinglePlayer Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(background);
        root.getChildren().add(stones);
        root.getChildren().add(scores);

        addBackground();
        makeControls();
        makeBoard();
        makeStones();
        makeInstructions();

        Image icon = new Image(getClass().getResource("assets/icon2.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

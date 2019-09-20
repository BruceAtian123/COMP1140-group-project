package comp1140.ass2.gui;

import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
//Written by Jinming Dong
//Tutorial given by Youtube:https:www.youtube.com/watch?v=N2EmtYGLh4U&index=1&list=PL4h6ypqTi3RQWPZfR6t73rxZK_TFkyURe
public class MenuTitle extends Pane {
    private Text text;

    public MenuTitle(String name) {
        String spread = "";
        for (char c : name.toCharArray()) {
            spread += c + " ";
        }

        text = new Text(spread);
        text.setFont(Font.loadFont(Board.class.getResource("assets/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
        text.setFill(Color.WHITE);
        text.setEffect(new DropShadow(30, Color.BLACK));

        getChildren().addAll(text);
    }

    public double getTitleWidth() {
        return text.getLayoutBounds().getWidth();
    }

    public double getTitleHeight() {
        return text.getLayoutBounds().getHeight();
    }
}

import engine.Display;
import engine.Game;
import engine.rendering.Graphics;
import scenes.MainScene;

import java.awt.*;

public class Launcher {

    public static void main(String[] args){
        Display d = Game.display("main");

        Game.addScene(new MainScene(), "main");
        d.attachScene("main");

        d.setBackgroundColor(Color.darkGray);

        Game.start();
    }
}

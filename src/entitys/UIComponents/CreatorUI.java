package entitys.UIComponents;

import engine.Entity;
import engine.Game;
import engine.Scene;
import engine.rendering.Graphics;
import scenes.MainScene;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class CreatorUI extends Entity {

    //reverences
    MainScene scene;
    int initX, initY;

    public CreatorUI(int x, int y){
        this.initX = x;
        this.initY = y;
    }
    @Override
    public void init() {
        scene = (MainScene) Game.getScene("main");
    }

    @Override
    public void logicLoop() {
        //attach position to cam
        x = -Graphics.getCamPos().x + initX;
        y = -Graphics.getCamPos().y + initY;
    }

    @Override
    public void renderLoop() {
        //Draw Background
        g.setColor(Color.gray);
        g.fillRect(x, y, scene.creatorUIDim.width, scene.creatorUIDim.height);
    }
}

package entitys.UIComponents.SelectorUIs;

import engine.Entity;
import engine.Game;
import engine.listeners.MouseButtons;
import engine.mechanics.*;
import engine.mechanics.Button;
import engine.rendering.Graphics;
import scenes.MainScene;


import java.awt.*;

import static engine.rendering.Graphics.g;

public class SelectorUI extends Entity {
    //reverences
    MainScene scene;
    //Variables
    int initX, initY;
    TextBox screenWidth, screenHeight, BGColor;
    Button apply;


    public SelectorUI(int x, int y) {
        this.initX = x;
        this.initY = y;
    }

    @Override
    public void init() {
        scene = (MainScene) Game.getScene("main");

        MethodObject bg = new MethodObject(scene).execRenderLoop(e -> {
            g.setColor(Color.gray);
            g.fillRect(x, y, scene.selectorUIDim.width, scene.selectorUIDim.height);
        });
        MethodObject descriptions = new MethodObject(scene).execRenderLoop(e -> {
            g.setColor(Color.black);
            g.setFont(new Font("base", Font.PLAIN, 15));
            g.drawString("Cam width:", x + 60, y + 65);
            g.drawString("Cam height:", x + 60, y + 115);
            g.drawString("Background color (Hex):", x + 60, y + 190);
        });


        //objects
        screenWidth = new TextBox(x, y, 60, 20, scene)
                .setBorderColor(Color.black)
                .setMatcher("[^a-z^A-Z]+")
                .setText("1280");
        screenHeight = new TextBox(x, y, 60, 20, scene)
                .setBorderColor(Color.black)
                .setMatcher("[^a-z^A-Z]+")
                .setText("720");
        BGColor = new TextBox(x, y, 80, 20, scene)
                .setBorderColor(Color.black);

        apply = new Button(x, y, 70, 30, scene)
                .setColor(Color.darkGray)
                .setHoverColor(Color.blue)
                .setText("Apply")
                .addEvent(MouseButtons.LEFT_DOWN, e -> {
                    //apply settings
                    try {
                        scene.camSize.width = Integer.parseInt(screenWidth.getText());
                        scene.camSize.height = Integer.parseInt(screenHeight.getText());
                        scene.camVision = new Hitbox(new Point(0, 0), new Point(scene.camSize.width, scene.camSize.height));
                        scene.display.setBackgroundColor(new Color(Integer.parseInt(BGColor.getText().replace("#", ""), 16)));
                    } catch (NumberFormatException ignore) {
                    }
                });


        //register objects
        addObject(bg);
        addObject(descriptions);
        addObject(screenWidth);
        addObject(screenHeight);
        addObject(BGColor);
        addObject(apply);
    }

    @Override
    public void logicLoop() {
        //attach position to cam
        x = -Graphics.getCamPos().x + initX;
        y = -Graphics.getCamPos().y + initY;

        screenWidth.move(x + 150, y + 50);
        screenHeight.move(x + 150, y + 100);
        BGColor.move(x + 100, y + 200);
        apply.move(x + 130, y + scene.selectorUIDim.height - 100);
    }

    @Override
    public void renderLoop() {
    }
}

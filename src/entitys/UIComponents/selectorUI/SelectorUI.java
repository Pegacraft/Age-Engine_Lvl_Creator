package entitys.UIComponents.selectorUI;

import engine.Entity;
import engine.Game;
import engine.listeners.MouseButtons;
import engine.mechanics.*;
import engine.mechanics.Button;
import engine.rendering.Graphics;
import entitys.RegisteredEntity;
import scenes.MainScene;


import java.awt.*;
import java.util.ArrayList;

import static engine.rendering.Graphics.g;

public class SelectorUI extends Entity {
    //reverences
    MainScene scene;
    //Variables
    int initX, initY;
    TextBox screenWidth, screenHeight, bgColorR, bgColorG, bgColorB;
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
            g.drawString("Background color (RGB):", x + 60, y + 190);
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
        bgColorR = new TextBox(x, y, 50, 20, scene)
                .setBorderColor(Color.black)
                .setMatcher("[^a-z^A-Z]+");
        bgColorG = new TextBox(x, y, 50, 20, scene)
                .setBorderColor(Color.black)
                .setMatcher("[^a-z^A-Z]+");
        bgColorB = new TextBox(x, y, 50, 20, scene)
                .setBorderColor(Color.black)
                .setMatcher("[^a-z^A-Z]+");

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
                        scene.display.setBackgroundColor(new Color(Integer.parseInt(bgColorR.getText()),Integer.parseInt(bgColorG.getText()),Integer.parseInt(bgColorB.getText())));
                    } catch (NumberFormatException ignore) {
                    }
                });



        //register objects
        scene.addObject(bg);
        scene.addObject(descriptions);
        scene.addObject(screenWidth);
        scene.addObject(screenHeight);
        scene.addObject(bgColorR);
        scene.addObject(bgColorG);
        scene.addObject(bgColorB);
        scene.addObject(apply);
    }

    @Override
    public void logicLoop() {
        //attach position to cam
        x = -Graphics.getCamPos().x + initX;
        y = -Graphics.getCamPos().y + initY;

        screenWidth.move(x + 150, y + 50);
        screenHeight.move(x + 150, y + 100);
        bgColorR.move(x + 30, y + 200);
        bgColorG.move(x + 110, y + 200);
        bgColorB.move(x + 190, y + 200);
        apply.move(x + 130, scene.selectorUIDim.height - 100);
    }

    @Override
    public void renderLoop() {
    }
}

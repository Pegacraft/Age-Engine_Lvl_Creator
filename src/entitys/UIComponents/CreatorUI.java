package entitys.UIComponents;

import engine.Entity;
import engine.Game;
import engine.listeners.MouseButtons;
import engine.mechanics.Button;
import engine.mechanics.MethodObject;
import engine.mechanics.TextBox;
import engine.rendering.Graphics;
import entitys.RegisteredEntity;
import scenes.MainScene;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class CreatorUI extends Entity {

    //reverences
    MainScene scene;
    int initX, initY;
    TextBox className, params;
    Button create;

    public CreatorUI(int x, int y) {
        this.initX = x;
        this.initY = y;
    }

    @Override
    public void init() {
        scene = (MainScene) Game.getScene("main");

        className = new TextBox(initX + 100, initY + 20, 200, 20, scene);
        params = new TextBox(initX + 100, initY + 150, 400, 20, scene);
        create = new Button(initX, initY, 70, 30, scene)
                .setColor(Color.darkGray)
                .setHoverColor(Color.blue)
                .setText("Create")
                .addEvent(MouseButtons.LEFT_DOWN, e -> {
                    addToRegistry();
                });

        //Draw Background
        scene.addObject(new MethodObject(scene).execRenderLoop(e -> {
            g.setColor(Color.gray);
            g.fillRect(x, y, scene.creatorUIDim.width, scene.creatorUIDim.height);
        }));

        //Add descriptions
        scene.addObject(new MethodObject(scene).execRenderLoop(e -> {
            g.setColor(Color.black);
            g.setFont(new Font("base", Font.PLAIN, 15));

            g.drawString("Class name:", x + 110, y + 34);
            g.drawString("Constructor parameters:", x + 30, y + 165);
        }));

        scene.addObject(className);
        scene.addObject(params);
        scene.addObject(create);
    }

    @Override
    public void logicLoop() {
        //attach position to cam
        x = -Graphics.getCamPos().x + initX;
        y = -Graphics.getCamPos().y + initY;
        className.move(x + 200, y + 20);
        params.move(x + 200, y + 150);
        create.move(x + scene.creatorUIDim.width - 100, y + 200);
    }

    @Override
    public void renderLoop() {
    }

    private void addToRegistry() {
        //add class to registry
        RegisteredEntity reg = new RegisteredEntity();
        reg.className = className.getText();
        reg.paramString = params.getText().replace(" ", "");
        System.out.println(reg.paramString);
        scene.registeredEntities.add(reg);
        System.out.println("Added to registry!");
    }
}

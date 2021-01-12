package entitys.UIComponents.CreatorUis;

import engine.Entity;
import engine.Game;
import engine.listeners.MouseButtons;
import engine.mechanics.Button;
import engine.mechanics.MethodObject;
import engine.mechanics.TextBox;
import engine.rendering.Graphics;
import entitys.RegisteredEntity;
import entitys.CanvasObject;
import scenes.MainScene;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static engine.rendering.Graphics.g;

public class CustomEntityCreatorUI extends Entity {

    //reverences
    MainScene scene;
    int initX, initY;
    TextBox className, params;
    public Button create;
    public Button export;
    TextBox pkg;

    public CustomEntityCreatorUI(int x, int y) {
        this.initX = x;
        this.initY = y;
    }

    @Override
    public void init() {
        scene = (MainScene) Game.getScene("main");

        pkg = new TextBox(initX + 100, initY + 20, 200, 20, scene);
        className = new TextBox(initX + 200, initY + 20, 200, 20, scene);
        params = new TextBox(initX + 100, initY + 150, 400, 20, scene);
        create = new Button(initX, initY, 70, 30, scene)
                .setColor(Color.darkGray)
                .setHoverColor(Color.blue)
                .setText("Create")
                .addEvent(MouseButtons.LEFT_DOWN, e -> {
                    addToRegistry();
                });

        export = new Button(initX, initY, 70, 30, scene)
                .setColor(Color.darkGray)
                .setHoverColor(Color.blue)
                .setText("Export")
                .addEvent(MouseButtons.LEFT_DOWN, e -> {
                    scene.export();
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

            g.drawString("Package root:", x + 110, y + 34);
            g.drawString("Class name:", x + scene.creatorUIDim.width - 340, y + 104);
            g.drawString("Constructor parameters:", x + 30, y + 165);
        }));

        addObject(className);
        addObject(params);
        addObject(create);
        addObject(pkg);
        addObject(export);
    }

    @Override
    public void logicLoop() {
        //attach position to cam
        x = -Graphics.getCamPos().x + initX;
        y = -Graphics.getCamPos().y + initY;
        pkg.move(x + 200, y + 20);
        params.move(x + 200, y + 150);
        className.move(x + scene.creatorUIDim.width - 250, y + 90);
        create.move(x + scene.creatorUIDim.width - 100, y + 200);
        export.move(x + 10, y + 200);
    }

    @Override
    public void renderLoop() {
    }

    public void delete(){
        create.deleteEvent(MouseButtons.LEFT_DOWN);
        export.deleteEvent(MouseButtons.LEFT_DOWN);
    }

    private void addToRegistry() {
        //add class to registry
        RegisteredEntity reg = new RegisteredEntity();
        reg.className = pkg.getText()+ "." + className.getText();
        reg.type = "CUSTOM";
        if (!params.getText().replace(" ", "").equals(""))
            reg.paramString = params.getText().replace(" ", "");
        else
            reg.paramString = "NONE";
        scene.current = reg;
        System.out.println("Added to registry!");
    }
}

package entitys.UIComponents.CreatorUis;

import engine.Entity;
import engine.Game;
import engine.listeners.MouseButtons;
import engine.mechanics.Button;
import engine.mechanics.MethodObject;
import engine.mechanics.TextBox;
import engine.mechanics.TickBox;
import engine.rendering.Graphics;
import entitys.RegisteredEntity;
import scenes.MainScene;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class TickBoxCreatorUI extends Entity {

    //reverences
    MainScene scene;
    int initX, initY;
    TextBox borderColor, tickColor, tickImage;
    TickBox isTicked;
    Button create;

    public TickBoxCreatorUI(int x, int y) {
        this.initX = x;
        this.initY = y;
    }

    @Override
    public void init() {
        scene = (MainScene) Game.getScene("main");

        //Objects
        borderColor = new TextBox(initX + 100, initY + 20, 200, 20, scene);
        tickColor = new TextBox(initX + 100, initY + 20, 200, 20, scene);
        tickImage = new TextBox(initX + 100, initY + 40, 200, 20, scene);
        isTicked = new TickBox(initX + 100, initY + 60, 20, 20, scene);
        create = new Button(initX, initY, 70, 30, scene)
                .setColor(Color.darkGray)
                .setHoverColor(Color.blue)
                .setText("Create")
                .addEvent(MouseButtons.LEFT_DOWN, e -> {
                    this.addToRegistry();
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

            g.drawString("Border color (Hex):", x + 73, y + 34);
            g.drawString("Tick color (Hex):", x + 90, y + 64);
            g.drawString("Tick image:", x + 90, y + 94);
            g.drawString("Is ticked:", x + 90, y + 124);
        }));

        //add objects
        addObject(borderColor);
        addObject(tickColor);
        addObject(tickImage);
        addObject(isTicked);
        addObject(create);
    }

    @Override
    public void logicLoop() {
        //attach position to cam
        x = -Graphics.getCamPos().x + initX;
        y = -Graphics.getCamPos().y + initY;

        borderColor.move(x + 200, y + 20);
        tickColor.move(x + 200, y + 50);
        tickImage.move(x + 200, y + 80);
        isTicked.move(x + 200, y + 110);
        create.move(x + scene.creatorUIDim.width - 100, y + 200);
    }

    @Override
    public void renderLoop() {
    }

    public void delete() {
        if (create != null)
            create.deleteEvent(MouseButtons.LEFT_DOWN);
    }

    private void addToRegistry() {
        StringBuilder params = new StringBuilder();
        if (!borderColor.getText().replace(" ", "").matches(""))
            params.append(borderColor.getText()).append(",");
        else
            params.append("NONE,");
        if (!tickColor.getText().replace(" ", "").matches(""))
            params.append(tickColor.getText()).append(",");
        else
            params.append("NONE,");
        if (!tickImage.getText().replace(" ", "").matches(""))
            params.append(tickImage.getText()).append(",");
        else
            params.append("NONE,");
            params.append(isTicked.isTicked()).append(",");
        //add class to registry
        RegisteredEntity reg = new RegisteredEntity();
        reg.className = "engine.mechanics.TickBox";
        reg.type = "TICKBOX";
        reg.paramString = params.toString();
        scene.current = reg;
        System.out.println("Added to registry!");
    }
}

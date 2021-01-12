package entitys.UIComponents.CreatorUis;

import engine.Entity;
import engine.Game;
import engine.listeners.MouseButtons;
import engine.mechanics.Button;
import engine.mechanics.MethodObject;
import engine.mechanics.TextBox;
import engine.rendering.Graphics;
import entitys.CanvasObject;
import entitys.RegisteredEntity;
import scenes.MainScene;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static engine.rendering.Graphics.g;

public class TextBoxCreatorUI extends Entity {

    //reverences
    MainScene scene;
    int initX, initY;
    TextBox borderColor, textType, fontSize, maxValue, setText, setMatcher;
    Button create;

    public TextBoxCreatorUI(int x, int y) {
        this.initX = x;
        this.initY = y;
    }

    @Override
    public void init() {
        scene = (MainScene) Game.getScene("main");

        //Objects
        borderColor = new TextBox(initX + 100, initY + 20, 200, 20, scene);
        textType = new TextBox(initX + 100, initY + 20, 200, 20, scene);
        fontSize = new TextBox(initX + 100, initY + 40, 200, 20, scene);
        maxValue = new TextBox(initX + 100, initY + 60, 200, 20, scene);
        setText = new TextBox(initX + 100, initY + 80, 200, 20, scene);
        setMatcher = new TextBox(initX + 100, initY + 10, 200, 20, scene);
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

            g.drawString("Border color:", x + 90, y + 34);
            g.drawString("Text type:", x + 90, y + 64);
            g.drawString("Font size:", x + 90, y + 94);
            g.drawString("Max char value:", x + 90, y + 124);
            g.drawString("Text:", x + 90, y + 154);
            g.drawString("Matcher:", x + 90, y + 184);
        }));

        //add objects
        addObject(borderColor);
        addObject(textType);
        addObject(fontSize);
        addObject(maxValue);
        addObject(setText);
        addObject(setMatcher);
        addObject(create);
    }

    @Override
    public void logicLoop() {
        //attach position to cam
        x = -Graphics.getCamPos().x + initX;
        y = -Graphics.getCamPos().y + initY;

        borderColor.move(x + 200, y + 20);
        textType.move(x + 200, y + 50);
        fontSize.move(x + 200, y + 80);
        maxValue.move(x + 200, y + 110);
        setText.move(x + 200, y + 140);
        setMatcher.move(x + 200, y + 170);
        create.move(x + scene.creatorUIDim.width - 300, y + 200);
    }

    @Override
    public void renderLoop() {
    }

    public void delete() {
        create.deleteEvent(MouseButtons.LEFT_DOWN);
    }

    private void addToRegistry() {
        StringBuilder params = new StringBuilder();
        if (!borderColor.getText().replace(" ", "").matches(""))
            params.append(borderColor.getText()).append(",");
        else
            params.append("NONE,");
        if (!textType.getText().replace(" ", "").matches(""))
            params.append(textType.getText()).append(",");
        else
            params.append("NONE,");
        if (!fontSize.getText().replace(" ", "").matches(""))
            params.append(fontSize.getText()).append(",");
        else
            params.append("NONE,");
        if (!maxValue.getText().replace(" ", "").matches(""))
            params.append(maxValue.getText()).append(",");
        else
            params.append("NONE,");
        if (!setText.getText().replace(" ", "").matches(""))
            params.append(setText.getText()).append(",");
        else
            params.append("NONE,");
        if (!setMatcher.getText().replace(" ", "").matches(""))
            params.append(setMatcher.getText()).append(",");
        else
            params.append("NONE,");
        //add class to registry
        RegisteredEntity reg = new RegisteredEntity();
        reg.className = "engine.mechanics.TextBox";
        reg.type = "TEXTBOX";
//        reg.paramString = params.toString().replace(" ", "");
//        reg.paramString = params.toString().replace("", "NONE");
        reg.paramString = params.toString();
        scene.current = reg;
        System.out.println("Added to registry!");
    }
}

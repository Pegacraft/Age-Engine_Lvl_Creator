package entitys.UIComponents.CreatorUis;

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

public class ButtonCreatorUI extends Entity {

    //reverences
    MainScene scene;
    int initX, initY;
    TextBox color, hoverColor, text, font, fontSize, textColor;
    Button create;

    public ButtonCreatorUI(int x, int y) {
        this.initX = x;
        this.initY = y;
    }

    @Override
    public void init() {
        scene = (MainScene) Game.getScene("main");

        //Objects
        color = new TextBox(initX + 100, initY + 20, 200, 20, scene);
        hoverColor = new TextBox(initX + 100, initY + 20, 200, 20, scene);
        text = new TextBox(initX + 100, initY + 40, 200, 20, scene);
        font = new TextBox(initX + 100, initY + 60, 200, 20, scene);
        fontSize = new TextBox(initX + 100, initY + 80, 200, 20, scene);
        textColor = new TextBox(initX + 100, initY + 10, 200, 20, scene);
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

            g.drawString("Color (Hex):", x + 90, y + 34);
            g.drawString("Hover color (Hex):", x + 70, y + 64);
            g.drawString("Text:", x + 90, y + 94);
            g.drawString("Font:", x + 90, y + 124);
            g.drawString("Font size:", x + 90, y + 154);
            g.drawString("Text color (Hex):", x + 90, y + 184);
        }));

        //add objects
        addObject(color);
        addObject(hoverColor);
        addObject(text);
        addObject(font);
        addObject(fontSize);
        addObject(textColor);
        addObject(create);
    }

    @Override
    public void logicLoop() {
        //attach position to cam
        x = -Graphics.getCamPos().x + initX;
        y = -Graphics.getCamPos().y + initY;

        color.move(x + 200, y + 20);
        hoverColor.move(x + 200, y + 50);
        text.move(x + 200, y + 80);
        font.move(x + 200, y + 110);
        fontSize.move(x + 200, y + 140);
        textColor.move(x + 200, y + 170);
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
        if (!color.getText().replace(" ", "").matches(""))
            params.append(color.getText()).append(",");
        else
            params.append("NONE,");
        if (!hoverColor.getText().replace(" ", "").matches(""))
            params.append(hoverColor.getText()).append(",");
        else
            params.append("NONE,");
        if (!text.getText().replace(" ", "").matches(""))
            params.append(text.getText()).append(",");
        else
            params.append("NONE,");
        if (!font.getText().replace(" ", "").matches(""))
            params.append(font.getText()).append(",");
        else
            params.append("NONE,");
        if (!fontSize.getText().replace(" ", "").matches(""))
            params.append(fontSize.getText()).append(",");
        else
            params.append("NONE,");
        if (!textColor.getText().replace(" ", "").matches(""))
            params.append(textColor.getText()).append(",");
        else
            params.append("NONE,");
        //add class to registry
        RegisteredEntity reg = new RegisteredEntity();
        reg.className = "engine.mechanics.Button";
        reg.type = "BUTTON";
        reg.paramString = params.toString();
        scene.current = reg;
        System.out.println("Added to registry!");
    }
}

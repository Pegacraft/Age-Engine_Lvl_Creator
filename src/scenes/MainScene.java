package scenes;

import engine.Scene;
import engine.mechanics.EntityList;
import engine.mechanics.Hitbox;
import engine.mechanics.TextBox;
import engine.rendering.Graphics;
import entitys.RegisteredEntity;
import entitys.UIComponents.CreatorUI;
import entitys.UIComponents.SelectorUI;

import static engine.rendering.Graphics.g;

import java.awt.*;
import java.util.ArrayList;

public class MainScene extends Scene {
    //Variables (Values)
    public Dimension canvasSize = new Dimension(1280, 720);
    public Point camPos = new Point(0, 0);
    public int panSpeed = 10;

    //Fixed vars
    public final Dimension creatorUIDim = new Dimension(canvasSize.width, 300);
    public final Dimension selectorUIDim = new Dimension(300, canvasSize.height + creatorUIDim.height);

    //Variables (Objects)
    public Hitbox canvas = new Hitbox(new Point(0, 0), new Point(canvasSize.width, canvasSize.height));
    public CreatorUI creatorUI = new CreatorUI(0, canvasSize.height);
    public SelectorUI selectorUI = new SelectorUI(canvasSize.width, 0);

    //Variables (Lists)
    public ArrayList<RegisteredEntity> registeredEntities = new ArrayList<>();
    public EntityList placedEntities = new EntityList();

    //Variables end
    @Override
    public void init() {
        setDisplaySize();

        //add objects to scene
        //entities

        //UI components
        addObject(creatorUI);
        addObject(selectorUI);

        //add objects to scene end
    }

    @Override
    public void logicLoop() {
        //canvas Panning
        if(keyListener.isHeld('W'))
            camPos.y -= panSpeed;
        if(keyListener.isHeld('A'))
            camPos.x -= panSpeed;
        if(keyListener.isHeld('D'))
            camPos.x += panSpeed;
        if(keyListener.isHeld('S'))
            camPos.y += panSpeed;

        Graphics.moveCam(camPos.x, camPos.y);

        //align canvas to cam
        canvas.move(-Graphics.getCamPos().x, -Graphics.getCamPos().y);

    }

    @Override
    public void renderLoop() {
        //Show canvas hitbox
        g.setColor(Color.red);
        g.draw(canvas.getShape());
    }

    private void setDisplaySize(){
        //set display to fitting dimensions
        Graphics.stdHeight = canvasSize.height + creatorUIDim.height;
        Graphics.stdWidth = canvasSize.width + selectorUIDim.width;
        display.setSize(canvasSize.width + selectorUIDim.width, canvasSize.height + creatorUIDim.height);
    }
}

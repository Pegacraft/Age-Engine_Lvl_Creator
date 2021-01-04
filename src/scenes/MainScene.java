package scenes;

import engine.Scene;
import engine.mechanics.Hitbox;
import engine.rendering.Graphics;
import entitys.UIComponents.CreatorUI;
import entitys.UIComponents.SelectorUI;

import static engine.rendering.Graphics.g;

import java.awt.*;

public class MainScene extends Scene {
    //Variables (Values)
    public Dimension canvasSize = new Dimension(1280, 720);

    //Fixed vars
    public final Dimension creatorUIDim = new Dimension(canvasSize.width, 300);
    public final Dimension selectorUIDim = new Dimension(300, canvasSize.height);

    //Variables (Objects)
    public Hitbox canvas = new Hitbox(new Point(0, 0), new Point(canvasSize.width, canvasSize.height));
    public CreatorUI creatorUI = new CreatorUI(0, canvasSize.height);
    public SelectorUI selectorUI = new SelectorUI(canvasSize.width, 0);

    //Variables end
    @Override
    public void init() {
        setDisplaySize();

        //add objects to scene
        addObject(creatorUI);
        addObject(selectorUI);
    }

    @Override
    public void logicLoop() {

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

package scenes;

import engine.Scene;
import engine.listeners.MouseButtons;
import engine.mechanics.EntityList;
import engine.mechanics.Hitbox;
import engine.rendering.Graphics;
import entitys.RegisteredEntity;
import entitys.UIComponents.CreatorUI;
import entitys.UIComponents.selectorUI.CanvasObject;
import entitys.UIComponents.selectorUI.SelectorUI;

import static engine.rendering.Graphics.g;

import java.awt.*;
import java.util.ArrayList;

public class MainScene extends Scene {
    //Variables (Values)
    public Dimension canvasSize = new Dimension(1280, 720);
    public Point camPos = new Point(0, 0);
    public int panSpeed = 10;
    public boolean isDrawing = false;
    int x1, y1;

    //Fixed vars
    public final Dimension creatorUIDim = new Dimension(canvasSize.width, 300);
    public final Dimension selectorUIDim = new Dimension(300, canvasSize.height + creatorUIDim.height);

    //Variables (Objects)
    public Hitbox canvas = new Hitbox(new Point(0, 0), new Point(canvasSize.width, canvasSize.height));
    public CreatorUI creatorUI = new CreatorUI(0, canvasSize.height);
    public SelectorUI selectorUI = new SelectorUI(canvasSize.width, 0);
    private CanvasObject draw;

    //Variables (Lists)
    //public ArrayList<RegisteredEntity> registeredEntities = new ArrayList<>();
    public RegisteredEntity current = null;
    public EntityList placedEntities = new EntityList();

    //Variables end
    @Override
    public void init() {
        setDisplaySize();
        //events
        mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (canvas.isInside(mouseListener.getMousePos()) && current != null) {
                isDrawing = !isDrawing;
                if (isDrawing) {
                    draw = new CanvasObject(mouseListener.getMousePos().x, mouseListener.getMousePos().y, 0, 0, null, current.className, current.paramString);
                    x1 = mouseListener.getMousePos().x;
                    y1 = mouseListener.getMousePos().y;
                    placedEntities.add(draw);
                }
            }
        }, false);

        mouseListener.addEvent(MouseButtons.RIGHT_DOWN, e -> {
            if (canvas.isInside(mouseListener.getMousePos())) {
                placedEntities.getEntityList().removeIf(r -> {
                    return ((CanvasObject) r).hitbox.isInside(mouseListener.getMousePos());
                });
            }
        }, false);

        //add objects to scene
        //entities
        addObject(placedEntities);

        //UI components
        addObject(creatorUI);
        addObject(selectorUI);

        //add objects to scene end
    }

    @Override
    public void logicLoop() {
        //canvas Panning
        if (keyListener.isHeld('W'))
            camPos.y += panSpeed;
        if (keyListener.isHeld('A'))
            camPos.x += panSpeed;
        if (keyListener.isHeld('D'))
            camPos.x -= panSpeed;
        if (keyListener.isHeld('S'))
            camPos.y -= panSpeed;

        Graphics.moveCam(camPos.x, camPos.y);

        //align canvas to cam
        canvas.move(-Graphics.getCamPos().x, -Graphics.getCamPos().y);

        drawToCanvas();

    }

    @Override
    public void renderLoop() {
        //Show canvas hitbox
        g.setColor(Color.red);
        g.draw(canvas.getShape());
    }

    private void setDisplaySize() {
        //set display to fitting dimensions
        Graphics.stdHeight = canvasSize.height + creatorUIDim.height;
        Graphics.stdWidth = canvasSize.width + selectorUIDim.width;
        display.setSize(canvasSize.width + selectorUIDim.width, canvasSize.height + creatorUIDim.height);
    }

    private void drawToCanvas() {
        if (canvas.isInside(mouseListener.getMousePos())) {
            if (isDrawing) {
                int calcWidth = Math.abs(x1 - mouseListener.getMousePos().x);
                int calcHeight = Math.abs(y1 - mouseListener.getMousePos().y);

                int x2 = mouseListener.getMousePos().x, y2 = mouseListener.getMousePos().y;

                if (x1 <= x2 && y1 <= y2) {
                    draw.x = x1;
                    draw.y = y1;
                }
                if (x1 >= x2 && y1 >= y2) {
                    draw.x = x2;
                    draw.y = y2;
                }
                if (x1 <= x2 && y1 >= y2) {
                    draw.x = x1;
                    draw.y = y1 - calcHeight;
                }
                if (x1 >= x2 && y1 <= y2) {
                    draw.x = x1 - calcWidth;
                    draw.y = y1;
                }

                draw.width = calcWidth;
                draw.height = calcHeight;
            }
        }
    }
}

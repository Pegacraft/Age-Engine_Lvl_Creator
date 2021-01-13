package scenes;

import engine.Entity;
import engine.Scene;
import engine.listeners.MouseButtons;
import engine.mechanics.Button;
import engine.mechanics.DropDownMenu;
import engine.mechanics.EntityList;
import engine.mechanics.Hitbox;
import engine.mechanics.MethodObject;
import engine.rendering.Graphics;
import entitys.RegisteredEntity;
import entitys.UIComponents.CreatorUis.ButtonCreatorUI;
import entitys.UIComponents.CreatorUis.CustomEntityCreatorUI;
import entitys.CanvasObject;
import entitys.UIComponents.CreatorUis.TextBoxCreatorUI;
import entitys.UIComponents.CreatorUis.TickBoxCreatorUI;
import entitys.UIComponents.SelectorUIs.SelectorUI;

import static engine.rendering.Graphics.g;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainScene extends Scene {
    //Variables (Values)
    public Dimension canvasSize = new Dimension(1280, 720);
    public Dimension camSize = new Dimension(1280, 720);
    public Point camPos = new Point(0, 0);
    public int panSpeed = 10;
    public boolean isDrawing = false;
    int x1, y1;

    //Fixed vars
    public final Dimension creatorUIDim = new Dimension(canvasSize.width, 300);
    public final Dimension selectorUIDim = new Dimension(300, canvasSize.height + creatorUIDim.height);
    public final Color subButtonColor1 = new Color(90, 90, 90);
    public final Color subButtonColor2 = new Color(111, 111, 111);

    //Variables (Objects)
    public Hitbox canvas = new Hitbox(new Point(0, 0), new Point(canvasSize.width, canvasSize.height));
    public CustomEntityCreatorUI customEntityCreatorUI = new CustomEntityCreatorUI(0, canvasSize.height);
    public TextBoxCreatorUI textBoxCreatorUI = new TextBoxCreatorUI(0, canvasSize.height);
    public ButtonCreatorUI buttonCreatorUI = new ButtonCreatorUI(0, canvasSize.height);
    public TickBoxCreatorUI tickBoxCreatorUI = new TickBoxCreatorUI(0, canvasSize.height);
    public SelectorUI selectorUI = new SelectorUI(canvasSize.width, 0);
    private CanvasObject draw;
    public Hitbox camVision = new Hitbox(new Point(0, 0), new Point(camSize.width, camSize.height));
    public DropDownMenu selectType;
    public Entity currentSelectorUI = selectorUI;
    public Entity currentCreatorUI = customEntityCreatorUI;

    //Variables (Lists);
    public RegisteredEntity current = null;
    public EntityList placedEntities = new EntityList();

    //Variables end
    @Override
    public void init() {
        selectType = new DropDownMenu(new Button(10, canvasSize.height + 10, 40, 30, this)
                .setColor(Color.darkGray)
                .setHoverColor(Color.blue)
                .setText("Type"))
                .addDropDownButton(new Button(0, 0, 70, 30, this)
                        .setColor(subButtonColor1)
                        .setHoverColor(Color.blue)
                        .setText("Custom Entity")
                        .addEvent(MouseButtons.LEFT_DOWN, e -> {
                            removeAll();
                            currentCreatorUI = customEntityCreatorUI;
                            redraw();
                            selectType.closeMenu();
                        })
                )
                .addDropDownButton(new Button(0, 0, 70, 30, this)
                        .setColor(subButtonColor2)
                        .setHoverColor(Color.blue)
                        .setText("Text Box")
                        .addEvent(MouseButtons.LEFT_DOWN, e -> {
                            removeAll();
                            currentCreatorUI = textBoxCreatorUI;
                            redraw();
                            selectType.closeMenu();
                        }))
                .addDropDownButton(new Button(0, 0, 70, 30, this)
                        .setColor(subButtonColor1)
                        .setHoverColor(Color.blue)
                        .setText("Button")
                        .addEvent(MouseButtons.LEFT_DOWN, e -> {
                            removeAll();
                            currentCreatorUI = buttonCreatorUI;
                            redraw();
                            selectType.closeMenu();
                        }))
                .addDropDownButton(new Button(0, 0, 70, 30, this)
                        .setColor(subButtonColor2)
                        .setHoverColor(Color.blue)
                        .setText("Tick box")
                        .addEvent(MouseButtons.LEFT_DOWN, e -> {
                            removeAll();
                            currentCreatorUI = tickBoxCreatorUI;
                            redraw();
                            selectType.closeMenu();
                        }));
        setDisplaySize();
        //events
        mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (canvas.isInside(mouseListener.getMousePos()) && current != null) {
                isDrawing = !isDrawing;
                if (isDrawing) {
                    draw = new CanvasObject(mouseListener.getMousePos().x,
                            mouseListener.getMousePos().y,
                            0,
                            0,
                            null,
                            current.className,
                            current.paramString,
                            current.type,
                            placedEntities.getEntityList().indexOf(draw));
                    x1 = mouseListener.getMousePos().x;
                    y1 = mouseListener.getMousePos().y;
                    placedEntities.add(draw);
                    placedEntities.getEntityList().forEach(o -> {
                        CanvasObject placedEntity = (CanvasObject) o;

                        placedEntity.reverence = placedEntities.getEntityList().indexOf(o);
                    });
                }
            }
        }, false);

        mouseListener.addEvent(MouseButtons.RIGHT_DOWN, e -> {
            if (isDrawing) {
                placedEntities.getEntityList().remove(placedEntities.getEntityList().size() - 1);
                isDrawing = false;
            }

            if (canvas.isInside(mouseListener.getMousePos())) {
                placedEntities.getEntityList().removeIf(r -> ((CanvasObject) r).hitbox.isInside(mouseListener.getMousePos()));
            }
        }, false);

        //add objects to scene
        //entities
        addObject(placedEntities);

        addObject(new MethodObject(this).execRenderLoop(e -> {
            g.setColor(Color.cyan);
            g.draw(camVision.getShape());
        }));

        //UI components
        addObject(currentCreatorUI);
        addObject(currentSelectorUI);
        addObject(selectType);

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
        camVision.move(-Graphics.getCamPos().x, -Graphics.getCamPos().y);

        selectType.move(-Graphics.getCamPos().x + 10, -Graphics.getCamPos().y + canvasSize.height + 10);

        drawToCanvas();

    }

    private void redraw() {
        addObject(placedEntities);

        addObject(new MethodObject(this).execRenderLoop(e -> {
            g.setColor(Color.cyan);
            g.draw(camVision.getShape());
        }));

        addObject(currentCreatorUI);
        addObject(currentSelectorUI);
        addObject(selectType);
    }

    public void export() {
        //Create file
        File exportFile = new File("export.age");
        try {
            exportFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Write to file
        try {
            FileWriter writer = new FileWriter("export.age");
            //save scene
            StringBuilder saveTxt = new StringBuilder();

            placedEntities.getEntityList().forEach(entity -> {
                saveTxt.append(((CanvasObject) entity).type).append("~");
                saveTxt.append(((CanvasObject) entity).className).append("~");
                saveTxt.append(entity.x).append("~");
                saveTxt.append(entity.y).append("~");
                saveTxt.append(entity.width).append("~");
                saveTxt.append(entity.height).append("~");
                saveTxt.append(((CanvasObject) entity).paramString);
                saveTxt.append("\n");
            });

            System.out.println("Exported!");

            writer.write(saveTxt.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renderLoop() {
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

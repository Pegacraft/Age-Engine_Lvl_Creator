package entitys.UIComponents.selectorUI;

import engine.Entity;
import engine.mechanics.Hitbox;

import static engine.rendering.Graphics.g;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CanvasObject extends Entity {
    BufferedImage previewImage;
    public String className;
    public Hitbox hitbox = new Hitbox(new Point(x, y), new Point(x + width, y + height));;
    public String paramString;

    public CanvasObject(int x, int y, int width, int height, BufferedImage previewImage, String className, String paramString) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.previewImage = previewImage;
        this.className = className;
        this.paramString = paramString;
    }

    @Override
    public void init() {

    }

    @Override
    public void logicLoop() {
        hitbox = new Hitbox(new Point(x, y), new Point(x + width, y + height));

    }

    @Override
    public void renderLoop() {
        g.setColor(Color.white);
        g.draw(hitbox.getShape());
        g.drawString(className, x, y + 15);
    }
}

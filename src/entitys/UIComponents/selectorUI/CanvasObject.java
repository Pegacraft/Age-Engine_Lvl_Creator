package entitys.UIComponents.selectorUI;

import engine.Entity;

import static engine.rendering.Graphics.g;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CanvasObject extends Entity {
    BufferedImage previewImage;
    String className;

    public CanvasObject(int x, int y, int width, int height, BufferedImage previewImage, String className) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.previewImage = previewImage;
        this.className = className;
    }

    @Override
    public void init() {

    }

    @Override
    public void logicLoop() {

    }

    @Override
    public void renderLoop() {
        g.setColor(Color.white);
        g.drawRect(x, y, width, height);
    }
}

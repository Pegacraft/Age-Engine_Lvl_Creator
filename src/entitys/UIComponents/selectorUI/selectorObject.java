package entitys.UIComponents.selectorUI;

import engine.Entity;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class selectorObject extends Entity {
    String className;

    public selectorObject(int x, int y, int width, int height, String className) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
        g.setColor(Color.white);
        g.drawString(className, x, y);
    }
}

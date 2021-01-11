package entitys;

import engine.Entity;
import engine.rendering.Image;

import java.awt.image.BufferedImage;

public class RegisteredEntity extends Entity {

    //Variables
    public String className = "";
    public String paramString = "";
    public BufferedImage previewImage = Image.load("preview.png");
    public int width = 100, height = 100;
    public String type = "";
    //Variables end

    @Override
    public void init() {

    }

    @Override
    public void logicLoop() {

    }

    @Override
    public void renderLoop() {

    }
}

package entitys;

import engine.Entity;
import engine.rendering.Image;

import java.awt.image.BufferedImage;

public class RegisteredEntity extends Entity {

    //Variables
    public String className = "";
    public String paramString = "";
    public BufferedImage previewImage = Image.load("preview.png");
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

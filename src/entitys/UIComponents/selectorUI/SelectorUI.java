package entitys.UIComponents.selectorUI;

import engine.Entity;
import engine.Game;
import engine.mechanics.Button;
import engine.mechanics.Grid;
import engine.mechanics.MethodObject;
import engine.rendering.Graphics;
import entitys.RegisteredEntity;
import scenes.MainScene;


import java.awt.*;
import java.util.ArrayList;

import static engine.rendering.Graphics.g;

public class SelectorUI extends Entity {
    //reverences
    MainScene scene;
    //Variables
    int current_page = 0;
    int initX, initY;
    Button next, prev;
    ArrayList<ArrayList<RegisteredEntity>> pages = new ArrayList<>();
    Grid page_grid;

    public SelectorUI(int x, int y) {
        this.initX = x;
        this.initY = y;
    }

    @Override
    public void init() {
        scene = (MainScene) Game.getScene("main");
        MethodObject bg = new MethodObject(scene).execRenderLoop(e -> {
            //Draw Background
            g.setColor(Color.gray);
            g.fillRect(x, y, scene.selectorUIDim.width, scene.selectorUIDim.height);
        });

        //Grid
        page_grid = new Grid(x, y, scene.selectorUIDim.width, scene.selectorUIDim.height - 100, 6, 2);
        page_grid.show(Color.red);

        //Buttons
        prev = new Button(x, scene.selectorUIDim.height - 100, scene.selectorUIDim.width / 2, 100, scene)
                .setColor(Color.darkGray)
                .setText("Prev")
                .setHoverColor(Color.blue);

        next = new Button(x + scene.selectorUIDim.width / 2, scene.selectorUIDim.height - 100, scene.selectorUIDim.width / 2, 100, scene)
                .setColor(Color.darkGray)
                .setText("Next")
                .setHoverColor(Color.blue);

        //Add objects to scene
        scene.addObject(bg);
        scene.addObject(prev);
        scene.addObject(next);
        scene.addObject(page_grid);
    }

    @Override
    public void logicLoop() {
        //attach position to cam
        x = -Graphics.getCamPos().x + initX;
        y = -Graphics.getCamPos().y + initY;
        prev.move(x, y + scene.selectorUIDim.height - 100);
        next.move(x + scene.selectorUIDim.width / 2, y + scene.selectorUIDim.height - 100);
        page_grid.move(x, y);

        //Logic
        //splitInPages();
    }

    @Override
    public void renderLoop() {
    }

    private void splitInPages() {

    }

    private void load_page() {
        Dimension objectSize = new Dimension(scene.selectorUIDim.width / 2, scene.selectorUIDim.height / 6);
        for (int i = 0; i < pages.get(current_page).size(); i++) {
            //Gewi macht den teil :3
        }
    }
}

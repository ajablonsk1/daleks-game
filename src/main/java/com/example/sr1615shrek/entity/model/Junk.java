package com.example.sr1615shrek.entity.model;

import com.example.sr1615shrek.collisions.visitors.JunkVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.StaticEntity;
import com.example.sr1615shrek.entity.position.Vector2d;
import javafx.scene.image.Image;

import java.util.Objects;

public class Junk extends StaticEntity {

    private final JunkVisitor junkVisitor;

    private Image graphics;

    private static String path = "images/junk.png";

    public Junk(Vector2d position,
                JunkVisitor junkVisitor) {
        super(position);
        this.junkVisitor = junkVisitor;
        setGraphics(this.path);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.collisionWithJunk(this);
    }

    @Override
    public void collision(Entity entity) {
        entity.accept(junkVisitor);
    }

    @Override
    public Image getGraphics() {
        return this.graphics;
    }

    private void setGraphics(String path){
        this.graphics = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
    }

}

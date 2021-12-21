package com.example.sr1615shrek.entity;

import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public interface Entity {

    void accept(Visitor visitor);

    void collision(Entity entity);

    /*
    For now, it returns string because we don't
    know how the entities will be visualized
    */
    Image getGraphics();

    Vector2d getPosition();

    void setPosition(Vector2d position);
}

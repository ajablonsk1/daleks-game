package com.example.sr1615shrek.entity.model;

import com.example.sr1615shrek.collisions.visitors.DoctorVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import javafx.scene.image.Image;

import java.util.Objects;

public class Doctor extends DynamicEntity {

    private DoctorVisitor doctorVisitor;

    private Image graphics;

    private static String graphicPath = "images/shrek.png";

    private boolean isAlive;

    public Doctor(Vector2d position,
                  BehaviorSubject<DynamicEntity> entityMoveSubject,
                  DoctorVisitor doctorVisitor) {
        super(position, entityMoveSubject);
        this.doctorVisitor = doctorVisitor;
        isAlive = true;

        setGraphics(graphicPath);
    }

    public void passingAway(){
        // GAME OVER
        isAlive = false;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.collisionWithDoctor(this);
    }

    @Override
    public void collision(Entity entity) {
        entity.accept(doctorVisitor);
    }

    @Override
    public Image getGraphics() {
        return this.graphics;
    }

    private void setGraphics(String path){
        this.graphics = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
    }

    public boolean isAlive() {
        return isAlive;
    }
}

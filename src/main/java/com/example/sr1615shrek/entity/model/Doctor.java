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

    private static String path = "images/person.png";

    private boolean isAlive;

    public Doctor(Vector2d position,
                  BehaviorSubject<DynamicEntity> positionSubject,
                  DoctorVisitor doctorVisitor) {
        super(position, positionSubject);
        this.doctorVisitor = doctorVisitor;
        isAlive = true;

        setGraphics(this.path);
    }

    public void passingAway(){
        // GAME OVER
        isAlive = false;
    }

    private void setGraphics(String path){
        this.graphics = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
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

    public boolean isAlive() {
        return isAlive;
    }
}

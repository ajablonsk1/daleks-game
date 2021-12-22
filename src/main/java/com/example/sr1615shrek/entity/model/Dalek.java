package com.example.sr1615shrek.entity.model;

import com.example.sr1615shrek.collisions.visitors.DalekVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import javafx.scene.image.Image;

import java.util.Objects;

public class Dalek extends DynamicEntity {

    private final BehaviorSubject<Dalek> deadDaleksSubject;

    private final DalekVisitor dalekVisitor;

    private Image graphics;

    private static String graphicPath = "images/robot.png";

    public Dalek(Vector2d position,
                 BehaviorSubject<DynamicEntity> entityMoveSubject,
                 BehaviorSubject<Dalek> deadDaleksSubject,
                 DalekVisitor dalekVisitor) {
        super(position, entityMoveSubject);
        this.deadDaleksSubject = deadDaleksSubject;
        this.dalekVisitor = dalekVisitor;
        setGraphics(graphicPath);
    }

    @Override
    public void move(Vector2d vector2d) {
        Vector2d goToVector =  vector2d.subtract(this.getPosition()).getUnitVector();
        super.move(goToVector);
    }

    public void passingAway() {
        deadDaleksSubject.onNext(this);
    }

    private void setGraphics(String path){
        this.graphics = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.collisionWithDalek(this);
    }

    @Override
    public void collision(Entity entity) {
        entity.accept(this.dalekVisitor);
    }

    @Override
    public Image getGraphics() {
        return this.graphics;
    }
}

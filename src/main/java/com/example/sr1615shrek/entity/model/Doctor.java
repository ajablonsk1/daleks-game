package com.example.sr1615shrek.entity.model;

import com.example.sr1615shrek.collisions.visitors.DoctorVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class Doctor extends DynamicEntity {

    private DoctorVisitor doctorVisitor;

    private final String graphics = "Dr";

    public Doctor(Vector2d position,
                  BehaviorSubject<DynamicEntity> positionSubject,
                  DoctorVisitor doctorVisitor) {
        super(position, positionSubject);
        this.doctorVisitor = doctorVisitor;
    }

    public void passingAway(){
        // GAME OVER
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
    public String getGraphics() {
        return this.graphics;
    }
}

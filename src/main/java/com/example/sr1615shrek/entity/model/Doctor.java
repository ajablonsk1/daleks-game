package com.example.sr1615shrek.entity.model;

import com.example.sr1615shrek.collisions.visitors.DoctorVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.model.powerups.Teleport;
import com.example.sr1615shrek.entity.model.powerups.TimeReverse;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Doctor extends DynamicEntity {

    private DoctorVisitor doctorVisitor;

    private boolean isAlive;

    private List<Teleport> teleportList = new ArrayList<>();

    private List<TimeReverse> timeReverseList = new ArrayList<>();

    public Doctor(Vector2d position,
                  BehaviorSubject<DynamicEntity> entityMoveSubject,
                  DoctorVisitor doctorVisitor) {
        super(position, entityMoveSubject);
        this.doctorVisitor = doctorVisitor;
        isAlive = true;
    }

    public void passingAway(){
        // GAME OVER
        isAlive = false;
    }

    public void useTeleport(){
        teleportList.get(0).teleport(this);
        teleportList.remove(0);
    }

    public void useTimeReverse(){
        timeReverseList.remove(0);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.collisionWithDoctor(this);
    }

    @Override
    public void collision(Entity entity) {
        entity.accept(doctorVisitor);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public List<Teleport> getTeleportList() {
        return teleportList;
    }

    public List<TimeReverse> getTimeReverseList() {
        return timeReverseList;
    }

    public void addTeleport(Teleport teleport){
        teleportList.add(teleport);
    }

    public void addTimeReverse(TimeReverse timeReverse){
        timeReverseList.add(timeReverse);
    }

    public int getTeleportNumber(){
        return this.teleportList.size();
    }

    public int getTimeReverseNumber(){
        return this.timeReverseList.size();
    }

    public TimeReverse getTimeReverse(){
        return this.timeReverseList.get(0);
    }
}

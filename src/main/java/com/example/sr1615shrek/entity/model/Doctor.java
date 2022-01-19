package com.example.sr1615shrek.entity.model;

import com.example.sr1615shrek.collisions.visitors.DoctorVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.model.powerups.PowerUp;
import com.example.sr1615shrek.entity.model.powerups.Teleport;
import com.example.sr1615shrek.entity.model.powerups.TimeReverse;
import com.example.sr1615shrek.entity.position.Vector2d;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends DynamicEntity {

    private final DoctorVisitor doctorVisitor;

    private boolean isAlive;

    private final List<PowerUp> teleportList = new ArrayList<>();

    private final List<PowerUp> timeReverseList = new ArrayList<>();

    private final static int FIRST_ELEMENT = 0;

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
        teleportList.get(FIRST_ELEMENT).execute(this);
        teleportList.remove(FIRST_ELEMENT);
    }

    public void removeTimeReverseFromEq(){
        timeReverseList.remove(FIRST_ELEMENT);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isTeleportListEmpty() {
        return teleportList.isEmpty();
    }

    public boolean isTimeReverseListEmpty() {
        return timeReverseList.isEmpty();
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

    public PowerUp getTimeReverse(){
        return this.timeReverseList.get(FIRST_ELEMENT);
    }

    public List<PowerUp> getTimeReverseList() {
        return timeReverseList;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.collisionWithDoctor(this);
    }

    @Override
    public void collision(Entity entity) {
        entity.accept(doctorVisitor);
    }
}

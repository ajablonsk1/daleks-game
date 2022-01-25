package com.example.sr1615shrek.entity.model.powerups;

import com.example.sr1615shrek.collisions.visitors.PowerUpVisitor;
import com.example.sr1615shrek.collisions.visitors.Visitor;
import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.StaticEntity;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.PositionRandomizer;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class Teleport extends StaticEntity implements PowerUp {

    private final PowerUpVisitor powerUpVisitor;

    private final BehaviorSubject<Teleport> deadTeleportSubject;

    private final PositionRandomizer positionRandomizer;

    public Teleport(Vector2d position,
                    PowerUpVisitor powerUpVisitor,
                    BehaviorSubject<Teleport> deadTeleportSubject,
                    PositionRandomizer positionRandomizer) {
        super(position);
        this.powerUpVisitor = powerUpVisitor;
        this.deadTeleportSubject = deadTeleportSubject;
        this.positionRandomizer = positionRandomizer;
    }

    @Override
    public void execute(DynamicEntity doctor){
        Vector2d newPosition = this.positionRandomizer.getRandomPosition();
        doctor.moveOnSpecificPositionAndAddLastPosition(newPosition);
    }

    @Override
    public void onPowerUpPickUp(){
        deadTeleportSubject.onNext(this);
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.collisionWithTeleport(this);
    }

    @Override
    public void collision(Entity entity) {
        entity.accept(this.powerUpVisitor);
    }
}

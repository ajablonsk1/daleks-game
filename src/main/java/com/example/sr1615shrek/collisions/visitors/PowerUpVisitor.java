package com.example.sr1615shrek.collisions.visitors;

import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.model.Junk;
import com.example.sr1615shrek.entity.model.powerups.Teleport;
import com.example.sr1615shrek.entity.model.powerups.TimeReverse;
import org.springframework.stereotype.Component;

@Component
public class PowerUpVisitor implements Visitor{

    @Override
    public void collisionWithDoctor(Doctor doctor) {
    }

    @Override
    public void collisionWithJunk(Junk junk) {

    }

    @Override
    public void collisionWithDalek(Dalek dalek) {
        dalek.moveBack();
    }

    @Override
    public void collisionWithTeleport(Teleport teleport) {

    }

    @Override
    public void collisionWithTimeReverse(TimeReverse timeReverse) {

    }
}

package com.example.sr1615shrek.collisions.visitors;

import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.model.Junk;

public interface Visitor {

    void collisionWithDoctor(Doctor doctor);

    void collisionWithJunk(Junk junk);

    void collisionWithDalek(Dalek dalek);
}

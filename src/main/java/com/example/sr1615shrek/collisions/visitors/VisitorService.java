package com.example.sr1615shrek.collisions.visitors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorService {

    private final DalekVisitor dalekVisitor;

    private  final DoctorVisitor doctorVisitor;

    private final JunkVisitor junkVisitor;

    private final PowerUpVisitor powerUpVisitor;

    @Autowired
    public VisitorService(DalekVisitor dalekVisitor,
                          DoctorVisitor doctorVisitor,
                          JunkVisitor junkVisitor,
                          PowerUpVisitor powerUpVisitor){
        this.dalekVisitor = dalekVisitor;
        this.doctorVisitor = doctorVisitor;
        this.junkVisitor = junkVisitor;
        this.powerUpVisitor = powerUpVisitor;
    }

    public DalekVisitor getDalekVisitor(){
        return this.dalekVisitor;
    }

    public DoctorVisitor getDoctorVisitor(){
        return this.doctorVisitor;
    }

    public PowerUpVisitor getPowerUpVisitor() {
        return powerUpVisitor;
    }

    public JunkVisitor getJunkVisitor() {
        return junkVisitor;
    }
}

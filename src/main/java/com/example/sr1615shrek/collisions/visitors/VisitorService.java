package com.example.sr1615shrek.collisions.visitors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorService {

    private final DalekVisitor dalekVisitor;

    private  final DoctorVisitor doctorVisitor;

    private final JunkVisitor junkVisitor;

    private final TeleportVisitor teleportVisitor;

    private final TimeReverseVisitor timeReverseVisitor;

    @Autowired
    public VisitorService(DalekVisitor dalekVisitor,
                          DoctorVisitor doctorVisitor,
                          JunkVisitor junkVisitor,
                          TeleportVisitor teleportVisitor,
                          TimeReverseVisitor timeReverseVisitor){
        this.dalekVisitor = dalekVisitor;
        this.doctorVisitor = doctorVisitor;
        this.junkVisitor = junkVisitor;
        this.teleportVisitor = teleportVisitor;
        this.timeReverseVisitor = timeReverseVisitor;
    }

    public DalekVisitor getDalekVisitor(){
        return this.dalekVisitor;
    }

    public DoctorVisitor getDoctorVisitor(){
        return this.doctorVisitor;
    }

    public TeleportVisitor getTeleportVisitor() {
        return teleportVisitor;
    }

    public TimeReverseVisitor getTimeReverseVisitor() {
        return timeReverseVisitor;
    }

    public JunkVisitor getJunkVisitor() {
        return junkVisitor;
    }
}

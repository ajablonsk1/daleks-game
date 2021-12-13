package com.example.sr1615shrek.collisions.visitors;

import com.example.sr1615shrek.entity.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;

@Service
public class VisitorService {

    private final DalekVisitor dalekVisitor;

    private  final DoctorVisitor doctorVisitor;

    private final JunkVisitor junkVisitor;

    @Autowired
    public VisitorService(DalekVisitor dalekVisitor,
                          DoctorVisitor doctorVisitor,
                          JunkVisitor junkVisitor){
        this.dalekVisitor = dalekVisitor;
        this.doctorVisitor = doctorVisitor;
        this.junkVisitor = junkVisitor;
    }

    public DalekVisitor getDalekVisitor(){
        return this.dalekVisitor;
    }

    public DoctorVisitor getDoctorVisitor(){
        return this.doctorVisitor;
    }

    public JunkVisitor getJunkVisitor() {
        return junkVisitor;
    }
}

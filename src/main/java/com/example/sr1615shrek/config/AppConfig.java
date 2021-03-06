package com.example.sr1615shrek.config;

import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.SubjectService;
import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.powerups.Teleport;
import com.example.sr1615shrek.entity.model.powerups.TimeReverse;
import com.example.sr1615shrek.game.Board;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfig {

    @Value("${appConfig.width}")
    private int width;

    @Value("${appConfig.height}")
    private int height;

    @Bean
    public SubjectService subjectService(){
        BehaviorSubject<List<Entity>> collisionSubject = BehaviorSubject.create();
        BehaviorSubject<DynamicEntity> entityMoveSubject = BehaviorSubject.create();
        BehaviorSubject<Dalek> deadDaleksSubject = BehaviorSubject.create();
        BehaviorSubject<Teleport> deadTeleportSubject = BehaviorSubject.create();
        BehaviorSubject<TimeReverse> deadTimeReverseSubject = BehaviorSubject.create();

        return new SubjectService(collisionSubject,
                entityMoveSubject,
                deadDaleksSubject,
                deadTeleportSubject,
                deadTimeReverseSubject);
    }

    @Bean
    public Board board(SubjectService subjectService){

        return new Board(width,
                height,
                subjectService);
    }
}



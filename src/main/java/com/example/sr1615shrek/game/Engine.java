package com.example.sr1615shrek.game;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.controllers.BoardController;
import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Engine {

    private Board board;

    private CollisionDetector collisionDetector;

    private final BoardController boardController;

    @Autowired
    public Engine(CollisionDetector collisionDetector, BoardController boardController){
        this.board = new Board(40, 80);
        this.collisionDetector = collisionDetector;
        this.boardController = boardController;
    }

    public void start(){
        board.getEntities().subscribe(Entity::onNext);
//        this.collisionDetector.checkForCollisions();
    }
}

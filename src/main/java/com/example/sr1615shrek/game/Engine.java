package com.example.sr1615shrek.game;

import com.example.sr1615shrek.controllers.BoardController;
import com.example.sr1615shrek.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Engine {

    private Board board;

    private final BoardController boardController;

    @Autowired
    public Engine(BoardController boardController){
        this.board = new Board(40, 80);
        this.boardController = boardController;
    }

    public void start(){
        board.getEntities().subscribe(Entity::onNext);
//        this.collisionDetector.checkForCollisions();
    }
}

package com.example.sr1615shrek.game;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.collisions.visitors.DalekVisitor;
import com.example.sr1615shrek.collisions.visitors.DoctorVisitor;
import com.example.sr1615shrek.collisions.visitors.JunkVisitor;
import com.example.sr1615shrek.collisions.visitors.VisitorService;
import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.view.BoardPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Engine {

    private final Board board;

    private final BoardPresenter boardPresenter;

    private final CollisionDetector collisionDetector;

    private VisitorService visitorService;

    @Autowired
    public Engine(BoardPresenter boardPresenter,
                  Board board,
                  CollisionDetector collisionDetector,
                  VisitorService visitorService){
        this.board = board;
        this.boardPresenter = boardPresenter;
        this.collisionDetector = collisionDetector;
        this.visitorService = visitorService;
    }

    public void start(){
        Random random = new Random();
        int x = random.nextInt(board.getWidth());
        int y = random.nextInt(board.getHeight());
        this.board.setDoctor(new Doctor(new Vector2d(x, y),
                this.board.getEntityMoveSubject(),
                this.visitorService.getDoctorVisitor()));
    }

    public void startTurn(Direction direction){
        this.board.getDoctor().move(direction);
    }
}

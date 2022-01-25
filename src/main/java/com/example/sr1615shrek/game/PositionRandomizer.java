package com.example.sr1615shrek.game;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class PositionRandomizer {

    private final Board board;

    private List<Vector2d> freePositions = new ArrayList<>();

    private final Random random = new Random();

    @Autowired
    public PositionRandomizer(Board board){
        this.board = board;
    }

    public void updateFreePositionList(){
        this.freePositions = new ArrayList<>();
        for(int i = 0; i < this.board.getWidth(); i++){
            for(int j = 0; j < this.board.getHeight(); j++){
                this.freePositions.add(new Vector2d(i, j));
            }
        }
        this.freePositions = this.freePositions
                .stream()
                .filter(position -> !isVectorOccupied(position))
                .toList();
    }

    public Vector2d getRandomPosition() {
        updateFreePositionList();
        return this.freePositions.get(random.nextInt(this.freePositions.size()));
    }

    private boolean isVectorOccupied(Vector2d vector2d) {
        List<Entity> entityList = board.getEntitiesOnVector(vector2d);
        return entityList != null && !entityList.isEmpty();
    }
}

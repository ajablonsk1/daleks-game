package com.example.sr1615shrek.test_models;

import com.example.sr1615shrek.entity.EntityHierarchy;
import com.example.sr1615shrek.entity.StaticEntity;
import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.PositionObserver;

public class StaticActiveEntityModel extends StaticEntity {

    public StaticActiveEntityModel(Vector2d position) {
        super(position);
    }

    @Override
    public void move(Direction direction) {

    }

    @Override
    public void setPosition(Vector2d position) {

    }

    @Override
    public void addObserver(PositionObserver positionObserver) {

    }

    @Override
    public void onNext() {

    }

    @Override
    public EntityHierarchy getRank() {
        return EntityHierarchy.STATIC_ACTIVE;
    }
}

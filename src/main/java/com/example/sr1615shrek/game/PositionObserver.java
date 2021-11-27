package com.example.sr1615shrek.game;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;

public interface PositionObserver {

    public void onPositionChange(Entity entity, Vector2d oldPosition);
}

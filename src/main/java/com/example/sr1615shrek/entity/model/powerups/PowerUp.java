package com.example.sr1615shrek.entity.model.powerups;

import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.Entity;

public interface PowerUp extends Entity {

    void execute(DynamicEntity dynamicEntity);

    void undo();
}

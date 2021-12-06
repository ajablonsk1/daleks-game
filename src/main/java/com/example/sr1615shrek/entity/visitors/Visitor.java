package com.example.sr1615shrek.entity.visitors;

import com.example.sr1615shrek.entity.DynamicEntity;
import com.example.sr1615shrek.entity.StaticEntity;

public interface Visitor {


    // Sample functions for testing(to change)
    void collisionWithDynamicEntity(DynamicEntity dynamicEntity);

    void collisionWithStaticEntity(StaticEntity staticEntity);
}

package com.example.sr1615shrek.tests;

import com.example.sr1615shrek.entity.position.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void addVector2d(){

        // Given
        Vector2d vector2d1 = new Vector2d(2, 3);
        Vector2d vector2d2 = new Vector2d(5, 8);

        // When
        Vector2d addResult = vector2d1.add(vector2d2);

        // Then
        assertEquals(new Vector2d(7, 11), addResult);
    }

    @Test
    void subtractVector2d(){

        // Given
        Vector2d vector2d1 = new Vector2d(2, 3);
        Vector2d vector2d2 = new Vector2d(5, 8);

        // When
        Vector2d subtractResult = vector2d1.subtract(vector2d2);

        // Then
        assertEquals(new Vector2d(-3, -5), subtractResult);
    }
}




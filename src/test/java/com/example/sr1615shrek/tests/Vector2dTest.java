package com.example.sr1615shrek.tests;

import com.example.sr1615shrek.entity.position.Vector2d;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
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

    @Test
    void unitVector2d(){

        // Given
        Vector2d vector2d1 = new Vector2d(2, 3);
        Vector2d vector2d2 = new Vector2d(0, -2);
        Vector2d vector2d3 = new Vector2d(1, 0);
        Vector2d vector2d4 = new Vector2d(-3, -3);

        // Then
        assertEquals(new Vector2d(1,1), vector2d1.getUnitVector());
        assertEquals(new Vector2d(0,-1), vector2d2.getUnitVector());
        assertEquals(new Vector2d(1,0), vector2d3.getUnitVector());
        assertEquals(new Vector2d(-1,-1), vector2d4.getUnitVector());
    }
}




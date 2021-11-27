package com.example.sr1615shrek.entity.position;

public enum Direction {
    NORTH(0, 1),
    NORTHWEST(-1, 1),
    WEST(-1, 0),
    SOUTHWEST(-1,-1),
    SOUTH(0, -1),
    SOUTHEAST(1, -1),
    EAST(1, 0),
    NORTHEAST(1, 1);

    private final Vector2d vector2d;

    Direction(int x, int y) {
        this.vector2d = new Vector2d(x, y);
    }

    public Vector2d getVector() {
        return this.vector2d;
    }
}

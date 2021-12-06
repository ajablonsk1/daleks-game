package com.example.sr1615shrek.entity.position;


import java.util.Objects;

public record Vector2d(int x, int y){

    public Vector2d add(Vector2d position) {
        return new Vector2d(this.x + position.getX(),
                this.y + position.getY());
    }

    public Vector2d subtract(Vector2d position) {
        return add(new Vector2d(-position.getX(), -position.getY()));
    }

    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }

    public Vector2d copy(){
        return new Vector2d(this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

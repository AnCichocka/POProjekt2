package org.example;

public enum MoveDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    @Override
    public String toString(){

        return switch(this){
            case UP -> "up";
            case DOWN -> "down";
            case LEFT -> "left";
            case RIGHT -> "right";
        };
    }

    public Vector2d toUnitVector(){

        return switch(this){
            case UP -> new Vector2d(0,1);
            case DOWN -> new Vector2d(0,-1);
            case LEFT -> new Vector2d(-1,0);
            case RIGHT -> new Vector2d(1,0);
        };
    }
}

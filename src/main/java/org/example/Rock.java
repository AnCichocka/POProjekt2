package org.example;

public class Rock implements IMapObstacle {
    private final Vector2d position;
    public Rock(Vector2d position){
        this.position = position;
    }

    @Override
    public String toString(){
        return "*";
    }

    @Override
    public Vector2d getPositon() {
        return this.position;
    }
}

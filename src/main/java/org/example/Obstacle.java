package org.example;

public class Obstacle implements IMapObstacle, IMapElement {
    private final Vector2d position;
    private final ObstacleType type;
    public Obstacle(Vector2d position){

        this.position = position;
        this.type = ObstacleType.getRandomObstacleType();
    }
    public String getImagePath(){
        return this.type.getImagePath();
    }
    @Override
    public String toString(){
        return "*";
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }
}

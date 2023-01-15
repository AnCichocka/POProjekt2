package org.example;

import java.util.Random;

public enum ObstacleType {
    ROCK,
    PUDDLE;

    private static final Random RANDOM = new Random();

    public String getImagePath(){

        String path = switch (this){
            case ROCK -> "src/main/resources/rock.png";
            case PUDDLE -> "src/main/resources/puddle.png";
        };
        return path;
    }

    public static ObstacleType getRandomObstacleType()  {
        ObstacleType[] types = values();
        return types[RANDOM.nextInt(types.length)];
    }
}

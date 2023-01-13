package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Pokemon implements IPositionChangeObserver {

    int level;
    private Vector2d position;
    private final ArrayList<IPositionChangeObserver> observers;
    private final RectangularMap map;
    public Vector2d getPosition() {
        return position;
    }

    public Pokemon(Vector2d position, RectangularMap map){
        this(position, map, 1);
    }
    public Pokemon(Vector2d position, RectangularMap map, int level){
        this.position = position;
        this.map = map;
        this.level = level;
        this.observers = new ArrayList<>();
        this.observers.add(map);
    }

    @Override
    public String toString(){
        return "%d".formatted(this.level);
    }

    public void move(MoveDirection direction){

        Vector2d newPosition = this.position;
        Vector2d unitVector;

        System.out.println(direction);
        switch (direction){
            case UP, RIGHT, LEFT, DOWN -> unitVector = direction.toUnitVector();
            default -> throw new IllegalArgumentException("you idiot enum mistake?");
        }

        newPosition = newPosition.add(unitVector);
        if(map.canMoveTo(newPosition)){
            if(map.willBeFight(newPosition)){
                System.out.println("FIGHT----------------------------------------------");
                //czy pokemony, które pokonaliśmy powinny znikać z mapy? - mogą
            }
            this.positionChanged(this.position, newPosition);
            this.position = newPosition;
            System.out.println(map);
        }
        else{
            System.out.println("BLOCKED OR OUTSIDE------------------------------------------------");
        }

    }
    public void moveWildPokemon(){

        //probability
        Random random = new Random();
        boolean willPokemonBeMoved = random.nextInt(0,10) <= 1;
        if(!willPokemonBeMoved){
//            System.out.println("DONT MOVE");
            return;
        }

//        System.out.println("MOVE");

        //move pokemon
        ArrayList<Vector2d> possibleNewPositions = new ArrayList<>();

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                Vector2d positionToCheck = this.position.add(new Vector2d(i,j));
                if(map.canMoveTo(positionToCheck) && !(i == 0 && j == 0)){
                    possibleNewPositions.add(positionToCheck);
                }
            }
        }
//        System.out.println("POSSIBLE: " + possibleNewPositions);
        //random position
        if(possibleNewPositions.size() == 0){
            return;
        }
        int positionIndex = random.nextInt(0,possibleNewPositions.size());
        Vector2d newPosition = possibleNewPositions.get(positionIndex);
        positionChanged(this.position, newPosition);
        this.position = newPosition;
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}

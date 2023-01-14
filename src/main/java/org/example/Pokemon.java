package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Pokemon implements IPositionChangeObserver, IMapElement, IFightObserver {

    int level;
    private Vector2d position;
    private final ArrayList<IPositionChangeObserver> observersMove;
    private final ArrayList<IFightObserver> observersFight;
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
        this.observersFight = new ArrayList<>();
        this.observersMove = new ArrayList<>();

        this.addMoveObserver(map);
    }

    @Override
    public String toString(){
        return "lvl. %d".formatted(this.level);
    }

    public void move(MoveDirection direction){

        Vector2d newPosition = this.position;
        Vector2d unitVector;

        System.out.println("DIRECTION: " + direction);
        switch (direction){
            case UP, RIGHT, LEFT, DOWN -> unitVector = direction.toUnitVector();
            default -> throw new IllegalArgumentException("you idiot enum mistake?");
        }

        newPosition = newPosition.add(unitVector);
        if(map.canMoveTo(newPosition)){
            if(map.willBeFight(newPosition)){
                this.fightStart(this, (Pokemon) map.objectAt(newPosition));
//                FightView fightView = new FightView();
//                fightView.createFightScene();
                //czy pokemony, które pokonaliśmy powinny znikać z mapy? - mogą
            }
            else{
                this.positionChanged(this.position, newPosition);
                this.position = newPosition;
                System.out.println(map);
            }
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
                if(map.canMoveTo(positionToCheck) && !(i == 0 && j == 0) && !map.willBeFight(positionToCheck)){
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
    public void addMoveObserver(IPositionChangeObserver observer){
        observersMove.add(observer);
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver observer : observersMove){
            observer.positionChanged(oldPosition, newPosition);
        }
    }
    public void addFightObserver(IFightObserver observer){
        observersFight.add(observer);
    }
    @Override
    public void fightStart(Pokemon myPokemon, Pokemon wildPokemon) {
        System.out.println("FIGHT START");
        for(IFightObserver observer : observersFight){
            observer.fightStart(myPokemon, wildPokemon);
        }
    }
}

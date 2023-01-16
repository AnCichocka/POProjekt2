package org.example;

import java.util.*;

public class RectangularMap implements IPositionChangeObserver, IFightObserver{

    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final int width;
    private final int height;
    private final MapVisualizer visualizer;
    private final Map<Vector2d, Pokemon> pokemons;
    private final Map<Vector2d, Obstacle> obstacles;
    private final ArrayList<Vector2d> freePositions;
    private Pokemon bossPokemon;
    private final Pokemon myPokemon;

    public Pokemon getMyPokemon() {
        return myPokemon;
    }
    public Pokemon getBossPokemon() {
        return bossPokemon;
    }

    public RectangularMap(int width, int height, int numberOfPokemons, int numberOfRocks){

        this.width = width;
        this.height = height;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width-1, height-1);
        this.obstacles = new HashMap<>();
        this.pokemons = new HashMap<>();
        this.freePositions = createFreePositions();
        this.myPokemon = putNewMyPokemonOnMap();
        putNewBossOnMap();

        createPokemonsOnMap(numberOfPokemons);
        createObstaclesOnMap(numberOfRocks);

        this.visualizer = new MapVisualizer(this);
    }
    public ArrayList<Vector2d> createFreePositions() {

        ArrayList<Vector2d> freePositions = new ArrayList<>();

        for(int i=0; i < width; i++){
            for(int j=0; j < height; j++){
                freePositions.add(new Vector2d(i,j));
            }
        }

        return freePositions;
    }
    public Pokemon putNewMyPokemonOnMap(){
        Vector2d position = getRandomFreePosition();
        Pokemon pokemon = new Pokemon(position, this, 1);
        this.place(pokemon);
        return pokemon;
    }
    public Pokemon putNewBossOnMap(){

        Vector2d position = getRandomFreePosition();
        Pokemon pokemon = new Pokemon(position, this, Math.max(10, getMyPokemon().level*2));
        this.bossPokemon = pokemon;
        this.place(pokemon);
        return pokemon;
    }
    public Pokemon putNewWildPokemonOnMap(){
        Vector2d position = getRandomFreePosition();
        int level = getNewPokemonOnMapLevel();
        Pokemon pokemon = new Pokemon(position, this, level);
        this.place(pokemon);
        return pokemon;
    }
    public void createPokemonsOnMap(int n){

        for(int i=0; i<n; i++){
            Vector2d position = getRandomFreePosition();
            int level = getNewPokemonOnMapLevel();
            Pokemon pokemon = new Pokemon(position, this, level);
            this.place(pokemon);
        }
    }
    private int getNewPokemonOnMapLevel(){
        Random random = new Random();
        int level = random.nextInt(1, getMyPokemon().level + 2 + 1);
        return level;
    }
    public void createObstaclesOnMap(int n){

        for(int i=0; i<n; i++){
            Vector2d position = getRandomFreePosition();
            Obstacle obstacle = new Obstacle(position);
            this.placeObstacle(obstacle);
        }
    }
    public Vector2d getRandomFreePosition(){

        Random random = new Random();

        int indexOfPosition = random.nextInt(0,freePositions.size());
        Vector2d position = freePositions.get(indexOfPosition);
        this.freePositions.remove(position);

        return position;
    }
    public Vector2d getLowerLeftBound(){
        return this.lowerLeft;
    }
    public Vector2d getUpperRightBound(){
        return this.upperRight;
    }
    //only when there is an obstacle
    public boolean place(Pokemon pokemon){
        if (this.canPlaceOnPosition(pokemon.getPosition())){
            pokemons.put(pokemon.getPosition(), pokemon);
            return true;
        }
        else {
            throw new IllegalArgumentException(pokemon.getPosition() + " is invalid position");
        }
    }
    public boolean placeObstacle(Obstacle obstacle){
        if (this.canPlaceOnPosition(obstacle.getPosition())){
            obstacles.put(obstacle.getPosition(), obstacle);
            return true;
        }
        else {
            throw new IllegalArgumentException(obstacle.getPosition() + " is invalid position");
        }
    }
    boolean isBlocked(Vector2d position){
        return obstacles.get(position) != null;
    }
    public Object objectAt(Vector2d position){
        if(this.pokemons.get(position) == null){
            return this.obstacles.get(position);
        }
        return this.pokemons.get(position);
    }
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight) && !this.isBlocked(position);
    }
    public boolean canPlaceOnPosition(Vector2d position){
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight) && objectAt(position) == null;
    }
    public boolean willBeFight(Vector2d position){
        return pokemons.get(position) != null;
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Pokemon pokemon = pokemons.remove(oldPosition);
        pokemons.put(newPosition, pokemon);
    }
    @Override
    public String toString(){
        System.out.println(pokemons);
        return this.visualizer.draw(getLowerLeftBound(), getUpperRightBound());
        }
        //służy do drukowania mapy w konsoli
    public boolean isOccupied(Vector2d position){
        return pokemons.get(position) != null || obstacles.get(position) != null;
    }
    public void moveWildPokemons(){

        // robie to tylko po to, żeby nie wywalało mi błędu - zmiana iteratora podczas przechodzenia po strukturze
        //observer ogarnia zmiany dla pokemons

        ArrayList<Pokemon> pokemonsValues = new ArrayList<>(pokemons.values());

        for(Pokemon pokemon : pokemonsValues){
            if(!Objects.equals(pokemon, myPokemon) && !Objects.equals(pokemon, bossPokemon)){
                pokemon.moveWildPokemon();
            }
        }
    }

    @Override
    public void fightStarted(Pokemon myPokemon, Pokemon wildPokemon) {

    }

    @Override
    public void fightEnded(Pokemon deadPokemon) {
        if(Objects.equals(bossPokemon, deadPokemon)){
            putNewBossOnMap();
        }
        if(!Objects.equals(myPokemon, deadPokemon)){
            pokemons.remove(deadPokemon.getPosition());
            putNewWildPokemonOnMap();
        }
        //TODO: add new pokemon on random place
        //TODO: check if boss -> new boss
    }
}
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

    public RectangularMap(int width, int height, int numberOfPokemons, int numberOfObstacles){

        this.width = width;
        this.height = height;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width-1, height-1);
        this.obstacles = new HashMap<>();
        this.pokemons = new HashMap<>();
        this.freePositions = createFreePositions();
        this.myPokemon = putNewPokemonOnMap(1);
        this.bossPokemon = putNewBossOnMap();

        addWildPokemons(numberOfPokemons);
        createObstaclesOnMap(numberOfObstacles);

        this.visualizer = new MapVisualizer(this);
    }
    private ArrayList<Vector2d> createFreePositions() {

        ArrayList<Vector2d> freePositions = new ArrayList<>();

        for(int i=0; i < width; i++){
            for(int j=0; j < height; j++){
                freePositions.add(new Vector2d(i,j));
            }
        }

        return freePositions;
    }
    private void addWildPokemons(int n){

        for(int i=0; i<n; i++){
            putNewWildPokemonOnMap();
        }
    }
    private void putNewWildPokemonOnMap(){

        int level = getNewWildPokemonOnMapLevel();
        putNewPokemonOnMap(level);
    }
    private Pokemon putNewPokemonOnMap(int level){
        Vector2d position = getRandomFreePosition();
        Pokemon pokemon = new Pokemon(position, this, level);
        this.place(pokemon);
        return pokemon;
    }
    private int getNewWildPokemonOnMapLevel(){

        Random random = new Random();
        return random.nextInt(1, getMyPokemon().getLevel() + 2 + 1);
    }
    private Pokemon putNewBossOnMap(){

        int level = Math.max(10, getMyPokemon().getLevel()*2);
        return putNewPokemonOnMap(level);
    }
    private void createObstaclesOnMap(int n){

        for(int i=0; i<n; i++){
            Vector2d position = getRandomFreePosition();
            Obstacle obstacle = new Obstacle(position);
            this.place(obstacle);
        }
    }
    private Vector2d getRandomFreePosition(){

        Random random = new Random();

        int indexOfPosition = random.nextInt(0,freePositions.size());
        Vector2d position = freePositions.get(indexOfPosition);
        this.freePositions.remove(position);

        return position;
    }
    private void place(IMapElement element){

        Vector2d position = element.getPosition();

        if (this.canPlaceOnPosition(element.getPosition())){

            if(element instanceof Pokemon){
                pokemons.put(position, (Pokemon) element);
            }
            else{
                obstacles.put(position, (Obstacle) element);
            }
        }
        else {
            throw new IllegalArgumentException(position + " is invalid position");
        }
    }
    private Vector2d getLowerLeftBound(){
        return this.lowerLeft;
    }
    private Vector2d getUpperRightBound(){
        return this.upperRight;
    }
    public Object objectAt(Vector2d position){
        if(this.pokemons.get(position) == null){
            return this.obstacles.get(position);
        }
        return this.pokemons.get(position);
    }
    public boolean canMoveTo(Vector2d position) {
        return isPositionInMapBoundaries(position) && !(objectAt(position) instanceof Obstacle);
    }
    public boolean canPlaceOnPosition(Vector2d position){
        return isPositionInMapBoundaries(position) && objectAt(position) == null;
    }
    private boolean isPositionInMapBoundaries(Vector2d position){
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight);
    }
    public boolean willBeFightAtPosition(Vector2d position){
        return pokemons.get(position) != null;
    }

    //printing map in console//////////////////////////////////////
    public boolean isOccupied(Vector2d position){
        return pokemons.get(position) != null || obstacles.get(position) != null;
    }
    @Override
    public String toString(){
        //System.out.println(pokemons);
        return this.visualizer.draw(getLowerLeftBound(), getUpperRightBound());
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
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Pokemon pokemon = pokemons.remove(oldPosition);
        pokemons.put(newPosition, pokemon);
    }
    @Override
    public void fightStarted(Pokemon myPokemon, Pokemon wildPokemon) {

    }

    @Override
    public void fightEnded(Pokemon deadPokemon) {
        if(Objects.equals(bossPokemon, deadPokemon)){
            this.bossPokemon = putNewBossOnMap();
        }
        if(!Objects.equals(myPokemon, deadPokemon)){
            pokemons.remove(deadPokemon.getPosition());
            putNewWildPokemonOnMap();
        }
    }
}
package org.example;

public enum PokemonSpecies {
    PIPLUP,
    BULBASAUR;

    @Override
    public String toString(){
        String message = switch(this){
            case PIPLUP -> "PIPLUP";
            case BULBASAUR -> "BULBASAUR";
        };

        return message;
    }

    public PokemonType getPokemonSpeciesType(){

        PokemonType type = switch(this){
            case PIPLUP -> PokemonType.WATER;
            case BULBASAUR -> PokemonType.GRASS;
        };

        return type;
    }

    public String getImagePath(){

        String path = switch(this){
            case PIPLUP -> "src/main/resources/piplup.png";
            case BULBASAUR -> "src/main/resources/bulbasaur.png";
        };

        return path;
    }
}

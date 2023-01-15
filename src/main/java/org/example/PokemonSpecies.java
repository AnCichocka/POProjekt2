package org.example;

import java.util.Random;

public enum PokemonSpecies {
    PIPLUP,
    BULBASAUR;

    private static final Random RANDOM = new Random();

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

    public PokemonAttack[] getPokemonAttacks(){

        PokemonAttack[] attacks = switch(this){
            case PIPLUP -> new PokemonAttack[]{PokemonAttack.POOR_NEUTRAL, PokemonAttack.PUSH_SOME_WATER, PokemonAttack.WATERFALL};
            case BULBASAUR -> new PokemonAttack[]{PokemonAttack.PREATTY_NEUTRAL, PokemonAttack.POWERFULL_NEUTRAL, PokemonAttack.PULL_OUT_WEED};
        };
        return attacks;
    }


    public static PokemonSpecies randomPokemonSpecies()  {
        PokemonSpecies[] directions = values();
        return directions[RANDOM.nextInt(directions.length)];
    }
}

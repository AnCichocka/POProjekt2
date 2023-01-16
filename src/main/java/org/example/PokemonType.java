package org.example;

public enum PokemonType {
    FIRE,
    WATER,
    GRASS;

    @Override
    public String toString(){

        return switch(this){
            case FIRE -> "fire";
            case WATER -> "water";
            case GRASS -> "grass";
        };
    }
}

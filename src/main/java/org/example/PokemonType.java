package org.example;

public enum PokemonType {
    FIRE,
    WATER,
    GRASS;

    @Override
    public String toString(){

        String type = switch(this){
            case FIRE -> "fire";
            case WATER -> "water";
            case GRASS -> "grass";
        };

        return type;
    }
}

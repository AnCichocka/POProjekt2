package org.example;

public enum PokemonType {
    FIRE,
    WATER,
    GRASS;

    @Override
    public String toString(){
        String message = switch(this){
            case FIRE -> "fire";
            case WATER -> "water";
            case GRASS -> "grass";
        };

        return message;
    }
}

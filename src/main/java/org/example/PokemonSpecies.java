package org.example;

import java.util.Random;

public enum PokemonSpecies {
    PIPLUP,
    MAGIKARP,
    OMANYTE,
    BULBASAUR,
    CACNEA,
    SEWADDLE,
    SLUGMA,
    TORKOAL,
    CARKOL;

    private static final Random RANDOM = new Random();

    @Override
    public String toString(){

        return switch(this){
            case PIPLUP -> "PIPLUP";
            case MAGIKARP -> "MAGIKARP";
            case OMANYTE -> "OMANYTE";
            case BULBASAUR -> "BULBASAUR";
            case CACNEA -> "CACNEA";
            case SEWADDLE -> "SEWADDLE";
            case SLUGMA -> "SLUGMA";
            case TORKOAL -> "TORKOAL";
            case CARKOL -> "CARKOL";
        };
    }

    public PokemonType getPokemonSpeciesType(){

        return switch(this){
            case PIPLUP, MAGIKARP, OMANYTE -> PokemonType.WATER;
            case BULBASAUR, CACNEA, SEWADDLE -> PokemonType.GRASS;
            case SLUGMA, TORKOAL, CARKOL -> PokemonType.FIRE;
        };
    }

    public String getImagePath(){

        return switch(this){
            case PIPLUP -> "src/main/resources/piplup1.png";
            case MAGIKARP -> "src/main/resources/magikarp.png";
            case OMANYTE -> "src/main/resources/omanyte1.png";
            case BULBASAUR -> "src/main/resources/bulbasaur.png";
            case CACNEA -> "src/main/resources/cacnea.png";
            case SEWADDLE -> "src/main/resources/sewaddle.png";
            case SLUGMA -> "src/main/resources/slugma.png";
            case TORKOAL -> "src/main/resources/torkoal.png";
            case CARKOL -> "src/main/resources/carkol1.png";
        };
    }

    public PokemonAttack[] getPokemonAttacks(){

        return switch(this){

            case PIPLUP -> new PokemonAttack[]{PokemonAttack.POOR_NEUTRAL, PokemonAttack.PUSH_SOME_WATER, PokemonAttack.WATERFALL};
            case MAGIKARP -> new PokemonAttack[]{PokemonAttack.CRY, PokemonAttack.PUSH_SOME_WATER, PokemonAttack.WATERFALL};
            case OMANYTE -> new PokemonAttack[]{PokemonAttack.POOR_NEUTRAL, PokemonAttack.CRY, PokemonAttack.PUSH_SOME_WATER};
            case BULBASAUR -> new PokemonAttack[]{PokemonAttack.LEAF_SLAP, PokemonAttack.POWERFULL_NEUTRAL, PokemonAttack.GRASS_CUT};
            case CACNEA -> new PokemonAttack[]{PokemonAttack.PREATTY_NEUTRAL, PokemonAttack.POWERFULL_NEUTRAL, PokemonAttack.PULL_OUT_WEED};
            case SEWADDLE -> new PokemonAttack[]{PokemonAttack.LEAF_SLAP, PokemonAttack.PREATTY_NEUTRAL, PokemonAttack.PULL_OUT_WEED};
            case SLUGMA -> new PokemonAttack[]{PokemonAttack.SPARK, PokemonAttack.PREATTY_NEUTRAL, PokemonAttack.FIREBALL};
            case TORKOAL -> new PokemonAttack[]{PokemonAttack.POOR_NEUTRAL, PokemonAttack.THROW_CANDLES, PokemonAttack.FIREBALL};
            case CARKOL -> new PokemonAttack[]{PokemonAttack.SPARK, PokemonAttack.THROW_CANDLES, PokemonAttack.FIREBALL};
        };
    }


    public static PokemonSpecies randomPokemonSpecies()  {
        PokemonSpecies[] directions = values();
        return directions[RANDOM.nextInt(directions.length)];
    }
}

package org.example;

public enum PokemonAttack {
    LEAF_SLAP,
    GRASS_CUT,
    PULL_OUT_WEED,
    CRY,
    PUSH_SOME_WATER,
    WATERFALL,
    POOR_NEUTRAL,
    PREATTY_NEUTRAL,
    POWERFULL_NEUTRAL,
    SPARK,
    THROW_CANDLES,
    FIREBALL;


    @Override
    public String toString(){

        return switch(this){
            case LEAF_SLAP -> "leaf slap";
            case GRASS_CUT -> "grass cut";
            case PULL_OUT_WEED -> "pull out weed";
            case CRY -> "cry";
            case PUSH_SOME_WATER -> "push some water";
            case WATERFALL -> "waterfall";
            case POOR_NEUTRAL -> "poor neutral";
            case PREATTY_NEUTRAL -> "preatty neutal";
            case POWERFULL_NEUTRAL -> "powerful neutral";
            case SPARK -> "spark";
            case THROW_CANDLES -> "throw candles";
            case FIREBALL -> "fireball";
        };
    }

    public PokemonAttackType getAttackType(){

        return switch(this){
            case PULL_OUT_WEED, GRASS_CUT, LEAF_SLAP -> PokemonAttackType.GRASS;
            case PUSH_SOME_WATER, WATERFALL, CRY -> PokemonAttackType.WATER;
            case POOR_NEUTRAL, PREATTY_NEUTRAL, POWERFULL_NEUTRAL -> PokemonAttackType.NEUTRAL;
            case SPARK, FIREBALL, THROW_CANDLES -> PokemonAttackType.FIRE;
        };
    }

    private int getRawDamagePoints(){

        return switch(this){

            case LEAF_SLAP, PUSH_SOME_WATER, POOR_NEUTRAL -> 5;
            case GRASS_CUT -> 15;
            case PULL_OUT_WEED, POWERFULL_NEUTRAL -> 25;
            case CRY -> 2;
            case WATERFALL, FIREBALL -> 30;
            case PREATTY_NEUTRAL, SPARK -> 10;
            case THROW_CANDLES -> 20;
        };
    }

    public int getDamageToType(PokemonType pokemonType) {

        double percentageOfDamage;
        int damagePoints = this.getRawDamagePoints();

        percentageOfDamage = switch (this.getAttackType()){

            case NEUTRAL -> 1;
            case GRASS -> switch (pokemonType){

                case FIRE -> 2;
                case WATER -> 0.5;
                case GRASS -> 1;
            };
            case FIRE -> switch (pokemonType){

                case FIRE -> 1;
                case WATER -> 0.5;
                case GRASS -> 2.5;
            };
            case WATER -> switch (pokemonType){

                case FIRE -> 2;
                case WATER -> 1;
                case GRASS -> 0.5;
            };
        };

//        System.out.println("attack type: " + getAttackType());
//        System.out.println("pokemon type: " + pokemonType);
//        System.out.println("percentage: " + percentageOfDamage);
//        System.out.println("before attack points: " + damagePoints);

        damagePoints = (int)Math.ceil(damagePoints * percentageOfDamage);
//        System.out.println("after damage points: " + damagePoints);
//        System.out.println();

        return damagePoints;
    }
}

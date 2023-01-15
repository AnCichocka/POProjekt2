package org.example;

public enum PokemonAttack {
    PULL_OUT_WEED,
    PUSH_SOME_WATER,
    POOR_NEUTRAL;

    @Override
    public String toString(){

        String message = switch(this){
            case PULL_OUT_WEED -> "pull out weed";
            case PUSH_SOME_WATER -> "push some water";
            case POOR_NEUTRAL -> "poor neutral";
        };

        return message;
    }

    public PokemonAttackType getAttackType(){

        PokemonAttackType attackType = switch(this){
            case PULL_OUT_WEED -> PokemonAttackType.GRASS;
            case PUSH_SOME_WATER -> PokemonAttackType.WATER;
            case POOR_NEUTRAL -> PokemonAttackType.NEUTRAL;
        };

        return attackType;
    }

    private int getRawDamagePoints(){

        int damagePoints = switch(this){
            case PULL_OUT_WEED -> 20;
            case PUSH_SOME_WATER -> 10;
            case POOR_NEUTRAL -> 5;
        };

        return damagePoints;
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

        System.out.println("attack type: " + getAttackType());
        System.out.println("pokemon type: " + pokemonType);
        System.out.println("percentage: " + percentageOfDamage);
        System.out.println("before attack points: " + damagePoints);

        damagePoints = (int)Math.ceil(damagePoints * percentageOfDamage);
        System.out.println("after damage points: " + damagePoints);
        System.out.println();

        return damagePoints;
    }
}

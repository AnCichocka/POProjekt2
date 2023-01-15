package org.example;

public interface IFightObserver {
    void fightStarted(Pokemon myPokemon, Pokemon wildPokemon);
    void fightEnded(Pokemon deadPokemon);

    //TODO: probably it's better to split into two different interfaces
}

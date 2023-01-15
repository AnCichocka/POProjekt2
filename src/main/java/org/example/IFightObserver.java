package org.example;

public interface IFightObserver {
    void fightStarted(Pokemon myPokemon, Pokemon wildPokemon);
    void fightEnded(Pokemon deadPokemon);
}

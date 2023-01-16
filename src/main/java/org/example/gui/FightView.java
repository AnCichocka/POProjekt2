package org.example.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class FightView implements IFightObserver {

    private Pokemon myPokemon;
    private Pokemon wildPokemon;
    private int myPokemonLife;
    private int wildPokemonLife;
    private VBox fightSceneContainer;
    private VBox middleContainer;
    private String titleText;
    private final ArrayList<IFightObserver> observers = new ArrayList();
    static final int FIGHT_SCENE_WIDTH = 1200;
    static final int FIGHT_SCENE_HEIGHT = 700;
    static final int ATTACK_BUTTONS_SPACING = 20;
    static final int ATTACK_BUTTON_PADDING = 10;
    static final int ATTACK_BUTTON_FONT_SIZE = 20;
    static final int FIGHT_SCENE_SPACING = 40;
    static final int POKEMONS_CONTAINER_SPACING = 60;
    static final int POKEMON_NAME_SIZE = 30;
    static final int POKEMON_IMAGE_SIZE = 450;
    static final int POKEMON_LIFE_SIZE = 20;
    static final int POKEMON_CONTAINER_SPACING = 20;
    static final int TITLE_FONT_SIZE = 40;
    static final int BUTTON_CONTIANER_WIDTH = 200;
    static final int BUTTON_HEIGHT = 30;
    public void getFightScene(){

        this.fightSceneContainer = new VBox();
        this.titleText = "FIGHT IN PROGRESS";

        createFightScene();

        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" + currentDir);

        BackgroundImage myBI = new BackgroundImage(new Image("background3.jpg",FIGHT_SCENE_WIDTH, FIGHT_SCENE_HEIGHT,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
//then you set to your node
        fightSceneContainer.setBackground(new Background(myBI));

        Scene scene = new Scene(this.fightSceneContainer,FIGHT_SCENE_WIDTH, FIGHT_SCENE_HEIGHT);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
    }
    public void createFightScene(){

        HBox pokemonsContainer = this.getPokemonsContainer();

        Text title = new Text(this.titleText);
        title.setFont(new Font(TITLE_FONT_SIZE));

        this.fightSceneContainer.getChildren().add(title);
        this.fightSceneContainer.getChildren().add(pokemonsContainer);

        fightSceneContainer.setAlignment(Pos.CENTER);
        fightSceneContainer.setSpacing(FIGHT_SCENE_SPACING);

    }
    private Background getBackgroundOfColor(Color color){
        BackgroundFill background_fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        return new Background(background_fill);
    }
    private HBox getPokemonsContainer(){

        VBox pokemon1Container = this.getPokemonContainer(myPokemon, this.myPokemonLife);
        VBox pokemon2Container = this.getPokemonContainer(wildPokemon, this.wildPokemonLife);

        this.middleContainer = this.getAttackButtonsContainer();

        HBox pokemonsContainer = new HBox(pokemon1Container, this.middleContainer, pokemon2Container);
        pokemonsContainer.setAlignment(Pos.CENTER);
        pokemonsContainer.setSpacing(POKEMONS_CONTAINER_SPACING);

        //pokemonsContainer.setBackground(this.getBackgroundOfColor(Color.YELLOW));

        return pokemonsContainer;
    }
    private VBox getPokemonContainer(Pokemon pokemon, int lifePoints){

        try{
            Image image = new Image(new FileInputStream(pokemon.getSpecies().getImagePath()));
            ImageView pokemonImg = new ImageView(image);
            pokemonImg.setFitWidth(POKEMON_IMAGE_SIZE);
            pokemonImg.setFitHeight(POKEMON_IMAGE_SIZE);

            //zwróć w odpowiednią stronę
            if(Objects.equals(pokemon, myPokemon)){
                pokemonImg.setScaleX(-1);
            }

            Text pokemonName = new Text(pokemon.getSpecies().toString());
            pokemonName.setFont(new Font(POKEMON_NAME_SIZE));

            Text pokemonLife = new Text(String.valueOf(lifePoints));
            pokemonLife.setFont(new Font(POKEMON_LIFE_SIZE));

            VBox pokemonContainer = new VBox(pokemonName, pokemonImg, pokemonLife);
            pokemonContainer.setAlignment(Pos.CENTER);
            pokemonContainer.setSpacing(POKEMON_CONTAINER_SPACING);

            //pokemonContainer.setBackground(getBackgroundOfColor(Color.RED));

            return pokemonContainer;
        }
        catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    private VBox getAttackButtonsContainer(){

        Button button1 = getAttackButton(0);
        Button button2 = getAttackButton(1);
        Button button3 = getAttackButton(2);

        VBox attackButtonsContainer = new VBox(button1, button2, button3);
        attackButtonsContainer.setPrefSize(BUTTON_CONTIANER_WIDTH, BUTTON_CONTIANER_WIDTH);

        attackButtonsContainer.setSpacing(ATTACK_BUTTONS_SPACING);
        attackButtonsContainer.setAlignment(Pos.CENTER);

        return attackButtonsContainer;
    }
    public Button getAttackButton(int attackIndex){

        Button button = new Button(myPokemon.getAttackOfIndex(attackIndex).toString());

        button.setFont(new Font(ATTACK_BUTTON_FONT_SIZE));
        button.setPadding(new Insets(ATTACK_BUTTON_PADDING));
        button.setPrefSize(BUTTON_CONTIANER_WIDTH, BUTTON_HEIGHT);

        //eventListeners
        button.setOnAction(event -> {
            //System.out.println(attackIndex + "clicked");
            myPokemon.attack(wildPokemon, attackIndex);
            this.wildPokemonLife = wildPokemon.getLifePoints();

            //you win
            if(wildPokemon.isDead()){
                this.titleText = "YOU WON THIS FIGHT";
                this.fightSceneContainer.getChildren().clear();
                refreshToEndFightView();

                myPokemon.wonFight();

                myPokemon.regenerate();
                wildPokemon.regenerate();

                fightEnded(wildPokemon);
            }
            else{

                Random random = new Random();
                int randomIndex = random.nextInt(0,3);
                wildPokemon.attack(myPokemon, randomIndex);
                this.myPokemonLife = myPokemon.getLifePoints();

                //you loose
                if(myPokemon.isDead()){
                    this.titleText = "YOU LOSE THIS FIGHT";
                    this.fightSceneContainer.getChildren().clear();
                    refreshToEndFightView();

                    myPokemon.lostFight();

                    myPokemon.regenerate();
                    wildPokemon.regenerate();

                    fightEnded(myPokemon);
                }

                //fight still in progress
                else{
                    refresh();
                }
            }
        });

        return button;
    }

    @Override
    public void fightStarted(Pokemon myPokemon, Pokemon wildPokemon) {

        this.myPokemon = myPokemon;
        this.wildPokemon = wildPokemon;

        System.out.println(myPokemon.getLevel());

        this.myPokemonLife = myPokemon.getLifePoints();
        this.wildPokemonLife = wildPokemon.getLifePoints();

        System.out.println(myPokemon.getLifePoints());

        getFightScene();
    }
    public void refresh() {

        Platform.runLater( () -> {
            this.fightSceneContainer.getChildren().clear();
            createFightScene();
        });
    }

    public void refreshToEndFightView() {

        Platform.runLater( () -> {
            this.fightSceneContainer.getChildren().clear();
            createFightScene();
            middleContainer.getChildren().clear();
        });
    }
    public void addFightObserver(IFightObserver observer){
        observers.add(observer);
    }

    @Override
    public void fightEnded(Pokemon deadPokemon) {
        for(IFightObserver observer : observers){
            observer.fightEnded(deadPokemon);
        }
    }

}



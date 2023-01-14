package org.example.gui;

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

public class FightView implements IFightObserver {

    static final int IMAGE_SIZE = 400;
    static final int FIGHT_SCENE_WIDTH = 1200;
    static final int FIGHT_SCENE_HEIGHT = 700;
    static final int ATTACK_BUTTONS_SPACING = 20;
    static final int ATTACK_BUTTON_PADDING = 10;
    static final int ATTACK_BUTTON_FONT_SIZE = 20;
    static final int FIGHT_SCENE_SPACING = 40;
    static final int POKEMONS_CONTAINER_SPACING = 60;
    static final int POKEMON_NAME_SIZE = 30;
    static final int POKEMON_LIFE_SIZE = 20;
    static final int POKEMON_CONTAINER_SPACING = 20;
    public void createFightScene(){

        HBox pokemonsContainer = this.getPokemonsContainer();

        Text title = new Text("FIGHT IN PROGRESS");
        title.setFont(new Font(30));

        VBox fightSceneContainer = new VBox(title, pokemonsContainer);
        fightSceneContainer.setAlignment(Pos.CENTER);
        fightSceneContainer.setSpacing(FIGHT_SCENE_SPACING);

        Scene scene = new Scene(fightSceneContainer,FIGHT_SCENE_WIDTH, FIGHT_SCENE_HEIGHT);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
    }
    private Background getBackgroundOfColor(Color color){
        BackgroundFill background_fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        return new Background(background_fill);
    }
    private HBox getPokemonsContainer(){

        VBox pokemon1Container = this.getPokemonContainer("src/main/piplup.png");
        VBox pokemon2Container = this.getPokemonContainer("src/main/bulbasaur.png");

        VBox attackButtonsContainer = this.getAttackButtonsContainer();

        HBox pokemonsContainer = new HBox(pokemon1Container, attackButtonsContainer, pokemon2Container);
        pokemonsContainer.setAlignment(Pos.CENTER);
        pokemonsContainer.setSpacing(POKEMONS_CONTAINER_SPACING);

        pokemonsContainer.setBackground(this.getBackgroundOfColor(Color.YELLOW));

        return pokemonsContainer;
    }
    private VBox getPokemonContainer(String imgPath){

        try{
            Image image = new Image(new FileInputStream(imgPath));
            ImageView pokemonImg = new ImageView(image);
            pokemonImg.setFitWidth(IMAGE_SIZE);
            pokemonImg.setFitHeight(IMAGE_SIZE);

            Text pokemonName = new Text("name");
            pokemonName.setFont(new Font(POKEMON_NAME_SIZE));

            Text pokemonLife = new Text("life");
            pokemonLife.setFont(new Font(POKEMON_LIFE_SIZE));

            VBox pokemonContainer = new VBox(pokemonName, pokemonImg, pokemonLife);
            pokemonContainer.setAlignment(Pos.CENTER);
            pokemonContainer.setSpacing(POKEMON_CONTAINER_SPACING);

            pokemonContainer.setBackground(getBackgroundOfColor(Color.RED));

            return pokemonContainer;
        }
        catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }


//        VBox pokemonImg = new VBox(new Text("POKEMON PHOTO 1"));
//        pokemonImg.setMinWidth(POKEMON_WIDTH);
//        pokemonImg.setMinHeight(POKEMON_HEIGHT);
//        pokemonImg.setBackground(this.getBackgroundOfColor(Color.RED));

    }
    private VBox getAttackButtonsContainer(){
        Button button1 = new Button("attack 1");
        Button button2 = new Button("attack 2");
        Button button3 = new Button("attack 3");

        button1.setFont(new Font(ATTACK_BUTTON_FONT_SIZE));
        button2.setFont(new Font(ATTACK_BUTTON_FONT_SIZE));
        button3.setFont(new Font(ATTACK_BUTTON_FONT_SIZE));

        button1.setPadding(new Insets(ATTACK_BUTTON_PADDING));
        button2.setPadding(new Insets(ATTACK_BUTTON_PADDING));
        button3.setPadding(new Insets(ATTACK_BUTTON_PADDING));

        VBox attackButtonsContainer = new VBox(button1, button2, button3);
        attackButtonsContainer.setSpacing(ATTACK_BUTTONS_SPACING);
        attackButtonsContainer.setAlignment(Pos.CENTER);

        return attackButtonsContainer;
    }
    @Override
    public void fightStart(Pokemon myPokemon, Pokemon wildPokemon) {
        createFightScene();
    }
}



package org.example.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.*;

public class App extends Application implements IFightObserver {

    private RectangularMap map;
    private MapView mapView;
    private Pokemon myPokemon;
    private boolean fightInProgress;


    @Override
    public void init() throws Exception {

        super.init();

        try{
            int numberOfPokemons = 1;
            int numberOfRocks = 5;
            int width = 25;
            int height = 15;
            RectangularMap map = new RectangularMap(width,height, numberOfPokemons, numberOfRocks);
            this.map = map;

            FightView fightView = new FightView();
            fightView.addFightObserver(this);

            this.mapView = new MapView(width, height, map);

            this.myPokemon = map.getMyPokemon();
            this.myPokemon.addFightObserver(fightView);
            this.myPokemon.addFightObserver(this);


            System.out.println(map);

        }
        catch(IllegalArgumentException ex){
            System.err.println(ex);
            System.exit(1);
        }
    }
    @Override
    public void start(Stage primaryStage){

        Scene scene = this.mapView.getMapScene();
        //catching pressed arrows
        scene.setOnKeyPressed(event -> {

            if(!fightInProgress){
                MoveDirection direction = null;

                switch (event.getCode()) {
                    case UP -> direction = MoveDirection.UP;
                    case DOWN -> direction = MoveDirection.DOWN;
                    case LEFT -> direction = MoveDirection.LEFT;
                    case RIGHT -> direction = MoveDirection.RIGHT;
                    default -> System.out.println("not arrow------------------");
                }

                if(direction != null){
                    myPokemon.move(direction);



                    // IF NOT FIGHT
                    map.moveWildPokemons();
                    this.mapView.refresh();
                }
            }
        });

//        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println("release");
//            }
//        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void fightStarted(Pokemon myPokemon, Pokemon wildPokemon) {
        fightInProgress = true;
    }

    @Override
    public void fightEnded(){
        fightInProgress = false;
    }
}
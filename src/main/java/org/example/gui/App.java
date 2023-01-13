package org.example.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.MoveDirection;
import org.example.Pokemon;
import org.example.RectangularMap;
import org.example.Vector2d;

public class App extends Application {
    private RectangularMap map;
    private GridPane gridPane;
    private int width;
    private int height;
    static final int CELL_SIZE = 60;
    private Pokemon pokemon;

    @Override
    public void init() throws Exception {

        super.init();

        try{
            int numberOfPokemons = 1;
            int numberOfRocks = 5;
            int width = 4;
            int height = 4;
            this.width = width;
            this.height = height;
            RectangularMap map = new RectangularMap(width,height, numberOfPokemons, numberOfRocks);
            this.map = map;
            Pokemon pokemon = map.getMyPokemon();
            this.pokemon = pokemon;
            System.out.println(map);

        }
        catch(IllegalArgumentException ex){
            System.err.println(ex);
            System.exit(1);
        }
    }
    @Override
    public void start(Stage primaryStage){

        this.gridPane = new GridPane();

        System.out.println(map);


        this.createScene();
        addAnimalsAndGrass();

        Scene scene = new Scene(this.gridPane, CELL_SIZE*(width + 1), CELL_SIZE*(height + 1));

        //catching pressed arrows
        scene.setOnKeyPressed(event -> {

            MoveDirection direction = null;

            switch (event.getCode()) {
                case UP -> direction = MoveDirection.UP;
                case DOWN -> direction = MoveDirection.DOWN;
                case LEFT -> direction = MoveDirection.LEFT;
                case RIGHT -> direction = MoveDirection.RIGHT;
                default -> System.out.println("not arrow------------------");
            }
            
            if(direction != null){
                pokemon.move(direction);
                map.moveWildPokemons();
                this.refresh();
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("release");
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void createScene(){

        this.addXYLabel();

        this.addColumns();
        this.addRows();
        this.addAnimalsAndGrass();

        gridPane.setGridLinesVisible(true);
    }
    public void addXYLabel(){
        Label labelXY = new Label("y/x");
        GridPane.setHalignment(labelXY, HPos.CENTER);

        gridPane.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        gridPane.getRowConstraints().add(new RowConstraints(CELL_SIZE));
        gridPane.add(labelXY, 0, 0);
    }
    public void addColumns(){
        for (int i = 1; i - 1 <= width - 1; i++){
            Label label = new Label(String.valueOf(i - 1));

            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
            gridPane.add(label, i, 0);
        }
    }
    public void addRows(){
        for (int i = 1; height - i >= 0; i++){
            Label label = new Label(String.valueOf(height - i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getRowConstraints().add(new RowConstraints(CELL_SIZE));
            gridPane.add(label, 0, i);
        }
    }
    public void addAnimalsAndGrass(){
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Vector2d position = new Vector2d(x, y);
                if (this.map.isOccupied(position)) {
                    Object object = this.map.objectAt(position);
                    VBox elementContainer = new VBox(new Text(object.toString()));
                    gridPane.add(elementContainer, position.x + 1, height - position.y);
                    GridPane.setHalignment(elementContainer, HPos.CENTER);
                }
            }
        }
    }
    public void refresh() {
        Platform.runLater( () -> {
            this.gridPane.getChildren().clear();
            this.gridPane.getColumnConstraints().clear();
            this.gridPane.getRowConstraints().clear();
            gridPane.setGridLinesVisible(false);
            this.createScene();
        });
    }
}
package org.example.gui;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.IMapElement;
import org.example.Pokemon;
import org.example.RectangularMap;
import org.example.Vector2d;

import java.util.Objects;

public class MapView {
    private final int height;
    private final int width;
    private final RectangularMap map;
    private GridPane gridPane;
    static final int CELL_SIZE = 50;
    private final Pokemon myPokemon;
    private final Pokemon bossPokemon;
    private final Color MY_POKEMON_COLOR = Color.YELLOW;
    private final Color BOSS_POKEMON_COLOR = Color.RED;

    public MapView(int width, int height, RectangularMap map){
        this.height = height;
        this.width = width;
        this.map = map;
        this.myPokemon = map.getMyPokemon();
        this.bossPokemon = map.getBossPokemon();
    }
    public Scene getMapScene(){
        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);

        System.out.println(map);


        this.createScene();
        addAnimalsAndGrass();

        Scene scene = new Scene(this.gridPane, CELL_SIZE*width, CELL_SIZE*height);
        return scene;
    }
    public void createScene(){

        this.addColumns();
        this.addRows();
        this.addAnimalsAndGrass();

        gridPane.setGridLinesVisible(true);
    }
    public void addColumns(){
        for (int i = 0; i < width; i++){
            gridPane.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        }
    }
    public void addRows(){
        for (int i = 0; i < height; i++){
            gridPane.getRowConstraints().add(new RowConstraints(CELL_SIZE));
        }
    }
    public void addAnimalsAndGrass(){
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Vector2d position = new Vector2d(x, y);
                if (this.map.isOccupied(position)) {

                    Object object = this.map.objectAt(position);
                    GuiElementBox guiElementBox = new GuiElementBox((IMapElement) object);
                    VBox elementContainer = guiElementBox.getElementContainer();

                    elementContainer.maxWidth(CELL_SIZE);
                    elementContainer.maxHeight(CELL_SIZE);

                    if(Objects.equals(object, myPokemon)){
                        elementContainer.setBackground(getBackgroundOfColor(MY_POKEMON_COLOR));
                    }

                    if(Objects.equals(object, map.getBossPokemon())){
                        elementContainer.setBackground(getBackgroundOfColor(BOSS_POKEMON_COLOR));
                    }

                    gridPane.add(elementContainer, position.x, height - position.y - 1);
                    GridPane.setHalignment(elementContainer, HPos.CENTER);
                }
            }
        }
    }
    private Background getBackgroundOfColor(Color color){
        BackgroundFill background_fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        return new Background(background_fill);
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

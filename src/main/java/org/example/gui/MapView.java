package org.example.gui;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.IMapElement;
import org.example.Pokemon;
import org.example.Map;
import org.example.Vector2d;

import java.util.Objects;

public class MapView {
    private final int height;
    private final int width;
    private final Map map;
    private GridPane gridPane;
    static final int CELL_SIZE = 55;
    private final Pokemon myPokemon;
    private final Color MY_POKEMON_COLOR = Color.YELLOW;
    private final Color BOSS_POKEMON_COLOR = Color.ORANGE;
    private final Color GRID_PANE_COLOR = Color.rgb(98,239,145);
    static final String MAP_BACKGROUND = "mapBackground.png";

    public MapView(int width, int height, Map map){
        this.height = height;
        this.width = width;
        this.map = map;
        this.myPokemon = map.getMyPokemon();
    }
    public Scene getMapScene(){
        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.setBackground(getBackgroundOfColor(GRID_PANE_COLOR));

        int scene_width = CELL_SIZE*width;
        int scene_height = CELL_SIZE*height;

        BackgroundImage myBI = new BackgroundImage(new Image(MAP_BACKGROUND,scene_width, scene_height,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        gridPane.setBackground(new Background(myBI));

        this.createScene();
        addAnimalsAndGrass();

        return new Scene(this.gridPane, scene_width, scene_height);
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

                    elementContainer.setPrefSize(CELL_SIZE, CELL_SIZE);

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

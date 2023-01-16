package org.example.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.IFightEndObserver;
import org.example.Pokemon;

import java.util.Objects;

public class WinView implements IFightEndObserver {
    private final Pokemon firstBoss;
    private final static int WIN_SCENE_WIDTH = 500;
    private final static int WIN_SCENE_HEIGHT = 300;
    private static final int TITLE_FONT_SIZE = 30;
    private static final int SUBTITLE_FONT_SIZE = 20;
    public WinView(Pokemon firstBoss){
        this.firstBoss = firstBoss;
    }

    public void getWinScene(){

        VBox sceneContainer = createWinSceneConatiner();

        Scene scene = new Scene(sceneContainer,WIN_SCENE_WIDTH, WIN_SCENE_HEIGHT);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
    }

    public VBox createWinSceneConatiner(){

        Text title = new Text("WYGRALES");
        title.setFont(new Font(TITLE_FONT_SIZE));

        Text subtitle1 = new Text("Gratulecje, pokonales swojego pierwszego bosa!");
        Text subtitle2 = new Text("Jesli chcesz mozesz grac dalej :)");

        subtitle1.setFont(new Font(SUBTITLE_FONT_SIZE));
        subtitle2.setFont(new Font(SUBTITLE_FONT_SIZE));

        VBox box = new VBox(title, subtitle1, subtitle2);
        box.setAlignment(Pos.CENTER);
        return box;
    }
    @Override
    public void fightEnded(Pokemon winner, Pokemon looser) {
        if(Objects.equals(firstBoss, looser)){
            getWinScene();
        }
    }
}

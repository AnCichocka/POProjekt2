package org.example.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.IMapElement;
import org.example.Pokemon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    final static int IMAGE_SIZE = 42;
    private final VBox elementContainer;

    public GuiElementBox(IMapElement element){
        try{
            String path = element.getImagePath();

            Image image = new Image(new FileInputStream(path));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(IMAGE_SIZE);
            imageView.setFitHeight(IMAGE_SIZE);

            this.elementContainer = new VBox(imageView);
            this.elementContainer.setAlignment(Pos.CENTER);

            int level = -1;

            if(element instanceof Pokemon){
                level = ((Pokemon)element).getLevel();
            }
            if(level != -1){
                Label levelLabel = new Label("lvl. " + level);
                this.elementContainer.getChildren().add(levelLabel);
            }
        }
        catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public VBox getElementContainer(){
        return this.elementContainer;
    }


}
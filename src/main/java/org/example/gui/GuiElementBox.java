package org.example.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.IMapElement;
import org.example.Pokemon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    final static int IMAGE_SIZE = 42;
    private VBox elementContainer;
    private ImageView imageView;

    public GuiElementBox(IMapElement element){
        try{
            String path = null;

            if(element instanceof Pokemon){
                path = "src/main/piplup.png";
            }
            else{
                path = "src/main/bulbasaur.png";
            }
            Image image = new Image(new FileInputStream(path));
            imageView = new ImageView(image);
            imageView.setFitWidth(IMAGE_SIZE);
            imageView.setFitHeight(IMAGE_SIZE);
        }
        catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

        Label elementPosition = new Label(element.getPosition().toString());
        this.elementContainer = new VBox(imageView, elementPosition);
        this.elementContainer.setAlignment(Pos.CENTER);
    }

    public VBox getElementContainer(){
        return this.elementContainer;
    }


}
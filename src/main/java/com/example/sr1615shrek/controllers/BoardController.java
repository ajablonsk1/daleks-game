package com.example.sr1615shrek.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView
public class BoardController {

    @FXML
    public GridPane grid;

    @FXML
    private void initialize(){
        SetBoardGridPane();
    }

    public GridPane SetBoardGridPane(){

        int columns = 10; // board.getWidth();
        int rows = 10; //board.getHeight();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                Tile tile = new Tile();
                tile.setTranslateX(j * 50);
                tile.setTranslateY(i * 50);

                //PRZYKLADOWE OBIEKTY
                if(j % 4 == 0 && i % 3 == 1){
                    tile.drawO();
                }
                if(j % 5 == 0 && i % 6 == 2){
                    tile.drawX();
                }
                grid.getChildren().add(tile);
            }
        }
        return grid;
    }

    private static class Tile extends StackPane{
        private Text text = new Text();

        public Tile(){
            Rectangle rectangle = new Rectangle(50,50);
            rectangle.setFill(null);
            rectangle.setStroke(Color.BLACK);

            setAlignment(Pos.CENTER);
            getChildren().addAll(rectangle, text);
        }
        private void drawO(){
            text.setText("O");
        }
        private void drawX(){
            text.setText("X");
        }
    }
}
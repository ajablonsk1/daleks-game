package com.example.sr1615shrek.view;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.EntityHierarchy;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

@Controller
@FxmlView
public class BoardPresenter {

    @FXML
    public GridPane grid;

    private final Board board;

    private final Map<Vector2d, Tile> tiles = new HashMap<>();

    private List<Entity> entities;

    @FXML
    private void initialize(){
        setBoardGridPane();
    }


    @Autowired
    public BoardPresenter(Board board){
        this.board = board;
        entities = board.getEntities();
    }

    // Setting the board with given game conditions
    public void setBoardGridPane(){

        // Filling the board with tiles
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {

                Tile tile = new Tile(i, j);

                //JUST FOR VISUALISATION
                if(i % 3 == 0 && j % 4 == 0){
                    tile.drawD();
                } else if(i % 4 == 0 && j % 5 == 0){
                    tile.drawS();
                }

                tile.setTranslateX(j * 50);
                tile.setTranslateY(i * 50);
                grid.getChildren().add(tile);
                tiles.put(new Vector2d(i, j), tile);
            }
        }


        // Drawing existing entities in the start of the game
        this.entities.forEach(entity -> {
            if(entity.getRank() == EntityHierarchy.DYNAMIC) {
                tiles.get(entity.getPosition()).drawD();
            }
            else{
                tiles.get(entity.getPosition()).drawS();
            }
        });
    }

    private static class Tile extends StackPane{
        private final Text text = new Text();

        private final int x;

        private final int y;

        public Tile(int x, int y){
            Rectangle rectangle = new Rectangle(50,50);
            rectangle.setFill(null);
            rectangle.setStroke(Color.BLACK);

            setAlignment(Pos.CENTER);
            getChildren().addAll(rectangle, text);
            this.x = x;
            this.y = y;
        }
        private void drawS(){
            text.setText("S");
        }
        private void drawD(){
            text.setText("D");
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    // Function to update the visualisation of entities
    public void updateMap(List<Entity> entities){
        this.entities = entities;
    }
}
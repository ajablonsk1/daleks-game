package com.example.sr1615shrek.view;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.EntityHierarchy;
import com.example.sr1615shrek.entity.position.Vector2d;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@FxmlView
public class BoardPresenter {

    @FXML
    public GridPane grid;

    @FXML
    private void initialize(){
        setBoardGridPane();
    }

    private int columns;

    private int rows;

    private List<Entity> entities;

    private Map<Vector2d, Tile> tiles = new HashMap<>();

    public void setBoardGridPane(){
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {

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
        private Text text = new Text();

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

    //Set the starting game data
    public void setData(int columns,
                        int rows,
                        List<Entity> entities){
        this.columns = columns;
        this.rows = rows;
        this.entities = entities;
    }

    public void updateEntities(List<Entity> entities){
        this.entities = entities;
    }
}
package com.example.sr1615shrek.view;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            for (int j = board.getWidth()-1; j >= 0; j--) {
                Tile tile = new Tile(i, j);

                tile.setTranslateX(i * 50);
                tile.setTranslateY(j * 50);
                grid.getChildren().add(tile);
                tiles.put(new Vector2d(i, j), tile);
            }
        }
    }


    private void makePopUpWindow(String textString) {
        grid.getChildren().clear();
        Text text = new Text(textString);
        text.setStyle("-fx-font: 150 arial;");
        grid.getChildren().add(text);
    }

    public void showPopUpWindowForLose() {
        makePopUpWindow("Game Over");
    }

    public void showPopUpWindowForWin() {
        makePopUpWindow("Win");
    }

    private static class Tile extends StackPane{
        private final ImageView imageView = new ImageView();

        private final int x;

        private final int y;

        public Tile(int x, int y) {
            Rectangle rectangle = new Rectangle(50, 50);
            rectangle.setFill(null);
            rectangle.setStroke(Color.BLACK);

            setAlignment(Pos.CENTER);

            imageView.setFitWidth(50);
            imageView.setFitHeight(50);

            getChildren().addAll(rectangle, imageView);
            this.x = x;
            this.y = y;
        }

        // Drawing entities graphics (for now)
        private void draw(Entity entity) {
            imageView.setImage(entity.getGraphics());
        }

        private void putBlank() {
            imageView.setImage(null);
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
        this.tiles.values().forEach(Tile::putBlank);
        this.entities.forEach(entity -> {
            tiles.get(entity.getPosition()).draw(entity);
        });
    }
}

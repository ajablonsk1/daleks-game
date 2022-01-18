package com.example.sr1615shrek.view;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.Board;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
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

@Controller
@FxmlView
public class BoardPresenter {

    @FXML
    public GridPane grid;

    @FXML
    public Text teleportCounter;

    @FXML
    public Text timeReverseCounter;

    private final Board board;

    private final Map<Vector2d, Tile> tiles = new HashMap<>();

    private List<Entity> entities;

    private final AppController appController;

    private final ImageService imageService;

    private static final int TILE_SIZE = 50;

    @FXML
    private void initialize(){
        setBoardGridPane();
    }

    @Autowired
    public BoardPresenter(Board board, AppController appController, ImageService imageService){
        this.board = board;
        entities = board.getEntities();
        this.appController = appController;
        this.imageService = imageService;
    }

    // Setting the board with given game conditions
    public void setBoardGridPane(){

        // Filling the board with tiles
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = board.getWidth()-1; j >= 0; j--) {
                Tile tile = new Tile(i, j);

                tile.setTranslateX(i * TILE_SIZE);
                tile.setTranslateY(j * TILE_SIZE);
                grid.getChildren().add(tile);
                tiles.put(new Vector2d(i, j), tile);
            }
        }
    }

    private class Tile extends StackPane{
        private final ImageView imageView = new ImageView();

        private final int x;

        private final int y;

        public Tile(int x, int y) {
            Rectangle rectangle = new Rectangle(TILE_SIZE, TILE_SIZE);
            rectangle.setFill(null);
            rectangle.setStroke(Color.LIGHTGRAY);

            setAlignment(Pos.CENTER);

            imageView.setFitWidth(TILE_SIZE);
            imageView.setFitHeight(TILE_SIZE);

            getChildren().addAll(rectangle, imageView);
            this.x = x;
            this.y = y;
        }

        // Drawing entities graphics (for now)
        private void draw(Entity entity) {
            imageView.setImage(imageService.getEntityImage(entity));
        }

        private void putBlank() {
            imageView.setImage(null);
        }
    }

    public void showPopUpWindowForLose() {
        this.appController.initGameOverViewIfLose();
    }

    public void showPopUpWindowForWin() {
        this.appController.initGameOverViewIfWin();
    }

    // Function to update the visualisation of entities
    public void updateMap(List<Entity> entities){
        this.entities = entities;
        this.tiles.values().forEach(Tile::putBlank);
        this.entities.forEach(entity -> {
            tiles.get(entity.getPosition()).draw(entity);
        });
        updatePowerUpsCounters();
    }

    private void updatePowerUpsCounters(){
        Doctor doctor = this.board.getDoctor();
        teleportCounter.setText(String.valueOf(doctor.getTeleportNumber()));
        timeReverseCounter.setText(String.valueOf(doctor.getTimeReverseNumber()));
    }
}

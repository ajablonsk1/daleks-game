package com.example.sr1615shrek.view;

import com.example.sr1615shrek.entity.position.Vector2d;
import com.example.sr1615shrek.game.GameInitializer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@FxmlView
@Component
public class CampaignModePresenter {

    @FXML
    public Text text;

    @FXML
    public Button playBtn;
    
    @FXML
    public ImageView imageView;

    @FXML
    public AnchorPane anchorPane;

    private final AppController appController;
    private final GameInitializer startGame;

    private final static int IMAGE_SIZE = 80;

    private int lvlNumber = 0;
    private final Map<Integer, Vector2d> circleLvlPosition = new HashMap<>();

    private List<Node> circles;

    @FXML
    private void initialize(){
        circles = anchorPane.getChildren().stream().filter(Circle.class::isInstance).toList();

        fillTheMap();
        loadImage();
        gameComplete();
    }

    public void loadImage(){
        imageView.setFitWidth(IMAGE_SIZE);
        imageView.setFitHeight(IMAGE_SIZE);

        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/shrek_face.png")));
        imageView.setImage(image);

        showPlayer();
    }

    @Autowired
    public CampaignModePresenter(AppController appController,
                            GameInitializer startGame){
        this.appController = appController;
        this.startGame = startGame;
    }

    private Vector2d calculateCircleCenter(Circle circle){
        return new Vector2d(
                (int)(circle.getLayoutX() - IMAGE_SIZE/2),
                (int)(circle.getLayoutY() - IMAGE_SIZE/2)
        );
    }

    private void fillTheMap(){
        for(int i = 0; i<10; i++)
            circleLvlPosition.put(i, calculateCircleCenter((Circle) circles.get(i)));
    }


    public void play() {
        this.startGame.startGame(this.appController.getStage());
    }

    public void goHome() {
        this.appController.initWelcomeView();
    }


    private void showPlayer(){
        Vector2d playerPosition = circleLvlPosition.get(lvlNumber);
        imageView.setX(playerPosition.getX());
        imageView.setY(playerPosition.getY());

    }

    private void gameComplete(){
        if(lvlNumber >= 9){
            playBtn.setDisable(true);

            Text endGameText = new Text("Congratulations, you have completed the campaign mode!");
            endGameText.setStyle("-fx-font: 24 arial;");
            endGameText.setX(180);
            endGameText.setY(800);
            anchorPane.getChildren().add(endGameText);
        }
    }

    public void nextLevel(){
        try{
            TimeUnit.MILLISECONDS.sleep(100);
        }catch (Exception e){
            System.out.println(e);
        }

        if(this.lvlNumber < 9){
            this.lvlNumber++;
        }
        else{
           gameComplete();
        }
        showPlayer();
    }
}

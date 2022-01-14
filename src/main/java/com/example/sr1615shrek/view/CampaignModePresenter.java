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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

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

    @Value("${campaignModePresenter.imageSize}")
    private int IMAGE_SIZE;

    private int levelID = 9;

    private final Map<Integer, Vector2d> circleLvlPosition = new HashMap<>();

    private List<Node> circles;

    @Value("${campaignModePresenter.maxLevelID}")
    private int maxLevelID;

    @Value("${campaignModePresenter.playerImagePath}")
    private String playerImagePath;

    @Value("${campaignModePresenter.congratulationForEndOfCampaignString}")
    private String congratulationForEndOfCampaignString;

    @Value("${campaignModePresenter.gameCompleteTextPositionX}")
    private int gameCompleteTextPositionX;

    @Value("${campaignModePresenter.gameCompleteTextPositionY}")
    private int gameCompleteTextPositionY;

    @FXML
    private void initialize(){
        this.circles = this.anchorPane.getChildren()
                .stream()
                .filter(Circle.class::isInstance)
                .toList();

        fillTheMap();
        loadImage();
        gameComplete();
    }

    public void loadImage(){
        imageView.setFitWidth(IMAGE_SIZE);
        imageView.setFitHeight(IMAGE_SIZE);

        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(playerImagePath)));
        imageView.setImage(image);

        movePlayerIcon();
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

    public void playCampaign() {
        this.startGame.startGameCampaign(this.appController.getStage(), levelID);
    }

    public void goHome() {
        this.appController.initWelcomeView();
    }

    private void movePlayerIcon(){
        Vector2d playerPosition = circleLvlPosition.get(Math.min(this.levelID, this.maxLevelID-1));
        imageView.setX(playerPosition.getX());
        imageView.setY(playerPosition.getY());
    }

    private void gameComplete(){
        if(this.levelID >= this.maxLevelID){
            playBtn.setDisable(true);

            Text endGameText = new Text(congratulationForEndOfCampaignString);
            endGameText.setStyle("-fx-font: 24 arial;");
            endGameText.setX(gameCompleteTextPositionX);
            endGameText.setY(gameCompleteTextPositionY);
            anchorPane.getChildren().add(endGameText);
        }
    }

    public void nextLevel(){
        this.levelID = Math.min(this.levelID +1, this.maxLevelID);
    }
}

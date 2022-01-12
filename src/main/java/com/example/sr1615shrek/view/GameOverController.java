package com.example.sr1615shrek.view;

import com.example.sr1615shrek.game.GameInitializer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@FxmlView
@Component
public class GameOverController {

    @FXML
    public Text text;

    @FXML
    public Button button;

    @FXML
    public Button home;

    private final AppController appController;

    private final GameInitializer startGame;

    @FXML
    private void initialize(){
    }

    @Autowired
    public GameOverController(GameInitializer startGame){
        this.startGame = startGame;
        this.appController = this.startGame.getAppController();
    }

    public void playAgain(){        // TODO: think how to start new game looking at GameType
        this.startGame.startGameRandom(this.appController.getStage());
    }

    public void setInfoText(String text){
        this.text.setText(text);
    }

    public void goToHomePage() {
        this.appController.initWelcomeView();
    }
}

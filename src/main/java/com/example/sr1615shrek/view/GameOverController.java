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

    private final AppController appController;

    private GameInitializer startGame;

    @FXML
    private void initialize(){
    }

    @Autowired
    public GameOverController(AppController appController,
                              GameInitializer startGame){
        this.appController = appController;
        this.startGame = startGame;
    }

    public void playAgain(){
        this.startGame.startGame(this.appController.getStage());
    }

    public void setInfoText(String text){
        this.text.setText(text);
    }
}

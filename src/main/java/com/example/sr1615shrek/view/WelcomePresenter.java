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
public class WelcomePresenter {

    private final GameInitializer startGame;
    private final AppController appController;

    @FXML
    public Text text;

    @FXML
    public Button randomGameBtn;

    @FXML
    public Button campaignModeBtn;

    @FXML
    private void initialize(){
    }

    @Autowired
    public WelcomePresenter(GameInitializer startGame){
        this.startGame = startGame;
        this.appController = startGame.getAppController();
    }

    public void playRandom() {
        this.startGame.startGameRandom(this.appController.getStage());
    }

    public void playCampaignMode() {
        this.appController.initCampaignModeView();
    }
}

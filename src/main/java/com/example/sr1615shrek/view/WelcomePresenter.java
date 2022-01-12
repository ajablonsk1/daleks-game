package com.example.sr1615shrek.view;

import com.example.sr1615shrek.GameApplication;
import com.example.sr1615shrek.game.GameInitializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@FxmlView
@Component
public class WelcomePresenter {

    private final AppController appController;
    private final GameInitializer startGame;
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
    public WelcomePresenter(AppController appController,
                              GameInitializer startGame){
        this.appController = appController;
        this.startGame = startGame;
    }


    public void playRandom() {
        this.startGame.turnOffCampaignMode();
        this.startGame.startGame(this.appController.getStage());
    }

    public void playCampaignMode() {
        this.startGame.turnOnCampaignMode();
        this.appController.initCampaignModeView();
    }
}

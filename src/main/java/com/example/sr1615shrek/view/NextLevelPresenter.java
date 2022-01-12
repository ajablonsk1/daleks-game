package com.example.sr1615shrek.view;

import com.example.sr1615shrek.game.GameInitializer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@FxmlView
@Component
public class NextLevelPresenter {

    @FXML
    public Button nextLvlBtn;

    @FXML
    public Button home;

    private final AppController appController;

    private GameInitializer startGame;

    @FXML
    private void initialize(){
    }

    @Autowired
    public NextLevelPresenter(AppController appController,
                              GameInitializer startGame){
        this.appController = appController;
        this.startGame = startGame;
    }


    public void goToNextLevel() {
        this.appController.initCampaignModeView();
        this.appController.getCampaignModePresenter().nextLevel();
    }

    public void goToHomePage() {
        this.appController.getCampaignModePresenter().nextLevel();
        this.appController.initWelcomeView();
    }
}

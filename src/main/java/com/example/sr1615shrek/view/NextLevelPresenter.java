package com.example.sr1615shrek.view;

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

    @FXML
    private void initialize(){
        this.appController.nextLevel();
    }

    @Autowired
    public NextLevelPresenter(AppController appController){
        this.appController = appController;
    }

    public void goToNextLevel() {
        this.appController.initCampaignModeView();
    }

    public void goToHomePage() {
        this.appController.initWelcomeView();
    }
}

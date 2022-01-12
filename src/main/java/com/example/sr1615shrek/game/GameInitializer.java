package com.example.sr1615shrek.game;

import com.example.sr1615shrek.moveControllers.DoctorMoveController;
import com.example.sr1615shrek.view.AppController;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameInitializer {

    private final AppController appController;

    private final Engine engine;

    private final DoctorMoveController doctorMoveController;

    private boolean campaignMode = false;

    @Autowired
    public GameInitializer(AppController appController, Engine engine, DoctorMoveController doctorMoveController) {
        this.appController = appController;
        this.engine = engine;
        this.doctorMoveController = doctorMoveController;
    }

    public void startGame(Stage stage){
        this.appController.setStage(stage);
        this.appController.initBoardView();
        this.doctorMoveController.setScene(this.appController.getStage().getScene());
        this.engine.start(campaignMode);
    }

    public void turnOnCampaignMode() {
        this.campaignMode = true;
    }

    public void turnOffCampaignMode() {
        this.campaignMode = false;
    }
}

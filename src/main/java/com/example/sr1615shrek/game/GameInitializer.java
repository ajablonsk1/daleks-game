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

    @Autowired
    public GameInitializer(AppController appController, Engine engine, DoctorMoveController doctorMoveController) {
        this.appController = appController;
        this.engine = engine;
        this.doctorMoveController = doctorMoveController;
    }

    private void initBeforeStartGame(Stage stage, GameType gameType) {
        this.appController.setStage(stage);
        this.appController.setCurrentGameType(gameType);
        this.appController.initBoardView();
        this.doctorMoveController.setScene(this.appController.getStage().getScene());
    }

    public void startGameRandom(Stage stage){
        this.initBeforeStartGame(stage, GameType.RANDOM);
        this.engine.startRandom();
    }

    public void startGameCampaign(Stage stage, String pathToLevelSettings) {
        this.initBeforeStartGame(stage, GameType.CAMPAIGN);
        this.engine.startCampaign(pathToLevelSettings);
    }

    public AppController getAppController() {
        return appController;
    }
}

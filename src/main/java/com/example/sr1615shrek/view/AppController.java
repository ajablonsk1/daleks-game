package com.example.sr1615shrek.view;

import com.example.sr1615shrek.game.GameType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class AppController {

    private Stage stage;

    private final FxWeaver fxWeaver;

    @Value("${appController.gameOverText}")
    private String GAME_OVER_TEXT;

    @Value("${appController.gameWinText}")
    private String GAME_WIN_TEXT;

    private GameType currentGameType = GameType.RANDOM;

    @Autowired
    public AppController(FxWeaver fxWeaver){
        this.fxWeaver = fxWeaver;
    }

    public void initBoardView() {
        Parent root = fxWeaver.loadView(BoardPresenter.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initGameOverViewIfWin() {
        switch (currentGameType) {
            case RANDOM -> initGameOverView(GAME_WIN_TEXT);
            case CAMPAIGN -> initNextLevelView();
        }
    }

    public void initGameOverViewIfLose() {
        initGameOverView(GAME_OVER_TEXT);
    }

    private void initGameOverView(String text){
        Parent root = fxWeaver.loadView(GameOverController.class);
        GameOverController gameOverController = fxWeaver.getBean(GameOverController.class);
        gameOverController.setInfoText(text);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initWelcomeView(){
        Parent root = fxWeaver.loadView(WelcomePresenter.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initCampaignModeView(){
        Parent root = fxWeaver.loadView(CampaignModePresenter.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initNextLevelView(){
        Parent root = fxWeaver.loadView(NextLevelPresenter.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void nextLevel(){
        getCampaignModePresenter().nextLevel();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public Stage getStage(){
        return this.stage;
    }

    public CampaignModePresenter getCampaignModePresenter(){
        return fxWeaver.getBean(CampaignModePresenter.class);
    }

    public void setCurrentGameType(GameType currentGameType) {
        this.currentGameType = currentGameType;
    }

    public GameType getCurrentGameType() {
        return currentGameType;
    }
}

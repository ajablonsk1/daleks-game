package com.example.sr1615shrek.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AppController {

    private Stage stage;

    private final FxWeaver fxWeaver;

    @Autowired
    public AppController(FxWeaver fxWeaver){
        this.fxWeaver = fxWeaver;
    }

    // Initializing the board view, which is the actual game view
    public void initBoardView() {
        Parent root = fxWeaver.loadView(BoardPresenter.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initGameOverView(String text){
        Parent root = fxWeaver.loadView(GameOverController.class);
        GameOverController gameOverController = fxWeaver.getBean(GameOverController.class);
        gameOverController.setTextField(text);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public Stage getStage(){
        return this.stage;
    }
}

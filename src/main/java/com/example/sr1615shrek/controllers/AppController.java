package com.example.sr1615shrek.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AppController {

    private Stage stage;

    FxWeaver fxWeaver;

    BoardController boardController;

    @Autowired
    public AppController(BoardController boardController, FxWeaver fxWeaver){
        this.boardController = boardController;
        this.fxWeaver = fxWeaver;
    }

    public void initBoardView() {
        Parent root = fxWeaver.loadView(BoardController.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}

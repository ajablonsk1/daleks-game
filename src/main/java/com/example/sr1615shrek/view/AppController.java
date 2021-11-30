package com.example.sr1615shrek.view;

import com.example.sr1615shrek.game.Engine;
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

    BoardPresenter boardPresenter;

    Engine engine;

    @Autowired
    public AppController(BoardPresenter boardPresenter,
                         FxWeaver fxWeaver,
                         Engine engine){
        this.boardPresenter = boardPresenter;
        this.fxWeaver = fxWeaver;
        this.engine = engine;
    }

    // Initializing the board view, which is the actual game view
    public void initBoardView() {
        Parent root = fxWeaver.loadView(BoardPresenter.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        //Does nothing for now
        engine.start();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}

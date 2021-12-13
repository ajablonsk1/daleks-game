package com.example.sr1615shrek.view;

import com.example.sr1615shrek.game.Engine;
import com.example.sr1615shrek.moveControllers.DoctorMoveController;
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

    private Engine engine;

    private DoctorMoveController doctorMoveController;

    @Autowired
    public AppController(FxWeaver fxWeaver,
                         Engine engine,
                         DoctorMoveController doctorMoveController){
        this.fxWeaver = fxWeaver;
        this.engine = engine;
        this.doctorMoveController = doctorMoveController;
    }

    // Initializing the board view, which is the actual game view
    public void initBoardView() {
        Parent root = fxWeaver.loadView(BoardPresenter.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        doctorMoveController.setScene(scene);
        doctorMoveController.setOnKeyHandler();
        stage.show();

        //Does nothing for now
        engine.start();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}

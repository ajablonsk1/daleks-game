package com.example.sr1615shrek.moveControllers;

import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.game.Engine;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DoctorMoveController {

    private Scene scene;

    private Engine engine;

    @Autowired
    public DoctorMoveController(Engine engine){
        this.engine = engine;
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }

    public void setOnKeyHandler(){
        this.scene.setOnKeyReleased(event -> {
            switch (event.getCode()){
                case DIGIT8 -> engine.startTurn(Direction.NORTH);
                case DIGIT9 -> engine.startTurn(Direction.NORTHEAST);
                case DIGIT6 -> engine.startTurn(Direction.EAST);
                case DIGIT3 -> engine.startTurn(Direction.SOUTHEAST);
                case DIGIT2 -> engine.startTurn(Direction.SOUTH);
                case DIGIT1 -> engine.startTurn(Direction.SOUTHWEST);
                case DIGIT4 -> engine.startTurn(Direction.WEST);
                case DIGIT7 -> engine.startTurn(Direction.NORTHWEST);
            }
        });
    }
}

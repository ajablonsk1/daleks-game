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

    private final Engine engine;

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
                case NUMPAD8 -> engine.startTurn(Direction.SOUTH);
                case NUMPAD9 -> engine.startTurn(Direction.SOUTHEAST);
                case NUMPAD6 -> engine.startTurn(Direction.EAST);
                case NUMPAD3 -> engine.startTurn(Direction.NORTHEAST);
                case NUMPAD2 -> engine.startTurn(Direction.NORTH);
                case NUMPAD1 -> engine.startTurn(Direction.NORTHWEST);
                case NUMPAD4 -> engine.startTurn(Direction.WEST);
                case NUMPAD7 -> engine.startTurn(Direction.SOUTHWEST);
            }
        });
    }
}

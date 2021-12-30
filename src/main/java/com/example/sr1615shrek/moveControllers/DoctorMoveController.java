package com.example.sr1615shrek.moveControllers;

import com.example.sr1615shrek.entity.position.Direction;
import com.example.sr1615shrek.game.Engine;
import javafx.scene.Scene;
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
        this.initOnKeyHandler();
    }

    private void initOnKeyHandler(){
        this.scene.setOnKeyReleased(event -> {
            switch (event.getCode()){
                case NUMPAD8, W -> engine.startTurn(Direction.SOUTH);
                case NUMPAD9, E -> engine.startTurn(Direction.SOUTHEAST);
                case NUMPAD6, D -> engine.startTurn(Direction.EAST);
                case NUMPAD3, C -> engine.startTurn(Direction.NORTHEAST);
                case NUMPAD2, S -> engine.startTurn(Direction.NORTH);
                case NUMPAD1, Z-> engine.startTurn(Direction.NORTHWEST);
                case NUMPAD4, A -> engine.startTurn(Direction.WEST);
                case NUMPAD7, Q -> engine.startTurn(Direction.SOUTHWEST);
            }
        });
    }
}

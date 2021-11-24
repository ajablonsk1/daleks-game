package com.example.sr1615shrek.controllers;

import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView
public class BoardController {

    public void saySomething(){
        System.out.println("I am giving up on you");
    }
}

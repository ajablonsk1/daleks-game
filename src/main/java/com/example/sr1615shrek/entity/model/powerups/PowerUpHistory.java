package com.example.sr1615shrek.entity.model.powerups;

import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Component
public class PowerUpHistory {

    private Stack<Pair<Integer, PowerUp>> historyStack= new Stack<>();

    public PowerUpHistory(){}

    public void push(int tour, PowerUp powerUp){
        historyStack.push(new Pair<>(tour, powerUp));
    }

    public List<PowerUp> pop(int tour){
        List<PowerUp> powerUps = new ArrayList<>();
        Pair<Integer, PowerUp> powerUpPair = historyStack.pop();
        while(tour == powerUpPair.getKey()){
            powerUps.add(powerUpPair.getValue());
            if(historyStack.empty()){
                break;
            }
            powerUpPair = historyStack.pop();
        }
        historyStack.push(powerUpPair);
        return powerUps;
    }
}

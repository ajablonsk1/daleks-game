package com.example.sr1615shrek.game;

import com.example.sr1615shrek.view.BoardPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Engine {

    private Board board;

    private final BoardPresenter boardPresenter;

    @Autowired
    public Engine(BoardPresenter boardPresenter, Board board){
        this.board = board;
        this.boardPresenter = boardPresenter;
    }

    public void start(){
        //IN FUTURE
    }
}

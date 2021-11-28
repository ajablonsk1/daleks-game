package com.example.sr1615shrek.config;

import com.example.sr1615shrek.collisions.CollisionDetector;
import com.example.sr1615shrek.game.Board;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Board board(){
        return new Board(40,80);
    }
}

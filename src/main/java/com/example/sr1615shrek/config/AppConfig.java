package com.example.sr1615shrek.config;

import com.example.sr1615shrek.game.Board;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;

@Configuration
public class AppConfig {

    @Value("${appConfig.width}")
    private int width;

    @Value("${appConfig.height}")
    private int height;

    @Bean
    public Board board(){
        return new Board(width, height);
    }
}


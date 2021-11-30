package com.example.sr1615shrek.config;

import com.example.sr1615shrek.game.Board;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;

@Configuration
public class AppConfig {

    @Bean
    public Board board() throws IOException, ParseException {

        //Configuration board width and height from the configuration file
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse
                (new FileReader("src/main/resources/configuration.json"));

        int width = Integer.parseInt((String) jsonObject.get("width"));
        int height = Integer.parseInt((String) jsonObject.get("height"));

        return new Board(width, height);
    }
}


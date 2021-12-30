package com.example.sr1615shrek;

import com.example.sr1615shrek.game.GameInitializer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavafxGameApp extends Application {

    private ConfigurableApplicationContext applicationContext;

    private GameInitializer startGame;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(GameApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.startGame = applicationContext.getBean(GameInitializer.class);
        startGame.startGame(stage);
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}

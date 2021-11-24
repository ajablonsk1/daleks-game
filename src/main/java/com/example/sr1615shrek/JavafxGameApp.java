package com.example.sr1615shrek;

import com.example.sr1615shrek.controllers.AppController;
import com.example.sr1615shrek.controllers.BoardController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

public class JavafxGameApp extends Application {

    private ConfigurableApplicationContext applicationContext;

    private AppController appController;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(GameApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AppController appController = applicationContext.getBean(AppController.class);
        appController.setStage(stage);
        appController.initBoardView();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}

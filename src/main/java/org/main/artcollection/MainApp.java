package org.main.artcollection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApp extends Application {
    public static Scene MAIN_SCENE;
    public static MainController MAIN_CONTROLLER;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        FXMLLoader mainLoader = new FXMLLoader(MainApp.class.getResource("main.fxml"));
        MAIN_SCENE = new Scene(mainLoader.load());
        MAIN_CONTROLLER = mainLoader.getController();

        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        stage.sceneProperty().addListener((a, b, c) -> {
            MAIN_CONTROLLER.initialize();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
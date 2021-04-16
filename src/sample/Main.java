package sample;

import businesslogic.*;

import domain.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //launch(args);

        Strategy strategy = new Strategy("asd444" ,"sdfs", "ffff", "sdfsd", 31, "sdfsf");
        Strategy strategyExpected = new Strategy("ii4d", "implementacion", "implementacion", "Preparar", 3, "Mejorar");

        StrategyDAO strategyDAO = new StrategyDAO();
        strategyDAO.addStrategy(strategy);
        strategyDAO.addStrategy(strategyExpected);
    }
}

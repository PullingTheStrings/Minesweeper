package com.example.minesweeper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author elliotadamson
 */
public class StartMenu {


    public void start(Stage primaryStage) {


    }

    /**
     * @param args the command line arguments
     */
    public void processStartButtonPress(ActionEvent event)
    {

    }
    public static void main(String[] args) {
        Stage primaryStage=new Stage(StageStyle.UNDECORATED);
        Menu menu=new Menu();
        TextField numberOfRows=new TextField();
        TextField numberOfColumns=new TextField();
        TextField numberOfMines=new TextField();
        Button btnStart = new Button();
        Button btnQuit=new Button();
        btnStart.setText("Start");
        btnQuit.setText("Quit");


        Pane root = new Pane();
        root.getChildren().add(btnStart);
        root.getChildren().add(btnQuit);
        root.getChildren().add(numberOfRows);
        root.getChildren().add(numberOfColumns);
        root.getChildren().add(numberOfMines);
        //root.getChildren().add(btnStart);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
package com.example.minesweeper;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

/**
 *
 * @author elliotadamson
 */
public class FinalProject extends Application {

    private Stage primaryStage;//this will have the grid pane with all the blocks
    private Stage startStage;// this will be the start menu
    private int numberOfRows;//self-explanatory
    private int numberOfColumns;//self-explanatory
    private int numberOfMines;//self-explanatory
    private TextField setNumberOfRows = new TextField();//self-explanatory
    private TextField setNumberOfColumns = new TextField();//self-explanatory
    private TextField setNumberOfMines = new TextField();//self-explanatory
    private ExtendedGridPane root;//referenced in some of the methods
    private Scene scene;//the scene



    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);

        root = new ExtendedGridPane();


        Stage startStage = new Stage(StageStyle.UNDECORATED);
        setStartStage(startStage);
        Menu menu = new Menu();
        setNumberOfRows = new TextField();
        setNumberOfColumns = new TextField();
        setNumberOfMines = new TextField();
        Button btnStart = new Button();
        Button btnQuit = new Button();
        Label label1 = new Label("Number of Rows:");
        Label label2 = new Label("Number of Columns:");
        Label label3 = new Label("Number of Mines:");
        Text text = new Text("Let's Play Minesweeper!");
        Font font = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 36);
        text.setFont(font);

        btnStart.setText("Start");
        btnQuit.setText("Quit");
        btnStart.setLayoutX(200);
        btnStart.setLayoutY(400);
        btnQuit.setLayoutX(350);
        btnQuit.setLayoutY(400);
        setNumberOfRows.setLayoutX(300);
        setNumberOfColumns.setLayoutX(300);
        setNumberOfMines.setLayoutX(300);
        setNumberOfRows.setLayoutY(125);
        setNumberOfColumns.setLayoutY(225);
        setNumberOfMines.setLayoutY(325);
        label1.setLayoutX(150);
        label1.setLayoutY(130);
        label2.setLayoutX(150);
        label3.setLayoutX(150);
        label2.setLayoutY(230);
        label3.setLayoutY(330);
        text.setLayoutX(100);
        text.setLayoutY(50);
        text.setFill(Color.BLUE);

        Pane startPane = new Pane();
        startPane.getChildren().add(btnStart);
        startPane.getChildren().add(btnQuit);
        startPane.getChildren().add(setNumberOfRows);
        startPane.getChildren().add(setNumberOfColumns);
        startPane.getChildren().add(setNumberOfMines);
        startPane.getChildren().add(label1);
        startPane.getChildren().add(label2);
        startPane.getChildren().add(label3);
        startPane.getChildren().add(text);

        Scene startScene = new Scene(startPane, 600, 500);

        startStage.setTitle("Menu");
        startStage.setScene(startScene);
        startStage.show();
        btnStart.setOnAction(this::processStartButtonClick);
        btnQuit.setOnAction(this::processQuitButtonClick);

    }
    private void main0() {
        //Rectangle box=new Rectangle(0,0,100,800);
        int row = 0;
        int sceneWidth = 1400;
        int sceneHeight = 700;

        root.setNumberOfColumns(numberOfColumns);
        root.setNumberOfRows(numberOfRows);
        root.setNumberOfMines(numberOfMines);
        ArrayList<ArrayList<Block>> rows = new ArrayList<ArrayList<Block>>();
        root.setPlane(rows);
        while (row < root.getNumberOfRows()) {
            ArrayList<Block> blocks = new ArrayList<Block>();
            int column = 0;
            while (column < root.getNumberOfColumns()) {
                Rectangle square0 = new Rectangle(sceneWidth / root.getNumberOfColumns(), sceneHeight / root.getNumberOfRows());//makes a rectangle for each block
                Block block = new Block(square0);//makes a block for each array spot
                blocks.add(block);//adds the rectangle to thr block
                column++;//moves to the next column
            }
            rows.add(blocks);
            row++;
        }

        int firstLoop = 0;
        for (ArrayList<Block> a : rows) {
            int secondLoop = 0;
            for (Block block : a) {
                block.setMine(false);//each block is not a mine
                block.setFlag(false);
                block.setIsClicked(false);//each block is not clicked

                root.add(block, secondLoop, firstLoop);//adds each block in its array spot to the corresponding spot in the gridpane
                block.setRow(firstLoop);//updates the row and column variables
                block.setColumn(secondLoop);
                secondLoop++;

            }
            firstLoop++;
        }

        firstLoop = 0;
        for (ArrayList<Block> a : rows) {
            int secondLoop = 0;
            for (Block block : a) {
                block.setPlane(rows);//because the block methods need an arraylist and a gridpane, this sets root and rows for each block
                block.setPane(root);
                secondLoop++;
            }
            firstLoop++;
        }
        root.setGridLinesVisible(true);

        //to open up space in the beginning before placing mines, use a variation of the clickAllSurroundings method










        /*

        Random random = new Random();
        int c = 0;
        while (c < numberOfMines) {
            int a = random.nextInt(numberOfRows - 1);
            int b = random.nextInt(numberOfColumns - 1);
            if (!rows.get(a).get(b).getMine()) {
                rows.get(a).get(b).setMine(true);
                c++;
            }
        }*/
        scene = new Scene(root, 1400, 700);

        primaryStage.setTitle("Minesweeper!");
        primaryStage.setScene(scene);


    }

    public Stage getStartStage() {
        return startStage;
    }

    public void setStartStage(Stage startStage) {
        this.startStage = startStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void processStartButtonClick(ActionEvent event) {
        numberOfRows=Integer.parseInt(setNumberOfRows.getText());
        numberOfColumns=Integer.parseInt(setNumberOfColumns.getText());
        numberOfMines=Integer.parseInt(setNumberOfMines.getText());


        main0();
        primaryStage.show();
        startStage.close();
    }

    public void processQuitButtonClick(ActionEvent event) {

        startStage.close();
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }



}
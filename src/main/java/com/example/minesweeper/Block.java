package com.example.minesweeper;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import static javafx.scene.text.Font.font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author elliotadamson
 */
public class Block extends StackPane {

    private Label number;//this will be either a number, M for mine, or blank for 0
    private boolean mine;//this boolean is true if the block is a mine and false if it isn't
    private MouseEvent event;//for event handlers
    private int row;//each block will have a row and column assigned to it
    private int column;
    private Rectangle square;//each block has a rectangle
    private Random random;//used for stuff
    private boolean isClicked = false;//the block has been clicked if this boolean is true
    private ArrayList<ArrayList<Block>> plane;//each block will have the same ArrayList as an attribute
    //so it can be referenced later in some of the methods
    private ExtendedGridPane pane;//each block will have the same ExtendedGridPane as an attribute
    //so it can be referenced later in some of the methods
    private boolean parse=false;


    private boolean once = true;//this is used for controlling the number of windows open when the game is over


    private boolean flag=false;//true if a block is flagged
    private Label flagLabel;//this is the label that appears on a block if it is flagged



    public boolean isOnce() {
        return once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean getMine() {
        return mine;
    }

    public void setPlane(ArrayList<ArrayList<Block>> plane) {
        this.plane = plane;
    }



    public Block(Rectangle square0) {
        square = square0;
        square0.setFill(Color.GRAY);
        random = new Random();


        getChildren().add(square0);

        setOnMouseClicked(this::processMouseClick);
        //setOnKeyTyped(this::processFlagOperator);
    }

    public void processMouseClick(MouseEvent event) {
        if(isClicked()&&getSurroundings()==getFlags())
        {//p("Not here");
            clickAllSurroundings();
        }
        else if(MouseButton.SECONDARY==event.getButton()&&!flag)
        {flagLabel=new Label("Flag");
            getChildren().add(flagLabel);
            //p(""+flag);
            flag=!flag;
        }
        else{
            if(!pane.isFirstClick())
            {
                firstClickAllSurroundings();//this method will put mines anywhere except the space clicked
                //and the spaces surrounding
                pane.setFirstClick(true);
            }
            if (!isClicked) {

                getNumber();
            }
        }
    }

    public void setPane(ExtendedGridPane pane) {
        this.pane = pane;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void getNumber() {
        if(flag)
        {
            flag=!flag;
            getChildren().remove(flagLabel);
        }

        else{

            if (isMined()) {

                Rectangle square0 = new Rectangle(square.getWidth(), square.getHeight());
                square0.setFill(Color.ANTIQUEWHITE);
                getChildren().add(square0);
                number = new Label("M");
                getChildren().add(number);
                isClicked = true;

                if (once)//if there is not already a window open
                {

                    Stage stage = new Stage();
                    StackPane stackPane = new StackPane();
                    Text text = new Text(100, 100, "GAME OVER");
                    Font font = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 72);
                    text.setFont(font);
                    text.setFill(Color.RED);
                    stackPane.getChildren().add(text);
                    Scene scene = new Scene(stackPane, 500, 500);
                    stage.setScene(scene);
                    stage.show();
                    for (ArrayList<Block> list : plane) {
                        for (Block block : list) {
                            block.setOnce(false);
                        }
                    }
                }
                pane.clickAllBlocks();
            } else {

                Rectangle square0 = new Rectangle(square.getWidth(), square.getHeight());//makes a new square
                //System.out.println("Rectangle square0 = new Rectangle(square.getWidth(), square.getHeight());");
                square0.setFill(Color.WHITE);//makes the square white
                //p("square0.setFill(Color.WHITE);");
                getChildren().add(square0);//adds the square to the block object
                // p("getChildren().add(square0);");








                if (getSurroundings() != 0) {
                    number = new Label("" + getSurroundings());
                    if(getSurroundings()==1)
                    {number.setTextFill(Color.BLUE);}
                    else if(getSurroundings()==2)
                    {number.setTextFill(Color.GREEN);}
                    else if(getSurroundings()==3)
                    {number.setTextFill(Color.RED);}
                    else if(getSurroundings()==4)
                    {number.setTextFill(Color.PURPLE);}
                    else if(getSurroundings()==5)
                    {number.setTextFill(Color.MAROON);}
                    else if(getSurroundings()==6)
                    {number.setTextFill(Color.ORANGE);}
                    else if(getSurroundings()==7)
                    {number.setTextFill(Color.PINK);}
                    else if(getSurroundings()==8)
                    {number.setTextFill(Color.BLACK);}

                }//gets number of mines around and makes a label with that number
                else {
                    number = new Label("");
                }
                //p("number = new Label(\"\" + getSurroundings(this, plane));");

                isClicked = true;//changes the block's status to being clicked
                //p("isClicked = true;");

                getChildren().add(number);//adds the number to the block
                // p("getChildren().add(number);");
                //pane.add(this, column, row);
                // p("pane.add(this, column, row);");

                if (getSurroundings() == 0) {

                    clickAllSurroundings();

                }

                boolean win = true;//this boolean is used to decide whether all the squares that aren't mines are clicked
                for (ArrayList<Block> list : plane) {
                    for (Block block : list) {
                        if (!block.isClicked() && !block.isMine()) {
                            win = false;
                        }
                    }
                }
                if (win && once) {

                    Stage stage = new Stage();
                    StackPane stackPane = new StackPane();
                    Text text = new Text(100, 100, "CONGRATULATIONS!!!");
                    Font font = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 72);
                    text.setFont(font);
                    text.setFill(Color.GREEN);
                    stackPane.getChildren().add(text);
                    Scene scene = new Scene(stackPane, 1000, 500);
                    stage.setScene(scene);
                    stage.show();

                    for (ArrayList<Block> list : plane) {
                        for (Block block : list) {

                            block.setOnce(false);//makes sure that only one stage is created


                        }
                    }
                    pane.clickAllBlocks();
                }

                pane.setFirstClick(true);
            }
            isClicked = true;
        }
    }

    public Block getBlock(int column0, int row0) {
        return plane.get(row0).get(column0);
    }

    public boolean isMined() {
        return mine;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setNumber(Label number) {
        this.number = number;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public boolean isIsClicked() {
        return isClicked;
    }

    public void setIsClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }



    public void clickAllSurroundings() {
        if (getColumn() - 1 >= 0 && getRow() - 1 >= 0 && getColumn() - 1 < pane.getNumberOfColumns() && getRow() - 1 < pane.getNumberOfRows()) {

            if (!getBlock(getColumn() - 1, getRow() - 1).isClicked()&&!getBlock(getColumn() - 1, getRow() - 1).isFlag()) {
                getBlock(getColumn() - 1, getRow() - 1).getNumber();


            }

        }

        if (getColumn() - 1 >= 0 && getRow() >= 0 && getColumn() - 1 < pane.getNumberOfColumns() && getRow() < pane.getNumberOfRows()) {
            if (!getBlock(getColumn() - 1, getRow()).isClicked()&&!getBlock(getColumn() - 1, getRow()).isFlag()) {
                getBlock(getColumn() - 1, getRow()).getNumber();


            }
        }

        if (getColumn() - 1 >= 0 && getRow() + 1 >= 0 && getColumn() - 1 < pane.getNumberOfColumns() && getRow() + 1 < pane.getNumberOfRows()) {
            if (!getBlock(getColumn() - 1, getRow() + 1).isClicked()&&!getBlock(getColumn() - 1, getRow() + 1).isFlag()) {
                getBlock(getColumn() - 1, getRow() + 1).getNumber();


            }
        }
        if (getColumn() >= 0 && getRow() - 1 >= 0 && getColumn() < pane.getNumberOfColumns() && getRow() - 1 < pane.getNumberOfRows()) {
            if (!getBlock(getColumn(), getRow() - 1).isClicked()&&!getBlock(getColumn(), getRow() - 1).isFlag()) {
                getBlock(getColumn(), getRow() - 1).getNumber();


            }
        }
        if (getColumn() >= 0 && getRow() + 1 >= 0 && getColumn() < pane.getNumberOfColumns() && getRow() + 1 < pane.getNumberOfRows()) {
            if (!getBlock(getColumn(), getRow() + 1).isClicked()&&!getBlock(getColumn(), getRow() + 1).isFlag()) {
                getBlock(getColumn(), getRow() + 1).getNumber();


            }
        }
        if (getColumn() + 1 >= 0 && getRow() - 1 >= 0 && getColumn() + 1 < pane.getNumberOfColumns() && getRow() - 1 < pane.getNumberOfRows()) {
            if (!getBlock(getColumn() + 1, getRow() - 1).isClicked()&&!getBlock(getColumn() + 1, getRow() - 1).isFlag()) {
                getBlock(getColumn() + 1, getRow() - 1).getNumber();


            }
        }
        if (getColumn() + 1 >= 0 && getRow() >= 0 && getColumn() + 1 < pane.getNumberOfColumns() && getRow() < pane.getNumberOfRows()) {
            if (!getBlock(getColumn() + 1, getRow()).isClicked()&&!getBlock(getColumn() + 1, getRow()).isFlag()) {
                getBlock(getColumn() + 1, getRow()).getNumber();


            }
        }
        if (getColumn() + 1 >= 0 && getRow() + 1 >= 0 && getColumn() + 1 < pane.getNumberOfColumns() && getRow() + 1 < pane.getNumberOfRows()) {
            if (!getBlock(getColumn() + 1, getRow() + 1).isClicked()&&!getBlock(getColumn() + 1, getRow() + 1).isFlag()) {
                getBlock(getColumn() + 1, getRow() + 1).getNumber();


            }
        }
    }

    public int getSurroundings() {
        int mines = 0;

        if (column - 1 >= 0 && row - 1 >= 0 && column - 1 < pane.getNumberOfColumns() && row - 1 < pane.getNumberOfRows()) {
            if (getBlock(column - 1, row - 1).isMined()) {
                mines++;
            }
        }
        if (column - 1 >= 0 && row >= 0 && column - 1 < pane.getNumberOfColumns() && row < pane.getNumberOfRows()) {
            if (getBlock(column - 1, row).isMined()) {
                mines++;
            }
        }
        if (column - 1 >= 0 && row + 1 >= 0 && column - 1 < pane.getNumberOfColumns() && row + 1 < pane.getNumberOfRows()) {
            if (getBlock(column - 1, row + 1).isMined()) {
                mines++;
            }
        }
        if (column >= 0 && row - 1 >= 0 && column < pane.getNumberOfColumns() && row - 1 < pane.getNumberOfRows()) {
            if (getBlock(column, row - 1).isMined()) {
                mines++;
            }
        }

        if (column >= 0 && row + 1 >= 0 && column < pane.getNumberOfColumns() && row + 1 < pane.getNumberOfRows()) {
            if (getBlock(column, row + 1).isMined()) {
                mines++;
            }
        }
        if (column + 1 >= 0 && row - 1 >= 0 && column + 1 < pane.getNumberOfColumns() && row - 1 < pane.getNumberOfRows()) {
            if (getBlock(column + 1, row - 1).isMined()) {
                mines++;
            }
        }
        if (column + 1 >= 0 && row >= 0 && column + 1 < pane.getNumberOfColumns() && row < pane.getNumberOfRows()) {
            if (getBlock(column + 1, row).isMined()) {
                mines++;
            }
        }
        if (column + 1 >= 0 && row + 1 >= 0 && column + 1 < pane.getNumberOfColumns() && row + 1 < pane.getNumberOfRows()) {
            if (getBlock(column + 1, row + 1).isMined()) {
                mines++;
            }
        }

        return mines;
    }
    public int getFlags() {
        int flags = 0;

        if (column - 1 >= 0 && row - 1 >= 0 && column - 1 < pane.getNumberOfColumns() && row - 1 < pane.getNumberOfRows()) {
            if (getBlock(column - 1, row - 1).isFlag()) {
                flags++;
            }
        }
        if (column - 1 >= 0 && row >= 0 && column - 1 < pane.getNumberOfColumns() && row < pane.getNumberOfRows()) {
            if (getBlock(column - 1, row).isFlag()) {
                flags++;
            }
        }
        if (column - 1 >= 0 && row + 1 >= 0 && column - 1 < pane.getNumberOfColumns() && row + 1 < pane.getNumberOfRows()) {
            if (getBlock(column - 1, row + 1).isFlag()) {
                flags++;
            }
        }
        if (column >= 0 && row - 1 >= 0 && column < pane.getNumberOfColumns() && row - 1 < pane.getNumberOfRows()) {
            if (getBlock(column, row - 1).isFlag()) {
                flags++;
            }
        }

        if (column >= 0 && row + 1 >= 0 && column < pane.getNumberOfColumns() && row + 1 < pane.getNumberOfRows()) {
            if (getBlock(column, row + 1).isFlag()) {
                flags++;
            }
        }
        if (column + 1 >= 0 && row - 1 >= 0 && column + 1 < pane.getNumberOfColumns() && row - 1 < pane.getNumberOfRows()) {
            if (getBlock(column + 1, row - 1).isFlag()) {
                flags++;
            }
        }
        if (column + 1 >= 0 && row >= 0 && column + 1 < pane.getNumberOfColumns() && row < pane.getNumberOfRows()) {
            if (getBlock(column + 1, row).isFlag()) {
                flags++;
            }
        }
        if (column + 1 >= 0 && row + 1 >= 0 && column + 1 < pane.getNumberOfColumns() && row + 1 < pane.getNumberOfRows()) {
            if (getBlock(column + 1, row + 1).isFlag()) {
                flags++;
            }
        }

        return flags;
    }

    public void p(String string) {
        System.out.println(string);
    }
    private void firstClickAllSurroundings()
    {
        ArrayList<Block> blocks=new ArrayList<Block>();
        setIsClicked(true);
        blocks.add(this);
        if (getColumn() - 1 >= 0 && getRow() - 1 >= 0 && getColumn() - 1 < pane.getNumberOfColumns() && getRow() - 1 < pane.getNumberOfRows()) {

            if (!getBlock(getColumn() - 1, getRow() - 1).isClicked()) {
                getBlock(getColumn() - 1, getRow() - 1).setIsClicked(true);
                blocks.add(getBlock(getColumn() - 1, getRow() - 1));

            }

        }

        if (getColumn() - 1 >= 0 && getRow() >= 0 && getColumn() - 1 < pane.getNumberOfColumns() && getRow() < pane.getNumberOfRows()) {
            if (!getBlock(getColumn() - 1, getRow()).isClicked()) {
                getBlock(getColumn() - 1, getRow()).setIsClicked(true);
                blocks.add(getBlock(getColumn() - 1, getRow()));

            }
        }

        if (getColumn() - 1 >= 0 && getRow() + 1 >= 0 && getColumn() - 1 < pane.getNumberOfColumns() && getRow() + 1 < pane.getNumberOfRows()) {
            if (!getBlock(getColumn() - 1, getRow() + 1).isClicked()) {
                getBlock(getColumn() - 1, getRow() + 1).setIsClicked(true);
                blocks.add(getBlock(getColumn() - 1, getRow() + 1));

            }
        }
        if (getColumn() >= 0 && getRow() - 1 >= 0 && getColumn() < pane.getNumberOfColumns() && getRow() - 1 < pane.getNumberOfRows()) {
            if (!getBlock(getColumn(), getRow() - 1).isClicked()) {
                getBlock(getColumn(), getRow() - 1).setIsClicked(true);
                blocks.add(getBlock(getColumn(), getRow() - 1));

            }
        }
        if (getColumn() >= 0 && getRow() + 1 >= 0 && getColumn() < pane.getNumberOfColumns() && getRow() + 1 < pane.getNumberOfRows()) {
            if (!getBlock(getColumn(), getRow() + 1).isClicked()) {
                getBlock(getColumn(), getRow() + 1).setIsClicked(true);
                blocks.add(getBlock(getColumn(), getRow() + 1));

            }
        }
        if (getColumn() + 1 >= 0 && getRow() - 1 >= 0 && getColumn() + 1 < pane.getNumberOfColumns() && getRow() - 1 < pane.getNumberOfRows()) {
            if (!getBlock(getColumn() + 1, getRow() - 1).isClicked()) {
                getBlock(getColumn() + 1, getRow() - 1).setIsClicked(true);
                blocks.add(getBlock(getColumn() + 1, getRow() - 1));

            }
        }
        if (getColumn() + 1 >= 0 && getRow() >= 0 && getColumn() + 1 < pane.getNumberOfColumns() && getRow() < pane.getNumberOfRows()) {
            if (!getBlock(getColumn() + 1, getRow()).isClicked()) {
                getBlock(getColumn() + 1, getRow()).setIsClicked(true);
                blocks.add(getBlock(getColumn() + 1, getRow()));

            }
        }
        if (getColumn() + 1 >= 0 && getRow() + 1 >= 0 && getColumn() + 1 < pane.getNumberOfColumns() && getRow() + 1 < pane.getNumberOfRows()) {
            if (!getBlock(getColumn() + 1, getRow() + 1).isClicked()) {
                getBlock(getColumn() + 1, getRow() + 1).setIsClicked(true);
                blocks.add(getBlock(getColumn() + 1, getRow() + 1));

            }
        }
        Random random = new Random();

        int c = 0;
        while (c < pane.getNumberOfMines()) {
            int a = random.nextInt(pane.getNumberOfRows() - 1);
            int b = random.nextInt(pane.getNumberOfColumns() - 1);
            if (!plane.get(a).get(b).getMine()&&!plane.get(a).get(b).isClicked()) {
                plane.get(a).get(b).setMine(true);
                c++;
            }
        }
        for(Block block:blocks)
        {
            block.setIsClicked(false);
        }
        p("Done");
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public void processFlagOperator(KeyEvent event)
    {
        getChildren().add(flagLabel);
        //p(""+flag);
        flag=!flag;
    }
    public void processParse(MouseEvent event)
    {
        if(isClicked())
        {p("Not here");
            clickAllSurroundings();
        }
    }

}
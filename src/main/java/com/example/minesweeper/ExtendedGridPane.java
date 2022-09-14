package com.example.minesweeper;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author elliotadamson
 */
public class ExtendedGridPane extends GridPane  {

    private ArrayList<ArrayList<Block>>plane;
    private int numberOfRows;//self-explanatory
    private int numberOfColumns;//self-explanatory
    private int numberOfMines;//self-explanatory
    private boolean FirstClick=false;//this will deal with giving the player a bit of kindness in the beginning

    public ExtendedGridPane() {
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public void clickAllBlocks()
    {
        for(ArrayList<Block> list:plane)
        {
            for(Block block:list)
            {

                if(!block.isClicked())
                {block.getNumber();}
            }
        }
    }


    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public ArrayList<ArrayList<Block>> getPlane() {
        return plane;
    }

    public void setPlane(ArrayList<ArrayList<Block>> pane) {
        this.plane = pane;
    }

    public boolean isFirstClick() {
        return FirstClick;
    }

    public void setFirstClick(boolean FirstClick) {
        this.FirstClick = FirstClick;
    }

    /**
     * @param args the command line arguments
     */

}
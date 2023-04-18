package home;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Stack;

public class Main extends Application {
    private int cols = 40;
    private int rows = 40;
    private int turn = 1;
    //Layout
    private ToolBar toolBar = new ToolBar();
    private GridPane gridPane = new GridPane();
    private ScrollPane scrollPane = new ScrollPane();
    private BorderPane borderPane = new BorderPane();
    //Controll
    private Tile[][] tiles = new Tile[rows][cols];
    private Button newGame_btn = new Button("New game");
    private Button undo_btn = new Button("Undo");
    private Stack<Tile> move = new Stack<>();
    private Label label = new Label();
    @Override
    public void start(Stage stage) {
        this.createGUI();
        this.eventHandler();

        //Scene
        Scene scene = new Scene(borderPane, 1080, 720);
        //Stage
        Image icon = new Image("home/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Caro");
        stage.setScene(scene);
        stage.show();
    }
    private void createGUI(){
        // Grid Pane
        gridPane.setAlignment(Pos.CENTER);
        for(int i = 0; i < cols; i++)
            for (int j = 0; j < rows; j++) {
                //Button
                Tile tile = new Tile(i,j);
                gridPane.add(tile, j, i);
                tiles[i][j] = tile;
            }
        //Toolbar
        toolBar.getItems().add(newGame_btn);
        toolBar.getItems().add(undo_btn);
        // Scroll Pane
        scrollPane.pannableProperty().set(true);
        scrollPane.setContent(gridPane);
        // Border Pane
        borderPane.setCenter(scrollPane);
        borderPane.setTop(toolBar);
        borderPane.setBottom(label);
    }
    private void eventHandler(){
        this.newGame(); // New game button
        this.undo(); // Undo button
        // Button action
        for(Tile[] elements : tiles)
            for(Tile tile : elements) {
                tile.setOnAction(actionEvent -> {
                    //Add move to stack
                    if (turn % 2 != 0 && tile.tickAble()) {
                        tile.setText("X");
                        tile.setTextFill(Color.RED);
                        turn++;
                        move.add(tile);
                    } else if (turn % 2 == 0 && tile.tickAble()) {
                        tile.setText("O");
                        tile.setTextFill(Color.GREEN);
                        turn++;
                        move.add(tile);
                    }
                    if(checkWin(tile.getX(), tile.getY())){
                        Alert alert = new Alert(Alert.AlertType.NONE);
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Completed");
                        alert.setContentText("WON");
                        alert.show();
                        setTilesDisable(true);
                        undo_btn.setDisable(true);
                    }
                });
            }
    }
    private void newGame(){
        newGame_btn.setOnAction(actionEvent ->  {
                for (Tile[] element : tiles)
                    for (Tile tile: element) {
                        tile.setText("");
                        tile.setDisable(false);
                    }
                undo_btn.setDisable(false);
                turn = 1;
        });
    }
    private void undo(){
        undo_btn.setOnAction(actionEvent -> {
            if(turn > 1) {
                move.peek().setText("");
                move.pop();
                turn--;
            }
        });
    }
    private void setTilesDisable(boolean b){
        for(Tile[] e : tiles)
            for(Tile tile : e)
                tile.setDisable(b);
    }
    private boolean checkWin(int x, int y){
        int temp1, temp2, point;

        // check rows
        temp1 = x; temp2 = x-1; point = 0;
        while(tiles[temp1][y].getText() == tiles[x][y].getText()){
            point++;
            temp1++;
        }
        while(tiles[temp2][y].getText() == tiles[x][y].getText()){
            point++;
            temp2--;
        }
        if(point > 4) return true;

        // check cols
        temp1 = y; temp2 = y-1; point = 0;
        while(tiles[x][temp1].getText() == tiles[x][y].getText()){
            point++;
            temp1++;
        }
        while(tiles[x][temp2].getText() == tiles[x][y].getText()){
            point++;
            temp2--;
        }
        if(point > 4) return true;

        // check cross 1
        temp1 = x; temp2 = y; point = 0;
        while(tiles[temp1][temp2].getText() == tiles[x][y].getText()){
            temp1++;
            temp2++;
            point++;
        }
        temp1 = x-1; temp2 = y-1;
        while(tiles[temp1][temp2].getText() == tiles[x][y].getText()){
            temp1--;
            temp2--;
            point++;
        }
        if(point > 4) return true;

        // check cross 2
        temp1 = x; temp2 = y; point = 0;
        while(tiles[temp1][temp2].getText() == tiles[x][y].getText()){
            temp1++;
            temp2--;
            point++;
        }
        temp1 = x-1; temp2 = y+1;
        while(tiles[temp1][temp2].getText() == tiles[x][y].getText()){
            temp1--;
            temp2++;
            point++;
        }
        if(point > 4) return true;
        // return false when no cross comfort the condition
        return false;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
package home;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class Player {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private Tile[][] tiles;
    private int turn = 1;
    private Button back_Btn;
    private Button newGame_btn;
    private Button undo_btn;
    private Stack<Tile> move = new Stack<>();
    private Label label;
    public Player(Tile[][] tiles,
                  Label label,
                  Button back_Btn, Button newGame_btn, Button undo_btn){
        this.tiles = tiles;
        this.back_Btn = back_Btn;
        this.newGame_btn = newGame_btn;
        this.undo_btn = undo_btn;
        this.label = label;
    }

    public void eventHandler(){
        this.back(); // Back button
        this.newGame(); // New game button
        this.undo(); // Undo button
        this.addPoint();
    }
    protected void addPoint(){
        for(Tile[] elements : tiles)
            for(Tile tile : elements) {
                tile.setOnAction(actionEvent -> {
                    label.setText(this.turn % 2 == 0 ? "X's turn" : "O's turn");
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
                        alert.setTitle("Game Over");
                        alert.setContentText(turn % 2 != 0 ? "O won" : "X won");
                        alert.show();
                        setTilesDisable(true);
                        undo_btn.setDisable(true);
                    }
                });
            }
    }
    protected void back(){
        back_Btn.setOnAction(actionEvent -> {
            try {
                root = FXMLLoader.load(getClass().getResource("../resource/home.fxml"));
                stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root, stage.getWidth(), stage.getHeight());
                scene.getStylesheets().add(getClass().getResource("../resource/home.css").toExternalForm());
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    protected void newGame(){
        newGame_btn.setOnAction(actionEvent ->  {
            for (Tile[] element : tiles)
                for (Tile tile: element) {
                    tile.setText("");
                    tile.setDisable(false);
                }
            undo_btn.setDisable(false);
            turn = 1;
            label.setText("X's turn");
        });
    }
    protected void undo(){
        undo_btn.setOnAction(actionEvent -> {
            if(turn > 1) {
                move.peek().setText("");
                move.pop();
                label.setText(turn % 2 == 0 ? "X's turn" : "O's turn");
                turn--;
            }
        });
    }
    protected void setTilesDisable(boolean b){
        for(Tile[] e : tiles)
            for(Tile tile : e)
                tile.setDisable(b);
    }
    protected boolean checkWin(int x, int y){
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
    public Tile[][] getTiles() {return tiles;}
    public int getTurn() {return turn;}
    public void setTurn(int turn) {this.turn = turn;}
    public Stack<Tile> getMove() {return move;}
    public Label getLabel() {return label;}
    public Button getUndo_btn() {return undo_btn;}
    public Tile getTile(int x, int y) {return this.tiles[x][y];}

}

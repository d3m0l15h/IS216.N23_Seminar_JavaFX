package home;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.Stack;

public class Board {
    private final int cols = 40;
    private final int rows = 40;
    private ToolBar toolBar = new ToolBar();
    private GridPane gridPane = new GridPane();
    private ScrollPane scrollPane = new ScrollPane();
    private BorderPane borderPane = new BorderPane();
    private Tile[][] tiles = new Tile[rows][cols];
    private Button back_Btn = new Button("Back");
    private Button newGame_btn = new Button("New game");
    private Button undo_btn = new Button("Undo");
    private Stack<Tile> move = new Stack<>();
    private Label label = new Label("X's turn");
    public Parent createGUI(){
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
        toolBar.getItems().add(back_Btn);
        toolBar.getItems().add(newGame_btn);
        toolBar.getItems().add(undo_btn);
        // Scroll Pane
        scrollPane.pannableProperty().set(true);
        scrollPane.setContent(gridPane);
        // Border Pane
        borderPane.setCenter(scrollPane);
        borderPane.setTop(toolBar);
        borderPane.setBottom(label);
        return borderPane;
    }
    public Tile[][] getTiles(){
        return tiles;
    }
    public Button getBack_Btn(){
        return back_Btn;
    }
    public Button getNewGame_btn(){
        return newGame_btn;
    }
    public Button getUndo_btn(){
        return undo_btn;
    }
    public Label getLabel() { return label; }
}

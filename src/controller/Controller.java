package controller;

import home.Board;
import home.Computer;
import home.Player;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class Controller {
    private Parent root;
    private Scene scene;
    private Stage stage;
    public void human(ActionEvent event) {
        Board board = new Board();
        root = board.createGUI();
        Player player = new Player(board.getTiles(),
                board.getLabel(),
                board.getBack_Btn(), board.getNewGame_btn(), board.getUndo_btn());
        player.eventHandler();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
    }
    public void computer(ActionEvent event) {
        Board board = new Board();
        root = board.createGUI();
        Computer computer = new Computer(board.getTiles(),
                                        board.getLabel(),
                                        board.getBack_Btn(), board.getNewGame_btn(), board.getUndo_btn());
        computer.eventHandler();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
    }
}

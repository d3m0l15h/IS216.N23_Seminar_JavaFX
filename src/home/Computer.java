package home;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.Random;

public class Computer extends Player{
    public Computer(Tile[][] tiles,
                    Label label,
                    Button back_Btn, Button newGame_btn, Button undo_btn){
        super(tiles, label, back_Btn, newGame_btn, undo_btn);
    }
    @Override
    protected void addPoint(){
        for(Tile[] elements : super.getTiles())
            for(Tile tile : elements) {
                tile.setOnAction(actionEvent -> {
                    super.getLabel().setText(super.getTurn() % 2 == 0 ? "Player's turn" : "Computer calculating...");
                    if (super.getTurn() % 2 != 0 && tile.tickAble()) {
                        tile.setText("X");
                        tile.setTextFill(Color.RED);
                        super.setTurn(super.getTurn()+1);
                        super.getMove().add(tile);
                        this.moveSet(super.getMove().peek());
                    }
                    if(checkWin(tile.getX(), tile.getY())){
                        Alert alert = new Alert(Alert.AlertType.NONE);
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Over");
                        alert.setContentText(super.getTurn() % 2 != 0 ? "Player won" : "Computer won");
                        alert.show();
                        setTilesDisable(true);
                        super.getUndo_btn().setDisable(true);
                    }
                });
            }

    }
    @Override
    protected void undo(){
        super.getUndo_btn().setOnAction(actionEvent -> {
            if(super.getTurn() > 1) {
                super.getMove().peek().setText("");
                super.getMove().pop();
                super.getMove().peek().setText("");
                super.getMove().pop();
                super.getLabel().setText(super.getTurn() % 2 == 0 ? "X's turn" : "Computer calculating...");
                super.setTurn(super.getTurn()-2);
            }
        });
    }
    private void moveSet(Tile move){
        Random random = new Random();
        int moveX = -1;
        int moveY = -1;
        while (true) {
            moveX = move.getX() + random.nextInt(2 + 1) - 1;
            moveY = move.getY() + random.nextInt(2 + 1) - 1;
            if(moveX != move.getX() && moveY != move.getY() && super.getTile(moveX, moveY).tickAble()) break;
        }
        super.getTile(moveX, moveY).setText("O");
        super.getTile(moveX, moveY).setTextFill(Color.GREEN);
        super.setTurn(super.getTurn()+1);
        super.getMove().add(super.getTile(moveX, moveY));
    }
}

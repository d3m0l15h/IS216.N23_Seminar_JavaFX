package home;

import javafx.scene.control.Button;

public class Tile extends Button {
    private final int x;
    private final int y;
    Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.setStyle("-fx-background-color:#FFFFFF; " +
                "-fx-border-color: #000000; " +
                "-fx-border-width: 0.5; " +
                "-fx-font-size: 20; " +
                "-fx-font-weight: bold");
        this.setPrefSize(45,45);
    }
    int getX(){
        return x;
    }
    int getY(){
        return y;
    }
    protected boolean tickAble(){
        if(super.getText() == "")
            return true;
        return false;
    }

}

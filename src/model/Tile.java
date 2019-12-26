package model;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.util.Objects;


/**
 * @author Tatarin Esli Che
 */
public class Tile implements Serializable {
    private int number;
    private int size;

    public int getNumber() {
        return number;
    }

    public Tile(int number, int size) {
        this.number = number;
        this.size = size;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }


    public StackPane draw() {
        Rectangle rec = new Rectangle(size, size);
        rec.setArcWidth(size / 4);
        rec.setArcHeight(size / 4);
        StackPane stack = new StackPane();
        if (number == 0) {
            rec.setFill(Color.BLUEVIOLET);
            stack.getChildren().add(rec);
        } else {
            Text text = new Text(Integer.toString(number));
            text.setFont(Font.font("Verdana", FontWeight.BOLD, size * 0.5));
            rec.setFill(Color.BLUE);
            stack.getChildren().addAll(rec, text);
        }
        return stack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return number == tile.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}

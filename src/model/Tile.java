package model;

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

    Tile(int number, int size) {
        this.number = number;
        this.size = size;
    }

    void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
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

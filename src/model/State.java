package model;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tatarin Esli Che
 */
public class State implements Serializable {
    private int g;
    private int h;
    private int blankPos;
    private State parent;
    private Tile[] tiles;
    private int size;

    public State(Game game) {
        g = 0;
        parent = null;
        tiles = game.getTiles();
        size = game.getSize();
        blankPos = game.getBlankPos();
        h = getH();
    }


    public int getF() {
        return h + g;
    }

    public int getBlankPos() {
        return blankPos;
    }

    public void setBlankPos(int blankPos) {
        this.blankPos = blankPos;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public State(int g, State parent, Tile[] tiles) {
        this.g = g;
        this.parent = parent;
        this.tiles = tiles;
    }


    public void setG(int g) {
        this.g = g;
    }

    public State cloneState() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(baos);
        ous.writeObject(this);
        ous.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        State newState = (State) ois.readObject();
        newState.g++;
        newState.parent = this;
        return newState;
    }

    public State getParent() {
        return parent;
    }

    public int getSize() {
        return size;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public int getH() {
        int c = 0;
        for (int i = 0; i < tiles.length; i++) {
            int cur = tiles[i].getNumber();
            if (cur == 0) cur += size * size;
            if (cur != i + 1) {
                c += Math.abs(i/size - (cur - 1) / size) + Math.abs(i % size - (cur - 1) % size);
            }
        }
        return h = c;
    }

    private int lc() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int s = 0; s < j; s++) {

                }
            }
        }
        return 1;
    }

    public boolean isSolved() {
        if (h == 0) return true;
        else return false;
    }

    public void swap(Tile first, Tile second) {
        int tmp = first.getNumber();
        first.setNumber(second.getNumber());
        second.setNumber(tmp);
        h = getH();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Arrays.equals(tiles, state.tiles);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tiles);
    }
}

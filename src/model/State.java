package model;

import java.io.*;
import java.util.Arrays;

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

    State(Game game) {
        g = 0;
        parent = null;
        tiles = game.getTiles();
        size = game.getSize();
        blankPos = game.getBlankPos();
        h = getH();
    }


    int getF() {
        return h + g;
    }

    int getBlankPos() {
        return blankPos;
    }

    void setBlankPos(int blankPos) {
        this.blankPos = blankPos;
    }

    public Tile[] getTiles() {
        return tiles;
    }


    State cloneState() throws IOException, ClassNotFoundException {
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

    State getParent() {
        return parent;
    }

    int getSize() {
        return size;
    }

    private int getH() {
        int c = 0;
        for (int i = 0; i < tiles.length; i++) {
            int cur = tiles[i].getNumber();
            if (cur == 0) cur += size * size;
            if (cur != i + 1) {
                c += Math.abs(i/size - (cur - 1) / size) + Math.abs(i % size - (cur - 1) % size);
            }
        }
        return h = c + lc();
    }

    private int lc() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int s = 0; s < j; s++) {
                    int a = tiles[i * size + s].getNumber();
                    int b = tiles[i * size + j].getNumber();
                    int c = tiles[s * size + i].getNumber();
                    int d = tiles[j * size + i].getNumber();
                    if (a == 0) a += size * size;
                    if (b == 0) b += size * size;
                    if (c == 0) c += size * size;
                    if (d == 0) d += size * size;
                    if (a > b)
                        sum += 2;
                    if (c > d) sum += 2;
                }
            }
        }
        return sum;
    }

    boolean isSolved() {
        return h == 0;
    }

    void swap(Tile first, Tile second) {
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

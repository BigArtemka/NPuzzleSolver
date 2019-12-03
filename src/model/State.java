package model;

/**
 * @author Tatarin Esli Che
 */
public class State {
    int g;
    int h;
    State parent;
    Tile[] tiles;

    public State(int g, State parent, Game game) {
        this.g = g;
        this.parent = parent;
        Tile[] tiles = game.getTiles();
        int c = 0;
        if (tiles[tiles.length - 1].getNumber() != 0)
            c++;

        for (int i = tiles.length - 1; i >= 0; i--) {
            if (tiles[i].getNumber() != i + 1)
                c++;
        }
        h = c;
    }

    public State(int g, State parent, Tile[] tiles) {
        this.g = g;
        this.parent = parent;
        this.tiles = tiles;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }


    public void setH(int h) {
        this.h = h;
    }

    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public int getH() {
        return  h;
    }
}

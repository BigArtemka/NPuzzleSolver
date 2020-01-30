package model;


import java.util.Random;

/**
 * @author Tatarin Esli Che
 */
public class Game {
    private int size;
    private int nbTiles;
    private static final Random RANDOM = new Random();
    private Tile[] tiles;
    private int blankPos;
    private boolean gameOver;

    public Game(int size) {
        this.size = size;
        nbTiles = size * size - 1;
        tiles = new Tile[size * size];
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public int getBlankPos() {
        return blankPos;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void newGame() {
        do {
            reset();
            shuffle();
        } while (!isSolvable() || isSolved());
        gameOver = false;
    }

    private void reset() {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile((i + 1) % tiles.length, 60);
        }
        blankPos = tiles.length - 1;
    }

    private void shuffle() {
        int n = nbTiles;
        while (n > 1) {
            int r = RANDOM.nextInt(n--);
            int tmp = tiles[r].getNumber();
            tiles[r].setNumber(tiles[n].getNumber());
            tiles[n].setNumber(tmp);
        }
    }

    private boolean isSolvable() {
        int countInversions = 0;
        for (int i = 0; i < nbTiles; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j].getNumber() > tiles[i].getNumber())
                    countInversions++;
            }
        }
        return countInversions % 2 == 0;
    }

    public void swap(Tile first, Tile second) {
        int tmp = first.getNumber();
        first.setNumber(second.getNumber());
        second.setNumber(tmp);
    }

    public void swap(int number) {
        Tile tile = tiles[number];
        if (number - 1 == blankPos && number % size > 0) { swap(tile, tiles[number - 1]); blankPos = number; }
        else if (number + 1 == blankPos && number % size < 3) {swap(tile, tiles[number + 1]); blankPos = number; }
        else if (number + size == blankPos) {swap(tile, tiles[number + size]); blankPos = number; }
        else if (number - size == blankPos) {swap(tile, tiles[number - size]); blankPos = number; }

    }

    public boolean isSolved() {
        if (tiles[tiles.length - 1].getNumber() != 0)
            return false;

        for (int i = nbTiles - 1; i >= 0; i--) {
            if (tiles[i].getNumber() != i + 1)
                return false;
        }
        return true;
    }



}

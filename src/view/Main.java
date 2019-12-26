package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Game;
import model.Solver;
import model.State;
import model.Tile;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        int size = 3;
        Game game = new Game(size);

        game.newGame();
        Tile[] tiles = game.getTiles();
        HBox[] h = new HBox[size];
        VBox v = new VBox();
        Scene scene = new Scene(v);
        primaryStage.setTitle(size * size - 1 + " puzzle");
        primaryStage.setScene(scene);
        fill(size, tiles, h, v);
        v.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (!game.isGameOver()) {
           //     double x = event.getX();
           //     double y = event.getY();
           //     int column = (int) (x / tiles[0].getSize());
           //     int row = (int) (y / tiles[0].getSize());
           //     game.swap(row * size + column);
           //     fill(size, tiles, h, v);
           //     game.setGameOver(game.isSolved());
            }
        });

        v.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Deque<State> solve;
            try {
                solve = Solver.search(game);
                Tile[] tiles1 = solve.getFirst().getTiles();
                fill(size, tiles1, h, v);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fill(int size, Tile[] tiles, HBox[] h, VBox v) {
        v.getChildren().remove(0, v.getChildren().size());
        for (int i = 0; i < tiles.length; i++) {
            if (h[i/size] == null) h[i/size] = new HBox();
            if (h[i/size].getChildren().size() == size) h[i/size].getChildren().remove(0, size);
            h[i / size].getChildren().add(tiles[i].draw());
            if (i % size == size - 1) v.getChildren().add(h[i/size]);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

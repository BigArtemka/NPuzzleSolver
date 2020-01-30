package view;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Game;
import model.Tile;

import java.util.concurrent.atomic.AtomicInteger;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
       primaryStage.setResizable(false);
        Slider slider = new Slider(2.0, 9.0, 4.0);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setBlockIncrement(1.0);
        slider.setMajorTickUnit(1.0);
        slider.setMinorTickCount(1);
        slider.setSnapToTicks(true);
        Button btn = new Button("Новая игра");
        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, slider, btn);
        Scene scene = new Scene(root, 200, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
        Tile[] tiles = new Tile[1]; HBox[] h = new HBox[1]; VBox v = new VBox(); Game game = new Game(1);
        AtomicInteger size = new AtomicInteger(0);
        newGame(size, tiles, h, v, game, primaryStage, slider, btn);

        btn.setOnAction(event -> {
            newGame(size, tiles, h, v, game, primaryStage, slider, btn);
        });

        v.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
        {
            if (!game.isGameOver()) {
                double x = event.getX();
                double y = event.getY();
                int column = (int) (x / tiles[0].getSize());
                int row = (int) (y / tiles[0].getSize());
                game.swap(row * size.get() + column);
                fill(size.get(), tiles, h, v);
                game.setGameOver(game.isSolved());
            }
        });
    }



        //       v.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        //          Deque<State> solve;
        //          try {
        //              solve = Solver.search(game);
        //              Tile[] tiles1 = solve.getFirst().getTiles();
        //              fill(size, tiles1, h, v);
        //          } catch (IOException | ClassNotFoundException e) {
        //              e.printStackTrace();
        //          }
        //         });
        //      primaryStage.setScene(scene);
        //      primaryStage.show();
        //  }

        private void fill ( int size, Tile[] tiles, HBox[]h, VBox v){
            v.getChildren().remove(0, v.getChildren().size());
            for (int i = 0; i < tiles.length; i++) {
                if (h[i / size] == null) h[i / size] = new HBox();
                if (h[i / size].getChildren().size() == size) h[i / size].getChildren().remove(0, size);
                h[i / size].getChildren().add(tiles[i].draw());
                if (i % size == size - 1) v.getChildren().add(h[i / size]);
            }
            v.getChildren().add(h[size]);
        }

        private void newGame(AtomicInteger size, Tile[] tiles, HBox[] h, VBox v, Game game, Stage primaryStage, Slider slider, Button btn) {
            size.set((int) slider.getValue());
            game = new Game(size.get());
            game.newGame();
            tiles = game.getTiles();
            h = new HBox[size.get() + 1];
            h[size.get()] = new HBox();
            h[size.get()].getChildren().addAll(slider, btn);
            primaryStage.setTitle(size.get() * size.get() - 1 + " puzzle");
            fill(size.get(), tiles, h, v);
            primaryStage.setScene(new Scene(v));
            primaryStage.show();
        }

        public static void main (String[]args){
            launch(args);
        }
    }

package view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Game;
import model.Solver;
import model.State;
import model.Tile;

import java.io.IOException;
import java.util.Date;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        Slider slider = new Slider(2.0, 9.0, 4.0);
        Label text = new Label("Размер поля");
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setBlockIncrement(1.0);
        slider.setMajorTickUnit(1.0);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        Button btn = new Button("Новая игра");
        Button btn1 = new Button("Решить");
        VBox v = new VBox();
        AtomicInteger size = new AtomicInteger(0);
        final Game[] game = {newGame(size, v, primaryStage, slider, btn, btn1, text)};
        final Tile[][] tiles = {game[0].getTiles()};
        primaryStage.setScene(new Scene(v, 500, 600));
        primaryStage.show();
        btn.setOnAction(event -> {
            game[0] = newGame(size, v, primaryStage, slider, btn, btn1, text);
            tiles[0] = game[0].getTiles();

        });

        v.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
        {
            if (!game[0].isGameOver()) {
                double x = event.getX();
                double y = event.getY();
                int column = (int) (x / tiles[0][0].getSize());
                int row = (int) (y / tiles[0][0].getSize());
                if (row < size.get() && column < size.get()) {
                    game[0].swap(row * size.get() + column);
                    fill(size.get(), tiles[0], v, slider, btn, btn1, text);
                    game[0].setGameOver(game[0].isSolved());
                }
            }
        });


        btn1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (!game[0].isGameOver()) {
                Deque<State> solve;
                try {
                    solve = Solver.search(game[0]);
                    AnimationTimer at = new AnimationTimer() {
                        private long lastUpdate = 0;
                        @Override
                        public void handle(long now) {
                            if (now - lastUpdate >= 500000000) {
                                if (solve.isEmpty()) this.stop();
                                else {
                                    Tile[] tiles1 = solve.pollLast().getTiles();
                                    fill(size.get(), tiles1, v, slider, btn, btn1, text);
                                    lastUpdate = now;
                                }
                            }
                        }
                    };
                    at.start();
                    game[0].setGameOver(true);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void fill(int size, Tile[] tiles, VBox v, Slider slider, Button btn, Button btn1, Label label) {
            v.getChildren().remove(0, v.getChildren().size());
        HBox[] h = new HBox[size + 2];
            for (int i = 0; i < tiles.length; i++) {
                if (h[i / size] == null) h[i / size] = new HBox();
                if (h[i / size].getChildren().size() == size) h[i / size].getChildren().remove(0, size);
                h[i / size].getChildren().add(draw(tiles[i]));
                if (i % size == size - 1) v.getChildren().add(h[i / size]);
            }
        h[size] = new HBox(label, slider, btn);
        v.getChildren().addAll(h[size], btn1);
        }

    private StackPane draw(Tile tile) {
        int size = tile.getSize();
        int number = tile.getNumber();
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

    private Game newGame(AtomicInteger size, VBox v, Stage primaryStage, Slider slider, Button btn, Button btn1, Label label) {
            size.set((int) slider.getValue());
        Game game = new Game(size.get());
            game.newGame();
        Tile[] tiles = game.getTiles();
        HBox[] h = new HBox[size.get() + 1];
            h[size.get()] = new HBox();
            h[size.get()].getChildren().addAll(slider, btn);
            primaryStage.setTitle(size.get() * size.get() - 1 + " puzzle");
        if (size.get() > 4) btn1.setDisable(true);
        else btn1.setDisable(false);
        fill(size.get(), tiles, v, slider, btn, btn1, label);
        return game;
        }

        public static void main (String[]args){
            launch(args);
        }
    }


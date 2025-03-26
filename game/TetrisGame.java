package game;

import input.InputHandler;
import render.Renderer;
import sound.SoundManager;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TetrisGame {
    private GameBoard board;
    private Renderer renderer;
    private InputHandler inputHandler;
    private SoundManager soundManager;
    private static final int TARGET_FPS = 60;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private boolean running = true;
    public TetrisPiece activePiece;
    private boolean waitCondition = false;

    public void startGame() {
        board = new GameBoard();
        renderer = new Renderer(board);
        inputHandler = new InputHandler(this);
        soundManager = new SoundManager();
        board.setRenderer(renderer);
        renderer.setTetrisGame(this);
        start();
    }

    public void onGameOver() {
        stop();
    }

    private void setWaitCondition(boolean waitCondition) {
        this.waitCondition = waitCondition;
    }

    public void start() {
        executor.scheduleAtFixedRate(() -> {
            try {
                if (running) {
                    update();
                    render();
                }
            } catch (Exception e) {
                e.printStackTrace(); // Print any exceptions that occur
            }
        }, 0, 1000 / TARGET_FPS, TimeUnit.MILLISECONDS);
    }

    private void update() {
        // Inside your TetrisGame.update() method
        if (activePiece == null) {
            activePiece = PieceFactory.newPiece();
        }

        if (board.collided(activePiece)) {
            board.placePiece(activePiece);
            activePiece = PieceFactory.newPiece(); // Create a new piece
        } else {
            activePiece.fall(); // Move the piece down
        }
        if (board.lineHasBlock(0)) {
            onGameOver();
        }

            // ... your existing code ...
            System.out.println("Active Piece X: " + activePiece.getX() + ", Y: " + activePiece.getY());
            System.out.println("Grid: " + java.util.Arrays.deepToString(board.grid));
            // ...
    }

    private void render() {
        renderer.repaint(); // Redibujar la pantalla.
    }

    public void stop() {
        running = false;
        executor.shutdown();
        System.out.println("Executor shutdown: " + executor.isShutdown());
        System.out.println("Executor terminated: " + executor.isTerminated());
    }

    public static void main(String[] args) {
        TetrisGame game = new TetrisGame();
        game.startGame();
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game.renderer); // Add the renderer from game
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
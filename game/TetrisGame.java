package game;

import input.InputHandler;
import render.Renderer;
import sound.SoundManager;

import javax.swing.*;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TetrisGame {
    private GameBoard board;
    private Renderer renderer;
    private InputHandler inputHandler;
    private SoundManager soundManager;
    private static final int TARGET_FPS = 5;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private boolean running = true;
    public TetrisPiece activePiece;

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

    public void start() {
        executor.scheduleAtFixedRate(() -> {
                if (running) {
                    update();
                    render();
                }
        }, 0, 1000 / TARGET_FPS, TimeUnit.MILLISECONDS);
    }

    private void update() {
        // Inside your TetrisGame.update() method
        System.out.println("updating part 1");
        if (activePiece == null) {
            activePiece = PieceFactory.newPiece();
        }
        System.out.println("updating part 2");
        if (board.canMoveDown(activePiece)) {
            System.out.println("piece fallen");
            activePiece.fall(); // Move the piece down

        } else {
            System.out.println("piece not fallen");
            board.placePiece(activePiece);
            activePiece = PieceFactory.newPiece(); // Create a new piece
        }
        if (board.lineHasBlock(3)) {
            System.out.println("line has block");
            onGameOver();
        }

            System.out.println("Active Piece X: " + activePiece.getX() + ", Y: " + activePiece.getY());
            for (int[] element : board.grid){
                System.out.println(Arrays.toString(element));
            }

    }

    private void render() {
        System.out.println("Rendering...");
        renderer.repaint(); // Redibujar la pantalla.
        System.out.println("Rendered.");
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
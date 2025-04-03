package game;

import input.InputHandler;
import render.Renderer;
import sound.SoundManager;

import javax.swing.*;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TetrisGame {
    private GameBoard board;
    private Renderer renderer;
    private InputHandler inputHandler;
    private SoundManager soundManager;
    private static final int TARGET_FPS = 60;
    private boolean running = true;
    public TetrisPiece activePiece;

    private final ScheduledExecutorService gameExecutor = Executors.newScheduledThreadPool(2);
    private ScheduledFuture<?> fallingTask;
    private long INITIAL_FALLING_SPEED = 350; // 1 second initial delay (milliseconds)

    public void start() {
        // Start render loop at 60 FPS
        gameExecutor.scheduleAtFixedRate(this::render, 0, 1000/TARGET_FPS, TimeUnit.MILLISECONDS);

        // Start falling loop separately
        restartFallingTimer();

        running = true;
    }
    /*
        public void start() {
        executor.scheduleAtFixedRate(() -> {
                if (running) {
                    update();
                    render();
                }
        }, 0, 1000 / TARGET_FPS, TimeUnit.MILLISECONDS);
    }
    */

    private void restartFallingTimer() {
        if (fallingTask != null) {
            fallingTask.cancel(false);
        }

        // Calculate falling speed based on level
        INITIAL_FALLING_SPEED = Math.max(63, INITIAL_FALLING_SPEED - (board.level * 75)); // Gets faster with level

        fallingTask = gameExecutor.scheduleAtFixedRate(this::updateFalling,
                0, INITIAL_FALLING_SPEED, TimeUnit.MILLISECONDS);
    }

    public void updateFalling() {
        if (!running) return;

        if (activePiece == null) {
            activePiece = PieceFactory.newPiece();
        }

        if (board.canMoveDown(activePiece)) {
            activePiece.fall();
        } else {
            board.placePiece(activePiece);
            activePiece = PieceFactory.newPiece();

            // Check for game over
            if (board.lineHasBlock(3)) {
                onGameOver();
                return;
            }

            // Update falling speed after level change
            if (INITIAL_FALLING_SPEED != Math.max(100, 1000 - (board.level * 100))) {
                restartFallingTimer();
            }
        }
    }

    private void render() {
        if (!running) return;
        renderer.repaint();
    }

    public void startGame() {
        board = new GameBoard();
        renderer = new Renderer(board);
        inputHandler = new InputHandler(this);
        soundManager = new SoundManager();
        board.setRenderer(renderer);
        renderer.setTetrisGame(this);
        renderer.addKeyListener(inputHandler);

        start();
    }

    public void onGameOver() {
        stop();
    }

    private void update() {

        if (activePiece == null) {
            activePiece = PieceFactory.newPiece();
        }

        if (board.canMoveDown(activePiece)) {
            activePiece.fall(); // Move the piece down

        } else {
            board.placePiece(activePiece);
            activePiece = PieceFactory.newPiece(); // Create a new piece
        }
        if (board.lineHasBlock(3)) {
            onGameOver();
        }

            for (int[] element : board.grid){
                System.out.println(Arrays.toString(element));
            }

    }

    public void stop() {
        running = false;
        gameExecutor.shutdown();
        System.out.println("Executor shutdown: " + gameExecutor.isShutdown());
        System.out.println("Executor terminated: " + gameExecutor.isTerminated());
    }

    public void moveLeft() {
        if (activePiece != null && running) {
            int newX = activePiece.getX() - 1;
            if (canMove(newX, activePiece.getY())) {
                activePiece.moveLeft();
            }
        }
    }

    public void moveRight() {
        if (activePiece != null && running) {
            int newX = activePiece.getX() + 1;
            if (canMove(newX, activePiece.getY())) {
                activePiece.moveRight();
            }
        }
    }

    public void rotate() {
        if (activePiece != null && running) {
            TetrisPiece temp = new TetrisPiece(activePiece);
            temp.rotate();
            // If rotation causes collision, undo it
            if (canMove(temp.getX(), temp.getY())) {
                activePiece.rotate();
            }
        }
    }

    public void hardDrop() {
        if (activePiece != null && running) {
            while (board.canMoveDown(activePiece)) {
                activePiece.fall();
            }
            // Force update after drop
            board.placePiece(activePiece);
            activePiece = PieceFactory.newPiece();

            if (board.lineHasBlock(3)) {
                onGameOver();
            }
        }
    }

    private boolean canMove(int x, int y) {
        // Add implementation to check if piece can move to x,y
        int[][] shape = activePiece.getShape();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int gridX = x + i;
                    int gridY = y + j;

                    // Check boundaries
                    if (gridX < 0 || gridX >= board.WIDTH || gridY >= board.HEIGHT) {
                        return false;
                    }

                    // Check collision with placed pieces (only if within board)
                    if (gridY >= 0 && board.grid[gridX][gridY] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
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
        frame.setFocusable(true);
        game.renderer.requestFocusInWindow();
    }

}
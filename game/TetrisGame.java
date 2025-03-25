package game;

import input.InputHandler;
import render.Renderer;
import sound.SoundManager;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.ScheduledExecutorService;

public class TetrisGame {
    private GameBoard board;
    private Renderer renderer;
    private InputHandler inputHandler;
    private SoundManager soundManager;
    private static final int TARGET_FPS = 60;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private boolean running = true;
    private TetrisPiece activePiece;

    public void startGame() {
        board = new GameBoard();
        renderer = new Renderer(board);
        inputHandler = new InputHandler(this);
        soundManager = new SoundManager();
        board.setRenderer(renderer);
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
        // Verificar que la pieza actual haya llegado a tocar el piso, sino caer
        // si toco el piso, sedimentar, verificar si se completo una linea
        if (activePiece.isPlaced()) {
            activePiece = PieceFactory.newPice();
        }
        if (board.collided(activePiece)) {
            board.placePiece(activePiece);
        }

    }

    private void render() {
        // Redibujar la pantalla
    }

    public void stop() {
        running = false;
        executor.shutdown();
    }

    public static void main(String[] args) {
        TetrisGame game = new TetrisGame();
        game.start();
    }
}

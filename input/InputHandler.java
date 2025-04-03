package input;

import game.TetrisGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    private final TetrisGame game;

    public InputHandler(TetrisGame game) {
        this.game = game;
        // Attach this handler to your frame or component
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> System.out.println("Left key pressed");
            case KeyEvent.VK_RIGHT -> System.out.println("Right key pressed");
            case KeyEvent.VK_UP -> System.out.println("Up key pressed");
            case KeyEvent.VK_DOWN -> System.out.println("Down key pressed");
            case KeyEvent.VK_SPACE -> System.out.println("Space key pressed");
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> game.moveLeft();
            case KeyEvent.VK_RIGHT -> game.moveRight();
            case KeyEvent.VK_UP -> game.rotate();
            case KeyEvent.VK_DOWN -> game.updateFalling();
            case KeyEvent.VK_SPACE -> game.hardDrop();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // Other KeyListener methods...
}
package UI;

import javax.swing.*;
import java.awt.*;

/**
 * Class that represents a panel of the user interface
 */
public abstract class UIPanel extends JPanel {
    protected static int gameWidth, gameHeight, horizBarY, vertBarX, barThickness;

    /*public static KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed (KeyEvent e) {
            switch (e.getKeyChar()) {
                case 'a' -> Editor.setCurrentTile(new NAND(0, 0));
                case 'n' -> Editor.setCurrentTile(new NOR(0, 0));
                case 'w' -> Editor.setCurrentTile(new Wire());
                case 'b' -> Editor.setCurrentTile(new Bridge(0, 0));
                case 'i' -> Editor.setCurrentTile(new Input(0, 0));
                case 'e' -> Editor.setCurrentTile(new Empty(0, 0));
                case 's' -> Editor.select();
                case 'c' -> Editor.copy();
                case 'm' -> Editor.move();
                case 'd' -> Editor.delete();
                default -> {
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };*/

    /**
     * UI Panel constructor
     * @param x Upper left corner X coord
     * @param y Upper left corner Y coord
     * @param width Panel width
     * @param height Panel height
     */
    public UIPanel(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setBackground(Color.BLACK);
    }

    public static void setParameters(int gameWidth, int gameHeight, int horizBarY, int vertBarX, int barThickness) {
        UIPanel.gameWidth = gameWidth;
        UIPanel.gameHeight = gameHeight;
        UIPanel.horizBarY = horizBarY;
        UIPanel.vertBarX = vertBarX;
        UIPanel.barThickness = barThickness;
    }
}

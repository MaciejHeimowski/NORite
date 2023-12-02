import UI.Editor;
import UI.MenuBar;
import UI.ToolBar;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    // Initial dimensions of the game window
    private static int width = 1024, height = 768;

    // References to all UI components
    private static UI.MenuBar menuBar;
    private static Editor editor;
    private static ToolBar toolBar;

    public Game() {

        // Set up the game window properties
        this.setSize(width, height);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.BLACK);
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setUndecorated(false);
        this.setLocation(200, 200);

        menuBar = new MenuBar();
        editor = new Editor();
        toolBar = new ToolBar();

        this.add(menuBar);
        this.add(toolBar);
        this.add(editor);

        this.setLayout(null);
        this.setVisible(true);
    }
}

package Init;

import Core.Elements.*;
import UI.Editor;
import UI.MenuBar;
import UI.ToolBar;
import UI.UIPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Game extends JFrame {

    // Initial dimensions of the game window
    private static int width = 1024, height = 768;

    private static int horizBarY = 50, vertBarX = 800, barThickness = 2;

    // References to all UI components
    private static UI.MenuBar menuBar;
    private static Editor editor;
    private static ToolBar toolBar;

    /**
     * Update all UI panels
     */
    public static void updateSimulationView() {
        editor.updateView();
        toolBar.repaint();
        menuBar.repaint();
    }

    public Game() throws IOException, ClassNotFoundException {
        super("NORite");

        // Set up the game window properties
        this.setSize(width, height);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.BLACK);
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(false);
        this.setLocation(200, 200);

        // Configure UI panels
        UIPanel.setParameters(width, height, horizBarY, vertBarX, barThickness);

        menuBar = new MenuBar();
        editor = new Editor();
        toolBar = new ToolBar();

        /*
        menuBar.addKeyListener(UIPanel.keyListener);
        editor.addKeyListener(UIPanel.keyListener);
        toolBar.addKeyListener(UIPanel.keyListener);
        */

        editor.requestFocus();

        this.add(menuBar);
        this.add(toolBar);
        this.add(editor);

        this.setLayout(null);
        this.setVisible(true);

        int delayMillis = 100;

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    Editor.eraseNetlist();
                    menuBar.saveFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ActionListener taskPerformer = evt -> {
            if(Editor.getStatus() == Editor.Status.Running) {
                Editor.tick();
                editor.repaint();
            }
        };

        new javax.swing.Timer(delayMillis, taskPerformer).start();

        menuBar.loadFile();
    }
}

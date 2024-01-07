package UI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

import Core.Elements.Tile;
import Init.Game;

import javax.swing.*;

import static UI.Editor.Status;

public class MenuBar extends UIPanel {
    /**
     * MenuBar panel constructor
     */
    public MenuBar() {
        super(0, 0, gameWidth, horizBarY);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getX() > 370 && e.getX() < 410) {
                    //System.out.println("STOP");
                    Editor.stopSimulation();
                }
                else if(e.getX() > 328 && e.getX() < 363) {
                    //System.out.println("START");
                    Editor.startSimulation();
                    Editor.disableSelectionMode();
                    Game.updateSimulationView();
                }
                else if(e.getX() > 217 && e.getX() < 324) {
                    //System.out.println("STEP");
                    Editor.stepSimulation();
                    Editor.disableSelectionMode();
                    Game.updateSimulationView();
                }
                Game.updateSimulationView();
                //System.out.println(e.getX() + " " + e.getY());
                repaint();
            }
        });

        this.setLayout(null);

        JButton saveButton = new JButton("SAVE");
        saveButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        saveButton.setFocusPainted(false);
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            if(Editor.getStatus() == Status.Stopped) {
                try {
                    Editor.eraseNetlist();
                    saveFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        saveButton.setBounds(2, 2, 128, 46);

        JButton loadButton = new JButton("LOAD");
        loadButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        loadButton.setFocusPainted(false);
        loadButton.setBackground(Color.BLACK);
        loadButton.setForeground(Color.WHITE);
        loadButton.addActionListener(e -> {
            if(Editor.getStatus() == Status.Stopped) {
                try {
                    loadFile();
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        loadButton.setBounds(132, 2, 128, 46);

        this.add(saveButton);
        this.add(loadButton);
    }

    /**
     * Serializes the map and places it in a file
     * @throws IOException
     */
    public void saveFile() throws IOException {
        FileOutputStream outputFile = new FileOutputStream("circuit.nrt");
        ObjectOutputStream objectOutput = new ObjectOutputStream(outputFile);
        objectOutput.writeObject(Editor.getMap());
    }

    /**
     * Loads a map from a file and deserializes it
     * @throws IOException
     */
    public void loadFile() throws IOException, ClassNotFoundException {
        FileInputStream inputFile = new FileInputStream("circuit.nrt");
        ObjectInputStream objectInput = new ObjectInputStream(inputFile);
        Editor.setMap((Tile[][]) objectInput.readObject());

        Game.updateSimulationView();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2 * barThickness));

        super.paint(g);

        this.paintBase(g2);
        this.paintOptions(g2);
    }

    private void paintBase(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,horizBarY - barThickness, gameWidth, barThickness);
    }

    private void paintOptions(Graphics2D g2) {
        Polygon stepButton = new Polygon(
                new int[] {3 * gameWidth / 10 + (11 * barThickness) - 47,
                        3 * gameWidth / 10 + (11 * barThickness) + (gameWidth / 30) - 47,
                        3 * gameWidth / 10 + (11 * barThickness) - 47
                },
                new int[] {
                        3 * barThickness,
                        (horizBarY - (4 * barThickness) + (3 * barThickness)) / 2,
                        horizBarY - (4 * barThickness)
                },
                3
        );

        Polygon startButton = new Polygon(
            new int[] {3 * gameWidth / 10 + (11 * barThickness),
            3 * gameWidth / 10 + (11 * barThickness) + (gameWidth / 30),
            3 * gameWidth / 10 + (11 * barThickness)},
            new int[] {
                    3 * barThickness,
                    (horizBarY - (4 * barThickness) + (3 * barThickness)) / 2,
                    horizBarY - (4 * barThickness)
            },
   3
        );

        g2.setColor(new Color(255, 255, 0));
        g2.drawLine(319, 2 * barThickness + 2, 319, horizBarY - (2 * barThickness) - 4);

        if(Editor.getStatus() == Status.Running) {
            g2.setColor(new Color(255, 255, 0));
            g2.drawPolygon(stepButton);

            g2.setColor(new Color(0, 255, 255));
            g2.fillPolygon(startButton);

            g2.setColor(new Color(255, 0, 255));
            g2.drawOval(
                    (int)(startButton.getBounds2D().getX() + startButton.getBounds2D().getWidth() + (5 * barThickness)),
                    3 * barThickness,
                    horizBarY - (7 * barThickness),
                    horizBarY - (7 * barThickness)
            );
        }
        else if(Editor.getStatus() == Status.Stopped){
            g2.setColor(new Color(255, 255, 0));
            g2.drawPolygon(stepButton);

            g2.setColor(new Color(0, 255, 255));
            g2.drawPolygon(startButton);

            g2.setColor(new Color(255, 0, 255));
            g2.fillOval(
                    (int)(startButton.getBounds2D().getX() + startButton.getBounds2D().getWidth() + (5 * barThickness)),
                    3 * barThickness,
                    horizBarY - (7 * barThickness),
                    horizBarY - (7 * barThickness)
            );
        }
        else {
            g2.setColor(new Color(255, 255, 0));
            g2.fillPolygon(stepButton);

            g2.setColor(new Color(0, 255, 255));
            g2.fillPolygon(startButton);

            g2.setColor(new Color(255, 0, 255));
            g2.drawOval(
                    (int)(startButton.getBounds2D().getX() + startButton.getBounds2D().getWidth() + (5 * barThickness)),
                    3 * barThickness,
                    horizBarY - (7 * barThickness),
                    horizBarY - (7 * barThickness)
            );
        }

        g2.setColor(Color.WHITE);
    }
}

package UI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

import Core.Elements.*;
import Core.Node;

public class Editor extends UIPanel {

    private static final int tileSize = 20;

    private static Tile[][] map;

    private static Tile currentTile;

    private static boolean simulationStarted;

    public static boolean isSimulationStarted() {
        return simulationStarted;
    }

    public static void startSimulation() {
        simulationStarted = true;

        createNetlist();
    }

    public static void stopSimulation() {
        simulationStarted = false;

        eraseNetlist();
    }

    private static HashSet<Node> netlist = new HashSet<>();

    public Editor() {
        super(0, horizBarY, vertBarX, gameHeight - horizBarY);

        map = new Tile[((gameHeight - horizBarY - 2) / tileSize) - 2][(vertBarX - 2) / tileSize];

        for(int i = 0; i < (vertBarX - 2) / tileSize; i++) {
            for(int j = 0; j < ((gameHeight - horizBarY - 2) / tileSize) - 2; j++) {
                map[j][i] = new Empty(j, i);
            }
        }

        currentTile = new Wire();

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if(isSimulationStarted()) {

                }
                else {
                    handleMouseClick(me);
                }
            }
        });

        repaint();
    }

    private void handleMouseClick(MouseEvent me) {
        int tileX = ((me.getX() - 10) / tileSize);
        int tileY = ((me.getY() - 10) / tileSize);

        if(me.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Place " + tileX + " " + tileY);

            switch(currentTile) {
                case Wire w -> {
                    currentTile = new Wire();
                }
                case NOR n -> {
                    currentTile = new NOR();
                }
                default -> {
                    currentTile = new Empty(tileX, tileY);
                }
            }

            map[tileY][tileX] = currentTile;
        }
        else if(me.getButton() == MouseEvent.BUTTON3) {
            System.out.println("Erase " + tileX + " " + tileY);
            map[tileY][tileX] = new Empty(tileX, tileY);
        }
        repaint();
    }

    public static void createNetlist() {
        int nodeId = 0;

        for(int i = 1; i < (vertBarX - 2) / tileSize - 1; i++) {
            for(int j = 1; j < ((gameHeight - horizBarY - 2) / tileSize) - 3; j++) {
                if(map[j][i] instanceof Wire) {
                    if(((Wire) map[j][i]).getNode() == null) {
                        Node newNode = new Node(nodeId);
                        netlist.add(newNode);

                        attachNode(j, i, newNode);

                        nodeId++;
                    }
                }
            }
        }

        for(Node node : netlist) {
            System.out.println("Netlist now contains node " + node.getId());
        }
    }

    public static void eraseNetlist() {
        int nodeId = 0;

        for(int i = 1; i < (vertBarX - 2) / tileSize - 1; i++) {
            for(int j = 1; j < ((gameHeight - horizBarY - 2) / tileSize) - 3; j++) {
                if(map[j][i] instanceof Wire) {
                    ((Wire) map[j][i]).setNode(null);
                }
            }
        }

        netlist.clear();
    }

    private static void attachNode(int j, int i, Node newNode) {
        Wire wire = (Wire) map[j][i];

        if(wire.getNode() == null) {
            wire.setNode(newNode);

            System.out.println("Attached node " + newNode.getId() + " to wire at " + j + ", " + i);

            if (map[j][i - 1] instanceof Wire) {
                attachNode(j, i - 1, newNode);
            }
            if (map[j][i + 1] instanceof Wire) {
                attachNode(j, i + 1, newNode);
            }
            if(map[j - 1][i] instanceof Wire) {
                attachNode(j - 1, i, newNode);
            }
            if(map[j + 1][i] instanceof Wire) {
                attachNode(j + 1, i, newNode);
            }
        }
    }

    public static void setCurrentTile(Tile tile) {
        currentTile = tile;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2 * barThickness));

        super.paint(g2);

        this.paintBase(g2);

        for(int i = 0; i < (vertBarX - 2) / tileSize; i++) {
            for(int j = 0; j < ((gameHeight - horizBarY - 2) / tileSize) - 2; j++) {
                g2.setColor(map[j][i].getColor());
                g2.fillRect(10 + tileSize * i, 10 + tileSize *  j + 2, tileSize, tileSize);
            }
        }
    }

    private void paintBase(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, vertBarX, barThickness);
        g2.fillRect(vertBarX - barThickness, 0, barThickness, gameHeight - horizBarY);
    }
}

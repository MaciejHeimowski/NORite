package UI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import Core.Elements.*;
import Core.Node;

public class Editor extends UIPanel {

    private static final int tileSize = 20;

    private static Tile[][] map;

    private static Tile currentTile;

    public enum Status {
        Stopped,
        Step,
        Running
    }

    private static Status status = Status.Stopped;

    private static HashSet<Node> netlist = new HashSet<>();

    public static Status getStatus() {
        return status;
    }

    public static void startSimulation() {
        if(status == Status.Stopped)
            createNetlist();

        status = Status.Running;
    }

    public static void stopSimulation() {
        status = Status.Stopped;

        eraseNetlist();
    }

    public static void stepSimulation() {
        if(status == Status.Stopped)
            createNetlist();

        status = Status.Step;

        tick();
    }

    public static void tick() {
        for(Node node : netlist) {
            node.updateState();
        }
    }

    public void updateView() {
        repaint();
    }

    public static Tile getTile(int x, int y) {
        return map[y][x];
    }

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
                if(status != Status.Stopped) {
                    handleInputTiles(me);
                }
                else {
                    handleMouseClick(me);
                }
            }
        });

        updateView();
    }

    private void handleInputTiles(MouseEvent me) {
        int tileX = ((me.getX() - 10) / tileSize);
        int tileY = ((me.getY() - 10) / tileSize);

        if(me.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Flip input " + tileX + " " + tileY);

            Tile pressedTile = map[tileY][tileX];

            if(pressedTile instanceof Input) {
                ((Input) pressedTile).flipOutputState();
            }
        }

        updateView();
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
                case NAND n -> {
                    currentTile = new NAND(tileX, tileY);
                }
                case NOR n -> {
                    currentTile = new NOR(tileX, tileY);
                }
                case Bridge b -> {
                    currentTile = new Bridge(tileX, tileY);
                }
                case Input i -> {
                    currentTile = new Input(tileX, tileY);
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

        updateView();
    }

    public static void createNetlist() {
        int nodeId = 0;

        // Attach nodes to wires

        for(int i = 1; i < (vertBarX - 2) / tileSize - 1; i++) {
            for(int j = 1; j < ((gameHeight - horizBarY - 2) / tileSize) - 3; j++) {
                if(map[j][i] instanceof Wire) {
                    Wire wire = (Wire) map[j][i];

                    if(wire.getNode() == null) {
                        Node newNode = new Node(nodeId);
                        netlist.add(newNode);

                        attachNode(j, i, newNode);

                        nodeId++;
                    }
                }
            }
        }

        // Attach gate outputs and inputs to nodes

        for(int i = 1; i < (vertBarX - 2) / tileSize - 1; i++) {
            for(int j = 1; j < ((gameHeight - horizBarY - 2) / tileSize) - 3; j++) {
                if(map[j][i] instanceof Gate) {
                    Gate gate = (Gate) map[j][i];
                    gate.attachConnections();
                }
            }
        }

        for(Node node : netlist) {
            System.out.println("Node " + node.getId() + " is driven by gates at " + node.enumerateDrivers());
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
        if(map[j][i] instanceof Wire) {
            Wire wire = (Wire) map[j][i];

            if(wire.getNode() == null) {
                wire.setNode(newNode);

                System.out.println("Attached node " + newNode.getId() + " to wire at " + j + ", " + i);

                if(map[j][i - 1] instanceof Wire) {
                    attachNode(j, i - 1, newNode);
                }
                if(map[j][i + 1] instanceof Wire) {
                    attachNode(j, i + 1, newNode);
                }
                if(map[j - 1][i] instanceof Wire) {
                    attachNode(j - 1, i, newNode);
                }
                if(map[j + 1][i] instanceof Wire) {
                    attachNode(j + 1, i, newNode);
                }

                if(map[j][i - 2] instanceof Wire && map[j][i - 1] instanceof Bridge) {
                    attachNode(j, i - 2, newNode);
                }
                if(map[j][i + 2] instanceof Wire && map[j][i + 1] instanceof Bridge) {
                    attachNode(j, i + 2, newNode);
                }
                if(map[j - 2][i] instanceof Wire && map[j - 1][i] instanceof Bridge) {
                    attachNode(j - 2, i, newNode);
                }
                if(map[j + 2][i] instanceof Wire && map[j + 1][i] instanceof Bridge) {
                    attachNode(j + 2, i, newNode);
                }
            }
        }
        else {

        }
    }

    public static void setCurrentTile(Tile tile) {
        currentTile = tile;
    }

    public static void setMap(Tile[][] newMap) {
        map = newMap;
    }

    public static Tile[][] getMap() {
        return map;
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

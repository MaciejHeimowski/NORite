package UI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import Core.Elements.*;
import Core.Node;
import Init.Game;

/**
 * Class that represents the schematic editor's window and its functionality
 */
public class Editor extends UIPanel {

    // Size of one square tile in pixels
    private static final int tileSize = 20;

    // Flags for the editing tool modes
    private static boolean selectionModeEnabled = false;
    private static boolean pasteModeEnabled = false;
    private static boolean moveModeEnabled = false;

    // Counter that keeps track of which selection corner we're setting
    private static int selectionClickCount = 0;

    // Coords of previous click in selection mode
    private int prevXClick, prevYClick;

    // Main map of tiles
    private static Tile[][] map;

    // Type of tile that's currently selected
    private static Tile currentTile;

    // Simulation status type
    public enum Status {
        Stopped,
        Step,
        Running
    }

    // Simulation status (default Stopped)
    private static Status status = Status.Stopped;

    // Netlist for running the simulation
    private static final HashSet<Node> netlist = new HashSet<>();

    /**
     * Inner class that represents the selection
     */
    public static class Selection {

        // Selection corners
        private int x1, y1, x2, y2;

        // Array that contains references to selected area
        private Tile[][] buffer;

        /**
         * New selection constructor
         * @param newx1 First corner X coord
         * @param newy1 FIrst corner Y coord
         * @param newx2 Second corner X coord
         * @param newy2 Second corner Y coord
         */
        public Selection(int newx1, int newy1, int newx2, int newy2) {
            this.x1 = newx1;
            this.y1 = newy1;
            this.x2 = newx2;
            this.y2 = newy2;

            if(this.y2 < this.y1) {
                int temp = this.y2;
                this.y2 = this.y1;
                this.y1 = temp;
            }

            if(this.x2 < this.x1) {
                int temp = this.x2;
                this.x2 = this.x1;
                this.x1 = temp;
            }

            getMapSection();
        }

        /**
         * Function that returns a reference to selected map area
         */
        public void getMapSection() {
            buffer = new Tile[this.y2 - this.y1 + 1][this.x2 - this.x1 + 1];

            for(int j = this.y1; j <= this.y2; j++) {
                for(int i = this.x1; i <= this.x2; i++) {
                    buffer[j - this.y1][i - this.x1] = map[j][i];
                }
            }

        }

        /**
         * Copies the buffer onto the selected area
         * @param x Upper left corner X coord
         * @param y Upper left corner Y coord
         */
        public void setMapSectionToSelected(int x, int y) {
            for(int j = 0; j <= this.y2 - this.y1; j++) {
                for(int i = 0; i <= this.x2 - this.x1; i++) {
                    if(!(buffer[j][i] instanceof Empty) && y + j < map.length && x + i < map[0].length)
                        switch(buffer[j][i]) {
                            case Wire ignored   -> map[y + j][x + i] = new Wire();
                            case NAND ignored   -> map[y + j][x + i] = new NAND(x + i, y + j);
                            case NOR ignored    -> map[y + j][x + i] = new NOR(x + i, y + j);
                            case Bridge ignored -> map[y + j][x + i] = new Bridge(x + i, y + j);
                            case Input ignored  -> map[y + j][x + i] = new Input(x + i, y + j);
                            default             -> map[y + j][x + i] = new Empty(x + i, y + j);
                        }
                }
            }
        }

        /**
         * Removes all tiles in the selected area
         */
        public void clearSelected() {
            getMapSection();

            for(int j = this.y1; j <= this.y2; j++) {
                for(int i = this.x1; i <= this.x2; i++) {
                    map[j][i] = new Empty(i, j);
                }
            }
        }
    }

    // Current selection - null if none
    private static Selection selection;

    public static Status getStatus() {
        return status;
    }

    /**
     * Start running the simulation
     */
    public static void startSimulation() {
        if(status == Status.Stopped)
            createNetlist();

        status = Status.Running;

        selection = null;
    }

    /**
     * Stops the simulation and returns to editing mode
     */
    public static void stopSimulation() {
        status = Status.Stopped;

        eraseNetlist();
    }

    /**
     * Makes a single step in the simulation
     */
    public static void stepSimulation() {
        if(status == Status.Stopped)
            createNetlist();

        status = Status.Step;

        selection = null;

        tick();
    }

    /**
     * Updates the node states based on gate outputs
     */
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

    /**
     * Editor panel constructor
     */
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
                    if(!selectionModeEnabled)
                        handleMouseClick(me);
                    else {
                        handleEditClick(me);
                    }
                }
            }
        });

        updateView();
    }

    /**
     * Mouse click handler for editing tools
     * @param me AWT MouseEvent variable
     */
    private void handleEditClick(MouseEvent me) {
        int tileX = ((me.getX() - 10) / tileSize);
        int tileY = ((me.getY() - 10) / tileSize);

        if(!(moveModeEnabled || pasteModeEnabled))
            handleSelectionClick(me);
        else if(moveModeEnabled && selection != null) {
            selection.clearSelected();
            selection.setMapSectionToSelected(tileX, tileY);

            selection = null;
            selectionClickCount = 0;
            moveModeEnabled = false;
            pasteModeEnabled = false;
        }
        else if(pasteModeEnabled && selection != null) {
            selection.setMapSectionToSelected(tileX, tileY);

            selection = null;
            selectionClickCount = 0;
            moveModeEnabled = false;
            pasteModeEnabled = false;
        }

        Game.updateSimulationView();
    }

    /**
     * Mouse click handler for selections
     * @param me AWT MouseEvent variable
     */
    private void handleSelectionClick(MouseEvent me) {
        selectionClickCount++;

        int tileX = ((me.getX() - 10) / tileSize);
        int tileY = ((me.getY() - 10) / tileSize);

        switch(selectionClickCount) {
            case 1 -> {
                prevXClick = tileX;
                prevYClick = tileY;
            }
            case 2 -> selection = new Selection(prevXClick, prevYClick, tileX, tileY);
            case 3 -> {
                prevXClick = tileX;
                prevYClick = tileY;

                selectionClickCount = 1;
            }
            default -> {
                selection = null;
                selectionClickCount = 0;
            }
        }

        Game.updateSimulationView();
    }

    /**
     * Mouse click handler for switching inputs while running
     * @param me AWT MouseEvent variable
     */
    private void handleInputTiles(MouseEvent me) {
        int tileX = ((me.getX() - 10) / tileSize);
        int tileY = ((me.getY() - 10) / tileSize);

        if(me.getButton() == MouseEvent.BUTTON1) {
            //System.out.println("Flip input " + tileX + " " + tileY);

            Tile pressedTile = map[tileY][tileX];

            if(pressedTile instanceof Input) {
                ((Input) pressedTile).flipOutputState();
            }
        }

        updateView();
    }

    /**
     * Mouse click handler for placing and removing tiles
     * @param me AWT MouseEvent variable
     */
    private void handleMouseClick(MouseEvent me) {
        int tileX = ((me.getX() - 10) / tileSize);
        int tileY = ((me.getY() - 10) / tileSize);

        if(me.getButton() == MouseEvent.BUTTON1) {
            //System.out.println("Place " + tileX + " " + tileY);

            switch(currentTile) {
                case Wire ignored   -> currentTile = new Wire();
                case NAND ignored   -> currentTile = new NAND(tileX, tileY);
                case NOR ignored    -> currentTile = new NOR(tileX, tileY);
                case Bridge ignored -> currentTile = new Bridge(tileX, tileY);
                case Input ignored  -> currentTile = new Input(tileX, tileY);
                default             -> currentTile = new Empty(tileX, tileY);
            }

            map[tileY][tileX] = currentTile;
        }
        else if (me.getButton() == MouseEvent.BUTTON2) {
            currentTile = map[tileY][tileX];
        }
        else if(me.getButton() == MouseEvent.BUTTON3) {
            //System.out.println("Erase " + tileX + " " + tileY);
            map[tileY][tileX] = new Empty(tileX, tileY);
        }

        updateView();
    }

    /**
     * Assigns nodes to all wire tiles to create logical connections
     */
    public static void createNetlist() {
        int nodeId = 0;

        // Attach nodes to wires

        for(int i = 0; i < (vertBarX - 2) / tileSize; i++) {
            for(int j = 0; j < ((gameHeight - horizBarY - 2) / tileSize) - 2; j++) {
                if(map[j][i] instanceof Wire wire) {

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

        for(int i = 0; i < (vertBarX - 2) / tileSize; i++) {
            for(int j = 0; j < ((gameHeight - horizBarY - 2) / tileSize) - 2; j++) {
                if(map[j][i] instanceof Gate gate) {
                    gate.attachConnections();
                }
            }
        }

        /*
        for(Node node : netlist) {
            System.out.println("Node " + node.getId() + " is driven by gates at " + node.enumerateDrivers());
        }
        */
    }

    /**
     * Clears the entire netlist, setting node references in wires to null
     */
    public static void eraseNetlist() {
        for(int i = 1; i < (vertBarX - 2) / tileSize - 1; i++) {
            for(int j = 1; j < ((gameHeight - horizBarY - 2) / tileSize) - 3; j++) {
                if(map[j][i] instanceof Wire) {
                    ((Wire) map[j][i]).setNode(null);
                }
            }
        }

        netlist.clear();
    }

    /**
     * Attaches a node reference to wires, gets called recursively for all neighbouring wire tiles
     * @param j X coord
     * @param i Y coord
     * @param newNode reference to the currently processed node
     */
    private static void attachNode(int j, int i, Node newNode) {
        if(map[j][i] instanceof Wire wire) {
            if(wire.getNode() == null) {
                wire.setNode(newNode);

                //System.out.println("Attached node " + newNode.getId() + " to wire at " + j + ", " + i);

                if(i > 0 && map[j][i - 1] instanceof Wire) {
                    attachNode(j, i - 1, newNode);
                }
                if(i < 38 && map[j][i + 1] instanceof Wire) {
                    attachNode(j, i + 1, newNode);
                }
                if(j > 0 && map[j - 1][i] instanceof Wire) {
                    attachNode(j - 1, i, newNode);
                }
                if(j < 32 && map[j + 1][i] instanceof Wire) {
                    attachNode(j + 1, i, newNode);
                }

                if(i > 1 && map[j][i - 2] instanceof Wire && map[j][i - 1] instanceof Bridge) {
                    attachNode(j, i - 2, newNode);
                }
                if(i < 37 && map[j][i + 2] instanceof Wire && map[j][i + 1] instanceof Bridge) {
                    attachNode(j, i + 2, newNode);
                }
                if(j > 1 && map[j - 2][i] instanceof Wire && map[j - 1][i] instanceof Bridge) {
                    attachNode(j - 2, i, newNode);
                }
                if(j < 31 && map[j + 2][i] instanceof Wire && map[j + 1][i] instanceof Bridge) {
                    attachNode(j + 2, i, newNode);
                }
            }
        }
    }

    /**
     * Changes the type of tile that's being placed, exits all editing tools
     * @param tile Tile type to be placed
     */
    public static void setCurrentTile(Tile tile) {
        currentTile = tile;

        selection = null;
        selectionModeEnabled = false;
        moveModeEnabled = false;
        pasteModeEnabled = false;
        selectionClickCount = 0;

        Game.updateSimulationView();
    }

    public static void setMap(Tile[][] newMap) {
        map = newMap;
    }

    public static Tile[][] getMap() {
        return map;
    }

    /**
     * Enters selection mode
     */
    public static void select() {
        selectionModeEnabled = !selectionModeEnabled;

        Game.updateSimulationView();

        if(!selectionModeEnabled) {
            selection = null;
            pasteModeEnabled = false;
            moveModeEnabled = false;
            selectionClickCount = 0;
        }
    }

    /**
     * Enters copy / paste mode
     */
    public static void copy() {
        if(selectionModeEnabled)
            pasteModeEnabled = !pasteModeEnabled;

        if(pasteModeEnabled)
            moveModeEnabled = false;

        Game.updateSimulationView();
    }

    /**
     * Enters move mode
     */
    public static void move() {
        if(selectionModeEnabled)
            moveModeEnabled = !moveModeEnabled;

        if(moveModeEnabled)
            pasteModeEnabled = false;

        Game.updateSimulationView();
    }

    /**
     * Enters delete mode
     */
    public static void delete() {
        if(selection != null && selectionModeEnabled) {
            selection.clearSelected();
            Game.updateSimulationView();
        }
    }

    public static boolean isSelectionModeEnabled() {
        return selectionModeEnabled;
    }

    public static boolean isPasteModeEnabled() {
        return pasteModeEnabled;
    }

    public static boolean isMoveModeEnabled() {
        return moveModeEnabled;
    }

    public static void disableSelectionMode() {
        selectionModeEnabled = false;
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

        if(selection != null) {
            paintSelection(g2);
        }
    }

    private void paintBase(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, vertBarX, barThickness);
        g2.fillRect(vertBarX - barThickness, 0, barThickness, gameHeight - horizBarY);
    }

    private void paintSelection(Graphics2D g2) {
        if(selectionClickCount == 2) {
            g2.setColor(Color.CYAN);
            g2.drawRect(10 + selection.x1 * tileSize, 10 + selection.y1 * tileSize, tileSize * (selection.x2 - selection.x1 + 1), tileSize * (selection.y2 - selection.y1 + 1));
        }
    }
}

package Core;

import Core.Elements.Gate;

import java.util.HashSet;

public class Node {
    private boolean state;

    private int id;

    private HashSet<Gate> drivers;

    public Node(int id) {
        drivers = new HashSet<>();
        this.id = id;
    }

    public boolean getState() {
        return this.state;
    }

    public int getId() {
        return this.id;
    }

    public void addDriver(Gate gate) {
        this.drivers.add(gate);
    }

    public void updateState() {
        this.state = false;

        for(Gate driver : this.drivers) {
            if(driver.getOutputState()) {
                this.state = true;
            }
        }
    }

    public String enumerateDrivers() {
        String ret = new String();

        for(Gate driver : drivers) {
            ret += "[" + driver.getX() + ", " + driver.getY() + "], ";
        }

        return ret;
    }
}

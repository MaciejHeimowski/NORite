package Circuit;

import Circuit.Entities.Active.ActiveEntity;
import Circuit.Entities.Entity;
import Circuit.Entities.Passive.PassiveEntity;
import Circuit.Entities.Passive.Wire;
import Utils.Orientation;
import Utils.Port;

import java.util.HashSet;

public class Circuit {
    private HashSet<Entity> entities;

    private HashSet<Node> netlist;

    public Circuit() {
        this.entities = new HashSet<>();
        this.netlist = new HashSet<>();
    }

    private void addActiveEntity(ActiveEntity newActiveEntity) {

    }

    private void addPassiveEntity(PassiveEntity newPassiveEntity) {

    }

    public void addEntity(Entity newEntity, Orientation orientation) {
        switch(newEntity) {
            case ActiveEntity e -> addActiveEntity(e);
            case PassiveEntity p -> addPassiveEntity(p);
            default -> {}
        }
    }
}

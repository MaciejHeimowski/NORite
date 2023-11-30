import Circuit.Entities.Active.Gates.NOR;
import Utils.Coords;
import Utils.Orientation;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        NOR nor = new NOR(new Coords(10, 10), Orientation.Up);

        System.out.println(Arrays.toString(nor.getPorts()));
    }
}
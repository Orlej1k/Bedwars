package ua.bedwars.Game;

import org.bukkit.Location;
import org.bukkit.Material;

public class Bed {

    private final static Material material = Material.RED_BED;
    private boolean isBroken;
    private Location locationBlock1, locationBlock2;

    public Bed(Location locationBlock1, Location locationBlock2) {
        this.locationBlock1 = locationBlock1;
        this.locationBlock2 = locationBlock2;
        this.isBroken = false;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public Location getLocationBlock1() {
        return locationBlock1;
    }

    public Location getLocationBlock2() {
        return locationBlock2;
    }
}

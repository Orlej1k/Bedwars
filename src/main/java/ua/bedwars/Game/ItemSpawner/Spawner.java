package ua.bedwars.Game.ItemSpawner;

import org.bukkit.Location;

public class Spawner {
    private SpawnerItems dropItem;
    private Location location;
    private int spawnTime;
    private int itemsDrop;

    public Spawner(SpawnerItems dropItem, Location location, int spawnTime, int itemsDrop){
        this.dropItem = dropItem;
        this.location = location;
        this.spawnTime = spawnTime;
        this.itemsDrop = itemsDrop;
    }

    public SpawnerItems getDropItem() {
        return dropItem;
    }

    public Location getLocation() {
        return location;
    }

    public int getSpawnTime() {
        return spawnTime;
    }

    public int getItemsDrop() {
        return itemsDrop;
    }

    public void setSpawnTime(int spawnTime) {
        this.spawnTime = spawnTime;
    }
}

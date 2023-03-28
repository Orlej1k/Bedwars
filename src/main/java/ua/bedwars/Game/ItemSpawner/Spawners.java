package ua.bedwars.Game.ItemSpawner;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import ua.bedwars.Arena;
import ua.bedwars.Configs.LocationsConfig;
import ua.bedwars.Configs.SpawnerLocationsConfig;
import ua.bedwars.Util.Util;

import java.util.ArrayList;
import java.util.List;

public enum Spawners {

    IRON(new ArrayList<>()),
    GOLD(new ArrayList<>()),
    DIAMOND(new ArrayList<>()),
    EMERALD(new ArrayList<>());

    private List<Spawner> spawners;
    Spawners(List<Spawner> spawners){
        this.spawners = spawners;
    }


    public List<Spawner> getSpawners() {
        return spawners;
    }

    static {
        loadSpawnersFromConfig();
    }

    private static void loadSpawnersFromConfig() {
        FileConfiguration config = SpawnerLocationsConfig.getData().getConfig();
        for (String path : config.getConfigurationSection("SpawnerTypes").getKeys(false)) {
            for (String spawnerPath : config.getConfigurationSection("SpawnerTypes." + path).getKeys(false)){
                if(!spawnerPath.equals("timeForDrop") && !spawnerPath.equals("spawnItems")){
                    int x = config.getInt("SpawnerTypes." + path + "." + spawnerPath + ".X");
                    int y = config.getInt("SpawnerTypes." + path + "." + spawnerPath + ".Y");
                    int z = config.getInt("SpawnerTypes." + path + "." + spawnerPath + ".Z");
                    switch(path) {
                        case "IRON":
                            IRON.spawners.add(new Spawner(SpawnerItems.IRON, new Location(Util.defaultMap, x, y, z), config.getInt("SpawnerTypes." + path + ".timeForDrop"), config.getInt("SpawnerTypes." + path + ".spawnItems")));
                            break;
                        case "GOLD":
                            GOLD.spawners.add(new Spawner(SpawnerItems.GOLD, new Location(Util.defaultMap, x, y, z), config.getInt("SpawnerTypes." + path + ".timeForDrop"), config.getInt("SpawnerTypes." + path + ".spawnItems")));
                            break;
                        case "DIAMOND":
                            DIAMOND.spawners.add(new Spawner(SpawnerItems.DIAMOND, new Location(Util.defaultMap, x, y, z), config.getInt("SpawnerTypes." + path + ".timeForDrop"), config.getInt("SpawnerTypes." + path + ".spawnItems")));
                            break;
                        case "EMERALD":
                            EMERALD.spawners.add(new Spawner(SpawnerItems.EMERALD, new Location(Util.defaultMap, x, y, z), config.getInt("SpawnerTypes." + path + ".timeForDrop"), config.getInt("SpawnerTypes." + path + ".spawnItems")));
                            break;
                    }
                }
            }
        }
    }

}

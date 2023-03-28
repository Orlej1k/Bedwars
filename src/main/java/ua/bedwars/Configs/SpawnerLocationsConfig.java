package ua.bedwars.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ua.bedwars.BedWars;

import java.io.File;
import java.io.IOException;

public class SpawnerLocationsConfig {
    private File file;
    private FileConfiguration config;

    private static SpawnerLocationsConfig data;

    public SpawnerLocationsConfig(String fileName){
        file = new File(BedWars.getInstance().getDataFolder(), fileName);
        try {
            if(!file.exists() && !file.createNewFile()) throw new IOException();
        } catch (IOException e) {
            throw new RuntimeException("Oh, can't create file 'spawnerLocations.yml'",e);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException("Can't save file 'spawnerLocations.yml'",e);
        }
    }

    public static SpawnerLocationsConfig getData() {
        return data;
    }

    public static void setData(SpawnerLocationsConfig data) {
        SpawnerLocationsConfig.data = data;
    }
}

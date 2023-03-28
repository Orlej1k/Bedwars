package ua.bedwars.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ua.bedwars.BedWars;

import java.io.File;
import java.io.IOException;

public class BlocksConfig {
    private File file;
    private FileConfiguration config;

    private static BlocksConfig data;

    public BlocksConfig(String fileName){
        file = new File(BedWars.getInstance().getDataFolder(), fileName);
        try {
            if(!file.exists() && !file.createNewFile()) throw new IOException();
        } catch (IOException e) {
            throw new RuntimeException("Oh, can't create file 'blocks.yml'",e);
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
            throw new RuntimeException("Can't save file 'blocks.yml'",e);
        }
    }

    public static BlocksConfig getData() {
        return data;
    }

    public static void setData(BlocksConfig data) {
        BlocksConfig.data = data;
    }
}

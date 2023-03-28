package ua.bedwars.Util;

import jdk.nashorn.internal.ir.Block;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import ua.bedwars.Configs.BlocksConfig;

import java.util.ArrayList;
import java.util.List;

public class Util {
    private static List<Thread> threads = new ArrayList<>();

    public static World defaultMap = Bukkit.createWorld(new WorldCreator("world_bedwars"));

    public static List<Thread> getThreads() {
        return threads;
    }
}

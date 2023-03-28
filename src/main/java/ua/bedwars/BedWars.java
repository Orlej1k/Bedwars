package ua.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ua.bedwars.Commands.ArenaCMD;
import ua.bedwars.Configs.BlocksConfig;
import ua.bedwars.Configs.LocationsConfig;
import ua.bedwars.Configs.OtherConfig;
import ua.bedwars.Configs.SpawnerLocationsConfig;
import ua.bedwars.Events.BoardListener;
import ua.bedwars.Events.GameEvents;
import ua.bedwars.Gui.GuiEvent;
import ua.bedwars.Util.Board;
import ua.bedwars.Util.Util;

import java.util.ArrayList;
import java.util.List;

public final class BedWars extends JavaPlugin {

    private static BedWars instance;

    @Override
    public void onEnable() {
        instance = this;
        createConfigs();
        getCommand("bw").setExecutor(new ArenaCMD());
        Bukkit.getPluginManager().registerEvents(new GameEvents(), this);
        Bukkit.getPluginManager().registerEvents(new BoardListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuiEvent(), this);
    }

    private void createConfigs(){
        saveResource("blocks.yml", false);
        BlocksConfig.setData(new BlocksConfig("blocks.yml"));
        BlocksConfig.getData().save();
        saveResource("locations.yml", false);
        LocationsConfig.setData(new LocationsConfig("locations.yml"));
        LocationsConfig.getData().save();
        saveResource("spawnerLocations.yml", false);
        SpawnerLocationsConfig.setData(new SpawnerLocationsConfig("spawnerLocations.yml"));
        SpawnerLocationsConfig.getData().save();
        saveResource("other.yml", false);
        OtherConfig.setData(new OtherConfig("other.yml"));
        OtherConfig.getData().save();
    }

    @Override
    public void onDisable() {
        for(Arena arena : Arena.arenas){
            Board.removeScoreboard(arena.getPlayers());
            for(Player player : arena.getPlayers()) {
                player.teleport(arena.getPlayersLocation().get(player));
                player.getInventory().setContents(arena.getPlayersInventories().get(player));
                player.setGameMode(GameMode.SURVIVAL);
            }
            for(Team team : arena.getTeamsList()){
                team.getPlayers().clear();
                team.getBed().setBroken(false);
            }
            Bukkit.unloadWorld(arena.getArenaName(), false);
            arena.getTeamsList().clear();
            Arena.arenas.remove(arena);
        }
        for(Thread thread : Util.getThreads()){
                thread.interrupt();
        }
    }

    public static BedWars getInstance() {
        return instance;
    }
}

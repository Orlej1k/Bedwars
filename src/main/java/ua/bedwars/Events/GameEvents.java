package ua.bedwars.Events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ua.bedwars.Arena;
import ua.bedwars.BedWars;
import ua.bedwars.Configs.BlocksConfig;
import ua.bedwars.Configs.OtherConfig;
import ua.bedwars.Team;

import java.util.List;

public class GameEvents implements Listener {

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
                for(Arena arena : Arena.arenas){
                    if(arena.getPlayers().contains(e.getPlayer())){
                        List<String> blocks = BlocksConfig.getData().getConfig().getStringList("canBreak");
                        if (!e.getPlayer().isOp()) {
                            if (!blocks.contains(e.getBlock().getType().toString())) {
                                e.setCancelled(true);
                            }
                    }
                        if (e.getBlock().getType() == Material.RED_BED) {
                            Location location = e.getBlock().getLocation();
                            for (Team team : arena.getTeamsList()) {
                                if (team.getBed().getLocationBlock1().equals(location)
                                        || team.getBed().getLocationBlock2().equals(location) && !team.getBed().isBroken()
                                        && arena.getPlayers().contains(e.getPlayer())) {
                                    if(team.getPlayers().contains(e.getPlayer())){
                                        e.setCancelled(true);
                                        return;
                                    }
                                    for (Player player : team.getPlayers()) {
                                        player.sendTitle("§cВаше ліжко зломане!", null);
                                    }
                                    team.getBed().setBroken(true);
                                    e.setDropItems(false);
                                }
                            }
                        }
                    }
            }
    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent e) {
        for(Arena arena : Arena.arenas){
            if(arena.getPlayers().contains(e.getPlayer())){
                List<String> blocks = BlocksConfig.getData().getConfig().getStringList("NoPlace");
                if (!e.getPlayer().isOp()) {
                    if (blocks.contains(e.getBlock().getType().toString())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
            for (Block block : e.blockList()){
                if(block.getType() != Material.RED_BED){
                    block.setType(Material.AIR);
                }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void death(EntityDamageEvent e){
        try {
            for (Arena arena : Arena.arenas) {
                if (e.getEntity() instanceof Player) {
                    Player player = (Player) e.getEntity();
                    if (arena.getPlayers().contains(player)) {
                        for (Team team : arena.getTeamsList()) {
                            if (team.getPlayers().contains(player)) {
                                if (player.getHealth() <= e.getFinalDamage()) {
                                    player.getInventory().clear();
                                    player.setGameMode(GameMode.SPECTATOR);
                                    player.teleport(team.getSpawnLocation());
                                    if (!team.getBed().isBroken()) {
                                        player.setGameMode(GameMode.SPECTATOR);
                                        player.teleport(team.getSpawnLocation());
                                        new BukkitRunnable() {
                                            int counter = OtherConfig.getData().getConfig().getInt("spawnTimeAfterDeath");

                                            @Override
                                            public void run() {
                                                if (counter <= 0) {
                                                    player.teleport(team.getSpawnLocation());
                                                    player.setGameMode(GameMode.SURVIVAL);
                                                    player.setHealth(player.getMaxHealth());
                                                    this.cancel();
                                                    return;
                                                }
                                                player.sendTitle("§cВи будете заспавнені через:", "§e" + counter + "§f секунд");
                                                counter--;
                                            }
                                        }.runTaskTimer(BedWars.getPlugin(BedWars.class), 0L, 20L);
                                    } else {
                                        team.getPlayers().remove(player);
                                        Arena.checkEnd();
                                    }
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception exception){
            System.out.println("Error in EntityDamageEvent");
        }
    }

    @EventHandler
    public void foodLose(FoodLevelChangeEvent e) {
        for (Arena arena : Arena.arenas) {
            if (e.getEntity() instanceof Player && arena.getPlayers().contains((Player) e.getEntity())) {
                    e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void quit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        for(Arena arena : Arena.arenas){
            if(arena.getPlayers().contains(player)){
                for(Team team : arena.getTeamsList()){
                    if(team.getPlayers().contains(player)){
                        team.getPlayers().remove(player);
                        Arena.checkEnd();
                    }
                }
                arena.playerQuit(player);
            }
        }
    }
    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        for(Arena arena : Arena.arenas){
            if(arena.getPlayers().contains(player)) {
                String prefix = "";
                for (Team team : arena.getTeamsList()) {
                    if(team.getPlayers().contains(player)){
                        switch(team.getTeamName()){
                            case RED:
                                prefix = "§cRed";
                                break;
                            case BLUE:
                                prefix = "§9Blue";
                                break;
                            case GREEN:
                                prefix = "§aGreen";
                                break;
                            case YELLOW:
                                prefix = "§aYellow";
                                break;
                        }
                        for(Player p : arena.getPlayers()){
                            p.sendMessage("§l" + prefix + " §f" + player.getDisplayName() + ": " + e.getMessage());
                        }
                        e.setCancelled(true);
                        return;
                    }
                }
                for(Player p : arena.getPlayers()){
                    p.sendMessage("§7§lSpectator" + " §f" + p.getDisplayName() + ": " + e.getMessage());
                }
                e.setCancelled(true);
            }
        }
    }

}

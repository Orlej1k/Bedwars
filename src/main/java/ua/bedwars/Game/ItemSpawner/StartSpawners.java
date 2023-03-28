package ua.bedwars.Game.ItemSpawner;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.scheduler.BukkitRunnable;
import ua.bedwars.Arena;
import ua.bedwars.BedWars;
import ua.bedwars.Util.Util;

public class StartSpawners {
    public static void spawnersDrop(Arena arena){
        for (Spawners spawners : Spawners.values()) {
            for (Spawner spawner : spawners.getSpawners()) {
                ArmorStand hologram = (ArmorStand) arena.getArenaWorld().spawnEntity(spawner.getLocation().add(0, 0, 0), EntityType.ARMOR_STAND);
                hologram.setVisible(false);
                hologram.setCustomNameVisible(true);
                hologram.setGravity(false);
                switch(spawner.getDropItem()){
                    case IRON:
                        hologram.setCustomName("§fЗалізний рудник");
                        break;
                    case GOLD:
                        hologram.setCustomName("§eЗолотий рудник");
                        break;
                    case DIAMOND:
                        hologram.setCustomName("§bДіамантовий рудник");
                        break;
                    case EMERALD:
                        hologram.setCustomName("§aСмарагдовий рудник");
                        break;
                }
                startSpawnDrop(arena, spawner);
            }
        }
    }

    private static void startSpawnDrop(Arena arena, Spawner spawner) {
        if (BedWars.getInstance().isEnabled()) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try{
                        while (Arena.arenas.contains(arena)) {
                            int spawnTime = spawner.getSpawnTime();
                            while (spawnTime > 0) {
                                spawnTime--;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    break;
                                }
                            }
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < spawner.getItemsDrop(); i++) {
                                        arena.getArenaWorld().dropItemNaturally(spawner.getLocation(), spawner.getDropItem().getItemStack());
                                    }
                                }
                            }.runTask(BedWars.getPlugin(BedWars.class));
                        }
                    } catch (IllegalPluginAccessException exception){
                            return;
                        }
                }
                }.runTaskAsynchronously(BedWars.getPlugin(BedWars.class));
        }
    }
}

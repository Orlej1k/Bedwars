package ua.bedwars.Gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ua.bedwars.Arena;

import java.util.ArrayList;
import java.util.List;

public class BWList {
    public static void openGui(Player player){
        int size = 9;
        while(Arena.arenas.size() > size) size += 9;
        Inventory gui = Bukkit.createInventory(player, size, "§c§lList");
        for(Arena arena : Arena.arenas){
            if(!arena.isStarted()) {
                ItemStack bw = new ItemStack(Material.RED_BED);
                ItemMeta meta = bw.getItemMeta();
                meta.setDisplayName(arena.getArenaName());
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add("§fМінімальна к-сть гравців: §e" + arena.getMinPlayers());
                lore.add("§fМаксимальна к-сть гравців: §e" + arena.getMaxPlayers());
                lore.add("§fГравців: §e" + arena.getPlayers().size());
                meta.setLore(lore);
                bw.setItemMeta(meta);
                gui.addItem(bw);
            }
        }
        player.openInventory(gui);
    }
}

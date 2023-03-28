package ua.bedwars.Gui;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiEvent implements Listener {
    @EventHandler
    public void clickEvent(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase("§c§lList")){
            if(e.getCurrentItem() != null) {
                Bukkit.getServer().dispatchCommand(e.getWhoClicked(), "bw join " + e.getCurrentItem().getItemMeta().getDisplayName());
                e.getWhoClicked().closeInventory();
                e.setCancelled(true);
            }
        }
    }
}

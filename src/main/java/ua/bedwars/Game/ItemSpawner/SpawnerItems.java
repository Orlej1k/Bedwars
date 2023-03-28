package ua.bedwars.Game.ItemSpawner;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public enum SpawnerItems {
    IRON(new ItemStack(Material.IRON_INGOT)),
    GOLD(new ItemStack(Material.GOLD_INGOT)),
    DIAMOND(new ItemStack(Material.DIAMOND)),
    EMERALD(new ItemStack(Material.EMERALD));

    private ItemStack itemStack;
    SpawnerItems(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    static {
        ItemMeta ironMeta = IRON.itemStack.getItemMeta();
        ironMeta.setDisplayName("§fЗалізний злиток");
        IRON.itemStack.setItemMeta(ironMeta);

        ItemMeta goldMeta = GOLD.itemStack.getItemMeta();
        goldMeta.setDisplayName("§eЗолотий злиток");
        GOLD.itemStack.setItemMeta(goldMeta);

        ItemMeta diamondMeta = DIAMOND.itemStack.getItemMeta();
        diamondMeta.setDisplayName("§bДіамант");
        DIAMOND.itemStack.setItemMeta(diamondMeta);

        ItemMeta emeraldMeta = EMERALD.itemStack.getItemMeta();
        emeraldMeta.setDisplayName("§aСмарагд");
        EMERALD.itemStack.setItemMeta(emeraldMeta);
    }

}

package ua.bedwars.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.bedwars.Arena;
import ua.bedwars.Game.ItemSpawner.SpawnerItems;
import ua.bedwars.Gui.BWList;
import ua.bedwars.Util.Util;

import java.util.ConcurrentModificationException;

public class ArenaCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            if(strings.length == 0){
                commandSender.sendMessage("§aCommands:");
                commandSender.sendMessage("§e/bw §clist");
                commandSender.sendMessage("§e/bw §ccreate §e(Minimum Players) (Maximum Players) (Timer)");
                commandSender.sendMessage("§e/bw §cjoin §e(Arena)");
                commandSender.sendMessage("§e/bw §cquit");
                commandSender.sendMessage("§e/bw §ctp");
                commandSender.sendMessage("§e/bw §cdelete §e(Arena)");
                commandSender.sendMessage("§e/bw §cgetItem §e(ItemName)");
                return true;
            }
            if (strings[0].equalsIgnoreCase("create")) {
                if (commandSender.hasPermission("bw.create") && commandSender instanceof Player) {
                    if (strings.length == 4) {
                        Location lobbyLocation = ((Player) commandSender).getLocation();
                        Arena arena = new Arena(lobbyLocation, Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
                        arena.getTeamsList().clear();
                        Arena.arenas.add(arena);
                        commandSender.sendMessage("§eАрена §aбула створена(§c" + arena.getArenaName() + "§a)");
                    } else commandSender.sendMessage("§cНеправильно введена команда(§e/bw create (Minimum Players) (Maximum Players) (Timer)§c)");
                } else commandSender.sendMessage("§cУ вас немає прав або ви не гравець");
            }
            if (strings[0].equalsIgnoreCase("join")) {
                if (strings.length == 2) {
                    if (commandSender instanceof Player && commandSender.hasPermission("bw.join")) {
                            for (Arena arena : Arena.arenas) {
                                if (arena.getArenaName().equals(strings[1]) && !arena.isStarted()) {
                                    arena.playerJoin((Player) commandSender);
                                    return true;
                                }
                            }
                        commandSender.sendMessage("§cТакої гри немає або вона вже розпочата");
                    } else commandSender.sendMessage("§cУ вас немає прав або ви не гравець");
                } else commandSender.sendMessage("§cНеправильно введена команда(§e/bw join (Arena name)§c)");
            }
            if(strings[0].equalsIgnoreCase("quit")){
                        if(commandSender instanceof Player){
                            Player player = (Player) commandSender;
                            for(Arena arena : Arena.arenas){
                                if(arena.getPlayers().contains(player)){
                                    Arena.checkEnd();
                                    arena.playerQuit(player);
                                    return true;
                                }
                            }
                            commandSender.sendMessage("§cВи не є учасником гри");
                        }
                    }
            if(strings[0].equalsIgnoreCase("tp")){
                    if(strings.length == 1) {
                        if (commandSender instanceof Player && commandSender.isOp()) {
                            ((Player) commandSender).teleport(new Location(Util.defaultMap, 9, 51, 7));
                        }
                    }
            }
            if (strings[0].equalsIgnoreCase("delete")) {
                    if(strings.length == 2) {
                        if (commandSender.hasPermission("bw.delete")) {
                            try {
                                for (Arena arena : Arena.arenas) {
                                    if (strings[1].equals(arena.getArenaName())) {
                                        arena.setStopped(true);
                                        arena.stopGame();
                                        commandSender.sendMessage("§cВи виключили гру " + arena.getArenaName());
                                    }
                                }
                            } catch(ConcurrentModificationException e){
                                return true;
                            }
                        } else commandSender.sendMessage("§cУ вас немає прав");
                    }
                }
            if(strings[0].equalsIgnoreCase("getItem")) {
                if (strings.length == 2) {
                    if (commandSender instanceof Player && commandSender.hasPermission("bw.getItem")) {
                        for (SpawnerItems item : SpawnerItems.values()) {
                            System.out.println(item.name());
                            if (strings[1].equals(item.name())) {
                                ((Player) commandSender).getInventory().addItem(item.getItemStack());
                            }
                        }
                    } else commandSender.sendMessage("§cУ вас немає прав або ви не гравець");
                }
            }
            if(strings[0].equalsIgnoreCase("list")){
                if(strings.length == 1){
                    if(commandSender instanceof Player && commandSender.hasPermission("bw.list")) BWList.openGui((Player) commandSender);
                    else commandSender.sendMessage("§cУ вас немає прав або ви не гравець");
                }
            }
            return true;
        }
}

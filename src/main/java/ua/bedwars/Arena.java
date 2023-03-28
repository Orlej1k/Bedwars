package ua.bedwars;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ua.bedwars.Configs.LocationsConfig;
import ua.bedwars.Game.Bed;
import ua.bedwars.Game.ItemSpawner.StartSpawners;
import ua.bedwars.Util.Board;
import ua.bedwars.Util.Util;
import ua.bedwars.Util.WorldCopy;

import java.util.*;

public class Arena {

    public static List<Arena> arenas = new ArrayList<>();

    private List<Player> players = new ArrayList<>();
    private Map<Player, Location> playersLocation = new HashMap<>();
    private Map<Player, ItemStack[]> playersInventories = new HashMap<>();

    private int minPlayers, maxPlayers, startTime, teams;
    private Location lobbyLocation;

    private World arenaWorld;

    private List<Team> teamsList;

    private String arenaName;

    private boolean isStarted;
    private boolean stopped;
    private static int arenaID = 0;

    public Arena(Location lobbyLocation, int minPlayers, int maxPlayers, int startTime){
        this.lobbyLocation = lobbyLocation;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.startTime = startTime;
        this.teams = 4;
        this.teamsList = new ArrayList<>();


        arenaID++;
        this.arenaName = "BW-" + arenaID;

        WorldCopy.copyWorld(Util.defaultMap, arenaName);
        WorldCopy.clearItems(Bukkit.getWorld(arenaName));
        this.arenaWorld = Bukkit.getWorld(arenaName);
    }

    public void playerJoin(Player player){
        for(Arena arena : Arena.arenas){
            if(arena.getPlayers().contains(player)) {
                player.sendMessage("§cВи вже в грі §e" + arena.getArenaName() + "(§c/bw quit, щоб вийти§e)");
                return;
            }
        }
        if(!players.contains(player)) {
            if(players.size() < maxPlayers) {
                players.add(player);
                playersLocation.put(player, player.getLocation());
                playersInventories.put(player, player.getInventory().getContents());
                player.getInventory().clear();
                player.setGameMode(GameMode.ADVENTURE);
                player.setFoodLevel(20);
                player.teleport(lobbyLocation);
                sendArenaMessage("§f" + player.getDisplayName() + "§a увійшов в гру");
                if (players.size() >= minPlayers) {
                    startTimeForGame();
                }
            } else player.sendMessage("§cВ грі вже максимальна кількість гравців");
        } else player.sendMessage("§cВи вже увійшли");
    }

    public void playerQuit(Player player){
        if(players.contains(player)) {
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(playersLocation.get(player));
            System.out.println(Arrays.toString(playersInventories.get(player)));
            player.getInventory().setContents(playersInventories.get(player));
            player.setHealth(player.getMaxHealth());
            sendArenaMessage("§f" + player.getDisplayName() + "§c вийшов з гри");
            playersLocation.remove(player);
            players.remove(player);
            playersInventories.remove(player);
            Board.removeScoreboard(player);
        }
    }

    private void startTimeForGame(){
        sendArenaMessage("§fГравців в лоббі: §e" + players.size());
        new BukkitRunnable() {
            final int defaultStartTime = startTime;
            @Override
            public void run() {
                if (players.size() < minPlayers || stopped) {
                    startTime = defaultStartTime;
                    this.cancel();
                    return;
                }
                if (startTime <= 0) {
                    startTime = defaultStartTime;
                    startGame();
                    this.cancel();
                    return;
                }
                sendArenaTitle("§fЗалишилось: §e" + startTime + " секунд");
                startTime--;
            }
        }.runTaskTimer(BedWars.getPlugin(BedWars.class), 0L, 20L);
    }

    public void sendArenaTitle(String message){
        for(Player player : players){
            player.sendTitle(message, null);
        }
    }

    public void startGame(){
        for(Player player : players){
            player.setGameMode(GameMode.SURVIVAL);
        }
        sendArenaTitle("§cГру розпочато!");
        shuffleTeams();
        spawnTeams();
        Board.createScoreboard();
        StartSpawners.spawnersDrop(this);
        isStarted = true;
    }

    private void shuffleTeams(){
        FileConfiguration config = LocationsConfig.getData().getConfig();
        Team Red = new Team(new Location(arenaWorld,config.getInt("Teams.Red.SpawnLocation.X"), config.getInt("Teams.Red.SpawnLocation.Y"), config.getInt("Teams.Red.SpawnLocation.Z")), new Bed(new Location(arenaWorld, config.getInt("Teams.Red.BedBlock1Location.X"), config.getInt("Teams.Red.BedBlock1Location.Y"), config.getInt("Teams.Red.BedBlock1Location.Z")), new Location(arenaWorld, config.getInt("Teams.Red.BedBlock2Location.X"), config.getInt("Teams.Red.BedBlock2Location.Y"), config.getInt("Teams.Red.BedBlock2Location.Z"))), TeamsName.RED ,new ArrayList<>());
        Team Blue = new Team(new Location(arenaWorld,config.getInt("Teams.Blue.SpawnLocation.X"), config.getInt("Teams.Blue.SpawnLocation.Y"), config.getInt("Teams.Blue.SpawnLocation.Z")), new Bed(new Location(arenaWorld, config.getInt("Teams.Blue.BedBlock1Location.X"), config.getInt("Teams.Blue.BedBlock1Location.Y"), config.getInt("Teams.Blue.BedBlock1Location.Z")), new Location(arenaWorld, config.getInt("Teams.Blue.BedBlock2Location.X"), config.getInt("Teams.Blue.BedBlock2Location.Y"), config.getInt("Teams.Blue.BedBlock2Location.Z"))), TeamsName.BLUE ,new ArrayList<>());
        Team Green = new Team(new Location(arenaWorld,config.getInt("Teams.Green.SpawnLocation.X"), config.getInt("Teams.Green.SpawnLocation.Y"), config.getInt("Teams.Green.SpawnLocation.Z")), new Bed(new Location(arenaWorld, config.getInt("Teams.Green.BedBlock1Location.X"), config.getInt("Teams.Green.BedBlock1Location.Y"), config.getInt("Teams.Green.BedBlock1Location.Z")), new Location(arenaWorld, config.getInt("Teams.Green.BedBlock2Location.X"), config.getInt("Teams.Green.BedBlock1Location.Y"), config.getInt("Teams.Green.BedBlock1Location.Z"))), TeamsName.GREEN ,new ArrayList<>());
        Team Yellow = new Team(new Location(arenaWorld,config.getInt("Teams.Yellow.SpawnLocation.X"), config.getInt("Teams.Yellow.SpawnLocation.Y"), config.getInt("Teams.Yellow.SpawnLocation.Z")), new Bed(new Location(arenaWorld, config.getInt("Teams.Yellow.BedBlock1Location.X"), config.getInt("Teams.Yellow.BedBlock1Location.Y"), config.getInt("Teams.Yellow.BedBlock1Location.Z")), new Location(arenaWorld, config.getInt("Teams.Yellow.BedBlock2Location.X"), config.getInt("Teams.Yellow.BedBlock1Location.Y"), config.getInt("Teams.Yellow.BedBlock1Location.Z"))), TeamsName.YELLOW ,new ArrayList<>());
        int counter = 0;
        for(Player player : players){
            if(counter <= 0) counter = teams;
            switch(counter){
                case 4:
                    Red.getPlayers().add(player);
                    break;
                case 3:
                    Blue.getPlayers().add(player);
                    break;
                case 2:
                    Green.getPlayers().add(player);
                    break;
                case 1:
                    Yellow.getPlayers().add(player);
                    break;
            }
            counter--;
        }
        teamsList.add(Red);
        teamsList.add(Blue);
        teamsList.add(Green);
        teamsList.add(Yellow);
    }
    private void spawnTeams(){
        for(Team team : teamsList){
            for(Player player : team.getPlayers()){
                player.setBedSpawnLocation(team.getSpawnLocation(), true);
                player.teleport(team.getSpawnLocation());
            }
        }
    }

    public void stopGame(){
        this.sendArenaTitle("§cАдміністратор виключив гру!");
        Board.removeScoreboard(players);
        for(Player player : players){
            player.teleport(playersLocation.get(player));
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().setContents(playersInventories.get(player));
        }
        teamsList.clear();
        Bukkit.unloadWorld(this.getArenaName(), false);
        isStarted = false;
        arenas.remove(this);
    }

    public void endGame(){
        Board.removeScoreboard(players);
        for(Player player : players){
            player.setHealth(player.getMaxHealth());
            player.teleport(playersLocation.get(player));
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().setContents(playersInventories.get(player));
        }
        teamsList.clear();
        Bukkit.unloadWorld(this.getArenaName(), false);
        isStarted = false;
        arenas.remove(this);
        Arena.setArenaID(Arena.getArenaID() - 1);
    }

    public static void checkEnd(){
        try {
            for (Arena arena : Arena.arenas) {
                    int teamsLive = 0;
                    Team winner = null;
                    for (Team team : arena.getTeamsList()) {
                        System.out.println(arena.getArenaName() + team.getTeamName().name());
                        if (team.getPlayers().size() > 0) {
                            System.out.println("True");
                            teamsLive++;
                            winner = team;
                        }
                    }
                    if (teamsLive == 1) {
                        Bukkit.broadcastMessage("§c<§e§l" + arena.getArenaName() + "§c> " + "§cКоманда §f" + winner.getTeamName() + "§c виграла!");
                        new BukkitRunnable() {
                            int temp = 5;
                            @Override
                            public void run() {
                                for(Player player : arena.getPlayers()){
                                    player.playSound(player.getLocation(), Sound.MUSIC_GAME, 20, 20);
                                }
                                if (temp <= 0) {
                                    arena.endGame();
                                    this.cancel();
                                    return;
                                }
                                temp--;
                            }
                        }.runTaskTimer(BedWars.getPlugin(BedWars.class), 0L, 20L);
                    }
            }
        } catch(Exception ex){
            return;
        }
    }

    public void sendArenaMessage(String message){
        for(Player player : players){
            player.sendMessage(message);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<Player, Location> getPlayersLocation() {
        return playersLocation;
    }

    public List<Team> getTeamsList() {
        return teamsList;
    }

    public String getArenaName() {
        return arenaName;
    }

    public World getArenaWorld() {
        return arenaWorld;
    }

    public static int getArenaID() {
        return arenaID;
    }

    public static void setArenaID(int arenaID) {
        Arena.arenaID = arenaID;
    }
    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public Map<Player, ItemStack[]> getPlayersInventories() {
        return playersInventories;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}

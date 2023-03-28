package ua.bedwars;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ua.bedwars.Game.Bed;

import java.util.List;

public class Team {
    private Location spawnLocation;
    private Bed bed;
    private TeamsName teamName;
    private List<Player> players;

    public Team(Location spawnLocation, Bed bed, TeamsName team, List<Player> players) {
        this.spawnLocation = spawnLocation;
        this.bed = bed;
        this.teamName = team;
        this.players = players;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public Bed getBed() {
        return bed;
    }

    public TeamsName getTeamName() {
        return teamName;
    }

    public List<Player> getPlayers() {
        return players;
    }
}

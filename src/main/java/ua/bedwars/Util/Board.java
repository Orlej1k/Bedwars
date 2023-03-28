package ua.bedwars.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import ua.bedwars.Arena;
import ua.bedwars.Team;

import java.util.List;

public class Board {

    public static void createScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective obj = scoreboard.registerNewObjective("bedWarsBoard", "dummy", "§9 <<§c§lBedWars§9>> ");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore("");
        score.setScore(6);
        for (Arena arena : Arena.arenas) {
            for (Team team : arena.getTeamsList()) {
                Score scoreTeam;
                String smile;
                if (!team.getBed().isBroken()) smile = "§a☑";
                else {
                    smile = "§c❌";
                }
                switch (team.getTeamName()) {
                    case RED:
                        scoreTeam = obj.getScore("§cRed: " + smile + " §f(§c" + team.getPlayers().size() + "§f)");
                        scoreTeam.setScore(5);
                        break;
                    case BLUE:
                        scoreTeam = obj.getScore("§9Blue: " + smile + " §f(§9" + team.getPlayers().size() + "§f)");
                        scoreTeam.setScore(4);
                        break;
                    case GREEN:
                        scoreTeam = obj.getScore("§aGreen: " + smile + " §f(§a" + team.getPlayers().size() + "§f)");
                        scoreTeam.setScore(3);
                        break;
                    case YELLOW:
                        scoreTeam = obj.getScore("§eYellow: " + smile + " §f(§e" + team.getPlayers().size() + "§f)");
                        scoreTeam.setScore(2);
                        break;
                }
            }

            Score score6 = obj.getScore("");
            score6.setScore(1);
            for (Player player : arena.getPlayers()) {
                player.setScoreboard(scoreboard);
            }
        }
    }
    public static void removeScoreboard(List<Player> players){
        for(Player player : players){
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }
    public static void removeScoreboard(Player player){
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

}

package ua.bedwars.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ua.bedwars.Util.Board;

public class BoardListener implements Listener {

    @EventHandler
    public void updateScoreboard(PlayerMoveEvent e){
            Board.createScoreboard();
    }

}

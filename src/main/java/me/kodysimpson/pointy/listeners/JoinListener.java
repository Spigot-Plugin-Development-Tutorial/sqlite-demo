package me.kodysimpson.pointy.listeners;

import me.kodysimpson.pointy.Pointy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class JoinListener implements Listener {

    private final Pointy pointyPlugin;

    public JoinListener(Pointy pointyPlugin) {
        this.pointyPlugin = pointyPlugin;
    }

    @EventHandler
    public void inJoin(PlayerJoinEvent e) throws SQLException {

        //if the player is new, add them to the database
        if (!e.getPlayer().hasPlayedBefore()){
            //add the player to the database
            this.pointyPlugin.getPointsDatabase().addPlayer(e.getPlayer());
        }

    }

}

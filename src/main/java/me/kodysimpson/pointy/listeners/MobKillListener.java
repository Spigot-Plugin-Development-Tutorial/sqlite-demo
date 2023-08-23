package me.kodysimpson.pointy.listeners;

import me.kodysimpson.pointy.Pointy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.sql.SQLException;

public class MobKillListener implements Listener {

    private final Pointy pointyPlugin;

    public MobKillListener(Pointy pointyPlugin) {
        this.pointyPlugin = pointyPlugin;
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent e) throws SQLException {

        if (e.getEntity().getKiller() == null) return;

        Player killer = e.getEntity().getKiller();

        //get the player's current points
        int points = pointyPlugin.getPointsDatabase().getPlayerPoints(killer);
        //add a random positive number between 1 and 10
        points += (int) (Math.random() * 10) + 1;

        //update the player's points in the DB
        pointyPlugin.getPointsDatabase().updatePlayerPoints(killer, points);

        killer.sendMessage(ChatColor.GREEN + "+" + points + " pts");
    }

}

package me.kodysimpson.pointy;

import me.kodysimpson.pointy.commands.GetPoints;
import me.kodysimpson.pointy.commands.SetPoints;
import me.kodysimpson.pointy.database.PointsDatabase;
import me.kodysimpson.pointy.listeners.JoinListener;
import me.kodysimpson.pointy.listeners.MobKillListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Pointy extends JavaPlugin {

    private PointsDatabase pointsDatabase;

    @Override
    public void onEnable() {
        try {
            // Ensure the plugin's data folder exists
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }

            pointsDatabase = new PointsDatabase(getDataFolder().getAbsolutePath() + "/pointy.db");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to database! " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }

        getCommand("setpoints").setExecutor(new SetPoints(this));
        getCommand("points").setExecutor(new GetPoints(this));
        Bukkit.getPluginManager().registerEvents(new MobKillListener(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
    }

    @Override
    public void onDisable() {
        try {
            pointsDatabase.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PointsDatabase getPointsDatabase() {
        return pointsDatabase;
    }
}

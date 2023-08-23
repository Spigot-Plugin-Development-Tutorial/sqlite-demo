package me.kodysimpson.pointy.commands;

import me.kodysimpson.pointy.Pointy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class SetPoints implements CommandExecutor {

    private final Pointy pointyPlugin;

    public SetPoints(Pointy pointyPlugin) {
        this.pointyPlugin = pointyPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length == 0){
            sender.sendMessage(ChatColor.YELLOW + "Run it like /setpoints <player> <amount>");
            return true;
        }

        //Get the player
        String playerName = args[0];
        Player player = Bukkit.getPlayer(playerName);
        if (player == null){
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }

        //Get the amount
        int amount = 0;
        try {
            amount = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            sender.sendMessage(ChatColor.RED + "An invalid amount was provided. Also make sure that the amount is a whole number.");
            return true;
        }

        //Update the player's points in the DB
        try {
            pointyPlugin.getPointsDatabase().updatePlayerPoints(player, amount);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully updated &e" + player.getName() + "&a's points to &e" + amount + "&a!"));
        } catch (SQLException e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "An error occurred while updating the player's points. Try again later.");
            return true;
        }

        return true;
    }
}

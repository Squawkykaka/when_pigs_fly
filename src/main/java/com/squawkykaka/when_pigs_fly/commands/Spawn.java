package com.squawkykaka.when_pigs_fly.commands;

import com.squawkykaka.when_pigs_fly.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Spawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            Msg.send(sender,"&cOnly players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        player.teleport(new Location(Bukkit.getWorld("world"), -12.54, 75.00, -103.70));
        return true;
    }
}

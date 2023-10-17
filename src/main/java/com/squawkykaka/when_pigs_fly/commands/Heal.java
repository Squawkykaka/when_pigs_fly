package com.squawkykaka.when_pigs_fly.commands;

import com.squawkykaka.when_pigs_fly.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Heal {
    public Heal() {
        new CommandBase("heal", true) {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments) {
                Player player = (Player) sender;
                player.setHealth(20);
                return true;
            }

            @Override
            public @NotNull String getUsage() {
                return "/heal";
            }
        }.enableDelay(5);
    }
}

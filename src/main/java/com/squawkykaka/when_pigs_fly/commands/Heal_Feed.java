package com.squawkykaka.when_pigs_fly.commands;

import com.squawkykaka.when_pigs_fly.util.CommandBase;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Heal_Feed {
    public Heal_Feed() {
        new CommandBase("heal", true) {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments) {
                Player player = (Player) sender;
                player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
                return true;
            }

            @Override
            public @NotNull String getUsage() {
                return "/heal";
            }
        }.enableDelay(5);

        new CommandBase("feed", true) {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments) {
                Player player = (Player) sender;
                player.setFoodLevel(20);
                return true;
            }

            @Override
            public @NotNull String getUsage() {
                return "/feed";
            }
        }.enableDelay(5);
    }
}

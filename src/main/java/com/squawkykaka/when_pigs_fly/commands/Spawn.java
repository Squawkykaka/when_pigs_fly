package com.squawkykaka.when_pigs_fly.commands;

import com.squawkykaka.when_pigs_fly.util.CommandBase;
import com.squawkykaka.when_pigs_fly.util.EventUtil;
import com.squawkykaka.when_pigs_fly.util.Msg;
import com.squawkykaka.when_pigs_fly.WhenPigsFly;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Spawn {
    private Location spawn = null;

    public Spawn(WhenPigsFly plugin) {
        FileConfiguration config = plugin.getConfig();
        String worldName = config.getString("spawn.world");
        if (worldName == null) {
            Bukkit.getLogger().warning("spawn.world does not exist within config.yml");
            return;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            Bukkit.getLogger().severe("World \"" + worldName + "\" does not exist.");
            return;
        }

        int x = config.getInt("spawn.x");
        int y = config.getInt("spawn.y");
        int z = config.getInt("spawn.z");
        float yaw = (float) config.getDouble("spawn.yaw");
        float pitch = (float) config.getDouble("spawn.pitch");

        spawn = new Location(world, x, y, z, yaw, pitch);

        new CommandBase("setspawn", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;
                Location location = player.getLocation();

                config.set("spawn.world", location.getWorld().getName());
                config.set("spawn.x", location.getBlockX());
                config.set("spawn.y", location.getBlockY());
                config.set("spawn.z", location.getBlockZ());
                config.set("spawn.yaw", location.getYaw());
                config.set("spawn.pitch", location.getPitch());

                plugin.saveConfig();
                Msg.send(player, "New spawn point saved.");

                spawn = location;
                return true;
            }

            @Override
            public String getUsage() {
                return "/setspawn";
            }
        }.enableDelay(2).setPermission("spawn.set");

        new CommandBase("spawn", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;

                player.teleport(spawn);
                return true;
            }

            @Override
            public String getUsage() {
                return "/spawn";
            }
        };
    }
}

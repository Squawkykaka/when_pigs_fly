package com.squawkykaka.when_pigs_fly;

import com.squawkykaka.when_pigs_fly.WhenPigsFly;
import org.bukkit.Material;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PluginEvents implements Listener {
//    private final WhenPigsFly plugin;
//
//    public PluginEvents(WhenPigsFly plugin) {
//        this.plugin = plugin;
//    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null && event.getItem().getType() == Material.STICK) {
            // Move the player in the direction they are looking
            Vector direction = player.getLocation().getDirection().normalize(); // Get the normalized direction vector
            // direction.setY(0); // Ensure the player doesn't move up or down
            player.setVelocity(direction.multiply(3)); // Adjust the speed as needed
        }
    }
}
package com.squawkykaka.when_pigs_fly;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

public class PluginEvents implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null && event.getItem().getType() == Material.STICK) {
            // Move the player in the direction they are looking
            Vector direction = player.getLocation().getDirection().normalize(); // Get the normalized direction vector
            player.setVelocity(direction.multiply(3)); // Adjust the speed as needed
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(WhenPigsFly.getInstance(), () -> {
            event.getPlayer().sendMessage("Hello World");
        }, 1L);
    }
}
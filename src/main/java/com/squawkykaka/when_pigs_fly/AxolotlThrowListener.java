package com.squawkykaka.when_pigs_fly;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AxolotlThrowListener implements Listener {

    private final Plugin plugin;

    public AxolotlThrowListener(WhenPigsFly plugin) {
        this.plugin = plugin;
    }

//    Configuration config
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Configuration config = plugin.getConfig();

        // Check for the specific item in the player's hand (Blaze Rod named "Gun") and the permission
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD
                && event.getAction().isRightClick()
                && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName()
                .equals(config.getString("axolotl_throw.item_name"))
                && player.hasPermission("when_pigs_fly.axolotl_throw")) {
            // Create an Axolotl
            Axolotl axolotl = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Axolotl.class);
            axolotl.setVelocity(event.getPlayer().getLocation().getDirection().multiply(config.getInt("axolotl_throw.power"))); // Adjust the velocity as needed
            axolotl.setInvulnerable(true);

            // Schedule a task to check for collisions and explode if the Axolotl hits a block or the ground
            new BukkitRunnable() {
                @Override
                public void run() {
                    // Check if the Axolotl has hit a block (wall or the ground)
                    if (!axolotl.getLocation().getBlock().isPassable() || axolotl.isOnGround()) {
                        axolotl.getWorld().createExplosion(axolotl.getLocation(),
                                (float) config.getDouble("axolotl_throw.explosion_power")); // Customize the explosion power as needed
                        axolotl.remove(); // Remove the Axolotl after the explosion
                        cancel(); // Stop the task
                    }
                }
            }.runTaskTimer(plugin, 0L, 1L); // Check every tick
        }
    }
}

package com.squawkykaka.when_pigs_fly;

import org.bukkit.Material;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SpyglassListener implements Listener {

    private final Plugin plugin;

    public SpyglassListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Check for the specific item in the player's hand (Blaze Rod named "Gun") and the permission
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD
                && event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()
                && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Gun")
                && player.hasPermission("when_pigs_fly.axolotl_throw")) {
            // Create an Axolotl
            Axolotl axolotl = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Axolotl.class);
            axolotl.setVelocity(event.getPlayer().getLocation().getDirection().multiply(6)); // Adjust the velocity as needed
            axolotl.setInvulnerable(true);

            // Schedule a task to check for collisions and explode if the Axolotl hits a block or the ground
            new BukkitRunnable() {
                @Override
                public void run() {
                    // Check if the Axolotl has hit a block (wall or the ground)
                    if (!axolotl.getLocation().getBlock().isPassable() || axolotl.isOnGround()) {
                        axolotl.getWorld().createExplosion(axolotl.getLocation(), 10.0F); // Customize the explosion power as needed
                        axolotl.remove(); // Remove the Axolotl after the explosion
                        cancel(); // Stop the task
                    }
                }
            }.runTaskTimer(plugin, 0L, 1L); // Check every tick
        }
    }
}

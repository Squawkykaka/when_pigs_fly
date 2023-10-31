package com.squawkykaka.when_pigs_fly;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
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
                && event.getAction().isLeftClick()
                && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName()
                .equals(config.getString("axolotl_throw.item_name"))
                && player.hasPermission("when_pigs_fly.axolotl_throw")) {
            // Create an Axolotl
            for (int i = 0; i < event.getPlayer().getInventory().getItemInMainHand().getAmount(); i++) {
                Cat axolotl = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Cat.class);
                axolotl.setVelocity(event.getPlayer().getLocation().getDirection().multiply(config.getInt("axolotl_throw.power"))); // Adjust the velocity as needed
                axolotl.setInvulnerable(true);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        int radius = 1; // Define the radius to check for surrounding blocks
                        Location axolotlLocation = axolotl.getLocation();
                        World world = axolotl.getWorld();

                        for (int x = -radius; x <= radius; x++) {
                            for (int y = -radius; y <= radius; y++) {
                                for (int z = -radius; z <= radius; z++) {
                                    Location checkLocation = axolotlLocation.clone().add(x, y, z);
                                    if (!checkLocation.getBlock().isPassable() || axolotl.isOnGround()) {
                                        axolotl.getWorld().createExplosion(axolotl.getLocation(),
                                                (float) config.getDouble("axolotl_throw.explosion_power")); // Customize the explosion power as needed
                                        axolotl.remove(); // Remove the Axolotl after the explosion
                                        cancel(); // Stop the task
                                        return; // No need to continue checking
                                    }
                                }
                            }
                        }
                    }
                }.runTaskTimer(plugin, 0L, 1L); // Check every tick
            }
        }
    }
}

package com.squawkykaka.when_pigs_fly.commands;

import com.squawkykaka.when_pigs_fly.WhenPigsFly;
import com.squawkykaka.when_pigs_fly.util.CommandBase;
import com.squawkykaka.when_pigs_fly.util.Msg;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Menu implements Listener {
    private final String invName = "When pigs fly! Menu";


    public Menu(WhenPigsFly plugin) {
        new CommandBase("menu", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;

                Inventory inv = Bukkit.createInventory(player, 9 * 3, invName);

                inv.setItem(11, getItem(new ItemStack(Material.FEATHER), "&2&oToggle &3&lFly", "&a&lClick&r&8 to toggle"));
                inv.setItem(13, getItem(new ItemStack(Material.BLAZE_ROD), "&aGive Yourself &d&lAxolotl Shooter", "&a&lClick&r&8 to give"));
                inv.setItem(15, getItem(new ItemStack(Material.ENDER_PEARL), "&6Go to Spawn", "&a&lClick&r&8 to tp", "&3Teleports&r you to the servers spawn"));

                player.openInventory(inv);
                return true;
            }

            @Override
            public String getUsage() {
                return "/menu";
            }
        };

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        WhenPigsFly plugin = WhenPigsFly.getPlugin(WhenPigsFly.class);
        FileConfiguration config = plugin.getConfig();

        if (!event.getView().getTitle().equals(invName)) {
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        // Declare isFlying outside of the if statement
        boolean isFlying = player.getAllowFlight();

        // if the feather is clicked it toggles the flying state
        if (slot == 11) {
            // Toggle the flight state
            isFlying = !isFlying;
            player.setAllowFlight(isFlying);
            player.closeInventory();
        }

        if (slot == 13) {
            ItemStack gun = getItem(new ItemStack(Material.BLAZE_ROD), "Gun", "Shoots axolotls");
            ItemMeta meta = gun.getItemMeta();
            List<String> lores = new ArrayList<>();

            meta.setDisplayName("Gun");
            lores.add("Shoots axolotls");
            meta.setLore(lores);

            gun.setItemMeta(meta);
            player.getInventory().addItem(gun);
        }

        // TODO: slot 13 + 15. 13 is give a blaze rod named gun, 15 is run /spawn

        if (slot == 15) {

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
            Location spawn = new Location(world, x, y, z, yaw, pitch);

            player.teleport(spawn);
            Msg.send(player, "Teleported to spawn.");
        }
    }

    private ItemStack getItem(ItemStack item, String name, String... lore) {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> lores = new ArrayList<>();
        for (String s : lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(lores);

        item.setItemMeta(meta);
        return item;
    }
}

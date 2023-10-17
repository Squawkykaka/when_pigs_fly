package com.squawkykaka.when_pigs_fly;

import com.squawkykaka.when_pigs_fly.commands.Heal;
import com.squawkykaka.when_pigs_fly.commands.Spawn;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class WhenPigsFly extends JavaPlugin {

    private static WhenPigsFly instance;

    @Override
    public void onEnable() {
        instance = this; // Set the instance to the current plugin
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PluginEvents(), this);
        getServer().getPluginManager().registerEvents(new AxolotlThrowListener(this), this);

        Objects.requireNonNull(getCommand("spawn")).setExecutor(new Spawn());
        getLogger().info("Plugin enabled.");

        new Heal();
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

    // Create a method to obtain the plugin instance
    public static WhenPigsFly getInstance() {
        return instance;
    }
}

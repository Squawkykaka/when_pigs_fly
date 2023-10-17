package com.squawkykaka.when_pigs_fly;

import org.bukkit.plugin.java.JavaPlugin;

public final class WhenPigsFly extends JavaPlugin {

    private static WhenPigsFly instance;

    @Override
    public void onEnable() {
        instance = this; // Set the instance to the current plugin
        PluginEvents pluginEvents = new PluginEvents();
        getServer().getPluginManager().registerEvents(pluginEvents, this);
        getServer().getPluginManager().registerEvents(new SpyglassListener(this), this);

        getLogger().info("Plugin enabled.");
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

package com.squawkykaka.when_pigs_fly;

import com.squawkykaka.when_pigs_fly.commands.Heal_Feed;
import com.squawkykaka.when_pigs_fly.commands.Menu;
import com.squawkykaka.when_pigs_fly.commands.Spawn;
import com.squawkykaka.when_pigs_fly.util.EventUtil;
import com.squawkykaka.when_pigs_fly.util.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class WhenPigsFly extends JavaPlugin {

    private static WhenPigsFly instance;

    @Override
    public void onEnable() {
        instance = this; // Set the instance to the current plugin
        saveDefaultConfig();
        getLogger().info("------------------------");
        getLogger().info("---- Plugin Loading ----");

        EventUtil.register(new AxolotlThrowListener(this));
        EventUtil.register(new PluginEvents());
        new Heal_Feed();
        new Spawn(this);
        new Menu(this);
        Metrics metrics = new Metrics(this, 20271);
        getLogger().info("------------------------");
        getLogger().info("---- Plugin enabled ----");
        getLogger().info("------------------------");
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

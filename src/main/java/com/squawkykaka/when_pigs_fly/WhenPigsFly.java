package com.squawkykaka.when_pigs_fly;

import com.squawkykaka.when_pigs_fly.commands.Heal_Feed;
import com.squawkykaka.when_pigs_fly.commands.Menu;
import com.squawkykaka.when_pigs_fly.commands.Spawn;
import com.squawkykaka.when_pigs_fly.util.EventUtil;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;

public final class WhenPigsFly extends JavaPlugin {

    private static WhenPigsFly instance;

    @Override
    public void onEnable() {
        instance = this; // Set the instance to the current plugin
        saveDefaultConfig();

        EventUtil.register(new AxolotlThrowListener(this));
        EventUtil.register(new PluginEvents());

        getLogger().info("Plugin enabled.");

        new Heal_Feed();
        new Spawn(this);
        new Menu(this);

        Metrics metrics = new Metrics(this,20190);
        metrics.addCustomChart(new SimplePie("online_mode", () -> {
            return Bukkit.getOnlineMode() ? "Yes" : "No";
        }));
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

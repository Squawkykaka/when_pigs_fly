package com.squawkykaka.when_pigs_fly.util;

import com.squawkykaka.when_pigs_fly.WhenPigsFly;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class EventUtil {
    public static void register(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, WhenPigsFly.getInstance());
    }
}

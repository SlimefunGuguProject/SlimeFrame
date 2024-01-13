package me.voper.slimeframe.core.managers;

import org.bukkit.Bukkit;

import lombok.Getter;

@Getter
public class SupportedPluginManager {

    private static final String GEYSER_SPIGOT = "Geyser-Spigot";

    private final boolean geyser;

    public SupportedPluginManager() {
        this.geyser = Bukkit.getPluginManager().isPluginEnabled(GEYSER_SPIGOT);
    }

}

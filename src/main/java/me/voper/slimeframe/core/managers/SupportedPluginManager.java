package me.voper.slimeframe.core.managers;

import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class SupportedPluginManager {

    private static final String GEYSER_SPIGOT = "Geyser-Spigot";

    private final boolean geyser;

    public SupportedPluginManager() {
        this.geyser = Bukkit.getPluginManager().isPluginEnabled(GEYSER_SPIGOT);
    }

}

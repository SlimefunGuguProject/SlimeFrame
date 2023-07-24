package me.voper.slimeframe.managers;

import lombok.AllArgsConstructor;
import me.voper.slimeframe.SlimeFrame;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.NumberConversions;

import javax.annotation.Nonnull;
import java.util.List;

public final class SettingsManager {

    private final SlimeFrame plugin;
    private final FileConfiguration config;

    public SettingsManager(@Nonnull SlimeFrame plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.config.options().copyDefaults(true);
        this.plugin.saveConfig();
    }

    public String getString(@Nonnull ConfigField field) {
        return config.getString(field.path, String.valueOf(field.defaultValue));
    }

    @Nonnull
    public List<String> getStringList(@Nonnull ConfigField field) {
        return config.getStringList(field.path);
    }

    public boolean getBoolean(@Nonnull ConfigField field) {
        return config.getBoolean(field.path, (Boolean) field.defaultValue);
    }

    public int getInt(@Nonnull ConfigField field) {
        return config.getInt(field.path, NumberConversions.toInt(field.defaultValue));
    }

    @AllArgsConstructor
    public enum ConfigField{

        AUTO_UPDATE("options.auto-update", true),
        RESEARCHES_ENABLED("options.enable-researches", true),
        ENABLE_AE_ITEMS_IN_SOUL_CONTRACTS("options.enable-ae-items-in-soul-contracts", false),
        ORE_TRADE_CHANCE("options.ore-trade-chance", 1),

        LITH_RELIC("relics.lith", 50),
        MESO_RELIC("relics.meso", 750),
        NEO_RELIC("relics.neo", 10000),
        AXI_RELIC("relics.axi", 750),

        SLIMEFRAME_CMD("commands.slimeframe"),
        INVENTORY_CMD("commands.inventory"),
        INVSEE_CMD("commands.invsee"),
        REFINE_CMD("commands.refine"),
        TRACES_CMD("commands.traces"),

        PREFIX("messages.prefix", "&f[&bSlimeFrame&f]"),
        PREFIX_ENABLED("messages.prefix-enabled", false),
        INSUFFICIENT_REACTANTS("messages.insufficient-reactants"),
        FREEZING_ITEM("messages.freezing-item");

        private final String path;
        private final Object defaultValue;

        ConfigField(String path) {
            this.path = path;
            this.defaultValue = null;
        }

    }

}

package me.voper.slimeframe.core.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.util.NumberConversions;

import me.voper.slimeframe.SlimeFrame;

import lombok.AllArgsConstructor;

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

    public Map<EntityType, Integer> getDropChanceMap(@Nonnull ConfigField field) {
        ConfigurationSection configurationSection = config.getConfigurationSection(field.path);
        Map<EntityType, Integer> dropChanceMap = new HashMap<>();

        for (String key : configurationSection.getKeys(false)) {
            EntityType entityType = EntityType.valueOf(key.toUpperCase());
            int percentage = configurationSection.getInt(key);
            if (percentage > 100 || percentage < 0) {
                String warning = "The value for " + field.path + "." + entityType.name() + " must be in the range 0 - 100. The default values and entities will be used until this error is fixed.";
                plugin.getLogger().log(Level.WARNING, warning);
                return (Map<EntityType, Integer>) field.defaultValue;
            }
            dropChanceMap.put(entityType, percentage);
        }

        return dropChanceMap;
    }

    @AllArgsConstructor
    public enum ConfigField {

        AUTO_UPDATE("options.auto-update", true),
        AUTO_UPDATE_MAJOR("options.auto-update-major", false),
        RESEARCHES_ENABLED("options.enable-researches", true),
        ENABLE_AE_ITEMS_IN_SOUL_CONTRACTS("options.enable-ae-items-in-soul-contracts", false),
        ORE_TRADE_CHANCE("options.ore-trade-chance", 1),

        LITH_RELIC("relics.lith", 50),
        MESO_RELIC("relics.meso", 750),
        NEO_RELIC("relics.neo", 10000),
        AXI_RELIC("relics.axi", 750),

        MORPHICS_CHANCE("drop-chance.morphics", Map.of(
                EntityType.WITHER, 5,
                EntityType.WARDEN, 10,
                EntityType.ELDER_GUARDIAN, 20)),
        NEURAL_SENSORS("drop-chance.neural-sensors", Map.of(EntityType.WARDEN, 5)),
        NEURODES_CHANCE("drop-chance.neurodes", Map.of(
                EntityType.ZOMBIE, 15,
                EntityType.ZOMBIE_VILLAGER, 15,
                EntityType.HUSK, 15,
                EntityType.DROWNED, 15)),
        OROKIN_CELL_CHANCE("drop-chance.orokin-cell", Map.of(
                EntityType.WITHER, 1,
                EntityType.WARDEN, 1,
                EntityType.ENDER_DRAGON, 10)),
        REACTANTS_CHANCE("drop-chance.reactants", 25),

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

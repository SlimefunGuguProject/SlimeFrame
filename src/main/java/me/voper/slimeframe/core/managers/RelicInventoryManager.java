package me.voper.slimeframe.core.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.implementation.items.relics.RelicInventory;

public class RelicInventoryManager {

    private static final String CONFIG_PREFIX = "inventories.";
    private static final String FILE_NAME = "relics-inventories.yml";

    private final SlimeFrame plugin;
    private final Map<UUID, RelicInventory> relicInventories = new HashMap<>();
    private File file;
    private FileConfiguration configFile;

    public RelicInventoryManager(SlimeFrame plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        this.file = new File(plugin.getDataFolder(), FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.configFile = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        for (Map.Entry<UUID, RelicInventory> entry : relicInventories.entrySet()) {
            String path = CONFIG_PREFIX + entry.getKey() + ".";
            Inventory inventory = entry.getValue().getInventory();
            for (int i = 0; i < RelicInventory.SIZE; i++) {
                configFile.set(path + i, inventory.getItem(i));
            }
        }

        try {
            configFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RelicInventory getRelicInventory(@Nonnull Player player) {
        if (relicInventories.get(player.getUniqueId()) == null) {
            loadInventory(player);
        }
        return relicInventories.get(player.getUniqueId());
    }

    public void loadInventory(@Nonnull Player player) {
        if (configFile.contains(CONFIG_PREFIX + player.getUniqueId())) {
            RelicInventory relicInventory = new RelicInventory(player);
            Inventory inventory = relicInventory.getInventory();
            for (int i = 0; i < RelicInventory.SIZE; i++) {
                inventory.setItem(i, configFile.getItemStack(CONFIG_PREFIX + player.getUniqueId() + "." + i));
            }
            relicInventories.put(player.getUniqueId(), relicInventory);
        } else {
            relicInventories.put(player.getUniqueId(), new RelicInventory(player));
        }
    }

}

package me.voper.slimeframe.implementation.listeners;

import com.jeff_media.customblockdata.CustomBlockData;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.implementation.items.resources.SpecialOre;
import me.voper.slimeframe.utils.Keys;

public final class BlockPlaceListener implements Listener {

    private final SlimeFrame plugin;

    public BlockPlaceListener(SlimeFrame plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        CustomBlockData.registerListener(plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block b = e.getBlockPlaced();
        if (!SpecialOre.SOURCE_ORE_MAP.containsKey(b.getType())) return;

        PersistentDataContainer pdc = new CustomBlockData(b, plugin);
        pdc.set(Keys.PLACED_BLOCK, PersistentDataType.BYTE, (byte) 1);

    }

}

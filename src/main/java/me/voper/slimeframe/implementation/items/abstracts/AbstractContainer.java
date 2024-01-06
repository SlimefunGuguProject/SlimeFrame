package me.voper.slimeframe.implementation.items.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotHopperable;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/*
This class is based on DynaTech's AbstractContainer class
Credits to ProfElements
https://github.com/ProfElements/DynaTech/blob/990c942e4302869fe80cce33c49fa2e458a3af99/src/main/java/me/profelements/dynatech/items/abstracts/AbstractContainer.java
*/
public abstract class AbstractContainer extends SlimefunItem implements NotHopperable {

    public AbstractContainer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        createMenuPreset(item.getItemId(), getItemName());
    }

    @ParametersAreNonnullByDefault
    protected final void createMenuPreset(String id, String title) {
        new BlockMenuPreset(id, title) {
            @Override
            public void init() {
                createMenu(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                onNewInstance(menu, b);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return (player.hasPermission("slimefun.inventory.bypass") || Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK)) && openConditions(player);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                return slotsAccessedByItemTransport(itemTransportFlow);
            }
        };
    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            @ParametersAreNonnullByDefault
            public void onPlayerBreak(BlockBreakEvent blockBreakEvent, ItemStack itemStack, List<ItemStack> list) {
                BlockMenu menu = BlockStorage.getInventory(blockBreakEvent.getBlock());
                if (menu != null) {
                    onBreak(blockBreakEvent, menu, blockBreakEvent.getBlock().getLocation());
                }
            }
        });

        addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                onPlace(blockPlaceEvent, blockPlaceEvent.getBlockPlaced());
            }
        });
    }

    protected abstract void createMenu(@Nonnull BlockMenuPreset preset);

    @Nonnull
    public abstract int[] getInputSlots();

    @Nonnull
    public abstract int[] getOutputSlots();

    public abstract int getStatusSlot();

    @ParametersAreNonnullByDefault
    protected void onNewInstance(BlockMenu menu, Block b) {}

    protected int[] slotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        switch (itemTransportFlow) {
            case INSERT -> { return getInputSlots(); }
            case WITHDRAW -> { return getOutputSlots(); }
            default -> {}
        }
        return new int[0];
    }

    protected void onBreak(BlockBreakEvent e, BlockMenu menu, Location l) {}

    protected void onPlace(BlockPlaceEvent e, Block b) {}

    protected boolean openConditions(@Nonnull Player player) { return true; }

}

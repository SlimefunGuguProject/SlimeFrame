package me.voper.slimeframe.slimefun.items.machines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.voper.slimeframe.slimefun.items.abstracts.AbstractSelectorMachine;
import me.voper.slimeframe.utils.MachineUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParametersAreNonnullByDefault
public class WoolGenerator extends AbstractSelectorMachine implements RecipeDisplayItem {

    private static final String BLOCK_KEY = "wool_selector";
    private static final List<ItemStack> WOOLS = List.of(
            MachineUtils.SELECTOR,
            new ItemStack(Material.WHITE_WOOL),
            new ItemStack(Material.ORANGE_WOOL),
            new ItemStack(Material.MAGENTA_WOOL),
            new ItemStack(Material.LIGHT_BLUE_WOOL),
            new ItemStack(Material.YELLOW_WOOL),
            new ItemStack(Material.LIME_WOOL),
            new ItemStack(Material.PINK_WOOL),
            new ItemStack(Material.GRAY_WOOL),
            new ItemStack(Material.LIGHT_GRAY_WOOL),
            new ItemStack(Material.CYAN_WOOL),
            new ItemStack(Material.PURPLE_WOOL),
            new ItemStack(Material.BLUE_WOOL),
            new ItemStack(Material.BROWN_WOOL),
            new ItemStack(Material.GREEN_WOOL),
            new ItemStack(Material.RED_WOOL),
            new ItemStack(Material.BLACK_WOOL)
    );

    public WoolGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.outputAmount = 4;
    }

    @Override
    public void postRegister() {
        registerRecipe(6, new ItemStack[]{new ItemStack(Material.STRING, 2)}, new ItemStack[]{new ItemStack(Material.WHITE_WOOL, 4)});
    }

    @Override
    protected ItemStack getProgressBar() {
        return new ItemStack(Material.LOOM);
    }

    @Override
    protected void onNewInstance(BlockMenu menu, Block b) {
        super.onNewInstance(menu, b);
        if (!BlockStorage.hasBlockInfo(b) || BlockStorage.getLocationInfo(b.getLocation(), BLOCK_KEY) == null) {
            BlockStorage.addBlockInfo(b, BLOCK_KEY, String.valueOf(0));
        } else {
            String woolSelector = BlockStorage.getLocationInfo(b.getLocation(), BLOCK_KEY);
            if (woolSelector == null) { return; }
            menu.replaceExistingItem(getSelectorSlot(), WOOLS.get(Integer.parseInt(woolSelector)));
        }
    }

    @Override
    protected boolean checkCraftConditions(BlockMenu menu) {
        return menu.getItemInSlot(getSelectorSlot()).getType().name().endsWith("_WOOL");
    }

    @Override
    protected void onCraftConditionsNotMet(BlockMenu menu) {
        menu.replaceExistingItem(getStatusSlot(), new CustomItemStack(Material.BARRIER, ChatColor.RED + "Select a wool to generate!"));
    }

    @Override
    @SuppressWarnings("deprecation")
    protected ChestMenu.MenuClickHandler onSelectorClick(BlockMenu menu) {
        return (player, i, itemStack, clickAction) -> {
            int woolSelector = Integer.parseInt(BlockStorage.getLocationInfo(menu.getLocation(), BLOCK_KEY));
            if (++woolSelector >= WOOLS.size()) {
                BlockStorage.addBlockInfo(menu.getLocation(), BLOCK_KEY, "0");
                woolSelector = 0;
            } else {
                BlockStorage.addBlockInfo(menu.getLocation(), BLOCK_KEY, String.valueOf(woolSelector));
            }
            menu.replaceExistingItem(getSelectorSlot(), WOOLS.get(woolSelector));
            return false;
        };
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> recipes = new ArrayList<>();
        WOOLS.subList(1, WOOLS.size()).forEach(itemStack -> {
            recipes.add(new ItemStack(Material.STRING, 2));
            recipes.add(new ItemStack(itemStack.getType(), 4));
        });
        return recipes;
    }
}

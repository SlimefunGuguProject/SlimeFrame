package me.voper.slimeframe.implementation.items.machines;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.voper.slimeframe.implementation.groups.Groups;
import me.voper.slimeframe.implementation.items.abstracts.AbstractSelectorMachine;
import me.voper.slimeframe.implementation.items.multiblocks.Foundry;
import me.voper.slimeframe.utils.MachineUtils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import net.md_5.bungee.api.ChatColor;

public class TerracottaGenerator extends AbstractSelectorMachine implements RecipeDisplayItem {

    private static final String BLOCK_KEY = "terracota_selector";
    private static final List<ItemStack> TERRACOTTA_LIST = List.of(
            MachineUtils.SELECTOR,
            new ItemStack(Material.TERRACOTTA),
            new ItemStack(Material.WHITE_TERRACOTTA),
            new ItemStack(Material.ORANGE_TERRACOTTA),
            new ItemStack(Material.MAGENTA_TERRACOTTA),
            new ItemStack(Material.LIGHT_BLUE_TERRACOTTA),
            new ItemStack(Material.YELLOW_TERRACOTTA),
            new ItemStack(Material.LIME_TERRACOTTA),
            new ItemStack(Material.PINK_TERRACOTTA),
            new ItemStack(Material.GRAY_TERRACOTTA),
            new ItemStack(Material.LIGHT_GRAY_TERRACOTTA),
            new ItemStack(Material.CYAN_TERRACOTTA),
            new ItemStack(Material.PURPLE_TERRACOTTA),
            new ItemStack(Material.BLUE_TERRACOTTA),
            new ItemStack(Material.BROWN_TERRACOTTA),
            new ItemStack(Material.GREEN_TERRACOTTA),
            new ItemStack(Material.RED_TERRACOTTA),
            new ItemStack(Material.BLACK_TERRACOTTA)
    );

    public TerracottaGenerator(SlimefunItemStack item, ItemStack[] recipe) {
        super(Groups.MACHINES, item, Foundry.RECIPE_TYPE, recipe);
        this.outputAmount = 6;
    }

    @Override
    protected ItemStack getProgressBar() {
        return new ItemStack(Material.WHITE_TERRACOTTA);
    }

    @Override
    public void postRegister() {
        registerRecipe(6, new ItemStack[]{new ItemStack(Material.DIRT, 2), new ItemStack(Material.CLAY, 2)}, new ItemStack[]{new ItemStack(Material.OBSERVER)});
    }

    @Nonnull
    @Override
    public List<ItemStack> selectionList() {
        return TERRACOTTA_LIST;
    }

    @Nonnull
    @Override
    public String getBlockKey() {
        return BLOCK_KEY;
    }

    @Override
    protected boolean checkCraftConditions(BlockMenu menu) {
        return menu.getItemInSlot(getSelectorSlot()).getType().name().endsWith("_TERRACOTTA");
    }

    @Override
    protected void onCraftConditionsNotMet(BlockMenu menu) {
        MachineUtils.replaceExistingItemViewer(menu, getStatusSlot(), new CustomItemStack(Material.BARRIER, ChatColor.RED + "Select a terracotta to generate!"));
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> displayRecipes = new ArrayList<>();
        for (ItemStack itemStack : TERRACOTTA_LIST) {
            if (!itemStack.getType().name().endsWith("TERRACOTTA")) continue;
            displayRecipes.add(recipes.get(0).getInput()[0]);
            ItemStack clone = itemStack.clone();
            clone.setAmount(outputAmount * production);
            displayRecipes.add(clone);
            displayRecipes.add(recipes.get(0).getInput()[1]);
            displayRecipes.add(null);
        }
        return displayRecipes;
    }
}

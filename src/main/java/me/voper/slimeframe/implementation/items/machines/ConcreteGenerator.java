package me.voper.slimeframe.implementation.items.machines;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.voper.slimeframe.implementation.items.abstracts.AbstractSelectorMachine;
import me.voper.slimeframe.utils.MachineUtils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import net.md_5.bungee.api.ChatColor;

@ParametersAreNonnullByDefault
public class ConcreteGenerator extends AbstractSelectorMachine implements RecipeDisplayItem {

    private static final String BLOCK_KEY = "concrete_selector";
    private static final List<ItemStack> CONCRETE_LIST = List.of(
            MachineUtils.SELECTOR,
            new ItemStack(Material.WHITE_CONCRETE),
            new ItemStack(Material.ORANGE_CONCRETE),
            new ItemStack(Material.MAGENTA_CONCRETE),
            new ItemStack(Material.LIGHT_BLUE_CONCRETE),
            new ItemStack(Material.YELLOW_CONCRETE),
            new ItemStack(Material.LIME_CONCRETE),
            new ItemStack(Material.PINK_CONCRETE),
            new ItemStack(Material.GRAY_CONCRETE),
            new ItemStack(Material.LIGHT_GRAY_CONCRETE),
            new ItemStack(Material.CYAN_CONCRETE),
            new ItemStack(Material.PURPLE_CONCRETE),
            new ItemStack(Material.BLUE_CONCRETE),
            new ItemStack(Material.BROWN_CONCRETE),
            new ItemStack(Material.GREEN_CONCRETE),
            new ItemStack(Material.RED_CONCRETE),
            new ItemStack(Material.BLACK_CONCRETE)
    );

    public ConcreteGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.outputAmount = 6;
    }

    @Override
    protected ItemStack getProgressBar() {
        return new ItemStack(Material.WATER_BUCKET);
    }

    @Override
    public void postRegister() {
        registerRecipe(6, new ItemStack[]{new ItemStack(Material.SAND, 2), new ItemStack(Material.GRAVEL, 2)}, new ItemStack[]{new ItemStack(Material.OBSERVER)});
    }

    @Nonnull
    @Override
    public List<ItemStack> selectionList() {
        return CONCRETE_LIST;
    }

    @Nonnull
    @Override
    public String getBlockKey() {
        return BLOCK_KEY;
    }

    @Override
    protected boolean checkCraftConditions(BlockMenu menu) {
        return menu.getItemInSlot(getSelectorSlot()).getType().name().endsWith("_CONCRETE");
    }

    @Override
    protected void onCraftConditionsNotMet(BlockMenu menu) {
        MachineUtils.replaceExistingItemViewer(menu, getStatusSlot(), new CustomItemStack(Material.BARRIER,
            ChatColor.RED + "选择需要生产的混凝土！"));
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> displayRecipes = new ArrayList<>();
        for (ItemStack itemStack : CONCRETE_LIST) {
            if (!itemStack.getType().name().endsWith("_CONCRETE")) continue;
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

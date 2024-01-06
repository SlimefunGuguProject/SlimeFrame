package me.voper.slimeframe.implementation.items.machines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.voper.slimeframe.implementation.SFrameStacks;
import me.voper.slimeframe.implementation.items.abstracts.AbstractProcessorMachine;
import me.voper.slimeframe.utils.Keys;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Recycler extends AbstractProcessorMachine implements RecipeDisplayItem {

    public static final RecipeType RECIPE_TYPE = new RecipeType(Keys.createKey("recycler_recipe"), SFrameStacks.RECYCLER);

    private static final int TIME = 5;

    private static final List<ItemStack> MACHINES_AND_GENERATORS = Slimefun.getRegistry().getAllSlimefunItems().stream()
            .filter(sf -> sf instanceof EnergyNetComponent)
            .filter(sf -> ((EnergyNetComponent) sf).getEnergyComponentType() != EnergyNetComponentType.CONNECTOR &&
                    ((EnergyNetComponent) sf).getEnergyComponentType() != EnergyNetComponentType.NONE)
            .map(SlimefunItem::getItem)
            .toList();

    public Recycler(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void postRegister() {
        registerRecipe(TIME, new SlimefunItemStack(SlimefunItems.ADVANCED_CIRCUIT_BOARD, 8), SFrameStacks.GALLIUM.clone());
        MACHINES_AND_GENERATORS.forEach(item -> registerRecipe(TIME, item, new SlimefunItemStack(SFrameStacks.SALVAGE, 8)));
    }

    @Override
    protected ItemStack getProgressBar() {
        return new ItemStack(Material.FIRE_CHARGE);
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> recipes = new ArrayList<>();

        recipes.add(new SlimefunItemStack(SlimefunItems.ADVANCED_CIRCUIT_BOARD, 8));
        recipes.add(SFrameStacks.GALLIUM.clone());

        MACHINES_AND_GENERATORS.forEach(item -> {
            recipes.add(item);
            recipes.add(new SlimefunItemStack(SFrameStacks.SALVAGE, 8));
        });

        return recipes;
    }
}

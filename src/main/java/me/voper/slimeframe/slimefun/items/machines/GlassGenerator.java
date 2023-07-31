package me.voper.slimeframe.slimefun.items.machines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.voper.slimeframe.slimefun.items.abstracts.AbstractSelectorMachine;
import me.voper.slimeframe.utils.MachineUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class GlassGenerator extends AbstractSelectorMachine implements RecipeDisplayItem {

    private static final String BLOCK_KEY = "glass_selector";
    private static final List<ItemStack> GLASSES = new ArrayList<>();

    static {
        GLASSES.add(MachineUtils.SELECTOR);
        GLASSES.addAll(SlimefunTag.GLASS_BLOCKS.stream()
                .sorted(Comparator.comparing(Enum::name))
                .filter(m -> !m.name().contains("TINTED"))
                .map(ItemStack::new)
                .toList());
    }

    public GlassGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.outputAmount = 2;
    }

    @Override
    public void postRegister() {
        registerRecipe(5, new ItemStack(Material.SAND), new ItemStack(Material.GLASS));
    }

    @Override
    protected ItemStack getProgressBar() {
        return new ItemStack(Material.FURNACE);
    }

    @Nonnull
    @Override
    public List<ItemStack> selectionList() {
        return GLASSES;
    }

    @Nonnull
    @Override
    public String getBlockKey() {
        return BLOCK_KEY;
    }

    @Override
    protected boolean checkCraftConditions(BlockMenu menu) {
        return menu.getItemInSlot(getSelectorSlot()).getType() != Material.FIREWORK_STAR;
    }

    @Override
    protected void onCraftConditionsNotMet(BlockMenu menu) {
        MachineUtils.replaceExistingItemViewer(menu, getStatusSlot(), new CustomItemStack(Material.BARRIER, ChatColor.RED + "Select a glass to generate!"));
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        return GLASSES.subList(1, GLASSES.size()).stream()
                .flatMap(item -> Stream.of(new ItemStack(Material.SAND), new ItemStack(item.getType(), outputAmount * production)))
                .toList();
    }
}

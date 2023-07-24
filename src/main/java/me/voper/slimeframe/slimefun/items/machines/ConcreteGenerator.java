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

// TODO improve findNextRecipe method and implement getRecipeSectionLabel
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
    }

    @Override
    protected ItemStack getProgressBar() {
        return new ItemStack(Material.WATER_BUCKET);
    }

    @Override
    public void postRegister() {
        registerRecipe(6, new ItemStack[]{new ItemStack(Material.SAND, 2), new ItemStack(Material.GRAVEL, 2)}, new ItemStack[]{new ItemStack(Material.OBSERVER)});
    }

    @Override
    @SuppressWarnings("deprecation")
    protected ChestMenu.MenuClickHandler onSelectorClick(@Nonnull BlockMenu menu) {
        return (player, slot, itemstack, clickAction) -> {
            int concreteSelector = Integer.parseInt(BlockStorage.getLocationInfo(menu.getLocation(), BLOCK_KEY));
            if (++concreteSelector >= CONCRETE_LIST.size()) {
                BlockStorage.addBlockInfo(menu.getBlock(), BLOCK_KEY, String.valueOf(0));
                concreteSelector = 0;
            } else {
                BlockStorage.addBlockInfo(menu.getBlock(), BLOCK_KEY, String.valueOf(concreteSelector));
            }
            menu.replaceExistingItem(getSelectorSlot(), CONCRETE_LIST.get(concreteSelector));
            return false;
        };
    }

    @Override
    protected void onNewInstance(BlockMenu menu, Block b) {
        super.onNewInstance(menu, b);
        if (!BlockStorage.hasBlockInfo(b) || BlockStorage.getLocationInfo(b.getLocation(), BLOCK_KEY) == null) {
            BlockStorage.addBlockInfo(b, BLOCK_KEY, String.valueOf(0));
        } else {
            String concreteSelector = BlockStorage.getLocationInfo(b.getLocation(), BLOCK_KEY);
            if (concreteSelector == null) { return; }
            menu.replaceExistingItem(getSelectorSlot(), CONCRETE_LIST.get(Integer.parseInt(concreteSelector)));
        }
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

    @Override
    protected MachineRecipe findNextRecipe(BlockMenu menu) {
        ItemStack[] inputs = recipes.get(0).getInput();

        // Creates a slot-ItemStack map according to the input slots
        Map<Integer, ItemStack> inv = new HashMap<>();
        for (int slot : getInputSlots()) {
            ItemStack item = menu.getItemInSlot(slot);

            if (item != null) {
                inv.put(slot, ItemStackWrapper.wrap(item));
            }
        }

        // If the output slots are full, return null
        int maxedSlots = 0;
        for (int slot : getOutputSlots()) {
            ItemStack item = menu.getItemInSlot(slot);
            if (item != null && item.getAmount() == item.getMaxStackSize()) {
                maxedSlots += 1;
            }
        }
        if (maxedSlots == getOutputSlots().length) { return null; }

        Map<Integer, Integer> found = new HashMap<>();
        for (ItemStack input: inputs) {
            for (int slot: getInputSlots()) {
                if (SlimefunUtils.isItemSimilar(inv.get(slot), input, true)) {
                    found.put(slot, input.getAmount());
                    break;
                }
            }
        }

        if (found.size() == inputs.length) {
            ItemStack itemStack = new ItemStack(menu.getItemInSlot(getSelectorSlot()));
            itemStack.setAmount(6);
            MachineRecipe machineRecipe = new MachineRecipe(6, inputs, new ItemStack[]{itemStack});
            if (!InvUtils.fitAll(menu.toInventory(), machineRecipe.getOutput(), getOutputSlots())) {
                MachineUtils.replaceExistingItemViewer(menu, getStatusSlot(), MachineUtils.NO_SPACE);
                return null;
            }

            for (Map.Entry<Integer, Integer> entry : found.entrySet()) {
                menu.consumeItem(entry.getKey(), entry.getValue());
            }

            return machineRecipe;
        } else {
            found.clear();
        }
        return null;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> displayRecipes = new ArrayList<>();
        for (ItemStack itemStack: CONCRETE_LIST) {
            if (!itemStack.getType().name().endsWith("_CONCRETE")) continue;
            displayRecipes.add(recipes.get(0).getInput()[0]);
            itemStack.setAmount(6);
            displayRecipes.add(itemStack);
            displayRecipes.add(recipes.get(0).getInput()[1]);
            displayRecipes.add(null);
        }
        return displayRecipes;
    }

}

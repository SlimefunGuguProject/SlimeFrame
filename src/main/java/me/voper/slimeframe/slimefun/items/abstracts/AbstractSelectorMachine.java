package me.voper.slimeframe.slimefun.items.abstracts;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.voper.slimeframe.slimefun.items.machines.MachineDesign;
import me.voper.slimeframe.utils.MachineUtils;
import me.voper.slimeframe.utils.Utils;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@ParametersAreNonnullByDefault
public abstract class AbstractSelectorMachine extends AbstractProcessorMachine {

    protected int production = 1;
    protected int outputAmount = 1;

    public AbstractSelectorMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        Validate.isTrue(outputAmount > 0 && outputAmount <= 64, "Ouput amount must be between 0 and 64");
        Validate.isTrue(production > 0, "Production must be greater than zero");
        Validate.isTrue(production * outputAmount <= 64, "Total production must not exceed 64");
        super.register(addon);
    }

    @Override
    protected void createMenu(BlockMenuPreset preset) {
        super.createMenu(preset);
        preset.addItem(getSelectorSlot(), MachineUtils.SELECTOR, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    protected void onNewInstance(BlockMenu menu, Block b) {
        super.onNewInstance(menu, b);
        menu.addMenuClickHandler(getSelectorSlot(), onSelectorClick(menu));
    }

    @Override
    protected MachineRecipe findNextRecipe(BlockMenu menu) {
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

        // For each recipe, we must check if the input actually matches the recipe input
        Map<Integer, Integer> found = new HashMap<>();
        for (MachineRecipe recipe : recipes) {
            for (ItemStack input : recipe.getInput()) {
                for (int slot : getInputSlots()) {
                    // It also checks the amount of both items
                    if (SlimefunUtils.isItemSimilar(inv.get(slot), input, true)) {
                        found.put(slot, input.getAmount());
                        break;
                    }
                }
            }

            if (found.size() == recipe.getInput().length) {

                ItemStack output = menu.getItemInSlot(getSelectorSlot()).clone();
                output.setAmount(production * outputAmount);
                MachineRecipe machineRecipe = new MachineRecipe(recipe.getTicks() / 2, recipe.getInput(), new ItemStack[]{output});

                if(!InvUtils.fitAll(menu.toInventory(), machineRecipe.getOutput(), getOutputSlots())) {
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
        }

        return null;
    }

    @SuppressWarnings("deprecation")
    protected abstract ChestMenu.MenuClickHandler onSelectorClick(BlockMenu menu);

    public int getSelectorSlot() {
        return MachineDesign.SELECTOR_MACHINE.selectorSlot();
    }

}

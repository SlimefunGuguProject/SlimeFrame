package me.voper.slimeframe.slimefun.items.machines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.voper.slimeframe.slimefun.SFrameStacks;
import me.voper.slimeframe.slimefun.items.abstracts.AbstractMachine;
import me.voper.slimeframe.utils.Keys;
import me.voper.slimeframe.utils.MachineUtils;
import me.voper.slimeframe.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Accessors(chain = true)
@ParametersAreNonnullByDefault
public class CryoticExtractor extends AbstractMachine {

    public static final RecipeType RECIPE_TYPE = new RecipeType(Keys.createKey("cryotic_recipe"), SFrameStacks.CRYOTIC_EXTRACTOR);

    private static final int TIME = Utils.secondsToSfTicks(5 * 60);
    private static final Map<BlockPosition, Integer> PROGRESS_MAP = new HashMap<>();
    public static final Map<Biome, Integer> VALID_BIOMES = new EnumMap<>(Map.of(
            Biome.SNOWY_TAIGA, 2,
            Biome.SNOWY_PLAINS, 1,
            Biome.SNOWY_SLOPES, 1,
            Biome.SNOWY_BEACH, 1,
            Biome.ICE_SPIKES, 3
    ));

    @Setter
    private int processingSpeed = 1;

    public CryoticExtractor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected boolean process(BlockMenu menu, Block b) {
        final BlockPosition blockPosition = new BlockPosition(b);
        int progress = PROGRESS_MAP.getOrDefault(blockPosition, 0);
        if (progress >= TIME) {
            int cryoticAmount = VALID_BIOMES.get(b.getBiome());
            ItemStack cryotic = new SlimefunItemStack(SFrameStacks.CRYOTIC, cryoticAmount);

            if (menu.fits(cryotic, getOutputSlots())) {
                menu.pushItem(cryotic, getOutputSlots());
                PROGRESS_MAP.put(blockPosition, 0);
                updateProgress(menu, 0);
                return true;
            }

            MachineUtils.replaceExistingItemViewer(menu, getStatusSlot(), MachineUtils.NO_SPACE);
            return false;
        }

        progress += processingSpeed;
        PROGRESS_MAP.put(blockPosition, progress);
        updateProgress(menu, progress);
        return true;
    }

    @Override
    protected boolean checkCraftConditions(BlockMenu menu) {
        return VALID_BIOMES.containsKey(menu.getBlock().getBiome());
    }

    @Override
    protected void onCraftConditionsNotMet(BlockMenu menu) {
        MachineUtils.replaceExistingItemViewer(menu, getStatusSlot(),
                new CustomItemStack(Material.BARRIER, ChatColor.RED + "该生物群系没有永冻晶体！",
                        ChatColor.WHITE + "有效的生物群系：",
                        ChatColor.BLUE + "积雪针叶林",
                        ChatColor.BLUE + "雪原",
                        ChatColor.BLUE + "积雪山坡",
                        ChatColor.BLUE + "积雪沙滩",
                        ChatColor.BLUE + "冰刺之地"
                )
        );
    }

    @Override
    protected void createMenu(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(new int[]{0, 1, 2, 3, 5, 6, 7, 8});
        preset.drawBackground(ChestMenuUtils.getOutputSlotTexture(), new int[]{9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53});
        preset.addItem(getStatusSlot(), MachineUtils.STATUS, ChestMenuUtils.getEmptyClickHandler());
    }

    @Nonnull
    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public int[] getOutputSlots() {
        return new int[]{19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
    }

    @Override
    public int getStatusSlot() {
        return 4;
    }

    private void updateProgress(BlockMenu menu, int progress) {
        ChestMenuUtils.updateProgressbar(menu, getStatusSlot(), TIME - progress, TIME,
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "提取中..."));
    }

}

package me.voper.slimeframe.slimefun.items.tools;

import com.cryptomorin.xseries.XMaterial;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.LimitedUseItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.voper.slimeframe.utils.Keys;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OrokinWand extends LimitedUseItem implements RecipeDisplayItem {

    private static final NamespacedKey usageKey = Keys.createKey("orokin_wand_usage");
    public static final List<Material> TARGET_MATERIALS = new ArrayList<>();

    static {
        TARGET_MATERIALS.add(Material.REINFORCED_DEEPSLATE);
        TARGET_MATERIALS.add(Material.BUDDING_AMETHYST);
        TARGET_MATERIALS.add(Material.FARMLAND);
        TARGET_MATERIALS.add(Material.CAKE);
        TARGET_MATERIALS.add(Material.INFESTED_COBBLESTONE);
        TARGET_MATERIALS.add(Material.INFESTED_DEEPSLATE);
        TARGET_MATERIALS.add(Material.INFESTED_STONE);
        TARGET_MATERIALS.add(Material.INFESTED_MOSSY_STONE_BRICKS);
        TARGET_MATERIALS.add(Material.INFESTED_CHISELED_STONE_BRICKS);
        TARGET_MATERIALS.add(Material.INFESTED_CRACKED_STONE_BRICKS);
        TARGET_MATERIALS.add(Material.INFESTED_STONE_BRICKS);
        
        if (XMaterial.supports(20)) {
            TARGET_MATERIALS.add(XMaterial.SUSPICIOUS_GRAVEL.parseMaterial());
            TARGET_MATERIALS.add(XMaterial.SUSPICIOUS_SAND.parseMaterial());
        }
    }

    public OrokinWand(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        setUseableInWorkbench(false);
    }

    @Nonnull
    @Override
    protected NamespacedKey getStorageKey() {
        return usageKey;
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            if (e.getClickedBlock().isEmpty()) return;

            Block block = e.getClickedBlock().get();
            if (!Slimefun.getProtectionManager().hasPermission(e.getPlayer(), block, Interaction.BREAK_BLOCK)) return;

            if (TARGET_MATERIALS.contains(block.getType())) {
                World world = block.getLocation().getWorld();
                if (world == null) return;
                world.dropItemNaturally(block.getLocation(), new ItemStack(block.getType()));
                block.setType(Material.AIR);
                damageItem(e.getPlayer(), e.getItem());
            }

        };
    }


    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        return TARGET_MATERIALS.stream()
                .map(ItemStack::new)
                .flatMap(item -> Stream.of(null, item))
                .toList();
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "可获取的方块";
    }
}
